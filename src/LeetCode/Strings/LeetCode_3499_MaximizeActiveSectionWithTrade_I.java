package LeetCode.Strings;

import java.util.ArrayList;

public class LeetCode_3499_MaximizeActiveSectionWithTrade_I {
    public static void main(String[] args) {

        String s = "010010";
        System.out.println(maxActiveSectionsAfterTrade(s));
    }

    /*
        Greedy + Run Length Encoding Approach :

        Step 1: Count the total number of active ('1') sections.

        Step 2: Compress the string into consecutive groups of identical characters (Run Length Encoding).

        Step 3: Find a block of '1's that is surrounded by '0's on both sides.

        Trading this block merges the two adjacent inactive blocks, producing the maximum possible increase in active sections.
     */
    static int maxActiveSectionsAfterTrade(String s) {

        // Count the total number of active sections.
        int totalOnes = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '1') {
                totalOnes++;
            }
        }

//      Add virtual '1's at both ends so every middle block has valid neighbours.

        String modified = "1" + s + "1";

        // Stores the type ('0' or '1') of each block.
        ArrayList<Character> blockType = new ArrayList<>();

        // Stores the length of each block.
        ArrayList<Integer> blockLength = new ArrayList<>();

        // Length of the current block.
        int count = 1;

        // Build the Run Length Encoding.
        for (int i = 1; i < modified.length(); i++) {

            if (modified.charAt(i) == modified.charAt(i - 1)) {

                count++;

            } else {

                blockType.add(modified.charAt(i - 1));
                blockLength.add(count);

                count = 1;
            }
        }

        // Store the final block.
        blockType.add(modified.charAt(modified.length() - 1));
        blockLength.add(count);

        // Stores the maximum gain after one trade.
        int maxGain = 0;

//  Find a '1' block surrounded by two '0' blocks.

        for (int i = 1; i < blockType.size() - 1; i++) {

            if (blockType.get(i) == '1'
                    && blockType.get(i - 1) == '0'
                    && blockType.get(i + 1) == '0') {

                // Gain equals the sizes of both adjacent inactive blocks.
                int gain = blockLength.get(i - 1) + blockLength.get(i + 1);

                maxGain = Math.max(maxGain, gain);
            }
        }

        // Final answer after the best possible trade.
        return totalOnes + maxGain;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = length of the string

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. Count active sections: O(n)

2. Build the Run Length Encoding: O(n)

3. Traverse the compressed blocks: O(n)

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: Two ArrayLists are used to store the compressed blocks and their lengths.

Overall: O(n)

---------------------------------------------------------

Key Observation:

Instead of examining every possible trade, compress the string into consecutive blocks.

A valid trade only affects a '1' block that is surrounded by two '0' blocks, so checking only these blocks is sufficient to determine the maximum possible gain.

---------------------------------------------------------
*/