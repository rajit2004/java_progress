class Solution {

    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        List<Integer> fac = new ArrayList<>();

        for (int x = 1; x * x <= n; x++) {
            if (n % x == 0) {
                fac.add(x);
                if (n / x != x) {
                    fac.add(n / x);
                }
            }
        }

        int[] target = nums.clone();
        Arrays.sort(target);

        int ans = 0;

        for (int d : fac) {
            boolean allGood = true;
            for (int left = 0; left < n; left += d) {
                int right = left + d - 1;

                int[] sub1 = Arrays.copyOfRange(nums, left, right + 1);
                int[] sub2 = Arrays.copyOfRange(target, left, right + 1);

                if (!check(sub1, sub2)) {
                    allGood = false;
                    break;
                }
            }
            if (allGood) {
                ans += d;
            }
        }

        return ans;
    }

    // Check whether cyclic rotations of nums1 can produce nums2
    private boolean check(int[] nums1, int[] nums2) {
        int L = nums1.length;

        for (int i = 0; i < L - 1; i++) {
            if (nums1[i] > nums1[i + 1]) {
                int[] rotated = new int[L];
                System.arraycopy(nums1, i + 1, rotated, 0, L - i - 1);
                System.arraycopy(nums1, 0, rotated, L - i - 1, i + 1);
                return Arrays.equals(rotated, nums2);
            }
        }

        return Arrays.equals(nums1, nums2);
    }
}
