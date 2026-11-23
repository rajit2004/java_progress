package LeetCode.PrefixSum;

import java.util.*;

public class LeetCode_1352_ProductOfLastKNums {

    public static void main(String[] args) {
        ProductOfNumbersApproach1 a1 = new ProductOfNumbersApproach1();
        a1.add(3); a1.add(0); a1.add(2); a1.add(5); a1.add(4);
        System.out.println(a1.getProduct(2) + " " + a1.getProduct(3) + " " + a1.getProduct(4));

        ProductOfNumbersApproach2 a2 = new ProductOfNumbersApproach2();
        a2.add(3); a2.add(0); a2.add(2); a2.add(5); a2.add(4);
        System.out.println(a2.getProduct(2) + " " + a2.getProduct(3) + " " + a2.getProduct(4));

        ProductOfNumbersApproach3 a3 = new ProductOfNumbersApproach3();
        a3.add(3); a3.add(0); a3.add(2); a3.add(5); a3.add(4);
        System.out.println(a3.getProduct(2) + " " + a3.getProduct(3) + " " + a3.getProduct(4));
    }

    /* Approach 1: Prefix product, reset on zero
       Keep a running product of the current non-zero segment. A zero clears the
       segment and restarts the prefix list at 1.
       Example: add 3,0,2,5,4 -> prefix = [1,3,1,2,10,40]
                getProduct(2) = 40/2 = 20, getProduct(3) = 40/1 = 40,
                getProduct(4) = 0 (zero is inside the last 4).
    */
    static class ProductOfNumbersApproach1 {
        private ArrayList<Integer> prefixProduct = new ArrayList<>();
        private int size = 0;

        public ProductOfNumbersApproach1() {
            prefixProduct.add(1);
        }

        public void add(int num) {
            if (num == 0) {
                prefixProduct = new ArrayList<>();
                prefixProduct.add(1);
                size = 0;
            } else {
                prefixProduct.add(prefixProduct.get(size) * num);
                size++;
            }
        }

        public int getProduct(int k) {
            if (k > size) return 0;
            return prefixProduct.get(size) / prefixProduct.get(size - k);
        }
    }

    /* Approach 2: Brute force, multiply the last k
       Store every number. getProduct walks the last k entries and multiplies
       them; a zero anywhere in the window makes the answer 0.
       Example: add 3,0,2,5,4 -> nums = [3,0,2,5,4]
                getProduct(2) = 5*4 = 20, getProduct(3) = 2*5*4 = 40,
                getProduct(4) = 0*2*5*4 = 0.
    */
    static class ProductOfNumbersApproach2 {
        private ArrayList<Integer> nums = new ArrayList<>();

        public void add(int num) {
            nums.add(num);
        }

        public int getProduct(int k) {
            int n = nums.size();
            if (k > n) return 0;

            int product = 1;
            for (int i = n - k; i < n; i++) {
                product *= nums.get(i);
            }
            return product;
        }
    }

    /* Approach 3: Prefix product with last-zero index (optimal)
       Never rebuild the list. Append 1 on a zero and remember its position.
       getProduct returns 0 if the last zero lies inside the last k elements,
       otherwise divides the two prefix products.
       Example: add 3,0,2,5,4 -> prefix = [1,3,1,2,10,40], lastZeroIndex = 1
                getProduct(2): 1 < 5-2 -> 40/2 = 20
                getProduct(3): 1 < 5-3 -> 40/1 = 40
                getProduct(4): 1 >= 5-4 -> 0
    */
    static class ProductOfNumbersApproach3 {
        private ArrayList<Integer> prefix = new ArrayList<>();
        private int size = 0;
        private int lastZeroIndex = -1;

        public ProductOfNumbersApproach3() {
            prefix.add(1);
        }

        public void add(int num) {
            if (num == 0) {
                lastZeroIndex = size;
                prefix.add(1);
            } else {
                prefix.add(prefix.get(size) * num);
            }
            size++;
        }

        public int getProduct(int k) {
            if (k > size) return 0;
            if (lastZeroIndex >= size - k) return 0;
            return prefix.get(size) / prefix.get(size - k);
        }
    }
}

/* Complexity Analysis
   Let n = number of add() calls so far.

   Approach 1: Prefix product, reset on zero
     Time : O(1) amortized per add, O(1) per getProduct.
     Space: O(n) - prefix list; a zero drops the old list (still O(n) total).
     Key Observation: inside a non-zero segment, product of last k is
     prefix[last] / prefix[last-k]; a zero resets the segment.

   Approach 2: Brute force
     Time : O(1) per add, O(k) per getProduct.
     Space: O(n) - stores every number.
     Key Observation: directly multiply the last k numbers; no division, so
     zeros need no special handling.

   Approach 3: Prefix product with last-zero index (recommended)
     Time : O(1) per add, O(1) per getProduct.
     Space: O(n) - single growing prefix list, never rebuilt.
     Key Observation: the answer is 0 exactly when the most recent zero lies
     inside the last k elements (lastZeroIndex >= size - k); otherwise divide
     the two prefix products.
*/
