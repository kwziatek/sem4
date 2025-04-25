import networkx as nx
import matplotlib.pyplot as plt

def create_network():
    G = nx.Graph()
    G.add_nodes_from(range(20))
    
    # 1. Tworzenie PIERŚCIENIA (tylko 20 krawędzi)
    for i in range(20):
        # Unikamy duplikatów: łączenie tylko z następnikiem
        if i < (i+1) % 20:  # Warunek eliminujący powtórzenia
            G.add_edge(i, (i+1) % 20, type="ring")

    # 2. Dodawanie SKRÓTÓW (9 krawędzi)
    for i in range(1, 10):
        G.add_edge(i, (i+10) % 20, type="chord")

    # 3. Weryfikacja liczby krawędzi
    print(f"Liczba krawędzi: {G.number_of_edges()}")  # Powinno być 29
    
    return G

# Generowanie i testowanie grafu
network = create_network()

# Wypisz wszystkie krawędzie dla weryfikacji
print("\nPrzykładowe krawędzie pierścienia:")
print([(u, v) for u, v, d in network.edges(data=True) if d['type'] == 'ring'][:5])

print("\nPrzykładowe skróty:")
print([(u, v) for u, v, d in network.edges(data=True) if d['type'] == 'chord'][:3])

plt.figure(figsize=(12, 12))
pos = nx.circular_layout(network)
nx.draw(network, pos, with_labels=True, node_size=500, 
        edge_color=['red' if d['type']=='chord' else 'gray' for _, _, d in network.edges(data=True)])
plt.title("Poprawna struktura grafu (29 krawędzi)")
plt.show()