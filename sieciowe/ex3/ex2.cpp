#include <iostream>
#include <vector>
#include <random>
#include<math.h>
#include <algorithm>
#include <ctime>
#include <thread>
#include <chrono>
#include <map>
using namespace std;

struct Signal {
    char label;
    int left;
    int right;
    int ttl;
    int width;
    bool active;
    bool success; // czy transmisja nie miała kolizji
    int originStation; // indeks stacji
    int stepStarted;
};

struct Station {
    int position;
    int backoff;
    int dataSize;
    bool hasData;
    bool isTransmitting;
    int numOfCollisions;
    char label;
    bool success; // czy udało się przesłać (sygnał dotarł do obu końców bez kolizji)
    int stepToLeft;  // w którym kroku sygnał dotarł do lewego końca
    int stepToRight; // w którym kroku sygnał dotarł do prawego końca
};

const int WIDTH = 55;
const int MEDIUM_SIZE = 60;
const int NUM_STATIONS = 5;
const int TX_TIME = 4;
const int MAX_BACKOFF = 300;
const int SIGNAL_TTL = MEDIUM_SIZE + WIDTH;

void printMedium(const vector<char>& medium) {
    for (int i = 0; i < medium.size(); ++i) {
        cout << medium[i];
    }
    cout << endl;
}

bool checkIfSignalContainsIndex(Signal& sig, int index) {
    if (sig.right - sig.left <= sig.width * 2) {
        return index >= sig.left && index <= sig.right;
    } else {
        return (index >= sig.left && index <= sig.left + sig.width) || (index <= sig.right && index >= sig.right - sig.width);
    }
}

