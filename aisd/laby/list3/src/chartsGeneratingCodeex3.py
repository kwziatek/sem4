import matplotlib.pyplot as plt
import numpy as np

# Słownik do przechowywania zgrupowanych danych
data = {}

# Wczytywanie i przetwarzanie danych
with open('outputex3.txt', 'r') as f:
    for line in f:
        # Pomijanie pustych linii
        if not line.strip():
            continue
            
        # Podział na część x i y
        x_part, y_part = line.split(':', 1)
        x = int(x_part.strip())
        y1, y2 = map(float, y_part.strip().split())
        
        # Grupowanie wartości
        if x not in data:
            data[x] = {'y1': [], 'y2': []}
        data[x]['y1'].append(y1)
        data[x]['y2'].append(y2)

# Obliczanie średnich i przygotowanie danych do wykresu
x_values = sorted(data.keys())
y1_avg = [sum(data[x]['y1'])/len(data[x]['y1']) for x in x_values]
y2_avg = [sum(data[x]['y2'])/len(data[x]['y2']) for x in x_values]

# Konfiguracja wykresu
plt.figure(figsize=(12, 6))
plt.plot(x_values, y1_avg, 'b-o', linewidth=2, markersize=8, label='Ilość porównań')
plt.plot(x_values, y2_avg, 'r--s', linewidth=2, markersize=8, label='Ilość zamian')

# Dodawanie opisów i legendy
plt.title('Wykres zamian i porównań w select_testing k = 9', fontsize=14)
plt.xlabel('Rozmiar tabeli', fontsize=12)
plt.ylabel('Średnia ilość operacji', fontsize=12)
plt.grid(True, linestyle='--', alpha=0.7)
plt.legend(fontsize=12)

# Ograniczenie liczby etykiet na osi X
max_ticks = 20  # Maksymalna liczba widocznych etykiet
if len(x_values) > max_ticks:
    indices = np.linspace(0, len(x_values)-1, num=max_ticks, dtype=int)
    selected_xticks = [x_values[i] for i in indices]
else:
    selected_xticks = x_values

plt.xticks(selected_xticks, rotation=45)
plt.tight_layout()

# Zapis wykresu do pliku
plt.savefig('select_testing_chart_k=9', dpi=300)
print("Wykres został zapisany jako 'select_testing_chart_k=9'")