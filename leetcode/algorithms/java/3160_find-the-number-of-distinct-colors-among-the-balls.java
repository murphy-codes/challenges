// Source: https://leetcode.com/problems/find-the-number-of-distinct-colors-among-the-balls/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-07

/****************************************
* 
* You are given an integer `limit` and a 2D array `queries` of size `n x 2`.
* 
* There are `limit + 1` balls with distinct labels in the range `[0, limit]`. Initially, all balls are uncolored. For every query in `queries` that is of the form `[x, y]`, you mark ball `x` with the color `y`. After each query, you need to find the number of distinct colors among the balls.
* 
* Return an array `result` of length `n`, where `result[i]` denotes the number of distinct colors after `ith` query.
* 
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
* After query 4, ball 0 has color 1, balls 1 and 2 have color 2, ball 3 has color 4, and ball 4 has color 5.
* 
* Constraints:
* • 1 <= limit <= 10^9
* • 1 <= n == queries.length <= 10^5
* • queries[i].length == 2
* • 0 <= queries[i][0] <= limit
* • 1 <= queries[i][1] <= 10^9
* 
****************************************/

import java.util.HashMap;

class Solution {
    public int[] queryResults(int limit, int[][] queries) {
        HashMap<Integer, Integer> balls = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
        int[] distinctColors = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            if (balls.containsKey(queries[i][0])){
                if (colors.get(balls.get(queries[i][0])) == 1){
                    colors.remove(balls.get(queries[i][0]));
                } else {
                    colors.replace(balls.get(queries[i][0]),colors.get(balls.get(queries[i][0]))-1);
                }
                balls.replace(queries[i][0],queries[i][1]);
                //
            } else {
                balls.put(queries[i][0],queries[i][1]);
            }
            if (colors.containsKey(queries[i][1])){
                colors.replace(queries[i][1],colors.get(queries[i][1])+1);
            } else {
                colors.put(queries[i][1],1);
            }
            distinctColors[i] = colors.size();
        }
        return distinctColors;
    }
}