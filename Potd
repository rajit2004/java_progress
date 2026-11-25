class Solution {

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] nextRank = new int[n];
        int[] temp = new int[n];

        for (int i = 0; i < n; ++i) {
            sa[i] = i;
            rank[i] = nums[i];
        }

        int maxRank = 0;
        for (int x : nums) {
            maxRank = Math.max(maxRank, x);
        }

        int cntSize = Math.max(n + 1, maxRank + 2);
        int[] cnt = new int[cntSize];

        for (int k = 1; k < n; k <<= 1) {
            final int kk = k;
            // Sort by the second key first
            Arrays.fill(cnt, 0);
            for (int i : sa) {
                int key = (i + kk < n ? rank[i + kk] : -1) + 1;
                ++cnt[key];
            }
            for (int i = 1; i <= maxRank + 1; ++i) cnt[i] += cnt[i - 1];
            for (int p = n - 1; p >= 0; --p) {
                int i = sa[p];
                int key = (i + kk < n ? rank[i + kk] : -1) + 1;
                temp[--cnt[key]] = i;
            }
            int[] swap = sa;
            sa = temp;
            temp = swap;

            // Then stably sort by the first key
            Arrays.fill(cnt, 0);
            for (int i : sa) {
                int key = rank[i] + 1;
                ++cnt[key];
            }
            for (int i = 1; i <= maxRank + 1; ++i) {
                cnt[i] += cnt[i - 1];
            }
            for (int p = n - 1; p >= 0; --p) {
                int i = sa[p];
                int key = rank[i] + 1;
                temp[--cnt[key]] = i;
            }
            swap = sa;
            sa = temp;
            temp = swap;

            nextRank[sa[0]] = 0;
            for (int i = 1; i < n; ++i) {
                int a = sa[i - 1],
                    b = sa[i];
                int ax = rank[a],
                    ay = a + kk < n ? rank[a + kk] : -1;
                int bx = rank[b],
                    by = b + kk < n ? rank[b + kk] : -1;
                nextRank[b] = nextRank[a] + (ax != bx || ay != by ? 1 : 0);
            }

            int[] t = rank;
            rank = nextRank;
            nextRank = t;
            maxRank = rank[sa[n - 1]];

            if (maxRank == n - 1) {
                break;
            }
        }

        // Kasai LCP
        int[] pos = new int[n];
        int[] lcp = new int[n];
        for (int i = 0; i < n; ++i) pos[sa[i]] = i;

        int h = 0;
        for (int i = 0; i < n; ++i) {
            if (pos[i] == 0) {
                h = 0;
                continue;
            }
            int j = sa[pos[i] - 1];
            while (i + h < n && j + h < n && nums[i + h] == nums[j + h]) {
                ++h;
            }
            lcp[pos[i]] = h;
            if (h > 0) {
                --h;
            }
        }

        int answer = n;
        for (int i = 0; i < n; ++i) {
            int next = i + 1 < n ? lcp[i + 1] : 0;
            int need = Math.max(lcp[i], next) + 1;
            if (sa[i] + need - 1 < n) {
                answer = Math.min(answer, need);
            }
        }

        return answer;
    }
}
