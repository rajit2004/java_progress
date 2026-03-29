package searching;

public class FindMin {
    public static void main(String[] args) {

        int[] nums = {-12, 45, -7, 89, -23, 56, -91, 34, -18, 67, 2, -78, 39, -50, 6, 99, -27, 41, -73, 15};

        System.out.println(min(nums));

    }
    static int min(int[] arr){
        int minimum = 0;
        for(int ele : arr){
            if(ele<minimum)
                minimum = ele;
        }
        return minimum;
    }
}
