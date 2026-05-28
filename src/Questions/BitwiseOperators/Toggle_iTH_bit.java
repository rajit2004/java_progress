package Questions.BitwiseOperators;

// toggle flips the bit     i.e. =>         0 -> 1      & 1 -> 0

public class Toggle_iTH_bit {
    public static void main(String[] args) {
        int n = 169;
        int b = 7;
        System.out.println(toggle(n,b));
    }
    static int toggle(int num , int bit){
        return (num ^ (1 << (bit - 1)));
    }
}
