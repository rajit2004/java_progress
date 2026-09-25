package LeetCode.Strings;

import java.util.*;

public class LeetCode_1096_BraceExpansion_II {
    public static void main(String[] args) {

        // Sample 1 (LeetCode): "{a,b}{c,{d,e}}"
        //   -> {ac, ad, ae, bc, bd, be}
        System.out.println("Sample 1 -> Stack: " + braceExpansionIIStack("{a,b}{c,{d,e}}")
                + " | Recursive: " + braceExpansionII("{a,b}{c,{d,e}}"));

        // Sample 2 (LeetCode): "{{a,z},a{b,c},{ab,z}}"
        //   -> {a, ab, ac, z}
        System.out.println("Sample 2 -> Stack: " + braceExpansionIIStack("{{a,z},a{b,c},{ab,z}}")
                + " | Recursive: " + braceExpansionII("{{a,z},a{b,c},{ab,z}}"));

        // Edge case: single letter
        System.out.println("Edge (single letter) -> Stack: " + braceExpansionIIStack("a")
                + " | Recursive: " + braceExpansionII("a"));

        // Edge case: single group
        System.out.println("Edge (single group) -> Stack: " + braceExpansionIIStack("{a,b,c}")
                + " | Recursive: " + braceExpansionII("{a,b,c}"));

        // Edge case: nested groups with concatenation
        //   "{a,b}{c,{d,e}}" -> {ac,ad,ae,bc,bd,be}
        System.out.println("Edge (nested group) -> Stack: " + braceExpansionIIStack("{a,b}{c,{d,e}}")
                + " | Recursive: " + braceExpansionII("{a,b}{c,{d,e}}"));

        // Edge case: union deduplicates across branches
        //   "{a,a,b}" -> {a,b}
        System.out.println("Edge (dedup) -> Stack: " + braceExpansionIIStack("{a,a,b}")
                + " | Recursive: " + braceExpansionII("{a,a,b}"));

        // Edge case: deep nesting
        //   "{a,{b,{c,d}}}" -> {a,b,c,d}
        System.out.println("Edge (deep nest) -> Stack: " + braceExpansionIIStack("{a,{b,{c,d}}}")
                + " | Recursive: " + braceExpansionII("{a,{b,{c,d}}}"));
    }


