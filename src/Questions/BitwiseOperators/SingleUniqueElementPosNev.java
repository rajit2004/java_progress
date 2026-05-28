package Questions.BitwiseOperators;

/*
        Approach =
            we keep on adding all the elements together while the positive and the negative pairs will cancel out each other
            leaving us with only the unique element in the array
*/

public class SingleUniqueElementPosNev {
    public static void main(String[] args) {
        int[] arr = {2,3,-2,4,5,-5,-4};
        System.out.println(ans(arr));
    }
    static int ans(int[] nums){

        int sum = 0;

        for(int ele : nums)
            sum += ele;

        return sum;
    }
}
