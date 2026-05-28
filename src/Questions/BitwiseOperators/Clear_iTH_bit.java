package Questions.BitwiseOperators;

// clear unsets the bit i.e. 1 -> 0

public class Clear_iTH_bit {
    public static void main(String[] args) {
        int n = 169;
        int b = 8;
        System.out.println(clear(n,b));
    }
    static int clear(int num , int bit){
        return (num & ~(1 << (bit - 1)));
    }
}
