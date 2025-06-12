// Source: https://leetcode.com/problems/zero-array-transformation-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-12
// At the time of submission:
//   Runtime 3 ms Beats 99.80%
//   Memory 129.09 MB Beats 5.24%

/****************************************
* 
* You are given an integer array `nums` of length `n` and a 2D array `queries` where `queries[i] = [li, ri, vali]`.
* Each `queries[i]` represents the following action on `nums`:
* • Decrement the value at each index in the range `[l_i, r_i]` in `nums` by at most `val_i`.
* • The amount by which each value is decremented can be chosen independently for each index.
* A Zero Array is an array with all its elements equal to 0.
* Return the minimum possible non-negative value of `k`, such that after processing the first `k` queries in sequence, `nums` becomes a Zero Array. If no such `k` exists, return -1.
*
* Example 1:
* Input: nums = [2,0,2], queries = [[0,2,1],[0,2,1],[1,1,3]]
* Output: 2
* Explanation:
* For i = 0 (l = 0, r = 2, val = 1):
* Decrement values at indices [0, 1, 2] by [1, 0, 1] respectively.
* The array will become [1, 0, 1].
* For i = 1 (l = 0, r = 2, val = 1):
* Decrement values at indices [0, 1, 2] by [1, 0, 1] respectively.
* The array will become [0, 0, 0], which is a Zero Array. Therefore, the minimum value of k is 2.
*
* Example 2:
* Input: nums = [4,3,2,1], queries = [[1,3,2],[0,2,1]]
* Output: -1
* Explanation:
* For i = 0 (l = 1, r = 3, val = 2):
* Decrement values at indices [1, 2, 3] by [2, 2, 1] respectively.
* The array will become [4, 1, 0, 0].
* For i = 1 (l = 0, r = 2, val = 1):
* Decrement values at indices [0, 1, 2] by [1, 1, 0] respectively.
* The array will become [3, 0, 0, 0], which is not a Zero Array.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 0 <= nums[i] <= 5 * 10^5
* • 1 <= queries.length <= 10^5
* • queries[i].length == 3
* • 0 <= l_i <= r_i < nums.length
* • 1 <= val_i <= 5
* 
****************************************/

class Solution {
    // Uses a difference array for efficient range updates in O(1) time.
    // Iterates through nums while dynamically applying the minimum queries.
    // Ensures queries are processed in order and updates are correctly applied.
    // Runs in O(N + M) time complexity, using O(N) space for difference tracking.
    // Returns -1 if nums cannot be fully zeroed after all queries.
    public int minZeroArray(int[] nums, int[][] queries) {
        int n = nums.length, sum = 0, queryCount = 0;
        int[] diffArray = new int[n + 1];

        // Iterate through nums and apply queries as needed
        for (int i = 0; i < n; i++) {
            // While the current index cannot be reduced to zero
            while (sum + diffArray[i] < nums[i]) {
                queryCount++;

                // If all queries are exhausted and nums isn't zeroed out, return -1
                if (queryCount > queries.length) {
                    return -1;
                }

                int left = queries[queryCount - 1][0];
                int right = queries[queryCount - 1][1];
                int val = queries[queryCount - 1][2];

                // Apply the range update using the difference array technique
                if (right >= i) {
                    diffArray[Math.max(left, i)] += val;
                    diffArray[right + 1] -= val;
                }
            }
            // Update the prefix sum at the current index
            sum += diffArray[i];
        }
        return queryCount;
    }
}