int main() {
    mt19937 rng(time(nullptr));
    uniform_int_distribution<int> posDist(0, MEDIUM_SIZE-1);
    uniform_int_distribution<int> backoffDist(MEDIUM_SIZE, MAX_BACKOFF);
    uniform_int_distribution<int> widthDist(WIDTH/2, WIDTH);

    // Labels for stations: A, B, C, D, E...
    vector<char> stationLabels;
    for (int i = 0; i < NUM_STATIONS; ++i)
        stationLabels.push_back('A' + i);

    // Initialize stations at random positions
    vector<Station> stations;
    vector<int> usedPos;
    while (stations.size() < NUM_STATIONS) {
        int p = posDist(rng);
        if (find(usedPos.begin(), usedPos.end(), p) == usedPos.end()) {
            Station s;
            s.position = p;
            s.backoff = 0;
            s.hasData = true;
            s.isTransmitting = false;
            s.dataSize = widthDist(rng);
            s.numOfCollisions = 0;
            s.label = stationLabels[stations.size()];
            s.success = false;
            s.stepToLeft = -1;
            s.stepToRight = -1;
            stations.push_back(s);
            usedPos.push_back(p);
        }
    }

    vector<Signal> signals;

    int timeStep = 0;
    bool transmitting = true;
    // Mapujemy (stacja, czy dotarlo) -> krok
    vector<bool> reachedLeft(NUM_STATIONS, false);
    vector<bool> reachedRight(NUM_STATIONS, false);

    while (transmitting) {
        //cout << "Time step: " << timeStep << ": ";

        // 1. Stacje decydują o nadawaniu
        for (int idx = 0; idx < stations.size(); ++idx) {
            auto& s = stations[idx];
            // Stacja nie próbuje ponownie dopóki nie osiągnie sukcesu!
            if (s.success) continue; // <- blokuje ponowne próby po sukcesie
            if (!s.isTransmitting) {
                if (s.backoff > 0) {
                    --s.backoff;
                } else if (s.hasData) {
                    // Sense medium: czy w miejscu stacji nie ma sygnału (czyste)
                    bool busy = false;
                    for (auto& sig : signals)
                        if (sig.active && checkIfSignalContainsIndex(sig, s.position))
                            busy = true;
                    if (!busy) {
                        // Start transmission
                        s.isTransmitting = true;
                        // Dodaj nowy sygnał
                        Signal sig;
                        sig.label = s.label;
                        sig.left = s.position;
                        sig.right = s.position;
                        sig.ttl = SIGNAL_TTL;
                        sig.width = s.dataSize;
                        sig.active = true;
                        sig.success = true;
                        sig.originStation = idx;
                        sig.stepStarted = timeStep;
                        signals.push_back(sig);
                        // hasData zostaje true aż do sukcesu!
                    }
                }
            }
        }

        // 2. Rozszerz sygnały w obie strony
        for (auto& sig : signals) {
            if (!sig.active) continue;
            if (sig.ttl > 0) {
                sig.left--;
                sig.right++;
                sig.ttl--;
            } else {
                sig.active = false;
            }
        }

        // 4. Tworzymy obraz medium (mapa: pozycja -> lista stacji)
        vector<vector<char>> medium_map(MEDIUM_SIZE);
        vector<vector<int>> medium_sig_idx(MEDIUM_SIZE); // indeksy sygnałów
        for (int sig_idx = 0; sig_idx < signals.size(); ++sig_idx) {
            auto& sig = signals[sig_idx];
            if (!sig.active) continue;
            for (int i = sig.left; i <= min(sig.left + sig.width, sig.right); ++i) {
                if (i < 0 || i >= MEDIUM_SIZE) continue;
                medium_map[i].push_back(sig.success? sig.label : tolower(sig.label));
                medium_sig_idx[i].push_back(sig_idx);
            }
            for (int i = sig.right; i >= max(sig.right - sig.width, sig.left); --i) {
                if (i < 0 || i >= MEDIUM_SIZE) continue;
                medium_map[i].push_back(sig.success? sig.label : tolower(sig.label));
                medium_sig_idx[i].push_back(sig_idx);
            }
        }

        // 5. Detekcja kolizji i wizualizacja
        vector<char> medium(MEDIUM_SIZE, '.');
        vector<int> collidedSignals; // sygnały, które mają kolizję
        for (int i = 0; i < MEDIUM_SIZE; ++i) {
            if (medium_map[i].empty()) continue;
            vector<char>& v = medium_map[i];
            sort(v.begin(), v.end());
            v.erase(unique(v.begin(), v.end()), v.end());
            if (v.size() > 1) {
                medium[i] = '#';
                // Kolizja – oznacz sygnały jako nieskuteczne
                for (int idx : medium_sig_idx[i]) {
                    signals[idx].success = false;
                    if (stations[signals[idx].originStation].position != i) continue; // tylko sygnały stacji, które są w tym miejscu
   
                    collidedSignals.push_back(idx);
                }
            } else {
                medium[i] = v[0];
            }
        }
        // Oznacz stacje na medium (małe litery)
        for (const auto& s : stations)
            if (medium[s.position] == '.')
                medium[s.position] = tolower(s.label);

        printMedium(medium);

        // 6. Zapamiętaj krok dotarcia sygnału stacji do końców medium
        for (int sig_idx = 0; sig_idx < signals.size(); ++sig_idx) {
            auto& sig = signals[sig_idx];
            if (!sig.active) continue;
            int st = sig.originStation;
            if (!reachedLeft[st] && sig.left == 0) {
                reachedLeft[st] = true;
                stations[st].stepToLeft = timeStep;
            }
            if (!reachedRight[st] && sig.right == MEDIUM_SIZE - 1) {
                reachedRight[st] = true;
                stations[st].stepToRight = timeStep;
            }
        }

        for (int idx : collidedSignals) {
            if (stations[signals[idx].originStation].isTransmitting) {
                stations[signals[idx].originStation].isTransmitting = false; // stacja przestaje nadawać
                stations[signals[idx].originStation].hasData = true; // mogą próbować ponownie
                int collisions = ++stations[signals[idx].originStation].numOfCollisions;
                stations[signals[idx].originStation].backoff = pow(2, collisions) * backoffDist(rng); // losują backoff
                cout << "Sygnał " << signals[idx].label << " z kolizją! Stacja " << stations[signals[idx].originStation].label
                    << " przerywa nadawanie i losuje backoff: " << stations[signals[idx].originStation].backoff << endl;
            }
        }

        // 8. Sprawdź sukces sygnałów (czy dotarł do obu końców i nie miał kolizji)
        for (auto& sig : signals) {
            if (!sig.active && sig.success) {
                int st = sig.originStation;
                if (stations[st].stepToLeft != -1 && stations[st].stepToRight != -1 && !stations[st].success) {
                    stations[st].success = true;
                    stations[st].hasData = false; // <-- teraz już nie będą próbować
                    cout << "Stacja " << stations[st].label << " przesłała sygnał bez kolizji! (Lewy: " << stations[st].stepToLeft
                        << ", Prawy: " << stations[st].stepToRight << ")\n";
                }
            }
        }

        // 9. Sprawdź zakończenie: czy wszystkim stacjom się udało
        transmitting = false;
        for (const auto& s : stations)
            if (!s.success) transmitting = true;

        this_thread::sleep_for(chrono::milliseconds(50));
        ++timeStep;
    }

    cout << "\nSymulacja zakończona!\n";
    cout << "Raport dotarcia sygnału do końców medium:\n";
    for (int i = 0; i < stations.size(); ++i) {
        cout << "Stacja " << stations[i].label << " (pozycja " << stations[i].position << "): ";
        if (stations[i].success) {
            cout << "sukces, lewy koniec w kroku " << stations[i].stepToLeft
                 << ", prawy koniec w kroku " << stations[i].stepToRight << endl;
        } else {
            cout << "nieudana transmisja\n";
        }
    }
    return 0;
}