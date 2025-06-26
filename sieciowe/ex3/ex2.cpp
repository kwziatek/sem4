#include <iostream>
#include <vector>
#include <random>
#include <math.h>
#include <algorithm>
#include <ctime>
#include <thread>
#include <chrono>
#include <map>
using namespace std;

struct Signal {
    char label;         //symbol stacji
    int dir;            //kierunek (-1 oznacza lewo, 1 prawo)
    int position;       //indeks w medium
    int originStation;  //indeks stacji

    Signal() : label(' '), dir(0), position(0), originStation(-1) {}
};

struct Station {
    int position;           //indeks w medium gdzie podłączona jest stacja
    int backoff;            //delay po wykryciu kolizji czyli ilośc krokow czasowych do odczekania
    int dataSize;           //szerokosc sygnału wysyłanego przez stacje
    bool isTransmitting;    //czy stacja transmituje aktualnie
    int numOfCollisions;    //ilość kolizji
    char label; 
    bool success;           // czy udało się przesłać (sygnał dotarł do obu końców bez kolizji)
    int transmittedData;    // ilość przesłanych danych w aktualnej próbie przesłania sygnału (czyli aktualna szerokość sygnału)

    Station() : position(0), backoff(0), dataSize(0), isTransmitting(false),
                numOfCollisions(0), label(' '), success(false), transmittedData(0) {}
};

const int WIDTH = 55;           //szerokosc sygnalu bedziemy losowac miedzy width/2 a width
const int MEDIUM_SIZE = 60;     //rozmiar medium
const int NUM_STATIONS = 5;     //ilosc stacji
const int MAX_BACKOFF = 200;    //maxymalny delay po wykryciu kolizji

void printMedium(const vector<char>& medium) {
    for (int i = 0; i < medium.size(); ++i) {
        cout<<medium[i];
    }
    cout << endl;
}

