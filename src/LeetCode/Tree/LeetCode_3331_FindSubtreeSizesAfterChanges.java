package LeetCode.Tree;

import java.util.*;

public class LeetCode_3331_FindSubtreeSizesAfterChanges {

    public static void main(String[] args) {
        int[] parent = {-1, 0, 1};
        String s = "aba";
        System.out.println(Arrays.toString(findSubtreeSizesArray(parent, s)));
        System.out.println(Arrays.toString(findSubtreeSizesList(parent, s)));
    }

    /* Approach 1: Iterative DFS with array-based child linked list
       Store each node's children as a linked list in two int arrays
       (firstChild / nextSibling), so no adjacency lists are needed. Run an
       iterative DFS with a manual stack using the ~x trick: pushing x means
       "enter", pushing ~x means "leave after children are done". On enter,
       record the deepest ancestor with the same letter (`above[x]`) and set
       `last[c] = x`; on leave, restore `last` and add size[x] to the new
       parent (above[x] if it exists, else parent[x]).
       Example: parent = [-1,0,1], s = "aba" -> node 2 ('a') finds node 0
                ('a') as its closest same-letter ancestor and re-attaches to 0,
                giving sizes [3,1,1].
    */
    static int[] findSubtreeSizesArray(int[] parent, String s) {
        int n = parent.length;

        // children as a linked list in two int arrays
        int[] firstChild  = new int[n];
        int[] nextSibling = new int[n];
        Arrays.fill(firstChild, -1);
        for (int x = n - 1; x > 0; x--) {
            nextSibling[x] = firstChild[parent[x]];
            firstChild[parent[x]] = x;
        }

        int[] size  = new int[n];
        int[] above = new int[n];   // closest same-letter ancestor, or -1
        int[] last  = new int[26];  // last[c]: deepest node with letter c on the root path
        int[] stack = new int[2 * n];

        Arrays.fill(size, 1);
        Arrays.fill(last, -1);

        int top = 0;
        stack[top++] = 0;  // x enters x, ~x leaves it: no recursion

        while (top > 0) {
            int x = stack[--top];

            if (x >= 0) {
                int c = s.charAt(x) - 'a';
                above[x] = last[c];
                last[c]  = x;

                stack[top++] = ~x;  // leave x after all its children
                for (int child = firstChild[x]; child != -1; child = nextSibling[child]) {
                    stack[top++] = child;
                }
            } else {
                x = ~x;
                last[s.charAt(x) - 'a'] = above[x];

                if (x > 0) {  // the root has no parent
                    size[above[x] >= 0 ? above[x] : parent[x]] += size[x];
                }
            }
        }

        return size;
    }

    /* Approach 2: Iterative DFS with adjacency list
       Same idea as Approach 1, but children are stored in a
       List<List<Integer>> — cleaner to read at the cost of some boxing and
       extra memory. The iterative DFS again uses the ~x trick to tell
       "enter" apart from "leave", so a deep tree cannot overflow the call
       stack.
       Example: parent = [-1,0,1], s = "aba" -> [3,1,1] (same as Approach 1).
    */
    static int[] findSubtreeSizesList(int[] parent, String s) {
        int n = parent.length;

        List<List<Integer>> children = new ArrayList<>();
        for (int x = 0; x < n; x++) children.add(new ArrayList<>());
        for (int x = 1; x < n; x++) children.get(parent[x]).add(x);

        int[] size  = new int[n];
        int[] above = new int[n];
        int[] last  = new int[26];

        Arrays.fill(size, 1);
        Arrays.fill(last, -1);

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);

        while (!stack.isEmpty()) {
            int x = stack.pop();

            if (x >= 0) {
                int c = s.charAt(x) - 'a';
                above[x] = last[c];
                last[c]  = x;

                stack.push(~x);
                for (int child : children.get(x)) stack.push(child);
            } else {
                x = ~x;
                last[s.charAt(x) - 'a'] = above[x];

                if (x > 0) {
                    size[above[x] >= 0 ? above[x] : parent[x]] += size[x];
                }
            }
        }

        return size;
    }
}

/* Complexity Analysis
   Let n = number of nodes (parent.length).

   Approach 1: Iterative DFS with array-based child linked list
     Time : O(n)  - each node is pushed/popped twice (enter + leave); each
            edge is walked once.
     Space: O(n)  - firstChild, nextSibling, size, above, stack arrays.
     Key Observation: entering pushes x then ~x, so the leave event fires
     after all children; `last[c]` on the current root-to-node path gives the
     closest same-letter ancestor in O(1) per node.

   Approach 2: Iterative DFS with adjacency list
     Time : O(n)  - same traversal, same per-node work.
     Space: O(n)  - adjacency list + above + stack.
     Key Observation: same ~x trick; only the storage of children differs.
     Prefer Approach 1 if memory is tight, Approach 2 if readability matters.
*/

