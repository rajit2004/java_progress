package LeetCode.Strings;

public class LeetCode_557_ReverseWordsInString3 {
    public static void main(String[] args) {
        String s = "Let's take LeetCode contest";
        System.out.println(reverseWords(s));
    }
    static String reverseWords(String s){
        String[] arr = s.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String word = new StringBuilder(arr[i]).reverse().toString();
            builder.append(word);

            if(i != arr.length - 1)
                builder.append(" ");

        }
        return builder.toString();
    }
}
