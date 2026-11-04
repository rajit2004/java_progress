class Solution {

    private static class MultiSet {

        TreeMap<Integer, Integer> counts = new TreeMap<>();
        int size = 0;
        long sum = 0;

        void add(int value) {
            counts.put(value, counts.getOrDefault(value, 0) + 1);
            ++size;
            sum += value;
        }

        void remove(int value) {
            int count = counts.get(value);
            if (count == 1) {
                counts.remove(value);
            } else {
                counts.put(value, count - 1);
            }
            --size;
            sum -= value;
        }

        int min() {
            return counts.firstKey();
        }

        int max() {
            return counts.lastKey();
        }
    }

    private long[] getCosts(int[] nums, int x) {
        int n = nums.length;
        long[] ops = new long[n];
        MultiSet left = new MultiSet();
        MultiSet right = new MultiSet();

        for (int i = 0; i < n; ++i) {
            int value = nums[i];
            if (left.size == 0 || value <= left.max()) {
                left.add(value);
            } else {
                right.add(value);
            }
            if (i >= x) {
                value = nums[i - x];
                if (left.counts.containsKey(value)) {
                    left.remove(value);
                } else {
                    right.remove(value);
                }
            }
            if (left.size > right.size + 1) {
                value = left.max();
                left.remove(value);
                right.add(value);
            } else if (left.size < right.size) {
                value = right.min();
                right.remove(value);
                left.add(value);
            }
            if (i >= x - 1) {
                long mid = left.max();
                ops[i] =
                    mid * left.size - left.sum + right.sum - mid * right.size;
            }
        }
        return ops;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] ops = getCosts(nums, x);
        long[] pre = new long[n + 1];
        long[] cur = new long[n + 1];

        for (int j = 1; j <= k; ++j) {
            Arrays.fill(cur, Long.MAX_VALUE);
            for (int i = j * x; i <= n; ++i) {
                cur[i] = Math.min(cur[i - 1], pre[i - x] + ops[i - 1]);
            }
            long[] temp = pre;
            pre = cur;
            cur = temp;
        }
        return pre[n];
    }
}
