package Questions.BitwiseOperators;

/*
Approach :
    we know that when we use bitwise and with a number , we get the number itself .

    also if we have a number -> internally it is going to operate in binary digits only.
    that means base 2.

    any number can be written in binary as follows :

        (qwerty)10 = (q*2^5 + w*2^4 + e*2^3 + r*2^2 + t*2^1 + y*2^0)2
            -> here all the nums till  q*2^5 + w*2^4 + e*2^3 + r*2^2 + t*2^1 are even
                -> what makes the total num odd is the LEAST SIGNIFICANT BIT (last digit)
    so if we simply check if the LEAST SIGNIFICANT BIT (last digit) is even (0) or odd (1)
    we can determine if the num is even or odd.

    so we use bitwise and to check the LEAST SIGNIFICANT BIT (last digit)
*/

public class OddEven {
    public static void main(String[] args) {
        System.out.println(checknum(9));
        System.out.println(checknum(21));
        System.out.println(checknum(12));
        System.out.println(checknum(8723));
    }
    static String checknum(int n){
        if((n & 1)==1)          // we are anding 1 to the LEAST SIGNIFICANT BIT (last digit)
            return "Odd";
        else
            return "Even";
    }
}
