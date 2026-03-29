package searching;

public class BinarySearchDescending {
    public static void main(String[] args) {
        int[] nums = {19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        System.out.println(search(nums , 16));

    }
    static int search(int[]arr , int target){
        int st = 0;
        int end = arr.length-1;
        while (st <= end){
            int mid = st + (end-st)/2;
            if(target<arr[mid])
                st = mid+1;
            else if(target>arr[mid])
                end = mid -1;
            else
                return mid;
        }
        return -1;
    }
}

