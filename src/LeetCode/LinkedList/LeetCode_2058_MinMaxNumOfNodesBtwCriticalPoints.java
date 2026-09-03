package LeetCode.LinkedList;

import java.util.Arrays;

public class LeetCode_2058_MinMaxNumOfNodesBtwCriticalPoints {
    public static void main(String[] args) {

        LeetCode_2058_MinMaxNumOfNodesBtwCriticalPoints solution = new LeetCode_2058_MinMaxNumOfNodesBtwCriticalPoints();

        // Test Case 1: Multiple critical points.
        ListNode head1 = createLinkedList(new int[] {5, 3, 1, 2, 5, 1, 2});
        int[] result1 = solution.nodesBetweenCriticalPoints(head1);
        System.out.println("Test Case 1: " + Arrays.toString(result1));

        // Test Case 2: Only one critical point, so no valid distance exists.
        ListNode head2 = createLinkedList(new int[] {1, 3, 2});
        int[] result2 = solution.nodesBetweenCriticalPoints(head2);
        System.out.println("Test Case 2: " + Arrays.toString(result2));

        // Test Case 3: No critical points.
        ListNode head3 = createLinkedList(new int[] {1, 2, 3, 4});
        int[] result3 = solution.nodesBetweenCriticalPoints(head3);
        System.out.println("Test Case 3: " + Arrays.toString(result3));

        // Test Case 4: Fewer than three nodes.
        ListNode head4 = createLinkedList(new int[] {1, 2});
        int[] result4 = solution.nodesBetweenCriticalPoints(head4);
        System.out.println("Test Case 4: " + Arrays.toString(result4));
    }

    /*
        Greedy + Linked List Traversal Approach:

        A critical point is a node that is either:
            1. A local maxima: greater than both neighboring nodes.
            2. A local minima: smaller than both neighboring nodes.

        We traverse the linked list once and record the positions of critical points.
        For the minimum distance, compare each critical point with the previous critical point.
        For the maximum distance, subtract the first critical point index from the last critical point index.

        If fewer than two critical points exist, return {-1, -1}.
     */
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int[] result = {-1, -1};

        // At least three nodes are required to have a critical point.
        if (head == null || head.next == null || head.next.next == null)
            return result;

        // Initialize minimum distance to the maximum possible value
        int minDistance = Integer.MAX_VALUE;

        // Pointers to track the previous node, current node, and indices
        ListNode previousNode = head;
        ListNode currentNode = head.next;
        int currentIndex = 1;
        int previousCriticalIndex = -1;
        int firstCriticalIndex = -1;

        while (currentNode.next != null) {
            // Check if the current node is a local maxima or minima
            if ((currentNode.val < previousNode.val &&currentNode.val < currentNode.next.val) ||(currentNode.val > previousNode.val &&currentNode.val > currentNode.next.val)) {
                // If this is the first critical point found
                if (previousCriticalIndex == -1) {
                    previousCriticalIndex = currentIndex;
                    firstCriticalIndex = currentIndex;
                } else {
                    // Calculate the minimum distance between critical points
                    minDistance = Math.min(minDistance,currentIndex - previousCriticalIndex);
                    previousCriticalIndex = currentIndex;
                }
            }

            // Move to the next node and update indices
            currentIndex++;
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        // If at least two critical points were found
        if (minDistance != Integer.MAX_VALUE) {
            int maxDistance = previousCriticalIndex - firstCriticalIndex;
            result = new int[] {minDistance, maxDistance};
        }

        return result;
    }

    // Create a linked list from an integer array for testing.
    private static ListNode createLinkedList(int[] values) {
        if (values == null || values.length == 0)
            return null;

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        for (int value : values) {
            current.next = new ListNode(value);
            current = current.next;
        }

        return dummy.next;
    }

    // Definition of a singly linked-list node.
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

We scan the linked list once to identify critical points.

All distance calculations take O(1) time during the traversal.

Overall: O(n)

---------------------------------------------------------

Space Complexity: O(1)

Only a few pointers, indices, and integer variables are used.

The input linked list is not modified.

Overall: O(1)

---------------------------------------------------------

Key Observation:

Only the first, previous, and last critical point positions are required.
The minimum distance is the smallest difference between two consecutive critical point indices.
The maximum distance is the difference between the last and first critical point indices.
If fewer than two critical points exist, the answer is {-1, -1}.

---------------------------------------------------------
*/
