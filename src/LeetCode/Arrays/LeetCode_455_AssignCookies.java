package LeetCode.Arrays;

/*
Assume you are an awesome parent and want to give your children some cookies.
But, you should give each child at most one cookie.

Each child i has a greed factor g[i],
which is the minimum size of a cookie that the child will be content with;
and each cookie j has a size s[j].
If s[j] >= g[i], we can assign the cookie j to the child i, and the child i will be content.
Your goal is to maximize the number of your content children and output the maximum number.
*/

import java.util.Arrays;

public class LeetCode_455_AssignCookies {
    public static void main(String[] args) {
        int[] greed = {1,2,3};
        int[] size = {1,1};
        System.out.println(findContentChildren(greed , size));
    }
    static int findContentChildren(int[] g, int[] s){
        Arrays.sort(g);
        Arrays.sort(s);

        int child = 0;
        int cookie = 0;

        while (child < g.length && cookie < s.length) {
            if (s[cookie] >= g[child]) {
                child++;    // child is satisfied
            }
            cookie++;       // cookie is used
        }

        return child;
    }
}
