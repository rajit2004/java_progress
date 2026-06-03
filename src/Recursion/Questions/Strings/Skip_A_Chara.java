package Recursion.Questions.Strings;

/*
=========================================================
TIME & SPACE COMPLEXITY ANALYSIS
=========================================================

1. Iterative Solution (skip)

Time Complexity  : O(n)

Reason:
- Traverse the string once.
- charAt() and append() are O(1).
- toString() copies all characters once.
- Total work remains linear.

Space Complexity : O(n)

Reason:
- StringBuilder stores the resulting string.
- In the worst case, all characters are kept.


---------------------------------------------------------

2. Recursive Solution using substring() (RecSkip)

Time Complexity  : O(n²)

Reason:
- There are n recursive calls.
- Each substring(1) creates a new string.
- Characters copied:
  (n-1) + (n-2) + (n-3) + ... + 1
- Total = O(n²)

Space Complexity : O(n²)

Reason:
- Recursion stack takes O(n).
- New substrings are created at every level.
- Total memory used grows quadratically.


---------------------------------------------------------

3. Recursive Solution using Index (altSkip)

Time Complexity  : O(n)

Reason:
- Each character is processed exactly once.
- No substring creation.
- Only recursive calls and comparisons.

Space Complexity : O(n)

Reason:
- Recursion depth can reach n.
- Call stack stores one frame per character.

=========================================================

Recursion + substring()  -> usually O(n²)
Recursion + index        -> usually O(n)

*/

public class Skip_A_Chara {
    public static void main(String[] args) {
        String str = "Apple";
        System.out.println(skip(str));
        System.out.println(RecSkip("papaya"));
        System.out.println(altSkip("purple" , 0));
    }

    //    iterative solution :
    static String skip(String word ){
        StringBuilder ans = new StringBuilder();
        for(int i = 0 ; i < word.length() ; i++){
            if(word.charAt(i) != 'p')
                ans.append(word.charAt(i));
        }
        return ans.toString();
    }

//    recursive approach :

    static String RecSkip(String word){

//        base condition :
        if(word.isEmpty())
            return word;

//      check if we get the targeted skip element

        char c = word.charAt(0);
        if(c == 'p')
            return RecSkip(word.substring(1));
        return c + RecSkip(word.substring(1));
    }

//    alternate recursive approach :
    static String altSkip(String word , int index){

//        base condition :
        if(index == word.length())
            return "";

//        checks:

        char c = word.charAt(index);

        if(c == 'p')
            return altSkip(word , index+1);

        return c + altSkip(word , index +1);
    }
}


