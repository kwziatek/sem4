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
    """Frame data from input_file into 32-bit packets and write to output_file."""
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
    framed_data = ""
    original_length = len(data)  # Store original data length
    
    # Split data into 32-bit packets
    for i in range(0, len(data), frame_size):
        packet = data[i:i + frame_size]
        is_last_packet = (i + frame_size >= len(data))
        
        # If packet is shorter than frame_size, pad with zeros
        if len(packet) < frame_size:
            packet = packet + '0' * (frame_size - len(packet))
        
        # Add length field (16 bits) for the last packet
        length_field = format(original_length, '016b') if is_last_packet else '0' * 16
        
        # Calculate CRC for the packet + length field
        frame_content = length_field + packet
        crc = calculate_crc(frame_content)
        
        # Combine packet and CRC
        frame = frame_content + crc
        
        # Apply bit stuffing to the frame
        stuffed_frame = bit_stuffing(frame)
        
        # Add flags to the frame
        framed_packet = flag + stuffed_frame + flag
        
        # Append to the final output
        framed_data += framed_packet
    
    # Write frames to output file
    try:
        with open(output_file, 'w') as f:
            f.write(framed_data)
    except Exception as e:
        print(f"Error writing to output file '{output_file}': {e}")

polynomial = "100000111"

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 3:
        print("Usage: python frame.py input_file output_file")
        sys.exit(1)
    frame_data(sys.argv[1], sys.argv[2])