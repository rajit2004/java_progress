package Recursion.Questions.Strings.SubsetProblems;

/*
For every character we make 2 choices:

        1. Pick it
        2. Skip it

Total subsets generated = 2ⁿ

Time Complexity  : O(2ⁿ)
Space Complexity : O(n)   // recursion stack depth
*/


public class StringCreateAllSubsets {
    public static void main(String[] args) {
        sets("", "abc");
    }

    static void sets(String pick, String skip){

        /*
            Base Condition:

            When there are no characters left to process,
            we have completed one possible subset.
         */
        if(skip.isEmpty()){
            System.out.println(pick);
            return;
        }

        // Current character on which we need to make a decision.
        char ch = skip.charAt(0);

        /*
            Choice 1: Pick the current character.

            Add the current character to our answer
            and continue processing the remaining string.
         */
        sets(pick + ch, skip.substring(1));

        /*
            Choice 2: Skip the current character.

            Do not add the current character to the answer.
            Simply continue with the remaining string.
         */
        sets(pick, skip.substring(1));
    }
}