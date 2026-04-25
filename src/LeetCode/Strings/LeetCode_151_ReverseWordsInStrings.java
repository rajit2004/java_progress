package LeetCode.Strings;

public class LeetCode_151_ReverseWordsInStrings {
    public static void main(String[] args) {
        String s = "the sky is blue";
        System.out.println(reverseWords(s));
    }
    static String reverseWords(String s) {
        // Step 1: remove extra spaces from start and end
        s = s.trim();

        // Step 2: split by one or more spaces
        String[] words = s.split("\\s+");

        // Step 3: reverse words
        int left = 0, right = words.length - 1;
        while (left < right) {
            String temp = words[left];
            words[left] = words[right];
            words[right] = temp;
            left++;
            right--;
        }

        // Step 4: join words with single space
        return String.join(" ", words);
    }
}
