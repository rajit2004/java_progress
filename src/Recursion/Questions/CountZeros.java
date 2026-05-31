package Recursion.Questions;

public class CountZeros {
    public static void main(String[] args) {
        System.out.println(count(100));
        System.out.println(checkRec(100));
    }

//    iterative approach :
    static int count(int n){
        int ans = 0;
        while(n!=0){
            int rem = n % 10;
            if(rem == 0)
                ans++;
            n /= 10;
        }
        return ans;
    }

//    recursive approach :
    static int checkRec(int n){

        if(n == 0)
            return 0;

        int rem = n % 10;

//        recursion here : check last digit if 0 then check next and num of 0 = 1
        if(rem ==0)
            return 1 + checkRec(n / 10);

//        recursion here when last digit is not 0 so we remove last digit and check for remaining
        return checkRec(n/10);
    }
}
