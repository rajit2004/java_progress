class Solution {

    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        long ans = 0;
        long operations = 0;
        // Maintain the window maximum with a priority queue
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        // Record the set of elements covered by this index
        List<List<Integer>> cnt = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            cnt.add(new ArrayList<>());
        }
        // Index of the first greater-or-equal element to the right
        int[] pos = new int[n];
        Arrays.fill(pos, n);
        // Decreasing monotonic stack
        Deque<Integer> st = new ArrayDeque<>();

        for (int left = 0, right = 0; right < n; right++) {
            // Maintain the monotonic stack
            while (!st.isEmpty() && nums[right] >= nums[st.peek()]) {
                pos[st.peek()] = right;
                st.pop();
            }
            // The stack top is the nearest greater element to the left of nums[right]
            if (!st.isEmpty()) {
                cnt.get(st.peek()).add(right);
            }
            st.push(right);

            // The priority queue front is the window maximum
            q.offer(new int[] { nums[right], right });
            // Adding nums[right] increases the operation count
            operations += Math.max(0, q.peek()[0] - nums[right]);

            // The minimum operation count exceeds k; shrink the window
            while (operations > k) {
                // Removing nums[left] from the window decreases the operation count
                for (int i : cnt.get(left)) {
                    // Subtract the contribution
                    operations -=
                        (long) (nums[left] - nums[i]) *
                        (Math.min(pos[i], right + 1) - i);
                }
                left++;
            }

            // Remove elements with indices less than left from the priority queue
            while (!q.isEmpty() && q.peek()[1] < left) {
                q.poll();
            }

            // There are right - left + 1 valid contiguous subarrays ending at right
            ans += right - left + 1;
        }

        return ans;
    }
}
