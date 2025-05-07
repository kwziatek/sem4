import matplotlib
matplotlib.use('Agg')  # Ustawienie backendu przed importem pyplot
import matplotlib.pyplot as plt

# Słownik do przechowywania danych
data = {}

# Wczytywanie danych z pliku
with open('outputex4.txt', 'r') as file:
    for line in file:
        line = line.strip()
        if not line:
            continue
        
        # Podział linii na część x i y
        parts = line.split(':', 1)
        if len(parts) != 2:
            continue
            
        x_str, y_str = parts
        try:
            x = int(x_str.strip())
            y_values = list(map(float, y_str.strip().split()))
            
            if len(y_values) != 5:
                continue
                
        except ValueError:
            continue
        
        # Inicjalizacja struktury danych
        if x not in data:
            data[x] = {f'y{i+1}': [] for i in range(5)}
        
        # Dodawanie wartości
        for i in range(5):
            data[x][f'y{i+1}'].append(y_values[i])

# Przygotowanie danych
x_values = sorted(data.keys())
colors = ['#1f77b4', '#ff7f0e', '#2ca02c', '#d62728', '#9467bd']
markers = ['o', 's', '^', 'D', 'v']
labels = [
    'pierwszy element',
    'środkowy element',
    'ostatni element',
    'element spoza tablicy',
    'losowy element z tablicy'
]

# Tworzenie wykresu
plt.figure(figsize=(12, 7))

for i in range(5):
    y_avg = [sum(data[x][f'y{i+1}'])/len(data[x][f'y{i+1}']) for x in x_values]
    plt.plot(
        x_values, y_avg,
        color=colors[i],
        linestyle='-',
        marker=markers[i],
        markersize=8,
        linewidth=1.5,
        markerfacecolor='white',
        markeredgewidth=1.5,
        label=labels[i]
    )

# Konfiguracja wykresu
plt.title('Średnie wartości wykonanych porównań dla tablic poszczególnych wielkości', fontsize=14)
plt.xlabel('Wielkość tablicy', fontsize=12)
plt.ylabel('Średnie wartość', fontsize=12)
plt.grid(True, linestyle=':', alpha=0.7)
plt.legend(fontsize=10, ncol=3, loc='upper center', bbox_to_anchor=(0.5, -0.15))

# Automatyczne dopasowanie etykiet na osi X
plt.xticks(
    ticks=[x_values[i] for i in range(0, len(x_values), max(1, len(x_values)//20))],
    rotation=45,
    ha='right'
)

plt.tight_layout()

# Zapis wykresu
plt.savefig('wykres_5funkcji.png', dpi=300, bbox_inches='tight')
print("Wykres został zapisany jako 'wykres_5funkcji.png'")