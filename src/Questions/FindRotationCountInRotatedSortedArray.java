package Questions;

public class FindRotationCountInRotatedSortedArray {
    public static void main(String[] args) {
        int[] arr = {15,18,2,3,6,12};
        System.out.println(count(arr));
        System.out.println("Num of rotations using min index : " + minIndex(arr));
    }
    static int pivotIndex(int[] arr){
        int st = 0;
        int end = arr.length-1;

        while(st<=end){
         int mid = st + (end-st) / 2 ;

         if(mid < end && arr[mid]>arr[mid+1])
             return mid;
         else if(mid > st && arr[mid]<arr[mid-1])
             return mid-1;
         else if(arr[st] >= arr[mid])
             end = mid -1;
         else
             st = mid+1;
        }
        return -1;
    }
    static int count(int[] arr){
        int pivot = pivotIndex((arr));
        return pivot + 1;
    }

//    alternative approach => no of rotations = index of min element
    static int minIndex(int[] arr){
        int min = arr[0];
        int index = 0;
        for(int i = 0 ; i< arr.length ; i++){
            if(arr[i]<min){
                min = arr[i];
                index = i;
            }
        }
        return index;
    }
}
