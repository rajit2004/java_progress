package Recursion.Questions.Strings;

public class Skip_A_Character {
    public static void main(String[] args) {
        String word = "Apple";
        System.out.println(skip(word));
    }

//    brute force :
    static StringBuilder skip(String s ){
        StringBuilder ans = new StringBuilder();
        for(int i = 0 ; i < s.length() ; i++){
            if(s.charAt(i) != 'p')
                ans.append(s.charAt(i));


        }
        return ans;

    }
}
