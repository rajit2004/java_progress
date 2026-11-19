class Solution {

    public int minimizeSum(int[] nums) {
        int mn1 = Integer.MAX_VALUE,
            mn2 = Integer.MAX_VALUE,
            mn3 = Integer.MAX_VALUE;
        int mx1 = Integer.MIN_VALUE,
            mx2 = Integer.MIN_VALUE,
            mx3 = Integer.MIN_VALUE;

        for (int x : nums) {
            if (x < mn1) {
                mn3 = mn2;
                mn2 = mn1;
                mn1 = x;
            } else if (x < mn2) {
                mn3 = mn2;
                mn2 = x;
            } else if (x < mn3) {
                mn3 = x;
            }

            if (x > mx1) {
                mx3 = mx2;
                mx2 = mx1;
                mx1 = x;
            } else if (x > mx2) {
                mx3 = mx2;
                mx2 = x;
            } else if (x > mx3) {
                mx3 = x;
            }
        }

        return Math.min(mx1 - mn3, Math.min(mx2 - mn2, mx3 - mn1));
    }
}
