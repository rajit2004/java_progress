package LeetCode.Strings;

public class LeetCode_1071_GCD_ofStrings {
    public static void main(String[] args) {

        String str1 = "ABCABC";
        String str2 = "ABC";

        System.out.println(gcdOfStrings(str1, str2));
    }

    /*
        Math + String Approach :

        Two strings can have a common divisor only if:

            str1 + str2 == str2 + str1

        If this condition is not satisfied,
        no common divisor string exists.

        Otherwise, the answer is the prefix whose
        length equals the GCD of the two string lengths.
    */
    static String gcdOfStrings(String str1, String str2) {

        // If concatenations differ, no common divisor exists.
        if (!(str1 + str2).equals(str2 + str1))
            return "";

        // Find the GCD of the string lengths.
        int gcdLength = gcd(str1.length(), str2.length());

        // The required divisor is the prefix of length gcdLength.
        return str1.substring(0, gcdLength);
    }

    /*
        Euclidean Algorithm

        Computes the Greatest Common Divisor (GCD)
        of two integers.
    */
    static int gcd(int a, int b) {

        if (b == 0)
            return a;

        return gcd(b, a % b);
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = str1.length()
m = str2.length()

---------------------------------------------------------

Time Complexity: O(n + m)

Reason:

1. Comparing the concatenated strings:
   O(n + m)

2. Computing the GCD of the lengths:
   O(log(min(n, m)))

3. Extracting the substring:
   O(gcdLength)

Overall:

O(n + m)

---------------------------------------------------------

Space Complexity: O(n + m)

Reason:

The concatenated strings:

str1 + str2
str2 + str1

require additional space.

Overall:

O(n + m)

---------------------------------------------------------

Key Observation:

If two strings are built by repeating the same
base pattern, then:

str1 + str2 == str2 + str1

Once this condition holds, the largest common
divisor string is simply the prefix whose length
equals the GCD of the two string lengths.

---------------------------------------------------------
*/