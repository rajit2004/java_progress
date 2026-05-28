package Questions.BitwiseOperators;

public class PosOfRightMostSetBit_BRUTE {
    public static void main(String[] args) {
        int n = 9;
        System.out.println(pos(n));
    }
    static int pos(int n){
        int index = 1;                  // indexTH bit from right is our answer.
        //        convert into binary:
        String binary = Integer.toBinaryString(n);
        for (int i = binary.length()-1 ; i >= 0 ; i--) {
            if (binary.toCharArray()[i] == '1')
                return index;
            index++;
        }
        return -1;
    }
}
