package Recursion.Questions.Arrays;

import java.util.ArrayList;

public class ALT_3_ReturnMultipleOccurrenceLinearSearch {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,4,4};
        System.out.println(find(arr , 4 , 0));
    }
    static ArrayList<Integer> find(int[] arr, int target, int index) {

        // create an empty list to store the indexes found at "THIS" level
        ArrayList<Integer> ans = new ArrayList<>();

        // base condition: we've gone past the last box -> return empty list
        if (index == arr.length)
            return ans;

        // current box matches target -> add this index to our list
        if (arr[index] == target)
            ans.add(index);

        // go deeper -> ask the next index to find all matches after me
        // whatever it returns (a list of indexes) -> store it in ansfromcalls
        ArrayList<Integer> ansfromcalls = find(arr, target, index + 1);

        // merge the deeper list into our current list
        // so our ans now has: what WE found + what EVERYONE after us found
        ans.addAll(ansfromcalls);       // we basically added everything we found in each call

        // return the combined list back to whoever called us
        return ans;
    }
}
