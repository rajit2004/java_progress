package LeetCode.BinaryTree;

/*
You are given a 2D integer array descriptions where: [parent, child, isLeft] describes a parent-child relationship in a binary tree.
Construct the binary tree and return its root.
*/

public class LeetCode_2196_CreateBinaryTreeFromDescriptions {
    public static void main(String[] args) {
        int[][] descriptions = {
                {20,15,1},
                {20,17,0},
                {50,20,1},
                {50,80,0},
                {80,19,1}
        };

        System.out.println(createBinaryTree(descriptions));
    }

    /*
        Approach:

        1. Create every node only once and store it
           using its value as the index.

        2. Connect parent and child nodes according
           to the given descriptions.

        3. Mark every node that appears as a child.

        4. The root is the only node that never
           appears as a child.
     */

    static TreeNode createBinaryTree(int[][] descriptions) {

        // Stores references to TreeNodes.
        // nodes[x] represents the TreeNode with value x.
        TreeNode[] nodes = new TreeNode[100001];

        // Tracks whether a value appears as a child.
        boolean[] isChild = new boolean[100001];

        for (int[] d : descriptions) {

            int parent = d[0];
            int child = d[1];
            int isLeft = d[2];

            // Create parent node if it does not exist.
            if (nodes[parent] == null) {
                nodes[parent] = new TreeNode(parent);
            }

            // Create child node if it does not exist.
            if (nodes[child] == null) {
                nodes[child] = new TreeNode(child);
            }

            // Connect parent and child according to the isLeft flag.
            if (isLeft == 1) {
                nodes[parent].left = nodes[child];
            } else {
                nodes[parent].right = nodes[child];
            }

            // Every child node is marked.
            isChild[child] = true;
        }

        /*
            Root Identification:

            Every node except the root appears exactly once as a child.

            Therefore, the root is the node that never appears as a child.
         */
        for (int[] d : descriptions) {

            int parent = d[0];

            if (!isChild[parent]) {
                return nodes[parent];
            }
        }

        return null;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Time Complexity: O(n)

Reason:

1. First traversal:
   - Create nodes
   - Connect parent and child
   - Mark child nodes

   O(n)

2. Second traversal:
   - Find the root node

   O(n)

Total:

O(n) + O(n) = O(n)

---------------------------------------------------------

Space Complexity: O(100001)

Reason:

1. nodes[] array stores TreeNode references.
2. isChild[] array stores child information.

Since the array size is fixed: O(100001) or simply: O(maxNodeValue)

---------------------------------------------------------

Key Observation:

The root is the only node that never appears as a child.

Instead of traversing the tree, we can identify the root directly using the isChild array.

---------------------------------------------------------
*/