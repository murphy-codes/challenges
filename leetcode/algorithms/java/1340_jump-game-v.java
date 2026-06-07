// Source: https://leetcode.com/problems/search-in-rotated-sorted-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-25
// At the time of submission:
//   Runtime 10 ms Beats 91.39%
//   Memory 46.22 MB Beats 78.17%

/****************************************
* 
* Given an array of integers `arr` and an integer `d`. In one step you can jump
* _ from index `i` to index:
* • `i + x` where: `i + x < arr.length` and `0 < x <= d`.
* • `i - x` where: `i - x >= 0` and  `0 < x <= d`.
* In addition, you can only jump from index `i` to index `j` if
* _ `arr[i] > arr[j]` and `arr[i] > arr[k]` for all indices `k` between
* _ `i` and `j` (More formally `min(i, j) < k < max(i, j)`).
* You can choose any index of the array and start jumping. Return the
* _ maximum number of indices you can visit.
* Notice that you can not jump outside of the array at any time.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/01/23/meta-chart.jpeg]
* Input: arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
* Output: 4
* Explanation: You can start at index 10. You can jump 10 --> 8 --> 6 --> 7 as shown.
* Note that if you start at index 6 you can only jump to index 7. You cannot jump to index 5 because 13 > 9. You cannot jump to index 4 because index 5 is between index 4 and 6 and 13 > 9.
* Similarly You cannot jump from index 3 to index 2 or index 1.
*
* Example 2:
* Input: arr = [3,3,3,3,3], d = 3
* Output: 1
* Explanation: You can start at any index. You always cannot jump to any index.
*
* Example 3:
* Input: arr = [7,6,5,4,3,2,1], d = 1
* Output: 7
* Explanation: Start at index 0. You can visit all the indicies.
*
* Constraints:
* • `1 <= arr.length <= 1000`
* • `1 <= arr[i] <= 10&5`
* • `1 <= d <= arr.length`
* 
****************************************/

class Solution {
    // DFS + memoized DP where dp[i] is max path length from index i.
    // Explore up to d positions left/right until blocked by >= height.
    // Strictly decreasing jumps prevent cycles and form a DAG.
    // Time: O(n * d), Space: O(n)
    public int maxJumps(int[] arr, int d) {
        int n = arr.length;
        int[] memo = new int[n];

        int best = 1;

        for (int i = 0; i < n; i++) {
            best = Math.max(best, dfs(arr, d, i, memo));
        }

        return best;
    }

    private int dfs(int[] arr, int d, int idx, int[] memo) {

        if (memo[idx] != 0) {
            return memo[idx];
        }

        int best = 1;

        // Explore left
        for (int next = idx - 1;
             next >= Math.max(0, idx - d);
             next--) {

            // Blocked by taller/equal element
            if (arr[next] >= arr[idx]) {
                break;
            }

            best = Math.max(
                best,
                1 + dfs(arr, d, next, memo)
            );
        }

        // Explore right
        for (int next = idx + 1;
             next <= Math.min(arr.length - 1, idx + d);
             next++) {

            // Blocked by taller/equal element
            if (arr[next] >= arr[idx]) {
                break;
            }

            best = Math.max(
                best,
                1 + dfs(arr, d, next, memo)
            );
        }

        memo[idx] = best;
        return best;
    }
}