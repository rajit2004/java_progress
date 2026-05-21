package LeetCode.Strings;

/*
You are given a string title consisting of one or more words separated by a single space,
where each word consists of English letters.
Capitalize the string by changing the capitalization of each word such that:

        If the length of the word is 1 or 2 letters, change all letters to lowercase.
        Otherwise, change the first letter to uppercase and the remaining letters to lowercase.
        Return the capitalized title.
*/

public class LeetCode_2129_CapitalizeTheTitle {
    public static void main(String[] args) {
        String s = "capiTalIze tHe titLe";
        System.out.println(capitalizeTitle(s));
    }
    static String capitalizeTitle(String title) {
        title = title.toLowerCase();
        String[] words = title.split(" ");
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if(words[i].length() <= 2) {
                ans.append(words[i]);
            }
            else {
                ans.append(Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1));
            }
            if(i != words.length - 1) {
                ans.append(" ");
            }
        }

        return ans.toString();
    }
}