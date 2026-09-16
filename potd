class Solution {

    private static final long MOD = 1000000007L;

    private long quickPow(long a, long e) {
        long result = 1;
        while (e > 0) {
            if ((e & 1) != 0) result = (result * a) % MOD;
            a = (a * a) % MOD;
            e >>= 1;
        }
        return result;
    }

    public int numberOfSets(int n, int k) {
        int m = 2 * k;
        long numerator = 1, denominator = 1;
        for (int i = 1; i <= m; i++) {
            numerator = (numerator * (n + k - i)) % MOD;
            denominator = (denominator * i) % MOD;
        }
        return (int) ((numerator * quickPow(denominator, MOD - 2)) % MOD);
    }
}
