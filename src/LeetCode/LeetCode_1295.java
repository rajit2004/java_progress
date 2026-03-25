// 1295. Find Numbers with Even Number of Digits
// (Given an array nums of integers, return how many of them contain an even number of digits.)

package LeetCode;

public class LeetCode_1295 {
    public static void main(String[] args) {

        int[] arr = {23, -500, 78, 102, -34, 5612, 9, -111, 4210, 173};

        System.out.println(sol(arr));

    }
    static int sol(int[] arr){
        int n = 0;
        for(int ele : arr){
            int len;
            if(ele == 0)
                len = 1;
            else
                len = (int)Math.log10(Math.abs(ele)) + 1;
            if(len % 2 == 0)
                n++;
        }
        return n;
    }
}
