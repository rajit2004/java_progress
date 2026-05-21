package LeetCode.Strings;

/*
Given two string arrays word1 and word2,
return true if the two arrays represent the same string, and false otherwise.
*/

public class LeetCode_1662_CheckTwoStringsAreEquivalent {
    public static void main(String[] args) {
        String[] word1 = {"ab" , "c"};
        String[] word2 = {"a" , "bc"};
        System.out.println(arrayStringsAreEqual(word1 , word2));
    }
    static boolean arrayStringsAreEqual(String[] word1, String[] word2){
        StringBuilder build1 = new StringBuilder();
        for (int i = 0; i < word1.length; i++)
            build1.append(word1[i]);

        StringBuilder build2 = new StringBuilder();
        for (int i = 0; i < word2.length; i++)
            build2.append(word2[i]);

        if (build1.toString().contentEquals(build2))
            return true;

        return false;
    }
}
