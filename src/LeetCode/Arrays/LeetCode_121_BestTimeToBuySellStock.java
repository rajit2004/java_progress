package LeetCode.Arrays;

/*
You are given an array prices where prices[i] is the price of a given stock on the ith day.

You want to maximize your profit by choosing a single day to buy one stock
and choosing a different day in the future to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
*/

/*
You are given an array prices where prices[i] is the stock price on day i.

We must:
1. Buy first.
2. Sell later.
3. Maximize profit.

If no profitable transaction exists, return 0.
*/

    public class LeetCode_121_BestTimeToBuySellStock {
        public static void main(String[] args) {
            int[] prices = {7,6,4,3,1};
            System.out.println(maxProfit(prices));
        }

/*
    Key Observation:

    For every day, assume we sell on that day.

    To maximize profit, we should have bought at the
    cheapest price seen BEFORE that day.

    Therefore:
    - Keep track of the minimum price seen so far.
    - Calculate profit if sold today.
    - Keep the best profit found.
 */

        static int maxProfit(int[] prices){

        // Stores the maximum profit found so far.
        // Starts at 0 because the problem says:
        // if no profit is possible, return 0.
        int maxprofit = 0;

        // Lowest stock price seen while traversing the array.
        int minSoFar = Integer.MAX_VALUE;


/*
        Example:

        prices = [7,1,5,3,6,4]

        Day 1:
        minSoFar = 7

        Day 2:
        minSoFar = 1

        Day 3:
        profit = 5 - 1 = 4

        Day 5:
        profit = 6 - 1 = 5

        Answer = 5
*/



/*
        int currprice = 0;
        int currProfit = 0;



        for (int i = 0; i < prices.length; i++) {
            currprice = prices[i];
            currProfit = currprice - minSoFar;
            maxprofit = Math.max(maxprofit , currProfit);
            minSoFar = Math.min(currprice , minSoFar);
        }
*/

/*
// optimized for loop :
        for (int i = 0; i < prices.length; i++) {
            maxprofit = Math.max(maxprofit , prices[i] - minSoFar);
            minSoFar = Math.min(prices[i] , minSoFar);
        }
 */


//        further optimized for loop :

// Visit every stock price once.

        for (int price : prices) {

            // Profit if we sell today and buy at the
            // cheapest price seen before today.
            maxprofit = Math.max(maxprofit, price - minSoFar);

            // Update the cheapest buying price seen so far.
            minSoFar = Math.min(price, minSoFar);
        }


        return maxprofit;
    }
}
