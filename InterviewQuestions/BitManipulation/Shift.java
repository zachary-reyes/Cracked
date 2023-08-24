public class Shift {

    // Arithmetic Shift
    // Shifting a one into the most significant bit repeatedly.
    // Returns -1 because arithmetic right shifting negative numbers fills with ones.
    public static int repeatedArithmeticShift(int x, int count) {
        for (int i = 0; i < count; i++) {
            x >>= 1;    // Arithmetic shift by 1
        }
        return x;
    }

    // Logical Shift
    // Shifting a zero into the most significant bit repeatedly.
    // Returns 0 because logical right shifting fills with zeros.
    public static int repeatedLogicalShift(int x, int count) {
        for (int i = 0; i < count; i++) {
            x >>>= 1;    // Logical shift by 1
        }
        return x;
    }

    // Get the value of a specific bit in a number.
    public static boolean getBit(int num, int i) {
        return ((num & (1 << i)) != 0);
    }

    // Set a specific bit to 1 in a number
    public static int setBit(int num, int i) {
        return num | (1 << i);
    }

    // Clear a specific bit (set to 0) in a number
    public static int clearBit(int num, int i) {
        int mask = ~(1 << i);
        return num & mask;
    }

    // Clear all bits from the most significant bit through i (inclusive) in a number.
    public static int clearBitsMSBthroughI(int num, int i) {
        int mask = (1 << i) - 1;
        return num & mask;
    }

     // Clear all bits from i through the least significant bit (inclusive) in a number.
    public static int clearBitsIthrough0(int num, int i) {
        int mask = (-1 << (i + 1));
        return num & mask;
    }

     // Update a specific bit in a number to either 0 or 1.
    public static int updateBit(int num, int i, boolean bitIs1) {
        int value = bitIs1 ? 1 : 0;
        int mask = ~(1 << i);
        return (num & mask) | (value << i);
    }

    public static void main(String[] args) {
        int x = -93242;
        int count = 40;
        System.out.println("With parameters x = " + x + " and count = " + count + ", what is the output?");
        int arithmetic = repeatedArithmeticShift(x, count);
        int logic = repeatedLogicalShift(x, count);
        System.out.println("Arithmetic: " + arithmetic);
        System.out.println("Logical: " + logic);

        // Test other bit manipulation functions
        int num =  0b110101; // Binary representation: 101011
        int bitIndex = 3;

        System.out.println(num + " -> " + Integer.toBinaryString(num));
        System.out.println("getBit(" + num + ", " + bitIndex + "): " + getBit(num, bitIndex));

        int setBitResult = setBit(num, bitIndex); // Set the fourth bit (index 3)
        System.out.println(setBitResult + " -> " + Integer.toBinaryString(setBitResult));
        System.out.println("setBit(" + num + ", " + bitIndex + "): " + Integer.toBinaryString(setBitResult));

        int clearBitResult = clearBit(setBitResult, bitIndex); // Clear the fourth bit (index 3)
        System.out.println("clearBit(" + setBitResult + ", " + bitIndex + "): " + Integer.toBinaryString(clearBitResult));

        int clearMSBthroughIResult = clearBitsMSBthroughI(setBitResult, bitIndex); // Clear bits from MSB through index 3
        System.out.println("clearBitsMSBthroughI(" + setBitResult + ", " + bitIndex + "): " + Integer.toBinaryString(clearMSBthroughIResult));

        int clearIthrough0Result = clearBitsIthrough0(setBitResult, bitIndex); // Clear bits from index 3 through LSB
        System.out.println("clearBitsIthrough0(" + setBitResult + ", " + bitIndex + "): " + Integer.toBinaryString(clearIthrough0Result));

        int updateBitResult = updateBit(setBitResult, 1, true); // Update second bit to 1
        System.out.println("updateBit(" + setBitResult + ", 1, true): " + Integer.toBinaryString(updateBitResult));
    }
}