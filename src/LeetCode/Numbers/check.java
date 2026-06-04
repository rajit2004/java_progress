package LeetCode.Numbers;

public class check {
    public static void main(String[] args) {
        int n = 120;
        int m = 130;
        System.out.println(totalWaviness(n,m));
    }
    static int helper(int num){
        String s = String.valueOf(num);
        int peak = 0;
        int valley = 0;
        for(int i = 1 ; i <= s.length()-2 ; i++) {              // s.length-2 bcoz peak or valley can't be the last or first element
            if (s.charAt(i-1) < s.charAt(i) && s.charAt(i) > s.charAt(i + 1))
                peak++;
            if(s.charAt(i - 1) > s.charAt(i) && s.charAt(i) < s.charAt(i+1))
                valley++;
        }
        return peak + valley;
    }
    static int totalWaviness(int num1, int num2){
//        base condition :
        if(num1 > num2)
            return 0;
        if(num1 == num2)
            return helper(num1);

// recursive call:
        return helper(num1) + totalWaviness(num1+1 , num2);
    }
}
