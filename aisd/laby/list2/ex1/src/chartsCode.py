import matplotlib.pyplot as plt
import numpy as np

# Input arrays
array1 = [33, 26, 87, 69, 257, 232, 351, 315, 611, 565]
array2 = [23, 7, 68, 29, 131, 37, 180, 87, 299, 126, 
          12325, 5988, 23889, 11749, 40013, 19568, 55408, 26039, 
          65658, 30349, 89676, 47172, 96238, 48247, 118539, 55373, 
          130699, 67780, 154131, 72675, 187524, 77781, 191496, 97085, 
          202566, 106940, 215547, 105558, 242455, 116093, 272125, 141747, 
          281469, 137052, 286891, 138829, 310575, 141971, 332129, 153913, 
          335546, 157961, 372811, 177391, 397485, 188005, 424208, 212504, 
          432096, 212155, 453163, 231301, 475634, 216105, 479994, 236531, 
          504669, 245965, 568409, 297039, 547121, 289706, 587657, 299079, 
          562071, 291578, 649116, 295141, 654566, 293994, 649461, 312289, 
          671645, 317259, 715809, 345880, 700243, 352058, 737446, 392375, 
          742507, 341111, 762095, 366133, 794227, 377825, 794746, 381239, 
          794735, 396674, 816497, 418050, 866099, 432414, 877635, 413673, 
          891901, 424651, 953703, 477520]
array3 = [33, 28, 63, 34, 122, 42, 179, 93, 299, 204, 
          11379, 5780, 25367, 12795, 42381, 20877, 52763, 23748, 
          73889, 37636, 88156, 41534, 96552, 45967, 131708, 60465, 
          147088, 74506, 154187, 74620, 171172, 77564, 199065, 99944, 
          198905, 95057, 227552, 119007, 258373, 101961, 257030, 118404, 
          287922, 139530, 301300, 137671, 307686, 150123, 359590, 195560, 
          357418, 191315, 393849, 177086, 381358, 173500, 401743, 207736, 
          432620, 231559, 465752, 219869, 453268, 217880, 468543, 222334, 
          507252, 261844, 549681, 267863, 540666, 248189, 555555, 262364, 
          585493, 282229, 584979, 289608, 634288, 323610, 695552, 357928, 
          700270, 303022, 672690, 342193, 733920, 374434, 726951, 364406, 
          778882, 404752, 754041, 396043, 788777, 394194, 839263, 397983, 
          829894, 438356, 841509, 408443, 899969, 417375, 921882, 485247, 
          923330, 478368, 937412, 500124]

# Generate x-values
x1 = [10, 20, 30, 40, 50]
x2 = [10, 20, 30, 40, 50] + [i*1000 for i in range(1, 51)]
x3 = [10, 20, 30, 40, 50] + [i*1000 for i in range(1, 51)] 

# Split arrays into pairs
def split_array(arr):
    return arr[::2], arr[1::2]

# Process data
data = [
    (x1, split_array(array1)),
    (x2, split_array(array2)),
    (x3, split_array(array3))
]

# Create plot
plt.figure(figsize=(14, 8))

# Plot settings
styles = [
    {'color': 'blue', 'marker': 'o', 'linestyle': '-'},
    {'color': 'orange', 'marker': 's', 'linestyle': '--'},
    {'color': 'green', 'marker': '^', 'linestyle': '-.'},
    {'color': 'red', 'marker': 'D', 'linestyle': ':'},
    {'color': 'purple', 'marker': 'X', 'linestyle': '-'},
    {'color': 'brown', 'marker': 'P', 'linestyle': '--'}
]

custom_labels = [
    "InsertionSort - Liczba porównań",    # First chart from array1
    "InsertionSort - Liczba przestawień",   # Second chart from array1
    "QuickSort - Liczba porównań",  # First chart from array2
    "QuickSort - Liczba przestawień", # Second chart from array2
    "HybridSort - Liczba porównań",    # First chart from array3
    "HybridSort - Liczba przestwień"  # Second chart from array3
]

label_counter = 0  # Track which name to use

for idx, (x, (y1, y2)) in enumerate(data):
    # Plot first chart from current array
    plt.plot(x, np.divide(y1, x), 
             label=custom_labels[label_counter],  # Use custom name
             **styles[2*idx])
    label_counter += 1
    
    # Plot second chart from current array
    plt.plot(x, np.divide(y2, x),
             label=custom_labels[label_counter],  # Use next custom name
             **styles[2*idx+1])
    label_counter += 1

plt.title('Liczba porównań i przestawień podzielona przez n, k = 1')
plt.xlabel('X Values (log scale)')
plt.ylabel('Y Values (log scale)')
plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
plt.grid(True)
plt.xscale('log')
plt.yscale('log')
plt.tight_layout()
plt.show()