int main() {
    mt19937 rng(time(nullptr));

    //generator pozycji dla stacji
    uniform_int_distribution<int> posDist(1, MEDIUM_SIZE-2);
    //generator delaya
    uniform_int_distribution<int> backoffDist(MEDIUM_SIZE, MAX_BACKOFF);
    //generator rozmiaru danych (szerokości sygnału)
    uniform_int_distribution<int> widthDist(WIDTH/2, WIDTH);

    // Labels for stations: A, B, C, D, E...
    vector<char> stationLabels;
    for (int i = 0; i < NUM_STATIONS; ++i)
        stationLabels.push_back('A' + i);

    // Inicjalizacja stacji na losowych pozycjach
    vector<Station*> stations;
    vector<int> usedPos;
    while (stations.size() < NUM_STATIONS) {
        int p = posDist(rng);
        if (find(usedPos.begin(), usedPos.end(), p) == usedPos.end()) {
            Station* s = new Station();
            s->position = p;
            s->backoff = 0;
            s->isTransmitting = false;
            s->dataSize = widthDist(rng);
            s->numOfCollisions = 0;
            s->transmittedData = 0;
            s->label = stationLabels[stations.size()];
            s->success = false;
            stations.push_back(s);
            usedPos.push_back(p);
        }
    }

    vector<Signal*> signals;

    int timeStep = 0;
    bool transmitting = true;
    // Mapujemy (stacja, czy dotarlo) -> krok
    vector<bool> reachedLeft(NUM_STATIONS, false);
    vector<bool> reachedRight(NUM_STATIONS, false);

    //wykonujemy pętle dopóki wszystkie stacje pomyslnie przeslą sygnał
    while (transmitting) {
        //cout << "Time step: " << timeStep << ": ";

        // 1. !Przesuwamy sygnały!
        for (auto sig : signals) {
            sig->position += sig->dir;
        }

        // dla kazdego indeksu w medium przechowujemy vector który zawiera sygnały które znajdują się na danym indeksie
        vector<vector<Signal*>> medium(MEDIUM_SIZE, vector<Signal*>());

        for (int i = 0; i < signals.size(); ++i) {
            //usuwamy sygnaly które juz wyszły poza medium
            if (signals[i]->position < 0 || signals[i]->position >= MEDIUM_SIZE) {
                signals.erase(signals.begin() + i);
                --i;
                continue;
            }
            //dodajemy sygnał do medium
            medium[signals[i]->position].push_back(signals[i]);
        }

        // 2. !Stacje decydują o nadawaniu!
        for (int idx = 0; idx < stations.size(); ++idx) {
            Station* s = stations[idx];
            // Stacja nie próbuje ponownie jesli osiągneła sukces
            if (s->success) continue;
            // sprawdzamy czy nie mamy nalozonego delaya po kolizji
            if (s->backoff <= 0) {
                // czy port jest wolny
                bool isPortFree = medium[s->position].size() == 0;

                // czy skonczylismy nadawac sygnal
                bool isAllDataTransmitted = s->transmittedData >= s->dataSize;

                if (isPortFree && !isAllDataTransmitted) 
                    s->isTransmitting = true;
                else 
                    s->isTransmitting = false;

                // sprawdzamy czy w miejscu stacji nie ma sygnału oraz czy nie wyslalismy jeszcze całego sygnału
                if (s->isTransmitting) {
                    s->isTransmitting = true;
                    // Dodaj nowy sygnał
                    Signal *sigL = new Signal(), *sigR = new Signal(); // lewy i prawy sygnał
                    sigR->label = sigL->label = s->label;
                    sigR->position = sigL->position = s->position;
                    sigR->originStation = sigL->originStation = idx;
                    sigR->dir = 1; // w prawo
                    sigL->dir = -1; // w lewo
                    signals.push_back(sigL);
                    signals.push_back(sigR);
                    s->transmittedData++;
                } else if (s->transmittedData > 0 && (medium[s->position].size() > 1 || 
                        (medium[s->position].size() == 1 && medium[s->position][0]->label == '#'))) {
                    // Trafia do nas sygnał kolizji bo stacja w trakcie nadawanie trafiła na inny sygnał
                    // wiec losujemy backoff i przerywamy transmisje
                    s->isTransmitting = false;
                    s->transmittedData = 0; // reset przesłanych danych
                    s->numOfCollisions++; // zwiększamy liczbę kolizji
                    s->backoff = pow(2, s->numOfCollisions) * backoffDist(rng); // losuj backoff
                    cout << "Stacja " << s->label << " medium zajęte! Losuje backoff: " << s->backoff << endl;
                }
            } else {
                s->backoff--;
            }
        }

        // 3. !Sprawdź kolizje!
        for (int i = 0; i < medium.size(); ++i) {
            auto& cell = medium[i];
            //kolizja wystepuje kiedy w vectorze na danym indeksie jest wiecej niz jeden sygnał
            if (cell.size() > 1) {
                for (Signal* sig: cell) {
                    sig->label = '#'; // oznaczamy kolizję, sygnal juz do konca bedzie oznaczony jako #
                }
            }
        }

        //4. generujemy wizualizacje medium
        vector<char> mediumVisualisation(MEDIUM_SIZE, '.');
        for (int i = 0; i < MEDIUM_SIZE; i++) {
            auto& cell = medium[i];
            if (cell.empty()) {
                for (Station* s : stations) {
                    if (s->position == i && s->isTransmitting) {
                        mediumVisualisation[i] = s->label;
                        break;
                    }
                }
            } else if (cell.size() > 1) {
                mediumVisualisation[i] = '#';
            } else if (cell.size() == 1) {
                mediumVisualisation[i] = cell[0]->label;
            }
        }

        printMedium(mediumVisualisation);

        // 5. Sprawdz czy stacja przeslala do prawego i lewego konca
        for (int sig_idx = 0; sig_idx < signals.size(); ++sig_idx) {
            Signal* sig = signals[sig_idx];
            if (sig->label == '#') continue; // kolizja
            if (sig->position == 0 && sig->dir == -1) {
                auto& prev = medium[sig->position + 1];
                if (prev.size() == 0 || (prev.size() == 1 && prev[0]->label != '#' && prev[0]->label != sig->label)) {
                    if (!reachedLeft[sig->originStation]) {
                        reachedLeft[sig->originStation] = true;
                        //cout << "Sygnał stacji " << sig.label << " dotarł do lewego końca medium!\n";
                    }
                }
                
            } else if (sig->position == MEDIUM_SIZE - 1 && sig->dir == 1) {
                auto& prev = medium[sig->position - 1];
                if (prev.size() == 0 || (prev.size() == 1 && prev[0]->label != '#' && prev[0]->label != sig->label)) {
                    if (!reachedRight[sig->originStation]) {
                        reachedRight[sig->originStation] = true;
                        //cout << "Sygnał stacji " << sig.label << " dotarł do lewego końca medium!\n";
                    }
                }
            }
        }

        for (int i = 0; i < stations.size(); ++i) {
            Station* s = stations[i];
            if (s->success) continue; // stacja już przesłała dane
            if (reachedLeft[i] && reachedRight[i]) {
                s->success = true; // sygnał dotarł do obu końców
                cout << "Stacja " << s->label << " przesłała dane pomyślnie!\n";
            }
        }

        // 6. Sprawdź zakończenie: czy wszystkim stacjom się udało
        transmitting = false;
        for (Station* s : stations)
            if (!s->success) transmitting = true;

        this_thread::sleep_for(chrono::milliseconds(50));
        ++timeStep;
    }

    cout << "\nSymulacja zakończona!\n";
    cout << "Raport dotarcia sygnału do końców medium:\n";
    for (int i = 0; i < stations.size(); ++i) {
        cout << "Stacja " << stations[i]->label << " (pozycja " << stations[i]->position << "): ";
    }
    return 0;
}