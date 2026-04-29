package LeetCode.BinarySearch;

public class LeetCode_374_GuessNum {

    // Simulated picked number (hidden in real LeetCode)
    static int picked = 6;          // guesses

    public static void main(String[] args) {
        int n = 6;         // actual number
        int result = guessNumber(n);
        System.out.println("Guessed number: " + result);
    }

    static int guessNumber(int n) {
        int low = 1;
        int high = n;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int res = guess(mid);

            if (res == 0) {
                return mid;
            } else if (res == -1) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1; // should never reach here
    }

    // Simulated guess API
    static int guess(int num) {
        if (num == picked) {
            return 0;
        } else if (num > picked) {
            return -1;
        } else {
            return 1;
        }
    }
}