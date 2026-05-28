package Questions.BitwiseOperators;

public class Get_iTH_bit {
    public static void main(String[] args) {
        int num = 6467;
        int bit = 2;
        System.out.println(ans(num,bit));
    }
    static int ans(int num , int bit){
//        return (num & (1 << (bit - 1)));          this returns the actual bit value (0,2,4,6,8,16, .........)
        return (num >> (bit-1) & 1);                // this returns the actual bit on that position
    }
}


/*

    (num >> (bit-1) & 1)    =>
            this part -> num >> (bit-1) brings the target bit ot the last (LSB)
            then -> & 1 = returns the bit

 */