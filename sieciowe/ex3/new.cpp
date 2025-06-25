
#include <iostream>
#include <random>
#include <string>

int main() {
    // Inicjalizacja generatora losowego
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> dis(0, 1);

    // Generowanie ciągu 2000 bitów
    std::string bits;
    bits.reserve(2000); // Rezerwacja miejsca dla optymalizacji
    for (int i = 0; i < 2000; ++i) {
        bits += (dis(gen) == 0) ? '0' : '1';
    }

    // Wypisanie ciągu bitów
    std::cout << "Wygenerowany ciąg 2000 bitów:\n" << bits << "\n";

    return 0;
}