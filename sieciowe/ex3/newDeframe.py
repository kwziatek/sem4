def remove_bit_stuffing(data):
    """Remove bit stuffing: remove '0' after five consecutive '1's."""
    destuffed = ""
    count = 0
    i = 0
    while i < len(data):
        destuffed += data[i]
        if data[i] == '1':
            count += 1
            if count == 5 and i + 1 < len(data) and data[i + 1] == '0':
                i += 1  # Skip stuffed '0'
                count = 0
        else:
            count = 0
        i += 1
    return destuffed

def calculate_crc(data, polynomial="100000111"):
    """Calculate CRC-8 for the given data."""
    data = list(data + "0" * 8)  # Append 8 zeros for CRC-8 calculation
    for i in range(len(data) - 8):
        if data[i] == '1':
            for j in range(9):
                data[i + j] = '1' if data[i + j] != polynomial[j] else '0'
    return ''.join(data[-8:])

def deframe_data(input_file, output_file, frame_size=32):
    """Deframe data from input_file (32-bit data packets) and write to output_file."""
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
    output_data = ""
    original_length = None
    
    # Split data into frames based on flags
    frames = data.split(flag)
    
    # Process each frame (skip empty strings)
    for i, frame in enumerate(frames):
        if not frame:
            continue
            
        # Remove bit stuffing
        frame_data = remove_bit_stuffing(frame)
        
        # Check if frame has enough bits for length field + data + CRC
        crc_len = len(polynomial) - 1  # CRC-8 = 8 bits
        length_field_len = 16
        if len(frame_data) < length_field_len + frame_size + crc_len:
            print(f"Warning: Skipping invalid frame (too short: {len(frame_data)} bits)")
            continue
        
        # Extract length field, data, and CRC
        length_field = frame_data[:length_field_len]
        data_part = frame_data[length_field_len:length_field_len + frame_size]
        crc_part = frame_data[length_field_len + frame_size:length_field_len + frame_size + crc_len]
        
        # Verify CRC
        frame_content = length_field + data_part
        remainder = calculate_crc(frame_content)
        if remainder == crc_part:
            # Store length from the last valid frame
            if int(length_field, 2) > 0:  # Non-zero length indicates last frame
                original_length = int(length_field, 2)
            output_data += data_part
        else:
            print(f"CRC error in frame: {frame_data[:length_field_len + frame_size + crc_len]}")
    
    if not output_data:
        print("Error: No valid frames found.")
        return
    
    # Trim output data to original length if provided
    if original_length is not None:
        output_data = output_data[:original_length]
    
    # Write output data to file
    try:
        with open(output_file, 'w') as f:
            f.write(output_data)
    except Exception as e:
        print(f"Error writing to output file '{output_file}': {e}")

polynomial = "100000111"

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 3:
        print("Usage: python deframe.py input_file output_file")
        sys.exit(1)
    deframe_data(sys.argv[1], sys.argv[2])