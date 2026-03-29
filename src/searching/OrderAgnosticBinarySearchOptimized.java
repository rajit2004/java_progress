package searching;

public class OrderAgnosticBinarySearchOptimized {
    public static void main(String[] args) {
        int[] arr1 = {2, 5, 8, 12, 16, 23, 38, 45, 56, 72};
        int target1 = 2;
        System.out.println(oabs(arr1, target1));
        int[] arr2 = {90, 75, 60, 50, 42, 31, 20, 15, 10, 3};
        int target2 = 15;
        System.out.println(oabs(arr2, target2));
    }

    static int oabs(int[] arr, int target) {

        // Edge case: empty array
        if (arr.length == 0) return -1;

        // Initialize search boundaries
        int start = 0;
        int end = arr.length - 1;

        // Determine if array is sorted in ascending or descending order
        boolean isAsc = arr[start] < arr[end];

        // Binary search loop
        while (start <= end) {

            // Find middle index (avoids overflow)
            int mid = start + (end - start) / 2;

            // If target is found, return its index
            if (arr[mid] == target) return mid;

            // If array is sorted in ascending order
            if (isAsc) {

                // Target lies in left half
                if (target < arr[mid])
                    end = mid - 1;
                // Target lies in right half
                else
                    start = mid + 1;

            } else { // Array is sorted in descending order

                // Target lies in right half (reverse logic)
                if (target < arr[mid])
                    start = mid + 1;
                // Target lies in left half
                else
                    end = mid - 1;
            }
        }
        // Target not found
        return -1;
    }
}