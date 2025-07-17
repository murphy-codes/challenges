// Source: https://leetcode.com/problems/find-the-maximum-length-of-valid-subsequence-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-16
// At the time of submission:
//   Runtime 26 ms Beats 100.00%
//   Memory 44.97 MB Beats 97.94%

/****************************************
* 
* You are given an integer array `nums` and a positive integer `k`.
* A subsequence `sub` of `nums` with length `x` is called valid if it satisfies:
* • `(sub[0] + sub[1]) % k == (sub[1] + sub[2]) % k == ... == (sub[x - 2] + sub[x - 1]) % k`.
* Return the length of the longest valid subsequence of `nums`.
*
* Example 1:
* Input: nums = [1,2,3,4,5], k = 2
* Output: 5
* Explanation:
* The longest valid subsequence is [1, 2, 3, 4, 5].
*
* Example 2:
* Input: nums = [1,4,2,3,1,4], k = 3
* Output: 4
* Explanation:
* The longest valid subsequence is [1, 4, 1, 4].
*
* Constraints:
* • 2 <= nums.length <= 10^3
* • 1 <= nums[i] <= 10^7
* • 1 <= k <= 10^3
* 
****************************************/

class Solution {
    // This solution checks all possible modulo values for (a + b) % k.  
    // It builds longest valid subsequences using dynamic programming.  
    // For each target value, we track max subsequence lengths by mod class.  
    // Time: O(n * k), Space: O(k). Efficient for input limits (n ≤ 1000, k ≤ 1000).
    public int maximumLength(int[] nums, int k) {
        int n = nums.length;
        if (k == 1) {
            return n;
        }

        int maxLength = 2;
        int[] remainders = new int[n];
        
        // Precompute each number modulo k
        for (int i = 0; i < n; i++) {
            remainders[i] = nums[i] % k;
        }

        // Try all possible values for (a + b) % k
        for (int target = 0; target < k; target++) {
            int[] dp = new int[k]; // dp[r] = max length of subseq ending with mod r
            for (int r : remainders) {
                int prevMod = (target - r + k) % k;
                dp[r] = dp[prevMod] + 1;
                maxLength = Math.max(maxLength, dp[r]);
            }
        }

        return maxLength;
    }
}
