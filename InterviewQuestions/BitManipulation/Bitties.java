import javax.print.DocFlavor.INPUT_STREAM;

public class Bitties {

    /* 5.1 Insertion: You are given two 32-bit numbers, N and M, and two bit positions, i and j. Write a method to insert M into N such that M starts at bit j and ends at bit i. You can assume that the bits j through i have enough space to fit all of M. That is, if M = 10011, you can assume that there are at least 5 bits between j and i. You would not, for example, have j = 3 and i = 2, because M could not fully fit between bit 3 and bit 2.
     * 
     * Hints: #137, #169, #215
     * -> Break this into parts. Focus first on clearing the appropriate bits.
     * -> To clear the bits, create a "bit mask"that looks like a series of 1s, then Os, then 1s.
     * -> It's easy to create a bit mask of Os at the beginning or end. But how do you create a bit mask with a bunch of zeroes in the middle? Do it the easy way: Create a bit mask for the left side and then another one for the right side.Then you can merge those.
     * 
     * Approach: 
     * (1) Clear the bits j through i in N
     * (2) Shift M so that it lines up with bits j through i
     * (3) Merge M and N
     */
    public static int updateBits(int n, int m, int i, int j) {
        /* Create a mask to clear bits i through j in N.
         * Example: i = 2, j = 4.
         * Result: 11100011 (8 bits for simplicity) */
        int allOnes = ~0;   // will equal sequence of all 1s

        // 1s before position j, then 0s. left = 11100000
        int left = allOnes << (j + 1);

        // 1s after position i. right = 00000011
        int right = ((1 << i) - 1);

        // All 1s, except for 0s between i and j. mask = 11100011
        int mask = left | right;

        /* Clear bits j through i then put m in there */
        int n_cleared = n & mask;   // Clear bits j through i
        int m_shifted = m << i; // Move m into correct position;

        return n_cleared | m_shifted;   // OR them, and we're done!
    }

    /* 5.2 Binary to String: Given a real number between O and 1 (e.g., 0.72) that is passed in as a double, print the binary representation. If the number cannot be represented accurately in binary with at most 32 characters, print "ERROR:' 
     * 
     * Hints: #143,#167,#173,#269,#297
     * -> To wrap your head around the problem, try thinking about how you'd do it for integers.
     * -> In a number like .893 (in base 10), what does each digit signify? What then does each digit in .10010 signify in base 2?
     * -> A number such as .893 (in base 10) indicates 8 * 10 ^ -1 + 9 * 10 ^ -2 + 3 * 10 ^ -3. Translate this system into base 2.
     * -> How would you get the first digit in .893? If you multiplied by 10, you'd shift the values over to get 8.93. What happens if you multiply by 2?
     * -> Think about what happens for values that can't be represented accurately in binary.
     * 
     * Approach: Fractional Binary Conversion..."multiplying by 2 and extracting the integer part."
     * This method iterates through the fractional part of the decimal number and constructs its binary representation bit by bit.
    */
    public static String printBinary(double num) {
        if (num >= 1|| num <= 0) {
            return "ERROR";
        }

        StringBuilder binary = new StringBuilder();
        binary.append(".");
        while (num > 0) {
            /* Setting a limit on length: 32 characters */
            if (binary.length() >= 32) {
                return "ERROR";
            }

            double r = num * 2;
            if (r >= 1) {
                binary.append(1);
                num = r - 1;
            } else {
                binary.append(0);
                num = r;
            }
        }
        return binary.toString();
    }

    /* 5.3 Flip Bit to Win: You have an integer and you can flip exactly one bit from a 0 to a 1. Write code to find the length of the longest sequence of 1s you could create. EX. Input: 1775 (or: 11011101111) Output: 8
     * 
     * Hints: #159, #226, #314,#352
     * ->  Start with a brute force solution. Can you try all possibilities?
     * -> Flipping a O to a 1 can merge two sequences of 1s-but only if the two sequences are separated by only one 0.
     * -> Each sequence can be lengthened by merging it with an adjacent sequence (if any) or just flipping the immediate neighboring zero. You just need to find the best choice.
     * -> Try to do it in linear time, a single pass, and 0(1) space.
     * 
     * Approach: traverses through the binary representation of the input integer, keeping track of consecutive 1s and updating the maximum length of consecutive 1s that can be achieved.
     * 
     * Runtime: O(b), while b is # of bits
     * additional memory used is constant, O(1)
     */
    public static int flipBit(int a) {
        /* If all 1s, this is already the longest sequence */
        if (~a == 0) return Integer.BYTES * 8;

        int currentLength = 0;
        int previousLength = 0;

        int maxLength = 1;  // We can always have a sequence of at least one 1
        while (a != 0) {
            if ((a & 1) == 1) { // Current bit is a 1
                currentLength++;
            } else if ((a & 1) == 0) { // Current bit is a 0
                /* Update to 0 (if next bit is 0) or currentLength (if next bit is 1) */
                previousLength = (a & 2) == 0 ? 0 : currentLength;
                currentLength = 0;
            }
            maxLength = Math.max(previousLength + currentLength + 1, maxLength);
            a >>>= 1;
            // System.out.println(Integer.toBinaryString(a));
        }
        return maxLength;
    }

