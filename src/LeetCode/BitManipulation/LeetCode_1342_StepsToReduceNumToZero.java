package LeetCode.BitManipulation;

/*
Given an integer num, return the number of steps to reduce it to zero.

In one step, if the current number is even, you have to divide it by 2, otherwise, you have to subtract 1 from it.
*/


public class LeetCode_1342_StepsToReduceNumToZero {
    public static void main(String[] args) {
        int n = 8;
        System.out.println(numberOfSteps(n));
    }

//    iterative approach :
    static int numberOfSteps(int num){
        int count = 0;
        while(num != 0){
            if(num % 2 == 0){
                num /= 2;
                count++;
            }
            else{
                num -= 1;
                count++;
            }
        }
        return count;
    }
}
