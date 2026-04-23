package LeetCode;

public class LeetCode_205_Isomorphic_String {
    public static void main(String[] args) {
        String s = "egg";
        String t = "ape";
        System.out.println(isIsomorphic(s,t));
    }
    static boolean isIsomorphic(String s, String t){
        if (s.length() != t.length()) return false;

        for (int i = 0; i < s.length(); i++) {
            // Compare first occurrence positions
            if (s.indexOf(s.charAt(i)) != t.indexOf(t.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
