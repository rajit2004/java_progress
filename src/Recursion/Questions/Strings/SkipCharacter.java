package Recursion.Questions.Strings;

public class SkipCharacter {
    public static void main(String[] args) {
        String word = "baccad";
        System.out.println(remove(word));
    }

//    iterative approach :
    static String remove(String s){
        StringBuilder ans = new StringBuilder();
        for(char c : s.toCharArray()){
            if(c != 'a')
                ans.append(c);
        }
        return ans.toString();
    }

//    recursive approach :
    static String recursiveRemove(String s){
        if(s.isEmpty())
            return s;

        return ans;
    }
}
