class Solution {

    public int minOperations(String s) {
        int n = s.length();
        char mx = 'a',
            mn = 'z';
        boolean sorted = true;
        for (int i = 0; i < n; i++) {
            mx = (char) Math.max(mx, s.charAt(i));
            mn = (char) Math.min(mn, s.charAt(i));
            if (i > 0 && s.charAt(i - 1) > s.charAt(i)) {
                sorted = false;
            }
        }
        if (sorted) {
            return 0;
        }
        if (n == 2) {
            return -1;
        }
        if (s.charAt(0) == mn || s.charAt(n - 1) == mx) {
            return 1;
        }
        for (int i = 1; i < n - 1; i++) {
            if (s.charAt(i) == mn || s.charAt(i) == mx) {
                return 2;
            }
        }
        return 3;
    }
}
