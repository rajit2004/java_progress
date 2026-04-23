package LeetCode;

//https://leetcode.com/problems/find-in-mountain-array/description/

/*
Given a mountain array mountainArr,
        return the minimum index such that mountainArr.get(index) == target.
        If such an index does not exist, return -1.
*/

public class LeetCode_1095_FindInMountainArrayBruteForce {
    public static void main(String[] args) {
        int[] mountainArr = {1,2,3,4,5,3,1};
        int target = 3;
        System.out.println(findInMountainArray(mountainArr,target));
    }
    static int findInMountainArray(int[] arr , int target){
        for(int i = 0 ; i < arr.length ; i++){
            if(arr[i] == target)
                return i;           //returns the first (minimum) index of occurrence of target.
        }
        return -1;
    }
}

/*
this algorithm is correct but fails the leetcode constraints coz it says make less than 100 calls but
for this algorithm if the input size is more it may take more than 100 calls so the test case fails .
*/
