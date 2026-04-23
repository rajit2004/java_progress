package LeetCode;

public class LeetCode_205_ALTERNATE {
    public static void main(String[] args) {
        String s = "egg";
        String t = "ape";
        System.out.println(isIsomorphic(s,t));
    }
    static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] mapST = new int[256];
        int[] mapTS = new int[256];

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            // If mapping doesn't match → not isomorphic
            if (mapST[c1] != mapTS[c2]) {
                return false;
            }

            // Store index+1 (to avoid default 0 confusion)
            mapST[c1] = i + 1;
            mapTS[c2] = i + 1;
        }

        return true;
    }

}
