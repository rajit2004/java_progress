package LeetCode.Tree;

import java.util.*;

public class LeetCode_3841_PalindromicPathQueriesInTree {

    public static void main(String[] args) {
        int n = 2;
        int[][] edges = {{0, 1}};
        String s = "ab";
        String[] queries = {"query 0 1", "update 1 a", "query 0 1"};
        System.out.println(palindromePathBrute(n, edges, s, queries));
        System.out.println(palindromePathOptimal(n, edges, s, queries));
    }

    /* Approach 1: Brute force per query (BFS path + parity mask)
       For an "update" query, just change the character at that node. For a
       "query u v", run BFS from u to v, walk the parent chain back from v to
       recover the path, and XOR the 26-bit letter mask of every node on it.
       The path can form a palindrome iff at most one bit remains set (i.e.
       bitCount(mask) <= 1). Correct but O(n) per query.
       Example: n = 2, edges = [[0,1]], s = "ab"
                query 0 1: path a,b -> mask = a|b -> 2 bits -> false
                update 1 a: chars = "aa"
                query 0 1: path a,a -> mask = 0 -> true -> [false, true].
    */
    static List<Boolean> palindromePathBrute(int n, int[][] edges, String s, String[] queries) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        for (int[] e : edges) {
            g.get(e[0]).add(e[1]);
            g.get(e[1]).add(e[0]);
        }

        char[] ch = s.toCharArray();
        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                ch[u] = parts[2].charAt(0);
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                List<Integer> path = findPath(g, u, v);
                int mask = 0;
                for (int node : path) mask ^= 1 << (ch[node] - 'a');

