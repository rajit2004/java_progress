package Questions.BitwiseOperators;

// Given an array with nums appearing twice except a single number which appears once . Find the single number.


/*
        Approach :
            any number xor by itself gives 0.
            we traverse through nums keeping xor with each other .
            the net xor will be getting canceled out by each other and in the end we will just have the unique element
*/

public class SingleUniqueElement {
    public static void main(String[] args) {
        int[] arr = {2,3,4,1,2,1,3,6,4};
        System.out.println(ans(arr));
    }
    static int ans(int nums[]){
        int unique = 0;

        for(int ele : nums)
            unique ^= ele;

        return unique;
    }
}
