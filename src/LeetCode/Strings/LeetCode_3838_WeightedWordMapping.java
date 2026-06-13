package LeetCode.Strings;

public class LeetCode_3838_WeightedWordMapping {
    public static void main(String[] args) {
        int[] wt = {5,3,12,14,1,2,3,2,10,6,6,9,7,8,7,10,8,9,6,9,9,8,3,7,7,2};
        String[] words = {"abcd","def","xyz"};

        System.out.println(mapWordWeights(words, wt));
    }

    /*
        Approach:

        For every word:

        1. Calculate its total weight by summing the weights of all characters.

        2. Find: totalWeight % 26

        3. Map the remainder to a character by moving backwards from 'z'.
                remainder = 0  -> 'z'
                remainder = 1  -> 'y'
                remainder = 2  -> 'x'
           ...
     */

    static String mapWordWeights(String[] words, int[] weights) {

        // Stores the final mapped string.
        StringBuilder ans = new StringBuilder();

        // Process each word independently.
        for (String word : words) {

            int totalWeight = 0;

            /*
                Calculate the total weight of the current word.
             */
            for (char ch : word.toCharArray()) {

                // Convert character into an index from 0 to 25.
                int index = ch - 'a';

                totalWeight += weights[index];
            }

            /*
                Reduce the weight into the range [0, 25].
             */
            int remainder = totalWeight % 26;

            /*
                Map the remainder to a character.

                Example:

                remainder = 0 -> 'z'
                remainder = 1 -> 'y'
                remainder = 2 -> 'x'
             */
            char mappedChar = (char) ('z' - remainder);

            // Append the mapped character.
            ans.append(mappedChar);
        }

        return ans.toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = number of words & m = total number of characters across all words

---------------------------------------------------------

Time Complexity: O(m)

Reason: Every character of every word is processed exactly once.

Therefore: O(total characters)

---------------------------------------------------------

Space Complexity: O(n)

Reason: The output StringBuilder stores one mapped character per word.

If there are n words: Output Size = O(n)
Auxiliary Space = O(1)

---------------------------------------------------------

Key Observation:

Character indexing can be done using: ch - 'a'

which converts:

'a' -> 0
'b' -> 1
...
'z' -> 25

allowing direct lookup into the weights array in O(1).

---------------------------------------------------------
*/