//Given an array nums of integers, return how many of them contain an even number of digits.

package searching_sorting;

public class Count_no_of_digits {
    public static void main(String[] args) {
        int[] nums = {12, -5, 27, 3, 18, 9, -41, -7, 221, -14};

        System.out.println(occurrences(nums));
    }
    static int count(int num){
        if (num == 0)
            return 1;
        if(num<0)
            num = num*-1;
        int c  = 0;
        while(num>0){
            c++;
            num = num/10;
        }
        return c;
    }
    static boolean isEven(int num){
            if(count(num)%2 == 0)
                return true;
        return false;
    }
    static int occurrences(int[] nums){
        int occur = 0;
        for(int element : nums){
            if(isEven(element))
                occur++;
        }
        return occur;
    }
}