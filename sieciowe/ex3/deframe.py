def remove_bit_stuffing(data):
    """Remove bit stuffing: remove '0' after five consecutive '1's."""
    destuffed = ""
    count = 0
    i = 0
    while i < len(data):
        destuffed += data[i]
        if data[i] == '1':
            count += 1
            if count == 5 and i + 1 < len(data):
                i += 1  # Skip stuffed '0'
                count = 0
        else:
            count = 0
        i += 1
    return destuffed

def calculate_crc(data, polynomial="100000111"):
    """Calculate CRC-8 for the given data."""
    data = list(data)
    for i in range(len(data) - 8):
        if data[i] == '1':
            for j in range(9):
                data[i + j] = '1' if data[i + j] != polynomial[j] else '0'
    return ''.join(data[-8:])


def deframe_data(input_file, output_file, frame_size=32):
    """Deframe data from input_file and write to output_file."""
    # Read input file
    try:
        with open(input_file, 'r') as f:
            data = f.read().strip()
    except FileNotFoundError:
        print(f"Error: Input file '{input_file}' not found.")
        return
    
    # Frame flag
    flag = "01111110"
    
    frame_without_flags = data[len(flag) : len(data) - len(flag)]
    data_with_crc = remove_bit_stuffing(frame_without_flags)

    crc_len = len(polynomial) - 1
    data = data_with_crc[ : -crc_len]
    
    remainder = calculate_crc(data_with_crc)
    if(set(remainder)) == {'0'}:
        # Write output data to file
        try:
            with open(output_file, 'w') as f:
                f.write(data)
        except Exception as e:
            print(f"Error writing to output file '{output_file}': {e}")

    else:
        print("crc error")

polynomial="100000111"
if __name__ == "__main__":
    import sys
    if len(sys.argv) != 3:
        print("Usage: python deframe.py input_file output_file")
        sys.exit(1)
    deframe_data(sys.argv[1], sys.argv[2])