    /*
        Approach 1: Stack-based expression parsing (operator precedence)

        Treat the expression as containing three operators:
            ','  ->  union        (lowest precedence)
            '*'  ->  concatenation (higher precedence, implicit)
            '{' '}' -> grouping

        We maintain two stacks:
            - op  : operator stack (chars '{', '+', '*')
                    where '+' represents union (we map ',' -> '+')
            - stk : operand stack, each entry is a sorted set of strings

        Rules while scanning left to right:
            1. ','  -> pop all '*' operators (higher precedence), then push '+'.
            2. '{'  -> if the previous token was a letter or '}', an implicit '*'
                       must be pushed before '{'. Then push '{'.
            3. '}'  -> pop all operators until '{', then pop the '{'.
            4. letter -> same implicit-'*' rule as '{'. Then create a singleton
                        set containing the letter and push onto stk.

        At the end, drain all remaining operators from `op`.

        `ope` performs the top operator on the top two operand sets:
            '+' : union (mutates the lower set)
            '*' : Cartesian product (concatenate each pair)

        Time:  O(N * L) where N = expression length, L = size of largest set
        Space: O(N * L)
    */
    static List<String> braceExpansionIIStack(String expression) {
        Deque<Character> op = new ArrayDeque<>();
        List<Set<String>> stk = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == ',') {
                // Union has the lowest precedence: first reduce all pending concatenations
                while (!op.isEmpty() && op.peek() == '*') {
                    applyOp(op, stk);
                }
                op.push('+');
            } else if (c == '{') {
                // Implicit concatenation between `letter{` or `}{`
                if (i > 0 && (expression.charAt(i - 1) == '}' ||
                        Character.isLetter(expression.charAt(i - 1)))) {
                    op.push('*');
                }
                op.push('{');
            } else if (c == '}') {
                // Reduce everything until the matching '{'
                while (!op.isEmpty() && op.peek() != '{') {
                    applyOp(op, stk);
                }
                op.pop();   // pop the '{'
            } else {
                // Letter: implicit concatenation with previous token
                if (i > 0 && (expression.charAt(i - 1) == '}' ||
                        Character.isLetter(expression.charAt(i - 1)))) {
                    op.push('*');
                }
                Set<String> singleton = new TreeSet<>();
                singleton.add(String.valueOf(c));
                stk.add(singleton);
            }
        }

        // Drain remaining operators (they appear in precedence order already)
        while (!op.isEmpty()) {
            applyOp(op, stk);
        }

        return new ArrayList<>(stk.get(stk.size() - 1));
    }

    /*
        Apply the top operator to the top two operand sets.

        '+' : set[l].addAll(set[r])       -- union (mutates set[l])
        '*' : set[l] = { a + b : a in left, b in right }  -- Cartesian concatenation

        In both cases, pop the operator and remove the now-redundant top operand.
    */
    static void applyOp(Deque<Character> op, List<Set<String>> stk) {
        int l = stk.size() - 2, r = stk.size() - 1;

        if (op.peek() == '+') {
            stk.get(l).addAll(stk.get(r));
        } else {
            Set<String> merged = new TreeSet<>();
            for (String left : stk.get(l)) {
                for (String right : stk.get(r)) {
                    merged.add(left + right);
                }
            }
            stk.set(l, merged);
        }

        op.pop();
        stk.remove(stk.size() - 1);
    }


    /*
        Approach 2: Recursive descent parser

        Grammar (in increasing precedence order):
            expr -> term (',' term)*
            term -> item+                 (implicit concatenation)
            item -> letter | '{' expr '}'

        Each rule returns a Set<String> (TreeSet for automatic sorting + dedup):
            - expr()  : union of all terms separated by ','
            - term()  : Cartesian concatenation of consecutive items
            - item()  : either a single letter, or a recursive expr() inside braces

        Parser state (`s` and `i`) is stored in static fields, which are
        initialized at the start of `braceExpansionII` so repeated calls
        don't leak state between invocations.

        Time:  O(N * L) — each character is consumed once; work per node
                          proportional to the size of sets being combined.
        Space: O(N * L) — recursion depth up to N; sets hold up to L strings.
    */
    static String s;    // the input expression
    static int i;       // current parsing index

    static List<String> braceExpansionII(String expression) {
        s = expression;
        i = 0;
        return new ArrayList<>(parseExpr());
    }

    /*
        item -> letter | '{' expr '}'
    */
    static Set<String> parseItem() {
        Set<String> ret = new TreeSet<>();

        if (s.charAt(i) == '{') {
            i++;                       // skip '{'
            ret = parseExpr();
        } else {
            ret.add(String.valueOf(s.charAt(i)));   // single letter
        }
        i++;                           // consume the letter or the closing '}'
        return ret;
    }

    /*
        term -> item+        (concatenation of consecutive items)
    */
    static Set<String> parseTerm() {
        // Start with {""} so the first Cartesian product just yields the item
        Set<String> ret = new TreeSet<>();
        ret.add("");

        // An item starts with '{' or a lowercase letter
        while (i < s.length() && (s.charAt(i) == '{' || Character.isLetter(s.charAt(i)))) {
            Set<String> sub = parseItem();
            Set<String> merged = new TreeSet<>();

            for (String left : ret) {
                for (String right : sub) {
                    merged.add(left + right);
                }
            }
            ret = merged;
        }

        return ret;
    }

    /*
        expr -> term (',' term)*
    */
    static Set<String> parseExpr() {
        Set<String> ret = new TreeSet<>();

        while (true) {
            ret.addAll(parseTerm());

            if (i < s.length() && s.charAt(i) == ',') {
                i++;                       // consume ','
                continue;
            }
            break;
        }

        return ret;
    }
}

/*
---------------------------------------------------------
Complexity Analysis
---------------------------------------------------------

Let N = length of the expression and L = size of the largest intermediate set.

Approach 1: Stack-based expression parsing

Time Complexity: O(N * L^2) in the worst case

- Each character is processed once (O(N)).
- Each union / Cartesian product costs O(L^2) for concatenations.
- Total work is bounded by O(N * L^2) where L is the max set size.

Space Complexity: O(N * L)

- Two stacks: operator stack O(N), operand stack O(N * L).


Approach 2: Recursive descent parser

Time Complexity: O(N * L^2) in the worst case

- Each character is consumed once; each parse rule does O(L^2) merge work.
- Depth of recursion is bounded by nesting depth (<= N).

Space Complexity: O(N * L)

- Recursion depth <= N; each level holds up to L strings.

Key Observation:
Both approaches parse the same grammar but differ in parsing strategy:
    - The stack version flattens the recursion into an explicit operator
      stack with precedence handling (',' = union, implicit = concat).
    - The recursive descent version mirrors the grammar directly, which is
      easier to read but uses the call stack for nesting.

Using a TreeSet for each result gives sorted, deduplicated output for free —
matching LeetCode's required return format.

---------------------------------------------------------
*/