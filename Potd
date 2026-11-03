class Solution {

    public int minMoves(int[] nums) {
        int mx = nums[0];
        for (int x : nums) {
            mx = Math.max(mx, x);
        }
        int ans = 0;
        for (int x : nums) {
            ans += mx - x;
        }
        return ans;
    }
}