    /* 5.4 Next Number: Given a positive integer, print the next smallest and the next largest number that have the same number of 1 bits in their binary representation. 
     * 
     * Hints: #147, #175, #242, #312, #339, #358, #375, #390
     * -> Get Next: Start with a brute force solution for each.
     * -> Get Next: Picture a binary number-something with a bunch of 1s and Os spread out throughout the number. Suppose you flip a 1 to a O and a O to a 1. In what case will the number get bigger? In what case will it get smaller?
     * -> Get Next: If you flip a 1 to a O and a O to a 1,it will get bigger if the 0->1 bit is more signifi­ cant than the 1->0 bit. How can you use this to create the next biggest number (with the same number of 1s)?
     * -> Get Next: Can you flip a O to a 1 to create the next biggest number?
     * -> Get Next: Flipping a O to a 1 will create a bigger number. The farther right the index is the smaller the bigger number is. If we have a number like 1001, we want to flip the rightmost O(to create 1011). But if we have a number like 1010, we should not flip the rightmost 1.
     * -> Get Next: We should flip the rightmost non-trailing 0. The number 1010 would become 1110. Once we've done that, we need to flip a 1 to a O to make the number as small as possible, but bigger than the original number (1010). What do we do? How can we shrink the number?
     * -> Get Next: We can shrink the number by moving all the 1s to the right of the flipped bit as far right as possible (removing a 1 in the process).
     * -> Get Previous: Once you've solved Get Next, try to invert the logic for Get Previous.
     * 
     * Supposedly shouldn't ever get something like this but should still comeback to review solution
    */

    /* 5.5 Debugger: Explain what the following code does: ((n & (n-1)) == 0). 
     *  
     * Hints:#151,#202,#261,#302,#346,#372,#383,#398
     * -> Reverse engineer this, starting from the outermost layer to the innermost layer.
     * -> What does it mean if A & B == 0?
     * -> If A & B == 0, then it means that A and B never have a 1 at the same spot. Apply this to the equation in the problem.
     * -> If (n & (n-1)) == 0, then this means that n and n - 1 never have a 1 in the same spot. Why would that happen?
     * -> What is the relationship between how n looks and how n - 1 looks? Walk through a binary subtraction. 
     * -> When you do a binary subtraction, you flip the rightmost Os to a 1, stopping when you get to a 1 (which is also flipped). Everything (all the 1s and Os) on the left will stay put.
     * -> Picture n and n -1. To subtract 1 from n, you flipped the rightmost 1 to a O and all the Os on its right to 1s.If n & n-1 == 0,then there are no 1s to the left of the first 1. What does that mean about n?
     * We know that n must have only one 1 if n & (n-1) == 0. What sorts of numbers have only one 1?
     * 
     * Answer: Checks if n is a power of 2 (or if n is 0)
    */

    /* 5.6  Conversion: Write a function to determine the number of bits you would need to flip to convert integer A to integer B.      EXAMPLE Input: 29 (or: 11101), 15 (or: 01111) Output: 2
     * 
     * Hints: #336,#369
     * -> How would you figure out how many bits are different between two numbers?
     * -> Think about what an XOR indicates. If you do a XOR b, where does the result have 1s? Where does it have Os?
     * 
     * Count the number of bits in A ^ B that are 1
    */
    public static int bitSwapRequired(int a, int b) {
        int count = 0;
        for (int c = a ^ b; c != 0; c = c >> 1) {
            count += c & 1;
        }
        return count;
    }

    /* 5.7 Pairwise Swap: Write a program to swap odd and even bits in an integer with as few instructions as
   possible (e.g., bit 0 and bit 1 are swapped, bit 2 and bit 3 are swapped, and so on). 
   *
   *  Hints:#145,#248,#328,#355 
   * -> Swapping each pair means moving the even bits to the left and the odd bits to the right. Can you break this problem into parts?
   * -> Can you create a number that represents just the even bits? Then can you shift the even bits over by one?
   * -> The value 1010 in binary is 10 in decimal or OxA in hex. What will a sequence of 101010... be in hex? That is, how do you represent an alternating sequence of 1s and Os with 1s in the odd places? How do you do this for the reverse (1s in the even spots)?
   * -> Try masks 0xaaaaaaaa and 0x55555555 to select the even and odd bits. Then try shifting the even and odd bits around to create the right number.
   * 
   * Approach: 
   *    Mask 0xaaaaaaaa extracts all the even bits from x, and they are shifted to the right one to be in odds place
   *    Mask 0x55555555 extracts all the odd bits from x, and they are shifted to the left one to be in evens place
   */    
    public static int swapOddEvenBits(int x) {
        return (((x & 0xaaaaaaaa) >>> 1) | ((x & 0x55555555) << 1));    // OR merges the odd and even sets 
    }

