package LeetCode.BitManipulation;

public class LeetCode_190_ReverseBit {
    public static void main(String[] args) {
        int n = 43261596;
        System.out.println(reverseBits(n));
    }
    static int reverseBits(int n) {
        int result = 0;

        for (int i = 0; i < 32; i++) {

            // Get last bit of n
            int lastBit = n & 1;

            // Shift result left to make space
            result = result << 1;

            // Add extracted bit
            result = result | lastBit;

            // Shift n right to process next bit
            n = n >> 1;
        }

        return result;
    }
}
