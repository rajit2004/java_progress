package Recursion.Questions.Arrays;

import java.util.ArrayList;

public class ReturnMultipleOccurrenceLinearSearch {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,4,4};
        System.out.println(find(arr,4));
    }

//    this helper fn finds all the indexes where we find the target.
    static ArrayList<Integer> helper(int[] arr , int target , int index){

//        base condition :
        if (index == arr.length)            // we have reached the end of the array but didn't find the target
            return new ArrayList<>();       // return the empty list

//        we have to store the index wherever we find the target:
        ArrayList<Integer> ans = helper(arr , target , index + 1);

        if(arr[index] == target) {
            ans.addFirst(index);
        }
        return ans;
    }
    static ArrayList<Integer> find(int[] arr , int target){
        if(arr == null || arr.length == 0)
            return new ArrayList<>();

        return helper(arr , target , 0);
    }
}


/*
    INTUITIVE APPROACH (what feels natural):

        ArrayList<Integer> ans = new ArrayList<>();
        if(index == arr.length) return ans;
        if(arr[index] == target) ans.add(index);
        helper(arr, target, index + 1);   // call next
        return ans;

    PROBLEM WITH THIS:
        Every single recursive call creates its OWN separate empty list.
        When helper(0) runs -> it has its own ans = []
        When helper(1) runs -> it has its own ans = []
        When helper(2) runs -> it has its own ans = []
        ...and so on.

        So even if helper(2) finds a match and adds index 2 to ITS ans,
        that information is LOST. helper(0) never sees it.
        Everyone has their own list. Nobody shares. Results die locally.

        Also notice: helper(arr, target, index + 1) is called but its
        return value is completely IGNORED. We never said:
        ans = helper(...) so whatever the deeper call found -> thrown away.

    WHY THE CURRENT APPROACH WORKS:

        ArrayList<Integer> ans = helper(arr, target, index + 1);

        Instead of creating a new empty list, we are saying:
        "Let everyone ahead of me finish first. Whatever list THEY return,
        that becomes MY list. Then I will add to it if I find a match."

        This means ONE single list is born at the base case (empty []),
        and it gets passed back up through every recursive call.
        Each level adds to the SAME list if it finds a match.

        helper(5) -> returns []          <- list born here (base case)
        helper(4) -> gets [],   match -> addFirst(4) -> returns [4]
        helper(3) -> gets [4],  no match             -> returns [4]
        helper(2) -> gets [4],  match -> addFirst(2) -> returns [2,4]
        helper(1) -> gets [2,4], no match            -> returns [2,4]
        helper(0) -> gets [2,4], match -> addFirst(0) -> returns [0,2,4]

    ONE LINE SUMMARY:
        Your approach = 5 people each with their own notebook, nobody shares.
        Current approach = 1 notebook born at the end, passed up the chain,
        everyone writes in the SAME notebook.
*/