    /* 5.8 8Draw Line: A monochrome screen is stored as a single array of bytes, allowing eight consecutive pixels to be stored in one byte. The screen has width w, where w is divisible by 8 (that is, no byte will be split across rows). The height of the screen, of course, can be derived from the length of the array and the width. Implement a function that draws a horizontal line from (x1, y) to ( x2, y). The method signature should look something like: 
        drawline(byte[] screen, int width, int x1, int x2, int y) 
     * 
     *  Hints: #366,#381,#384,#391
     * -> First try the naive approach. Can you set a particular "pixel"?
     * -> Analyze your algorithm. Is there any repeated work? Can you optimize this? When you're drawing a long line, you'll have entire bytes that will become a sequence of 1s. Can you set this all at once?
     * -> What about the start and end of the line? Do you need to set those pixels individually, or can you set them all at once?
     * -> Does your code handle the case when xl and x2 are in the same byte?
     * 
     * Recognize that is x1 and x2 are far away from each other, several full bytes will be contained between them. Set these with 0xFF and then use masks for start and end
     * 
     * fucking hard
    */
    public static void drawLine(byte[] screen, int width, int x1, int x2, int y) {
        int start_offset = x1 % 8;
        int first_full_byte = x1 / 8;
        if (start_offset != 0) {
            first_full_byte++;
        }

        int end_offset = x2 % 8;
        int last_full_byte = x2 / 8;
        if (end_offset != 0) {
            last_full_byte--;
        }

        // set full bytes
        for (int b = first_full_byte; b <= last_full_byte; b++) {
            screen[(width / 8) * y + b] = (byte)0xFF;
        }

        // Create masks for start and end of line
        byte start_mask = (byte)(0xFF >> start_offset);
        byte end_mask = (byte)~(0xFF >> (end_offset + 1));

        // set start and end of line
        if ((x1 / 8) == (x2 / 8)) { // x1 and x2 are in the same byte
            byte mask = (byte)(start_mask & end_mask);
            screen[(width / 8) * y + (x1 / 8)] |= mask;
        } else {
            if (start_offset != 0) {
                int byte_number = (width / 8) * y + first_full_byte - 1;
                screen[byte_number] |= start_mask;
            }
            if (end_offset != 7) {
                int byte_number = (width / 8) * y + last_full_byte + 1;
                screen[byte_number] |= end_mask;
            }
        }
    }

    public static void main(String[] args) {
        int n = 0b10000000000; // Binary: 10000000000
        int m = 0b10011;       // Binary: 000010011
        int i = 2, j = 6;

        int result = updateBits(n, m, i, j);

        System.out.println("Inserting m: " + m + " -> " + Integer.toBinaryString(m));
        System.out.println("Into n: " + n + " -> " + Integer.toBinaryString(n));
        System.out.println("Result: " + Integer.toBinaryString(result)); // Print binary representation of result

        double num1 = 0.625;
        double num2 = 0.375;
        double num3 = 0.1;
        double num4 = 0.3;

        System.out.println(num1 + " in binary: " + printBinary(num1));
        System.out.println(num2 + " in binary: " + printBinary(num2));
        System.out.println(num3 + " in binary: " + printBinary(num3));
        System.out.println(num4 + " in binary: " + printBinary(num4));

        int flipMe = 0b11011101111;
        int flipped = flipBit(flipMe);

        System.out.println("The length of the longest sequence of 1s (created by flipping one bit) in " + Integer.toBinaryString(flipMe) + " is " + flipped);

        int a = 29;
        int b = 15;
        int count = bitSwapRequired(a, b);
        System.out.println("The number of bits flipped to convert " + a + " (" + Integer.toBinaryString(a) + ") " + "into " + b + " (" + Integer.toBinaryString(b) + ") " + "is " + count);

        int input = 0b11001010;  // Binary: 11001010, Decimal: 202

        System.out.println("Original Number: " + Integer.toBinaryString(input));

        int swapped = swapOddEvenBits(input);
        System.out.println("Swapped Number:  " + Integer.toBinaryString(swapped));
    }
    
}
