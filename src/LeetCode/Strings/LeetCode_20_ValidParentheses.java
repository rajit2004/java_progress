package LeetCode.Strings;

/*
Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
An input string is valid if:
    Open brackets must be closed by the same type of brackets.
    Open brackets must be closed in the correct order.
    Every close bracket has a corresponding open bracket of the same type.
*/

public class LeetCode_20_ValidParentheses {
    public static void main(String[] args) {
        String s = "()[]{}";
        System.out.println(isValid(s));
    }
    static boolean isValid(String s) {
        StringBuilder store = new StringBuilder();

        for (char ch : s.toCharArray()) {

            // opening brackets → add
            if (ch == '(' || ch == '{' || ch == '[') {
                store.append(ch);
            }
            else {
                // if nothing to match
                if (store.isEmpty()) return false;

                char last = store.charAt(store.length() - 1);

                // check match
                if ((ch == ')' && last == '(') ||
                        (ch == '}' && last == '{') ||
                        (ch == ']' && last == '[')) {

                    // remove last
                    store.deleteCharAt(store.length() - 1);
                } else {
                    return false;
                }
            }
        }

        return store.isEmpty();
    }
}
