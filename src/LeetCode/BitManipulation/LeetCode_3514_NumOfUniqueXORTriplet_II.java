package LeetCode.BitManipulation;

public class LeetCode_3514_NumOfUniqueXORTriplet_II {
    public static void main(String[] args) {

        int[] nums = {1, 2, 3};

        System.out.println(uniqueXorTriplets(nums));
    }

    /*
        XOR Preprocessing Approach :
        Step 1: Compute the XOR of every possible pair (including the same element twice).
        Step 2: XOR every pair result with every array element to generate all possible triplet XOR values.
        Step 3: Count how many distinct XOR values exist.
     */
    static int uniqueXorTriplets(int[] nums) {

        final int MAX_XOR = 2048;

//            pairXor[x] == true means there exists at least one pair whose XOR value equals x.
        boolean[] pairXor = new boolean[MAX_XOR];


//            tripletXor[x] == true means there exists at least one triplet whose XOR value equals x.

        boolean[] tripletXor = new boolean[MAX_XOR];

        int n = nums.length;

        // Compute the XOR of every possible pair.
        for (int i = 0; i < n; i++) {

            for (int j = i; j < n; j++) {

                pairXor[nums[i] ^ nums[j]] = true;
            }
        }

//            Combine every valid pair XOR with every array element to generate all possible triplet XOR values.
        for (int xorValue = 0; xorValue < MAX_XOR; xorValue++) {

            if (!pairXor[xorValue]) {
                continue;
            }

            for (int value : nums) {

                tripletXor[xorValue ^ value] = true;
            }
        }

        // Count the number of distinct triplet XOR values.
        int count = 0;

        for (boolean exists : tripletXor) {

            if (exists) {
                count++;
            }
        }

        return count;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:n = nums.length

---------------------------------------------------------

Time Complexity: O(n² + MAX_XOR × n)

Reason:

1. Compute XOR of every pair: O(n²)

2. Generate all possible triplet XOR values: O(MAX_XOR × n)

3. Count distinct XOR values: O(MAX_XOR)

Overall: O(n² + MAX_XOR × n)

---------------------------------------------------------

Space Complexity: O(MAX_XOR)

Reason: Two boolean arrays of size MAX_XOR are maintained.

Overall: O(MAX_XOR)

---------------------------------------------------------

Key Observation: Every triplet XOR can be represented as: (a XOR b) XOR c

Instead of checking every possible triplet, first compute every possible pair XOR. Then combine each pair XOR with every array element to generate all distinct triplet XOR values efficiently.

---------------------------------------------------------
*/