                ans.add(Integer.bitCount(mask) <= 1);
            }
        }

        return ans;
    }

    // BFS from u to v, then walk the parent chain from v back to u.
    static List<Integer> findPath(List<List<Integer>> g, int u, int v) {
        int n = g.size();
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        Deque<Integer> dq = new ArrayDeque<>();
        dq.offer(u);
        parent[u] = u;

        while (!dq.isEmpty()) {
            int x = dq.poll();
            if (x == v) break;
            for (int y : g.get(x)) {
                if (parent[y] == -1) {
                    parent[y] = x;
                    dq.offer(y);
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        int cur = v;
        while (cur != u) {
            path.add(cur);
            cur = parent[cur];
        }
        path.add(u);
        return path;
    }

    /* Approach 2: Euler tour + XOR BIT + binary-lifting LCA (optimal)
       Idea: root the tree at 0. For any node x, let rootPath(x) = XOR of the
       26-bit letter masks from the root down to x. Then the mask of the
       u -> v path is
           rootPath(u) XOR rootPath(v) XOR letterMask(lca(u, v))
       because the root -> lca part is XORed twice (cancels) and the lca node
       itself is included in both but must be counted once.

       Euler tour + BIT: assign each node an entry time l[x] and an exit time
       r[x] so that x's subtree occupies [l[x], r[x]]. A point query at l[x]
       then gives the XOR of every node whose subtree contains x — i.e. every
       ancestor of x (including x itself) — which is exactly rootPath(x).
       Range updates over a subtree are done with XOR range-update on the BIT
       (update(l), update(r+1) — XOR is its own inverse).

       Updates: changing chars[u] from old to new means flipping the delta
       (oldMask XOR newMask) over u's entire subtree.

       Queries: LCA with binary lifting, one point query per endpoint, XOR
       with the LCA's letter, and check bitCount <= 1.
       Example: n = 2, edges = [[0,1]], s = "ab"
                query 0 1: rootPath(0)=a, rootPath(1)=a^b, lca=0(a)
                mask = a ^ (a^b) ^ a = a^b -> 2 bits -> false
                update 1 a: chars = "aa", delta = a^b applied to subtree {1}
                query 0 1: rootPath(0)=a, rootPath(1)=a^a=0, lca=0(a)
                mask = a ^ 0 ^ a = 0 -> true -> [false, true].
    */
    static List<Boolean> palindromePathOptimal(int n, int[][] edges, String s, String[] queries) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        for (int[] e : edges) {
            g.get(e[0]).add(e[1]);
            g.get(e[1]).add(e[0]);
        }

        int LOG = 32 - Integer.numberOfLeadingZeros(n);
        int[][] fa = new int[n][LOG];
        int[] dep = new int[n];
        int[] l = new int[n];
        int[] r = new int[n];
        int[] timer = {0};

        dfsEuler(0, 0, g, fa, dep, l, r, timer);

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                fa[i][j] = fa[fa[i][j - 1]][j - 1];
            }
        }

        Bit bit = new Bit(n + 1);
        char[] chars = s.toCharArray();
        for (int i = 0; i < n; i++) {
            bit.updateRange(l[i], r[i], 1 << (chars[i] - 'a'));
        }

        List<Boolean> ans = new ArrayList<>();
        for (String q : queries) {
            String[] parts = q.split(" ");
            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                bit.updateRange(l[u], r[u], (1 << (chars[u] - 'a')) ^ (1 << (c - 'a')));
                chars[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int w = lca(u, v, fa, dep, LOG);
                int mask = bit.query(l[u]) ^ bit.query(l[v]) ^ (1 << (chars[w] - 'a'));

                ans.add(Integer.bitCount(mask) <= 1);
            }
        }

        return ans;
    }

    // Euler tour: l[u] = entry time, r[u] = exit time, subtree interval [l[u], r[u]].
    static void dfsEuler(int u, int p, List<List<Integer>> g, int[][] fa, int[] dep,
                         int[] l, int[] r, int[] timer) {
        l[u] = ++timer[0];
        fa[u][0] = p;

        for (int v : g.get(u)) {
            if (v == p) continue;
            dep[v] = dep[u] + 1;
            dfsEuler(v, u, g, fa, dep, l, r, timer);
        }

        r[u] = timer[0];
    }

    // Binary-lifting LCA.
    static int lca(int u, int v, int[][] fa, int[] dep, int LOG) {
        if (dep[u] < dep[v]) { int t = u; u = v; v = t; }

        int diff = dep[u] - dep[v];
        for (int i = 0; i < LOG; i++) {
            if (((diff >> i) & 1) == 1) u = fa[u][i];
        }
        if (u == v) return u;

        for (int i = LOG - 1; i >= 0; i--) {
            if (fa[u][i] != fa[v][i]) {
                u = fa[u][i];
                v = fa[v][i];
            }
        }
        return fa[u][0];
    }

    // XOR Fenwick tree: range update with XOR, point query.
    static class Bit {
        private final int[] tree;
        private final int n;

        Bit(int n) {
            this.n = n;
            this.tree = new int[n + 1];
        }

        private static int lowbit(int x) {
            return x & -x;
        }

        void update(int x, int v) {
            while (x <= n) {
                tree[x] ^= v;
                x += lowbit(x);
            }
        }

        int query(int x) {
            int ret = 0;
            while (x > 0) {
                ret ^= tree[x];
                x -= lowbit(x);
            }
            return ret;
        }

        void updateRange(int l, int r, int v) {
            update(l, v);
            update(r + 1, v);
        }
    }
}

/* Complexity Analysis
   Let n = number of nodes, q = number of queries.

   Approach 1: Brute force per query
     Time : O(q * n)  - each "query u v" runs BFS over the whole tree and
            walks the path; updates are O(1).
     Space: O(n)      - BFS queue, parent array, path list.
     Key Observation: a path's characters can form a palindrome iff at most
     one letter has an odd count, which is exactly bitCount(mask) <= 1 on the
     26-bit parity mask.

   Approach 2: Euler tour + XOR BIT + binary-lifting LCA (recommended)
     Time : O((n + q) log n)  - DFS O(n), binary-lifting table O(n log n),
            n initial range updates O(n log n), then each query is one LCA
            (O(log n)) plus one point query (O(log n)) or one range update
            (O(log n)).
     Space: O(n log n)        - fa table of n x log n, plus BIT, dep, l, r.
     Key Observation: a point query at l[x] in the XOR BIT yields the XOR of
     every ancestor's letter, so rootPath(x) is available in O(log n); the
     u -> v path mask is rootPath(u) ^ rootPath(v) ^ char(lca), turning every
     query into O(log n) work regardless of path length.
*/

