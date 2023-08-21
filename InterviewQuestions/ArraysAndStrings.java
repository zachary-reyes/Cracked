import java.util.ArrayList;

class ArraysAndStrings {

    //////////////////////////////////
    // ArrayList & Resizable Arrays //
    //////////////////////////////////

    /**
     * 
     * takes two arrays of strings as input and returns an ArrayList containing all the strings from both input arrays 
     */
    public static ArrayList<String> merge(String[] words, String[] more) {
        ArrayList<String> sentence = new ArrayList<>();
        for (String w : words) sentence.add(w);
        for (String w : more) sentence.add(w);
        return sentence;
    }

    ///////////////////
    // StringBuilder //
    ///////////////////

    /*
     * Runtime: O(xn^2), where x is length of strings and n is number of strings
     * 
     * takes an array of strings (words) as input and returns a single concatenated string that contains all the strings from the input array.
     * 
     * Not ideal, b/c ineffiecient memory usage...strings are immutable and need to be copied over 
     */
    public static String joinWordsBad(String[] words) {
        String sentence = "";
        for (String w : words) {
            sentence += w;
        }
        return sentence;
    }

    /*
     * Runtime: O(n)
     * 
     * takes an array of strings (words) as input and returns a single concatenated string that contains all the strings from the input array.
     * 
     * Ideal
     */
    public static String joinWordsGood(String[] words) {
        StringBuilder sentence = new StringBuilder();
        for (String w : words) {
            sentence.append(w);
        }
        return sentence.toString();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 1.1 Is Unique: Implement an algorithm to determine if a string has all unique characters. What if you cannot use additional data structures?
     * 
     * Hints: 
     * -> Try a Hash Table
     * -> Could a bit vector be useful?
     * -> Can you solve it in O(nlog n) time? What might a solution like that look like?
     * 
     * Time Complexity: O(n), where n is the length of the sting
     * Space Complexity: O(1)
     */
    public static boolean isUnique(String str) {
        if (str.length() > 128) return false;

        boolean[] alphabet = new boolean[128];
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);    // returns ascii value of character...allowing us to set our "Alphabet"
            if (alphabet[val]) {    // already found this char in string
                return false;
            }
            alphabet[val] = true;
        }
        return true;
    }

    // Use a bit vector to reduce our space usage by a factor of 8
    // We will assume that the string only uses the lowercase letters a through z. This will allow us to use just a single int (32 bits)
    public static boolean isUniqueBitVector(String str) {
        int checker = 0;
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i) - 'a';  // Convert letter to a number (a=0, b=1, etc.)
            if ((checker & (1 << val)) > 0) {
                return false;
            }
            checker |= (1 << val);  // Set the corresponding bit to mark the character as encountered
        }
        return true;
    }

    /*
     * If we can't use additional data structures...
     * 
     * 1. Compare every character of the string to every other character of the string. 
     *      Time Complexity: O(n^2)
     *      Space Complexity: O(1)
     * 
     * 2. If we are allowed to modify the input string, we could sort the string in O(nlog(n)) time and then linearly check the string for neighboring characters that are identical. Careful, as many sorting algorithms take up extra space.
     */

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 1.2 Check Permutation: Given two strings, write a method to decide if one is a permutation of the other.
     * 
     * Hints: 
     * -> Describe what it means for two strings to be permutations of each other. Now, look at that definition you provided. Can you check the strings against that definition?
     * -> There is one solution that is O(NlogN) time. Another solution uses some space, but is O(N) time.
     * -> Could a hash table be useful?
     * -> Two strings that are permutations should have the same characters, but in different orders. Can you make the orders the same?
     * 
     * Solution #1: Sort the strings
     * 
     * Time Complexity: O(nlogn) ... just sorting
     * Space Complexity: O(n) ... string length n
     */
    public static String sort(String str) {
        char[] content = str.toCharArray();
        java.util.Arrays.sort(content);
        return new String(content);
    }

    public static boolean permutationSort(String s, String t) {
        if (s.length() != t.length()) { // Must have same length
            return false;
        }
        return sort(s).equals(sort(t));
    }

    /*
     * Solution #2: Check if the two strings have identical character count
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static boolean permutation(String s, String t) {
        if (s.length() != t.length()) { // Must have same length
            return false;
        }

        int[] letters = new int[128];   // Assumption...128 ascii character set

        char[] s_array = s.toCharArray();
        for (char c: s_array) {     // count number of each char in s
             letters[c]++;
        }
        for (int i = 0; i < t.length(); i++) {
            int c = (int)t.charAt(i);
            letters[c]--;
            if (letters[c] < 0) {
                return false;
            }
        }
        return true;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 1.3 URLify: Write a method to replace all space in a string with '%20'.
     * 
     * Assume that the string has sufficient space at the end to hold the additional characters, and that you are given the "true" length of the string (In Java, use a character array so that you can perform this operation in place)
     * 
     * Hints: 
     * -> Its often easiest to modify strings by going from the end of the string to the beginning
     * -> You might find you need to know the number of spaces. Can you just count them?
     * 
     * Solution #1: 
     * 
     * Time Complexity: O(trueLength)
     * Space Complexity: O(1)
     */
    public static void replaceSpaces(char[] str, int trueLength) {
        int spaceCount = 0, index, i = 0;
        for (i = 0; i < trueLength; i++) {  // count spaces in actual word, not buffer
            if (str[i] == ' ') {
                spaceCount++;
            }
        }
        index = trueLength + spaceCount * 2;
        if (trueLength < str.length) str[trueLength] = '\0';    // End Array
        for(i = trueLength - 1; i >= 0; i--) {
            if (str[i] == ' ') {
                str[index - 1] = '0';
                str[index - 2] = '2';
                str[index - 3] = '%';
                index -=3;
            }
        }
        System.out.println(str);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 1.4 Palindrome Permutation: Given a string, write a function to check if its a permutation of a palindrome.
     * 
     * A palindrome is a word or phrase that is the same forwards & backwards. 
     * A permutation is a rearrangement of letters.
     * 
     * The palindrome does not need to be limited to just dictionary words.
     * 
     * Hints: 
     * -> You do not have to - and should not - generate all permutations. This would be very inefficient
     * -> What characteristics would a string that is a permutation of a palindrome have?
     * -> Have you tried a hash table? You should be able to get this down to O(N) time.
     * -> Can you reduce the space usage by using a bit vector?
     * 
     * Solution #1: Use a hash table to count how many times each character appears. Then, iterate through the hash table and ensure that no more than one character has an odd count.
     * 
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    public static boolean isPermutationOfPalindrome(String phrase) {
        int[] table = buildCharFrequencyTable(phrase);
        return checkMaxOneOdd(table);
    }

    /* Check that no more than one character has on odd count */
    public static boolean checkMaxOneOdd(int[] table) {
        boolean foundOdd = false;
        for (int count : table) {
             if (count % 2 == 1) {
                if (foundOdd) {
                    return false;
                }
                foundOdd = true;
             }
        }
        return true;
    }

    /* Map each character to a number... a->0, b->1, etc 
     * This is case insensitive. Non-letter characterts map to -1 */
    public static int getCharNumber(Character c) {
        int a = Character.getNumericValue('a');
        int z = Character.getNumericValue('z');
        int val = Character.getNumericValue(c);
        if (a <= val && val <= z) {
            return val - a;
        }
        return -1;
    }

    /* Count how many times each character appears */
    public static int[] buildCharFrequencyTable(String phrase) {
        int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];
        for (char c : phrase.toCharArray()) {
            int x = getCharNumber(c);
            if (x != -1) {
                table[x]++;
            }
        }
        return table;
    }

    /*
     * Other solutions (refer to page 197)
     * 
     * Can't optimize big O time since need to look through entire string, however, we can look at some variations in the code
     * 
     * Instead of checking a the number of off counts at the end, we can check as we go along...we have eliminated the final iteration through the hash table, but now we have to run a few extra lines of code for each character in the string
     * 
     * Notice, don't actually need to know counts...just whether they are even or odd. Thus, can use a single integer (bit vector) and check that at most one bit in the int is set to 1
     */

     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 1.5 One Away: There are three types of edits that can be performed on strings: insert a character, remove a character, or replace a character. Given two strings, write a function to check if they are one edit (or zero edits) away
     * 
     * Hints: 
     * -> Start with the easy thing. Can you check each of the conditions separately?
     * -> What is the relationship between the "insert character" option and the "remove character" option? Do these need to be two separate checks?
     * -> Can you do all three checks in a single pass?
     * 
     * Let's look at the "meaning" of each of these operations:
     *      Replacement: Consider two strings, such as bale and pale, that are one replacement away. Yes, that does mean that you could replace a character in bale to make pale. But more precisely, it means that they are different only in one place
     *      Insertion: The strings apple and aple are one insertion away. This means that if you compared the strings, they would be identical-except for a shift at some point in the strings.
     *      Removal: The strings apple and aple are also one removal away, since removal is just the inverse of insertion.
     * 
     * Solution #1: Merge the insertion and removal check into one step, and check the replacement step separately.
     *              Observe, check the lengths of the strings, not the strings themselves, to see which checks are needed
     * 
     * Time Complexity: O(N), where N is the length of the shorter string
     * Space Complexity: O(1)
     */
    public static boolean oneEditAway(String first, String second) {
        if (first.length() == second.length()) {
            return oneEditReplace(first, second);
        } else if (first.length() + 1 == second.length()) {
            return oneEditInsert(first, second);
        } else if (first.length() - 1 == second.length()) {
            return oneEditInsert(first, second);
        }
        return false;
    }

    public static boolean oneEditReplace(String s1, String s2) {
        boolean foundDifference = false;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (foundDifference) {
                    return false;
                }
                foundDifference = true;
            }
        }
        return true;
    }

    /* Check if you can insert a character into s1 to make s2 */ 
    public static boolean oneEditInsert(String s1, String s2) {
        int index1 = 0;
        int index2 = 0;
        while  (index2 < s2.length() && index1 < s1.length()) {
            if (s1.charAt(index1) != s2.charAt(index2)) {
                if (index1 != index2) {
                    return false;
                }
                index2++;
            } else {
                index1++;
                index2++;
            }
        }
        return true;
    }
    /* Can also merge oneEditReplace and oneEditInsert */

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 1.6 String Compression: Implement a method to perform basic string compression using the counts of repeated characters.For example, the string aabcccccaaa would become a2b1c5a3. If the "compressed" string would not become smaller than the original string, your method should return the original string. You can assume the string has only uppercase and lowercase letters (a-z).
     * 
     * Hints: 
     * -> Do the easy thing first. Compress the string, then compare the lengths.
     * -> Be careful that you aren't repeatedly concatenating strings together. This can be inefficient.
     * 
     * Solution #1: Use StringBuilder to create the compressed string first and then return the shorter of the input string and the compressed string
     * 
     * Other Solutions: same as solution #1 but not using string builder, and then checking in advance , which is a more optimal case when we don't have a large number of repeating characters.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static String compress(String str) {
        StringBuilder compressed = new StringBuilder();
        int countConsecutive = 0;
        for (int i = 0; i < str.length(); i++) {
            countConsecutive++;

            /* If next character is different than current, append this char to result */
            if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
                compressed.append(str.charAt(i));
                compressed.append(countConsecutive);
                countConsecutive = 0;
            }
        }        
        return compressed.length() < str.length() ? compressed.toString() : str;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 1.7 Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4 bytes, write a method to rotate the image by 90 degrees. Can you do this in place?
     * 
     * Hints: 
     * -> Try thinking about it layer by layer. Can you rotate a specific layer?
     * -> Rotating a specific layer would just mean swapping the values in four arrays. If you were asked to swap the values in two arrays, could you do this? Can you then extend it to four arrays?
     * 
     * Solution #1: Implement the swap index by index...we perform such a swap on each layer
     * 
     * Other Solutions: Copy the top edge to an array, and then move the left to the top, the bottom to the left, and so on, in order to perform a four way swap...but this requires O(N) memory
     * 
     * Time Complexity: O(N^2)
     * Space Complexity: O(1)
     */
    public static boolean rotate(int[][] matrix) {
        if (matrix.length == 0 || matrix.length != matrix[0].length) return false;
        int n = matrix.length;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                int top = matrix[first][i]; // save top

                // left -> top
                matrix[first][i] = matrix[last - offset][first];

                // bottom -> left
                matrix[last-offset][first] = matrix[last][last - offset];

                // right -> bottom
                matrix[last][last - offset] = matrix[i][last];

                // top -> right
                matrix[i][last] = top;  // right <- saved top
            }
        }
        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 1.8 Zero Matrix: Write an algorithm such that if an element in an MxN matrix is 0, its entire row and column are set to 0
     * 
     * Hints: 
     * -> If you just cleared the rows and columns as you found 0s, you'd likely wind up clearing the whole matrix. Try finding the cells with zeros first before making any changes to the matrix.
     * -> Can you use O(N) additional space instead of O(N^2)? What info do you really need from the list of cells that are zero?
     * -> You probably need some data storage to maintain a list of the rows and columns that need to be zeroed. Can you reduce the additional space usage to O(1) by using the matrix iself for data storage?
     * 
     * Solution #1: Use two arrays to keep track of all the rows with zeroes and all the columns with zeros. We then nullify rows and columns based on the values in these arrays
     * 
     * Other Solutions: 
     *      Keep a second matrix which flags the zero locations. Then do second pass through the matrix to set the zeroes...taking O(MN) space....think about it tho, we dont need to keep track of exact zero locations, just need to know whether or not a row/column has zero in it
     * Could use bit vector to make somewhat more space efficient
     * Can reduce the space to O(1) by using the first row as a replacement for the row array abd the first column as a replacement for the column array (check back page 205 for code and explanation)
     * 
     * Time Complexity: O(M * N)
     * Space Complexity: O(M + N)
     */
    public static void setZeros(int[][] matrix) {
        boolean[] row = new boolean[matrix.length];
        boolean[] column = new boolean[matrix[0].length];

        // Store the row and column index with value 0
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    column[j] = true;
                }
            }
        }
        // Nullify rows
        for (int i = 0; i < row.length; i++) {
            if (row[i]) nullifyRow(matrix, i);
        }
        
        // Nullify columns
        for (int j = 0; j < row.length; j++) {
            if (column[j]) nullifyColumn(matrix, j);
        }
    }

    public static void nullifyRow(int[][] matrix, int row) {
        for (int j = 0; j < matrix[0].length; j++) {
            matrix[row][j] = 0;
        }
    }

    public static void nullifyColumn(int[][] matrix, int col) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][col] = 0;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 1.9 String Rotation: Assume you have a method isSubstring which checks if one word is a substring of another. Given two strings, s1 & s2, write code to check if s2 is a rotation of s1 using only one call to isSubstring
     * 
     * Hints: 
     * -> If a string is a rotation of another, then it's a rotation at a particular point. For example, a roatation of "waterbottle" at characater 3 means cutting "waterbottle" at character 3 and putting the right half (erbottle) before the left half (wat)
     * -> We are essentially asking if there's a way of splitting the first stringt into two parts, x and y, such that the first string is xy and the second string is yx. For example, x = "wat" and y = "erbottle". The first string is xy = "waterbottle". The second string is yx = "erbottlewat".
     * -> Think about the earlier hint. Then think about what happens when you concatenate "erbottlewat" to itself. You get erbottlewaterbottlewat.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static boolean isRotation(String s1, String s2) {
        int len = s1.length();
        /* Check that s1 and s2 are equal length and not empty */
        if (len == s2.length() && len > 0) {
            /* Concatenate s1 and s1 within new buffer */
            String s1s1 = s1 + s1;
            return isSubstring(s1s1, s2);
        }
        return false;
    }

    public static boolean isSubstring(String str, String sub) {
        return str.contains(sub);
    }

    public static void main(String[] args) {
        String[] words = {"apple", "peach", "pineapple"};
        String[] more = {"69","42","10","12"};
        String unique = "supercali";
        String silent = "silent";
        String listen = "listen";
        String pal = "tactcoa";
        String pale = "pale";
        String bale = "bale"; 
        String compressMe = "aabcccccaaa";
        char[] john = {'J','o','h','n',' ','S','m','i','t','h',' ',' ',' ',' '};    // strings when turned to character array have buffer at end so this why we edit in reverse as to not overwrite
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        ArrayList<String> merged = merge(words, more);
        String badSentence = joinWordsBad(words);
        String goodSentence = joinWordsGood(words);
        Boolean answer = isUnique(unique);
        Boolean answer2 = isUniqueBitVector(unique);
        Boolean answer3 = permutationSort(silent, listen);
        Boolean answer4 = permutation(silent, listen);
        Boolean answer5 = isPermutationOfPalindrome(pal);
        boolean answer6 = oneEditAway(pale, bale);
        String compressed = compress(compressMe);
        boolean rotated = rotate(matrix);

        System.out.println();
        System.out.println("ArraysList:        " + merged);
        System.out.println("Bad Concatenation: " + badSentence);
        System.out.println("Good Concatenation: " + goodSentence);
        System.out.println();
        System.out.println("Interview Questions: Arrays And Strings");
        System.out.println("        Is the string unique? Answer: " + answer + ", Algorithm: Boolean Array");
        System.out.println("        Is the string unique? Answer: " + answer2 + ", Algorithm: Bit Vector");
        System.out.println("Are the strings permutations? Answer: " + answer3 + ", Algorithm: Sorting");
        System.out.println("Are the strings permutations? Answer: " + answer4 + ", Algorithm: Frequency");
        System.out.print("Replace spaces with '%20':"); 
        replaceSpaces(john, 10);
        System.out.println("Is " + pal + " a permutation of palindrome? " + answer5);
        System.out.println("One edit away? " + answer6);
        System.out.println("This: " + compressMe + " -> Compressed: " + compressed);
        System.out.println("Rotated? " + rotated);
    }
}