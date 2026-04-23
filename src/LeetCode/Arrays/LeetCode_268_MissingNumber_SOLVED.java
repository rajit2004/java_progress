package LeetCode;

public class LeetCode_268_MissingNumber_SOLVED {
    public static void main(String[] args) {
        int[] nums = {1,2};
        System.out.println(missingNumber(nums));
    }

    static void swap(int[] arr , int num1 , int num2){
        int temp = arr[num1];
        arr[num1] = arr[num2];
        arr[num2] = temp;
    }

    static int missingNumber(int[] arr){
//        first cycle sort :
        int i = 0;

        while (i<arr.length){
            int correctIndex = arr[i];

            if(arr[i]< arr.length && arr[i] != arr[correctIndex])
                swap(arr,i,correctIndex);
            else
                i++;
        }

//        find missing number :
        int ans = 0;

        for(i = 0 ; i< arr.length ; i++){

//            case 1: n in array
            if(arr[i] != i)
               return i;
        }
//        case 2 : n not in array
        return arr.length;
    }
}
