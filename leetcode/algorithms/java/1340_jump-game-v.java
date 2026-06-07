// Source: https://leetcode.com/problems/search-in-rotated-sorted-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-25
// At the time of submission:
//   Runtime 5 ms Beats 100.00%
//   Memory 46.60 MB Beats 30.58%

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
    // Use monotonic stacks to find nearest greater neighbors within d.
    // Reverse the jump graph so larger values point to smaller values.
    // DFS + memoization computes the longest reachable chain per index.
    // Time: O(n), Space: O(n)
    public int maxJumps(int[] arr, int d) {

        int n = arr.length;

        // nearest greater index on left within distance d
        int[] leftGreater = new int[n];

        // monotonic decreasing stack of indices
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {

            while (top >= 0 && arr[stack[top]] <= arr[i]) {
                top--;
            }

            leftGreater[i] =
                (top < 0 || i - stack[top] > d)
                ? -1
                : stack[top];

            stack[++top] = i;
        }

        // nearest greater index on right within distance d
        int[] rightGreater = new int[n];

        top = -1;

        for (int i = n - 1; i >= 0; i--) {

            while (top >= 0 && arr[stack[top]] <= arr[i]) {
                top--;
            }

            rightGreater[i] =
                (top < 0 || stack[top] - i > d)
                ? -1
                : stack[top];

            stack[++top] = i;
        }

        int[] memo = new int[n];

        int best = 0;

        for (int i = 0; i < n; i++) {
            best = Math.max(
                best,
                dfs(i, leftGreater, rightGreater, memo)
            );
        }

        return best;
    }

    private int dfs(
        int idx,
        int[] leftGreater,
        int[] rightGreater,
        int[] memo
    ) {

        if (idx < 0) {
            return 0;
        }

        if (memo[idx] == 0) {

            memo[idx] =
                1 + Math.max(
                    dfs(leftGreater[idx],
                        leftGreater,
                        rightGreater,
                        memo),

                    dfs(rightGreater[idx],
                        leftGreater,
                        rightGreater,
                        memo)
                );
        }

        return memo[idx];
    }
}