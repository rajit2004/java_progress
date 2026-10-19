class Solution {

    public List<Integer> targetIndices(int[] nums, int target) {
        int cnt1 = 0; // Number of elements less than target
        int cnt2 = 0; // Number of elements equal to target

        for (int num : nums) {
            if (num < target) {
                cnt1++;
            } else if (num == target) {
                cnt2++;
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = cnt1; i < cnt1 + cnt2; i++) {
            res.add(i);
        }
        return res;
    }
}
