import java.util.List;

class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        
        int minSum = Integer.MAX_VALUE;
        for (int i = l; i <= r; i++) {
            int currSum = 0;

            for (int j = 0; j < i; j++) {
                currSum += nums.get(j);
            }
            if (currSum > 0) {
                minSum = Math.min(minSum, currSum);
            }

            int low = 0, high = i;

            while (high < nums.size()) {
                currSum -= nums.get(low);
                currSum += nums.get(high);

                low++;
                high++;

                if (currSum > 0) {
                    minSum = Math.min(minSum, currSum);
                }
            }
        }
        if (minSum == Integer.MAX_VALUE) return -1;
        return minSum;
    }
}
