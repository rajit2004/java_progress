package LeetCode;

/*
A sentence is a list of words that are separated by a single space with no leading or trailing spaces.
You are given an array of strings sentences, where each sentences[i] represents a single sentence.
Return the maximum number of words that appear in a single sentence.
*/

public class LeetCode_2114_MaxWordsInSentence {
    public static void main(String[] args) {
        String[] sentences = {"alice and bob love leetcode", "i think so too", "this is great thanks very much"};
        System.out.println(mostWordsFound(sentences));
    }
    static int mostWordsFound(String[] sentences){
        int maxLen = 0;
        for (String currSent : sentences) {
            int currLen = currSent.split(" ").length;
            if (maxLen < currLen)
                maxLen = currLen;
        }
        return maxLen;
    }

// Alternative method :

    static int mostWordsFound2(String[] sentences) {
        int max = 0;

        for (String s : sentences) {
            int words = 1; // at least 1 word

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ') {
                    words++;
                }
            }

            max = Math.max(max, words);
        }

        return max;
    }

// Alternative method :

    static int mostWordsFound3(String[] sentences) {
        int maxwords= 0;
        for(String Sentences: sentences){
            int count = 1;
            for (char ch : Sentences.toCharArray()){
                if (ch == ' '){
                    count++;
                }
            }
            maxwords= Math.max(maxwords,count);
        }
        return maxwords;
    }

// Alternative method :

    static int mostWordsFound4(String[] sentences) {
        int maxWordCount = 0;
        for (String eachSentence : sentences) {
            int words = getWordCount(eachSentence);
            if (words > maxWordCount) {
                maxWordCount = words;
            }
        }
        return maxWordCount;
    }

    static int getWordCount(String sentence) {
        int wordCount = 1;
        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) == ' ') {
                wordCount++;
            }
        }
        return wordCount;
    }
}
