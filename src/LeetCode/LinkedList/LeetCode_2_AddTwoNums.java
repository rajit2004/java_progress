package LeetCode.LinkedList;

public class LeetCode_2_AddTwoNums {
    public static void main(String[] args) {

        // Number 342
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        // Number 465
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode result = addTwoNumbers(l1, l2);

        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }

    /*
        Simulation Approach : Traverse both linked lists simultaneously.

        At every step:

        1. Take the current digit from both lists.
        2. Add them along with the carry.
        3. Store the current digit in the answer list.
        4. Carry the remaining value to the next position.

        Continue until both lists are exhausted and no carry remains.
     */
    static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        // Dummy node simplifies insertion at the head.
        ListNode dummy = new ListNode(0);

        // Points to the last node of the result list.
        ListNode current = dummy;

        // Stores the carry generated after every addition.
        int carry = 0;

//            Continue while at least one list has nodes remaining or a carry exists.
        while (l1 != null || l2 != null || carry != 0) {

            // Current digit from the first list.
            int digit1 = (l1 != null) ? l1.val : 0;

            // Current digit from the second list.
            int digit2 = (l2 != null) ? l2.val : 0;

            // Total value at the current position.
            int sum = digit1 + digit2 + carry;

            // Digit to be stored in the answer.
            int resultDigit = sum % 10;

            // Carry forwarded to the next position.
            carry = sum / 10;

            // Add the digit to the result list.
            current.next = new ListNode(resultDigit);

            current = current.next;

            // Move to the next node of the first list.
            if (l1 != null) {
                l1 = l1.next;
            }

            // Move to the next node of the second list.
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        // The answer starts after the dummy node.
        return dummy.next;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

m = length of l1

n = length of l2

---------------------------------------------------------

Time Complexity: O(max(m, n))

Reason: Each node from both linked lists is visited exactly once.

Overall: O(max(m, n))

---------------------------------------------------------

Space Complexity: O(max(m, n))

Reason: A new linked list is created to store the sum. In the worst case, it contains one node for every processed digit plus one extra node for the final carry.

Overall: O(max(m, n))

---------------------------------------------------------

Key Observation:

Instead of converting the linked lists into numbers, perform digit-by-digit addition exactly like manual arithmetic.

A dummy node simplifies list construction, while the carry naturally handles sums greater than or equal to 10.

---------------------------------------------------------
*/