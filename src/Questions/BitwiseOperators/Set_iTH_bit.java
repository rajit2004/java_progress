package Questions.BitwiseOperators;

public class Set_iTH_bit {
    public static void main(String[] args) {
        int n = 33;
        int bit = 5;
        System.out.println(set(n , bit));
    }
    static int set(int num , int bit){
        return (num | (1 << (bit - 1)));
    }
}

