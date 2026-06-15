package LeetCode.LinkedList;

public class LeetCode_2095_DeleteMidNode {
    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);

        ListNode ans = deleteMiddle(head);

        display(ans);
    }

    /*
        Approach:

        Use the Slow-Fast Pointer technique = > slow -> moves one step at a time    &   fast -> moves two steps at a time

        When fast reaches the end, slow will be positioned at the middle node.

        Keep track of the node before slow so that we can remove the middle node.
     */

    static ListNode deleteMiddle(ListNode head) {

        /*
            Edge Cases:

            Empty List: [] and  Single Node: [1]

            After deleting the middle node, the list becomes empty.
         */
        if (head == null || head.next == null) {
            return null;
        }

        // Finds the middle node.
        ListNode slow = head;

        // Moves twice as fast as slow.
        ListNode fast = head;

        // Stores the node before slow.
        ListNode prev = null;

        /*
            Example:

            1 -> 2 -> 3 -> 4 -> 5

            Iteration 1:
            slow = 2
            fast = 3

            Iteration 2:
            slow = 3
            fast = 5

            slow now points to the middle node.
         */
        while (fast != null && fast.next != null) {

            prev = slow;

            slow = slow.next;

            fast = fast.next.next;
        }

        /*
            Remove the middle node.

            Example:

            prev -> 2
            slow -> 3

            Make:

            2 -> 4

            effectively removing 3.
         */
        prev.next = slow.next;

        return head;
    }

    static void display(ListNode head) {

        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }

        System.out.println();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let:

n = number of nodes in the linked list

---------------------------------------------------------

Time Complexity: O(n)

Reason:

The slow-fast traversal visits the linked list only once.

Although fast moves two steps at a time, the overall traversal is still linear.

---------------------------------------------------------

Space Complexity: O(1)

Reason: Only a few pointer variables are used: slow , fast , prev

No extra data structures are created.

---------------------------------------------------------

Key Observation:

When: slow moves 1 step and fast moves 2 steps ; by the time fast reaches the end, slow will be exactly at the middle node.

Tracking the previous node allows us to delete the middle node in O(1) once it is found.
---------------------------------------------------------
*/