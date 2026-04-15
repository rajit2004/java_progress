package Questions;

//amazon question -> Find position of an element in a sorted array of infinite numbers.

public class PosInInfiniteArrayBruteForce {
    public static void main(String[] args) {
        int[] array = {2, 5, 7, 9, 12, 15, 18, 21};
        int target = 87;
        System.out.println(PositionBruteForce(array,target));

    }

/*
    this method internally uses arr.length method
    static int PositionBruteForce(int[] arr , int target){
        int index = 0;
        for(int element : arr){
            if(element == target)
                return index;
        index++;
        }
        return -1;
    }
*/
    static int PositionBruteForce(int[] arr , int target){
        int i = 0;

        try {
            while(true){
                if(arr[i] == target){
                    return i;
                }

                // check before incrementing
                if(arr[i] > target){
                    return -1;
                }

                i++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }
}
