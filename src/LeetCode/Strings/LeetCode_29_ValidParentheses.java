package LeetCode.Strings;

public class LeetCode_29_ValidParentheses {
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
