package LeetCode.LinkedList;

public class LeetCode_83_RemoveDuplicateFromSortedList {
    public static void main(String[] args) {
        System.out.println(printList(deleteDuplicates(build(1, 1, 2))));       // [1, 2]
        System.out.println(printList(deleteDuplicates(build(1, 1, 2, 3, 3)))); // [1, 2, 3]
    }


    /*
        Approach: Single-pass pointer skip

        Since the list is sorted, all duplicates of a value are adjacent.
        Walk with a single pointer `current`:
            - If current.val == current.next.val, skip current.next by
              pointing current.next to current.next.next (do NOT advance).
            - Otherwise advance current.

        This removes every extra occurrence in O(n) time, O(1) space,
        without allocating new nodes.

        Time:  O(n)
        Space: O(1)
    */
    static ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;

        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                current.next = current.next.next;   // skip duplicate
            } else {
                current = current.next;             // advance
            }
        }

        return head;
    }


    // ---- helpers for running locally ----

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // Build a linked list from the given values
    static ListNode build(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    // Convert a linked list to a readable string
    static String printList(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append(", ");
            head = head.next;
        }
        return sb.append("]").toString();
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Single-pass pointer skip

Time Complexity: O(n)

- One pass over the list; each node visited at most once.

Space Complexity: O(1)

- Only one pointer; no new nodes allocated.

Key Observation:
Because the list is SORTED, duplicates are always adjacent. So we can
delete them by simply skipping over nodes whose value matches the current
one — no hash set or extra storage needed. The key trick is: when we
delete current.next, we do NOT advance `current`, because the new
current.next might have the same value again.

---------------------------------------------------------
*/