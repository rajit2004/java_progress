package LeetCode;

public class LeetCode_1346_CheckForDouble {
    public static void main(String[] args) {
        int[] nums = {0,-2,2};
        System.out.println(checkIfExist(nums));

    }
    static boolean checkIfExist(int[] arr){
        for(int i = 0 ; i < arr.length ; i++){
            for(int j = 0 ; j < arr.length ; j++){
                if(i != j && arr[i] == arr[j]*2)
                    return true;
            }
        }
        return false;
    }
}
