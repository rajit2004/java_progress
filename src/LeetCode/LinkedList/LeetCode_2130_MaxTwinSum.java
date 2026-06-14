package LeetCode.LinkedList;

/*
In a linked list of size n, where n is even, the twin of node i is node (n - 1 - i).

Twin Sum: node[i] + node[n - 1 - i]

Return the maximum twin sum.
*/

import java.util.ArrayList;

public class LeetCode_2130_MaxTwinSum {
    public static void main(String[] args) {

        ListNode head = new ListNode(5);
        head.next = new ListNode(4);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(1);

        System.out.println(pairSum(head));
    }

    /*
        Approach: Linked Lists do not support random access. To easily access twin nodes, first copy all node values into an ArrayList.

        Then use two pointers:
            left  -> beginning of the list
            right -> end of the list

        Since twins are mirrored around the center:

        node[0] ↔ node[n-1]
        node[1] ↔ node[n-2]
        ...

        Check every twin pair and keep track of the maximum twin sum.
     */

    static int pairSum(ListNode head) {

        // Stores all linked list values.
        ArrayList<Integer> list = new ArrayList<>();

// Convert the linked list into an array list. This allows O(1) access to any position.

        while (head != null) {
            list.add(head.val);
            head = head.next;
        }

        // Two pointers representing twin nodes.
        int left = 0;
        int right = list.size() - 1;

        // Stores the largest twin sum found.
        int maxSum = 0;

        /*
            Compare all twin pairs.

            Example:

            [5,4,2,1]

            Twins:

            5 ↔ 1
            4 ↔ 2
         */
        while (left < right) {

            int twinSum = list.get(left) + list.get(right);

            maxSum = Math.max(maxSum, twinSum);

            left++;
            right--;
        }

        return maxSum;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let: n = number of nodes in the linked list

---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. First traversal: Copy linked list values into ArrayList. = > O(n)
2. Second traversal: Check all twin pairs. = > O(n)

Total: (n) + O(n) = O(n)

---------------------------------------------------------

Space Complexity: O(n)

Reason: The ArrayList stores all n node values. Therefore: O(n)

---------------------------------------------------------

Key Observation:

Linked Lists do not allow direct access to the last or mirrored nodes.

By converting the list into an ArrayList, we gain O(1) random access and can use the two-pointer technique to efficiently find the maximum twin sum.

---------------------------------------------------------
*/