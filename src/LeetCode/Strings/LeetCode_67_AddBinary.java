package LeetCode.Strings;

public class LeetCode_67_AddBinary {
    public static void main(String[] args) {
        String a = "11";
        String b = "1";
        System.out.println(addBinary(a,b));
    }
    static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();

        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        while (i >= 0 || j >= 0) {
            int sum = carry;

            if (i >= 0) {
                sum += a.charAt(i) - '0'; // convert char to int
                i--;
            }

            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }

            result.append(sum % 2); // binary digit
            carry = sum / 2;        // update carry
        }

        // if carry is still left
        if (carry != 0) {
            result.append(carry);
        }

        return result.reverse().toString();
    }
}
