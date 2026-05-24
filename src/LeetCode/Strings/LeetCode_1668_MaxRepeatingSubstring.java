package LeetCode.Strings;

public class LeetCode_1668_MaxRepeatingSubstring {
    public static void main(String[] args) {
        String sequence = "ababc";
        String word = "ab";
        System.out.println(maxRepeating(sequence,word));
    }
    static int maxRepeating(String sequence, String word) {
        int count = 0;
        String repeated = word;

        while (sequence.contains(repeated)) {
            count++;
            repeated += word;
        }

        return count;
    }
}
