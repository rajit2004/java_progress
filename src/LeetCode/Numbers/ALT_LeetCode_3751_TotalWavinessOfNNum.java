package LeetCode.Numbers;

public class ALT_LeetCode_3751_TotalWavinessOfNNum {
    public static void main(String[] args) {
        int n = 120;
        int m = 130;
        System.out.println(totalWaviness(n,m));
    }
    static int helper(int num){
        int count = 0;

        String s = String.valueOf(num);

        for(int i = 1; i < s.length()-1; i++){
            int curr = s.charAt(i);
            int prev = s.charAt(i -1);
            int next = s.charAt(i + 1);

            if(curr > prev && curr > next || (curr < prev && curr < next))
                count++;
        }

        return count;
    }

    static int totalWaviness(int num1, int num2) {
        // Just check each element from num1 to num2 for valleys and peaks and return the count.

        int start = Math.max(100, num1);        // waviness for num < 100 is 0 so no need to check for nums < 100
        int res = 0;

        for(int i = start; i <= num2; i++)
            res += helper(i);


        return res;
    }
}
