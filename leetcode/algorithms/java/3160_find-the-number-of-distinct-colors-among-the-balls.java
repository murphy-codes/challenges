// Source: https://leetcode.com/problems/find-the-number-of-distinct-colors-among-the-balls/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-07
// At the time of submission:
//   Runtime 29 ms Beats 99.70%
//   Memory 91.00 MB Beats 76.44%

/****************************************
* 
* You are given an integer `limit` and a 2D array `queries` of size `n x 2`.
* There are `limit + 1` balls with distinct labels in the range `[0, limit]`. 
* _ Initially, all balls are uncolored. For every query in `queries` that is of 
* _ the form `[x, y]`, you mark ball `x` with the color `y`. After each query, 
* _ you need to find the number of distinct colors among the balls.
* Return an array `result` of length `n`, where `result[i]` denotes the number 
* _ of distinct colors after `ith` query.
* Note that when answering a query, lack of a color will not be considered as a color.
* 
* Example 1:
* Input: limit = 4, queries = [[1,4],[2,5],[1,3],[3,4]]
* Output: [1,2,2,3]
* Explanation:
* After query 0, ball 1 has color 4.
* After query 1, ball 1 has color 4, and ball 2 has color 5.
* After query 2, ball 1 has color 3, and ball 2 has color 5.
* After query 3, ball 1 has color 3, ball 2 has color 5, and ball 3 has color 4.
* 
* Example 2:
* Input: limit = 4, queries = [[0,1],[1,2],[2,2],[3,4],[4,5]]
* Output: [1,2,2,3,4]
* Explanation:
* After query 0, ball 0 has color 1.
* After query 1, ball 0 has color 1, and ball 1 has color 2.
* After query 2, ball 0 has color 1, and balls 1 and 2 have color 2.
* After query 3, ball 0 has color 1, balls 1 and 2 have color 2, and ball 3 has color 4.
* After query 4, ball 0 has color 1, balls 1 and 2 have color 2, ball 3 has color 4, 
* __ and ball 4 has color 5.
* 
* Constraints:
* • 1 <= limit <= 10^9
* • 1 <= n == queries.length <= 10^5
* • queries[i].length == 2
* • 0 <= queries[i][0] <= limit
* • 1 <= queries[i][1] <= 10^9
* 
****************************************/

class Solution {
    // For each query, we update the color of a ball and track how many times
    // each color is used across all balls. We maintain the count of distinct
    // colors by incrementing when a color is first used, and decrementing when
    // a color becomes unused. All operations are O(1) thanks to HashMaps.
    // Time: O(n), Space: O(n), where n is the number of queries.
    public int[] queryResults(int limit, int[][] queries) {
        final int n = queries.length;
        HashMap<Integer, Integer> ballToColor = new HashMap<>(n);   // maps ball -> color
        HashMap<Integer, Integer> colorUsageCount = new HashMap<>(n); // maps color -> usage count
        int[] result = new int[n];
        int distinctColorCount = 0;

        for (int i = 0; i < n; i++) {
            int ball = queries[i][0];
            int newColor = queries[i][1];

            // Increase count for the new color (if it's new, increment distinct counter)
            int countBefore = colorUsageCount.getOrDefault(newColor, 0);
            if (countBefore == 0) distinctColorCount++;
            colorUsageCount.put(newColor, countBefore + 1);

            // Check if the ball had a previous color and update that color's count
            Integer oldColor = ballToColor.get(ball);
            if (oldColor != null) {
                int oldColorCount = colorUsageCount.getOrDefault(oldColor, 0);
                if (oldColorCount == 1) distinctColorCount--;
                colorUsageCount.put(oldColor, oldColorCount - 1);
            }

            // Update the ball's color
            ballToColor.put(ball, newColor);
            result[i] = distinctColorCount;
        }

        return result;
    }
}
