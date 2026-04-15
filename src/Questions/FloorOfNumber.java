package Questions;

public class FloorOfNumber {
    public static void main(String[] args) {
        int[] arr = {2,3,5,9,14,16,18};
        int target = 15;
        System.out.println(floor(arr,target));
    }
    static int floor(int[] array , int target){

        if(target<array[0])
            return 0;       // tells that the target elements is smaller that the smallest element itself

        int st = 0;
        int end = array.length-1;

        while(st<=end){
            int mid = st + (end-st)/2;

            if(array[mid] == target)
                return mid;
            else
                if(target>array[mid])
                    st = mid+1;
                else
                    end = mid-1;
        }
        return array[end];
    }
}
