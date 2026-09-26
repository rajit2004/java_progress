package LeetCode.Arrays;

import java.util.*;

public class LeetCode_1807_EvaluateTheBracketPairsOfString {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): s = "(name)is(age)yearsold", knowledge = [[name, bob], [age, two]]
        //   -> "bobistwoyearsold"
        System.out.println("Sample 1 -> " + evaluate("(name)is(age)yearsold",
                Arrays.asList(Arrays.asList("name", "bob"), Arrays.asList("age", "two")))
                + " (expected: bobistwoyearsold)");

        // Sample 2 (LeetCode): s = "hi(name)", knowledge = [[a, b]] -> "hi?"
        System.out.println("Sample 2 -> " + evaluate("hi(name)",
                Arrays.asList(Arrays.asList("a", "b")))
                + " (expected: hi?)");

        // Sample 3 (LeetCode): s = "(a)(a)(a)aaa", knowledge = [[a, yes]] -> "yesyesyesaaa"
        System.out.println("Sample 3 -> " + evaluate("(a)(a)(a)aaa",
                Arrays.asList(Arrays.asList("a", "yes")))
                + " (expected: yesyesyesaaa)");

        // Edge case: no brackets at all -> return s unchanged
        System.out.println("Edge (no brackets) -> " + evaluate("hello",
                Arrays.asList(Arrays.asList("x", "y")))
                + " (expected: hello)");

        // Edge case: empty knowledge -> all keys become '?'
        System.out.println("Edge (empty knowledge) -> " + evaluate("(a)(b)",
                new ArrayList<>())
                + " (expected: ??)");

        // Edge case: key maps to empty string -> replaced by empty
        System.out.println("Edge (empty value) -> " + evaluate("x(a)y",
                Arrays.asList(Arrays.asList("a", "")))
                + " (expected: xy)");

        // Edge case: adjacent keys with no separator
        System.out.println("Edge (adjacent keys) -> " + evaluate("(a)(b)",
                Arrays.asList(Arrays.asList("a", "1"), Arrays.asList("b", "2")))
                + " (expected: 12)");

        // Edge case: empty string
        System.out.println("Edge (empty s) -> " + evaluate("",
                Arrays.asList(Arrays.asList("a", "b")))
                + " (expected: )");
    }


    /*
        Approach: Single-pass scan with a key buffer

        Build a HashMap from `knowledge` for O(1) key lookup.

        Then scan `s` character by character with a boolean flag `addKey`:
            - '('       -> start collecting a key:  addKey = true
            - ')'       -> flush the buffered key:
                             append its mapped value, or '?' if missing
                             reset the buffer, addKey = false
            - addKey    -> append current char to the key buffer
            - otherwise -> append current char directly to the result

        After scanning, `res` is the evaluated string.

        Why a StringBuilder for the key:
            Keys can be up to 10 chars per constraint, and we might encounter
            many of them. Reusing one buffer (with setLength(0)) avoids
            repeated allocations of small strings.

        Time:  O(n + k) where n = s.length(), k = total chars in knowledge
        Space: O(k) for the dictionary + O(n) for the output
    */
    static String evaluate(String s, List<List<String>> knowledge) {
        // Build lookup dictionary: key -> replacement value
        Map<String, String> dict = new HashMap<>();
        for (List<String> kd : knowledge) {
            dict.put(kd.get(0), kd.get(1));
        }

        boolean addKey = false;                 // true while inside "( ... )"
        StringBuilder key = new StringBuilder(); // buffer for the current key
        StringBuilder res = new StringBuilder(); // final output

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                addKey = true;                  // start collecting a key
            } else if (c == ')') {
                // End of key: replace with mapped value or '?'
                String k = key.toString();
                res.append(dict.getOrDefault(k, "?"));
                addKey = false;
                key.setLength(0);               // reset buffer for next key
            } else if (addKey) {
                key.append(c);                  // accumulate key character
            } else {
                res.append(c);                  // plain character outside brackets
            }
        }

        return res.toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Single-pass scan with a key buffer

Time Complexity: O(n + k)

- n = s.length(), k = total characters across all knowledge entries.
- O(k) to build the hash map.
- O(n) to scan s once; each character is either copied verbatim or
  appended to a key buffer, and each ')' triggers one O(1) map lookup.
- Total length of all keys is <= n, so the extra key-processing work is O(n).

Space Complexity: O(k + n)

- O(k) for the dictionary.
- O(n) for the output StringBuilder (result can be longer than s if any
  value replaces a short key with a longer string).

Key Observation:
The grammar is trivial — brackets never nest and never span more than one
key — so no stack is needed. A single boolean flag distinguishes "inside a
key" from "plain text", and a HashMap turns each key into its replacement
in O(1).

---------------------------------------------------------
*/