package searching;

public class linear_search_int {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8,9,10};
        int target = 5;

        System.out.println(find(array,target));
    }
    static String find(int[] arr , int target){
        if(arr.length==0)
            return "Empty Array";
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == target)
                return "Found at index :" + i;
        }
        return "Not found!";
    }
}
