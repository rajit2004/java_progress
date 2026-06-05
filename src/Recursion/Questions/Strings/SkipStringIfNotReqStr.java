package Recursion.Questions.Strings;

// ex : skip app if its not from apple

public class SkipStringIfNotReqStr {
    public static void main(String[] args) {
        System.out.println(skip("qwertappyapple132"));

    }
    static String skip(String word){
        if(word.isEmpty())
            return "";

        if(word.startsWith("app") && !word.startsWith("apple"))
            return skip(word.substring(3));         // app = 3 charas
        return word.charAt(0) + skip(word.substring(1));
    }
}
