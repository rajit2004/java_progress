package Recursion.Questions.Arrays;

public class RotatedBinarySearch {
    public static void main(String[] args) {
        int[] arr = {5,6,7,8,9,1,2,3,4};
        System.out.println(find(arr , 9 , 0 , arr.length-1));
    }

    /*
The half without the big jump is the safe half — you can do normal binary search there
        because numbers are predictably increasing.
The half with the big jump is the unsafe half — normal greater/lesser logic breaks there
*/
    static int find(int[] arr, int target, int st, int end) {

        // find the middle index
        // using st+(end-st)/2 instead of (st+end)/2 to avoid number getting too big
        int mid = st + (end - st) / 2;

        // did we land exactly on the target? great, return its position
        if (arr[mid] == target)
            return mid;

        // --- the array was cut and flipped somewhere ---
        // example: [4,5,6,7,1,2,3] -> left side goes up normally, right side has a sudden drop
        // we need to figure out WHICH side has the sudden drop before we can search

        // if the middle number is LESS than the start number
        // that means the sudden drop happened somewhere on the LEFT side
        // so the RIGHT side (mid to end) is going up normally
        if (arr[mid] < arr[st]) {

            // is the target sitting between start and mid (value-wise)?
            // meaning: is it in the range that goes up normally on the left?
            if (target > arr[st] && target < arr[mid])
                // yes -> search the left side
                return find(arr, target, st, mid - 1);
            else
                // no -> it must be on the right side -> search there
                return find(arr, target, mid + 1, end);
        }

        // if we reach here, the sudden drop did NOT happen on the left side
        // meaning: left side (st to mid) is going up normally
        // example: [4,5,6,7,...] -> 4,5,6,7 all going up, safe to search here

        // is the target sitting between mid and end (value-wise)?
        // meaning: is it in the range that goes up normally on the right?
        if (arr[mid] < target && target < arr[end])
            // yes -> search the right side
            return find(arr, target, mid + 1, end);

        // no -> it must be on the left side -> search there
        return find(arr, target, st, mid - 1);
    }
}


