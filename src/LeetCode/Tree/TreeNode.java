package LeetCode.Tree;

/*
    Basic Binary Tree Node Structure

    Each node contains:

    1. val   -> value stored in the node
    2. left  -> reference to the left child
    3. right -> reference to the right child

    This class serves as the building block
    for all binary tree problems.
*/

public class TreeNode {

        // Value stored in the current node.
        int val;

        // Reference to the left child.
        TreeNode left;

        // Reference to the right child.
        TreeNode right;

        // Default constructor.
        TreeNode() {}

        // Creates a node with only a value.
        TreeNode(int val) {
                this.val = val;
        }

        // Creates a node with a value and references to both children.
        TreeNode(int val, TreeNode left, TreeNode right) {
                this.val = val;
                this.left = left;
                this.right = right;
        }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

TreeNode Creation:

Time Complexity : O(1)
Space Complexity: O(1)

Reason:
Creating a node only initializes a fixed number of fields.

---------------------------------------------------------

Memory Per Node:

1 integer value
1 left reference
1 right reference

Therefore each node occupies constant space.

---------------------------------------------------------
*/