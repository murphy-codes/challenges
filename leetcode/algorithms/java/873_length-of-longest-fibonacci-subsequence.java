// Source: https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-26

/****************************************
* 
* A sequence `x_1, x_2, ..., x_n` is Fibonacci-like if:
* • `n >= 3`
* • `x_i + x_i+1 == x_i+2` for all `i + 2 <= n`
* Given a strictly increasing array `arr` of positive integers forming a sequence, return the length of the longest Fibonacci-like subsequence of `arr`. If one does not exist, return `0`.
* A subsequence is derived from another sequence `arr` by deleting any number of elements (including none) from `arr`, without changing the order of the remaining elements. For example, `[3, 5, 8]` is a subsequence of `[3, 4, 5, 6, 7, 8]`.
*
* Example 1:
* Input: arr = [1,2,3,4,5,6,7,8]
* Output: 5
* Explanation: The longest subsequence that is fibonacci-like: [1,2,3,5,8].
*
* Example 2:
* Input: arr = [1,3,7,11,12,14,18]
* Output: 3
* Explanation: The longest subsequence that is fibonacci-like: [1,11,12], [3,11,14] or [7,11,18].
*
* Constraints:
* • 3 <= arr.length <= 1000
* • 1 <= arr[i] < arr[i + 1] <= 10^9
* 
****************************************/

import java.util.HashMap;

class Solution {
    // Uses DP with a HashMap to find the longest Fibonacci-like subsequence.
    // dp[i][j] stores the length of the sequence ending at indices i and j.
    // If arr[j] - arr[i] exists in arr, extend dp[k][i] → dp[i][j] = dp[k][i] + 1.
    // Time Complexity: O(n²) since we check all pairs (i, j) with O(1) lookups.
    // Space Complexity: O(n²) for DP storage plus O(n) for the HashMap.
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        HashMap<Integer, Integer> indexMap = new HashMap<>();
        int[][] dp = new int[n][n];
        int maxLen = 0;

        // Store indices for quick lookup
        for (int i = 0; i < n; i++) {
            indexMap.put(arr[i], i);
        }

        // Iterate through pairs (i, j) and find k such that arr[k] + arr[i] = arr[j]
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < j; i++) {
                int prev = arr[j] - arr[i]; // Find the number needed to form Fibonacci
                if (prev < arr[i] && indexMap.containsKey(prev)) {
                    int k = indexMap.get(prev);
                    dp[i][j] = dp[k][i] + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
                dp[i][j] = Math.max(dp[i][j], 2); // Ensure min length 2 for valid pairs
            }
        }

        return maxLen >= 3 ? maxLen : 0; // A Fibonacci sequence must have at least 3 numbers
    }
}
