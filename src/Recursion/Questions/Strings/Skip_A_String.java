package Recursion.Questions.Strings;

/*
Complexity Notes:

Iterative:
Time  : O(n)
Space : O(n)

Recursive:
Time  : O(n²)    (substring creates new strings)
Space : O(n²)

Recursion with substring() often looks O(n),
but hidden string creation can make it O(n²).
*/



public class Skip_A_String {
    public static void main(String[] args) {
        System.out.println(skip("bananaapple23"));
        System.out.println(skipStr("qwertyapple12ele"));
    }

    // Iterative Approach:
    // Traverse the string character by character.
    // If "apple" starts at the current index,
    // skip all 5 characters of "apple".
    // Otherwise, keep the current character.
    static String skip(String word){
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {

            // if you found "apple" starting at index i then skip the entire word.
            if(word.startsWith("apple", i)){
                i += 4;      // loop's i++ skips the 5th character
                continue;
            }

            // if the current character is not part of "apple", so include it in the answer.
            ans.append(word.charAt(i));
        }

        return ans.toString();
    }

    // Recursive Approach:
    // Process one character (or one word) at a time.
    // If the current string starts with "apple", remove it and solve the remaining string.
    static String skipStr(String s){

        // Base Condition:
        // Nothing left to process.
        if(s.isEmpty())
            return "";

        // If "apple" is found at the beginning, skip all 5 characters and recurse on the rest.
        if(s.startsWith("apple"))
            return skipStr(s.substring(5));

        // else keep the first character and recurse on the remaining string.
        return s.charAt(0) + skipStr(s.substring(1));
    }
}