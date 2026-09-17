package LeetCode.Tree;

public class LeetCode_2265_CountNodesEqualToAverageOfSubtree {
    public static void main(String[] args) {

        // Build sample tree:
        //          4
        //        /   \
        //       8     5
        //      / \     \
        //     0   1     6
        TreeNode root = new TreeNode(4,
                new TreeNode(8, new TreeNode(0), new TreeNode(1)),
                new TreeNode(5, null, new TreeNode(6)));

        // Expected: 5
        // Nodes whose value == average of its subtree:
        //   4 -> (4+8+5+0+1+6)/6 = 24/6 = 4  ✓
        //   8 -> (8+0+1)/3 = 9/3 = 3         ✗
        //   0 -> 0/1 = 0                     ✓
        //   1 -> 1/1 = 1                     ✓
        //   5 -> (5+6)/2 = 5 (integer div)   ✓
        //   6 -> 6/1 = 6                     ✓
        System.out.println("Sample -> " + averageOfSubtree(root) + " (expected: 5)");

        // Edge case: single node -> value equals its own average
        TreeNode single = new TreeNode(7);
        System.out.println("Edge (single node) -> " + averageOfSubtree(single) + " (expected: 1)");

        // Edge case: null root -> 0 nodes
        System.out.println("Edge (null root) -> " + averageOfSubtree(null) + " (expected: 0)");
    }


    /*
        Approach: Post-order DFS (single pass)

        We need, for every node:
            - sum of all values in its subtree
            - count of all nodes in its subtree

        Both values can be computed bottom-up using post-order traversal.
        For each node, after we get (sum, count) of left and right subtrees:
            nodeSum   = leftSum   + rightSum   + root.val
            nodeCount = leftCount + rightCount + 1

        If root.val == nodeSum / nodeCount (integer division, as per problem), this node satisfies the condition, so increment the count.

        We return (nodeSum, nodeCount) up to the parent.

        We use an int[] {sum, count} instead of a Pair to avoid extra imports and keep the file self-contained.
    */
    static int count = 0;

    static int averageOfSubtree(TreeNode root) {
        count = 0;              // reset for repeated calls in main / tests
        postOrder(root);
        return count;
    }

    /*
        postOrder returns int[] {subtreeSum, subtreeNodeCount}.
        Side effect: increments `count` whenever root.val == average of its subtree.
    */
    static int[] postOrder(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }

        // Traverse left and right subtrees first
        int[] left  = postOrder(root.left);
        int[] right = postOrder(root.right);

        int nodeSum   = left[0] + right[0] + root.val;
        int nodeCount = left[1] + right[1] + 1;

        // Check if the subtree's average (integer division) equals the node's value
        if (root.val == nodeSum / nodeCount) {
            count++;
        }

        return new int[]{nodeSum, nodeCount};
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Approach: Post-order DFS (single pass)

Time Complexity: O(n)

- Each node is visited exactly once during the post-order traversal.

Space Complexity: O(h)

- h = height of the tree (recursion stack).
- Worst case (skewed tree): O(n).
- Best case (balanced tree): O(log n).

---------------------------------------------------------
*/