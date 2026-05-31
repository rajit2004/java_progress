package Recursion.Questions;

public class ALT_CountZeros {
    public static void main(String[] args) {
        System.out.println(fn(100));
    }
    static int fn(int n){
        return helper(n , 0);
    }
    static int helper(int n , int count){

//        base condition :
        if (n == 0 )
            return count;

        int rem = n % 10;

//        recursion if we get remainder count = +1 and num gets rid of last digit:
        if(rem == 0)
            return helper(n / 10 , count + 1);

//        recursion if remainder != 0 , we keep count as it is and then reduce num by last digit
        return helper(n / 10 , count);
    }
}
