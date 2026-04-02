package LeetCode;

public class LeetCode744_FindSmallestLetterGreaterThanTarget {
    public static void main(String[] args) {
        char[] letters = {'a','c','e','g','i','k','m','o','q','s','u','w','y'};
        char target = 'z';
        System.out.println(nextGreatestLetter(letters,target));
    }
    static char nextGreatestLetter(char[] letters, char target){
        int start = 0;
        int end = letters.length-1;

        while(start<=end){

            int mid = start + (end- start)/2;

            if(target<letters[mid])
                end = mid -1;
            else
                start = mid + 1;
        }

        // if target is greater than or equal to all elements
        if(start == letters.length){
            return letters[0];
        }
        return letters[start];
    }
}
