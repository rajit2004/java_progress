package LeetCode.Arrays;

public class LeetCode_3633_EarliestFinishTime {
    public static void main(String[] args) {
        int[] landStartTime = {2,8};
        int[] landDuration = {4,1};
        int[] waterStartTime = {6};
        int[] waterDuration = {3};

        System.out.println(earliestFinishTime(landStartTime, landDuration, waterStartTime, waterDuration));
    }
    static int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration){

        int ans = Integer.MAX_VALUE;

//        case 1 : Land -> Water

        for(int i = 0 ; i < landStartTime.length ; i++){
            for(int j = 0 ; j < waterStartTime.length ; j++){

                int landEnd = landStartTime[i] + landDuration[i];

                int waterSt = Math.max(landEnd,waterStartTime[j]);
                int waterTotal = waterSt + waterDuration[j];

//        case 2 : Water -> Land

                int waterEnd = waterStartTime[j] + waterDuration[j];

                int landSt = Math.max(waterEnd , landStartTime[i]);
                int landTotal = landSt + landDuration[i];

                ans = Math.min(ans,Math.min(waterTotal , landTotal));

            }
        }
        return ans;
    }
}
