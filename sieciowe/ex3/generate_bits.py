import random

# Ustawienie ziarna dla powtarzalności (opcjonalne, można usunąć)
random.seed()

# Generowanie ciągu 2000 bitów
bits = ''.join(random.choice('01') for _ in range(2000))

# Wypisanie ciągu bitów
print(bits)