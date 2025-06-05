def bit_stuffing(data):
    """Apply bit stuffing: insert '0' after five consecutive '1's."""
    stuffed = ""
    count = 0
    for bit in data:
        stuffed += bit
        if bit == '1':
            count += 1
            if count == 5:
                stuffed += '0'
                count = 0
        else:
            count = 0
    return stuffed

def calculate_crc(data):
    """Calculate CRC-8 for the given data."""
    data = data + "0" * 8  # Append 8 zeros for CRC-8
    data = list(data)
    for i in range(len(data) - 8):
        if data[i] == '1':
            for j in range(9):
                data[i + j] = '1' if data[i + j] != polynomial[j] else '0'
    return ''.join(data[-8:])

def frame_data(input_file, output_file, frame_size=32):
    """Frame data from input_file and write to output_file."""
    # Read input file
    try:
        with open(input_file, 'r') as f:
            data = f.read().strip()
    except FileNotFoundError:
        print(f"Error: Input file '{input_file}' not found.")
        return
    
    # Filter only '0' and '1'
    data = ''.join(c for c in data if c in '01')
    if not data:
        print("Error: No valid data ('0' or '1') found in input file.")
        return
    
    # Frame flag
    flag = "01111110"
    
    crc = calculate_crc(data)

    print(crc)
    
    frame = data + crc
    stuffed_frame = ""
    count = 0

    # bit stuffing
    for bit in frame:
        stuffed_frame += bit
        if bit == "1":
            count += 1

            if count == 5:
                stuffed_frame += '0'
                count = 0
        else:
            count = 0

    
    framed_data = flag + stuffed_frame + flag

    # Write frames to output file
    try:
        with open(output_file, 'w') as f:
            f.write(framed_data)
    except Exception as e:
        print(f"Error writing to output file '{output_file}': {e}")

polynomial="100000111"

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 3:
        print("Usage: python frame.py input_file output_file")
        sys.exit(1)
    frame_data(sys.argv[1], sys.argv[2])