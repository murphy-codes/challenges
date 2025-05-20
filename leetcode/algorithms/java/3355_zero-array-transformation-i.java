// Source: https://leetcode.com/problems/zero-array-transformation-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-19
// At the time of submission:
//   Runtime 3 ms Beats 79.52%
//   Memory 96.86 MB Beats 5.40%

/****************************************
* 
* You are given an integer array `nums` of length `n` and a 2D array `queries`,
* _ where `queries[i] = [l_i, r_i]`.
* For each `queries[i]`:
* • Select a subset of indices within the range `[l_i, r_i]` in `nums`.
* • Decrement the values at the selected indices by 1.
* A Zero Array is an array where all elements are equal to 0.
* Return `true` if it is possible to transform `nums` into a Zero Array after
* _ processing all the queries sequentially, otherwise return `false`.
*
* Example 1:
* Input: nums = [1,0,1], queries = [[0,2]]
* Output: true
* Explanation:
* For i = 0:
* Select the subset of indices as [0, 2] and decrement the values at these indices by 1.
* The array will become [0, 0, 0], which is a Zero Array.
*
* Example 2:
* Input: nums = [4,3,2,1], queries = [[1,3],[0,2]]
* Output: false
* Explanation:
* For i = 0:
* Select the subset of indices as [1, 2, 3] and decrement the values at these indices by 1.
* The array will become [4, 2, 1, 0].
* For i = 1:
* Select the subset of indices as [0, 1, 2] and decrement the values at these indices by 1.
* The array will become [3, 1, 0, 0], which is not a Zero Array.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 0 <= nums[i] <= 10^5
* • 1 <= queries.length <= 10^5
* • queries[i].length == 2
* • 0 <= l_i <= r_i < nums.length
* 
****************************************/

class Solution {
    // Use a difference array to count how many times each index is affected.
    // For each query, increment the start and decrement after the end index.
    // Build prefix sums to get the final count of operations per index.
    // Check if each nums[i] can be reduced to 0 using available operations.
    // Time: O(n + q), Space: O(n), where n = nums.length and q = queries.length.
    public boolean isZeroArray(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] diff = new int[n + 1]; // Difference array for efficient range updates
        
        // Apply range updates using the difference array
        for (int[] query : queries) {
            int l = query[0];
            int r = query[1];
            diff[l]++;
            if (r + 1 < n) {
                diff[r + 1]--;
            }
        }
        
        // Compute prefix sums to get the total operation count per index
        int[] ops = new int[n];
        ops[0] = diff[0];
        for (int i = 1; i < n; i++) {
            ops[i] = ops[i - 1] + diff[i];
        }
        
        // Verify that each index has been decremented enough
        for (int i = 0; i < n; i++) {
            if (nums[i] > ops[i]) return false;
        }
        
        return true;
    }
}