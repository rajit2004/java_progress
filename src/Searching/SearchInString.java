package Searching;

public class SearchInString {
    public static void main(String[] args) {
        String name = "Ranesh Rajit";
        char target = 't';
        System.out.println(find(name , target));
    }
    static boolean find(String s , char target){
        if(s.length()==0)
            return false;
        for(int i = 0 ; i< s.length() ; i++){
            if(target == s.charAt(i))
                return true;
        }
        return false;
    }
}
