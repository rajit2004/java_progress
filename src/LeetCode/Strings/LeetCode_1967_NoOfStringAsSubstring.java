package LeetCode.Strings;

/*
Given an array of strings patterns and a string word,
return the number of strings in patterns that exist as a substring in word.
*/

public class LeetCode_1967_NoOfStringAsSubstring {
    public static void main(String[] args) {
        String word = "abc";
        String[] patterns ={"a","abc","bc","d"};
        System.out.println(numOfStrings(patterns , word));
    }
    static int numOfStrings(String[] patterns, String word){
        int count = 0;
        for (String substring : patterns) {
            if (word.contains(substring))
                count++;
        }
        return count;
    }
}
