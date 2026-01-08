// Source: https://leetcode.com/problems/max-dot-product-of-two-subsequences/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-07
// At the time of submission:
//   Runtime 11 ms Beats 59.26%
//   Memory 46.61 MB Beats 31.85%

/****************************************
* 
* Given two arrays `nums1` and `nums2`.
* Return the maximum dot product between non-empty subsequences of `nums1` and
* _ `nums2` with the same length.
* A subsequence of a array is a new array which is formed from the original
* _ array by deleting some (can be none) of the characters without disturbing
* _ the relative positions of the remaining characters. (ie, `[2,3,5]` is a
* _ subsequence of `[1,2,3,4,5]` while `[1,5,3]` is not).
*
* Example 1:
* Input: nums1 = [2,1,-2,5], nums2 = [3,0,-6]
* Output: 18
* Explanation: Take subsequence [2,-2] from nums1 and subsequence [3,-6] from nums2.
* Their dot product is (2*3 + (-2)*(-6)) = 18.
*
* Example 2:
* Input: nums1 = [3,-2], nums2 = [2,-6,7]
* Output: 21
* Explanation: Take subsequence [3] from nums1 and subsequence [7] from nums2.
* Their dot product is (3*7) = 21.
*
* Example 3:
* Input: nums1 = [-1,-1], nums2 = [1,1]
* Output: -1
* Explanation: Take subsequence [-1] from nums1 and subsequence [1] from nums2.
* Their dot product is -1.
*
* Constraints:
* • `1 <= nums1.length, nums2.length <= 500`
* • `-1000 <= nums1[i], nums2[i] <= 1000`
* 
****************************************/

class Solution {
    // Dynamic programming where dp[i][j] stores the maximum dot product
    // using non-empty subsequences from nums1[0..i] and nums2[0..j].
    // At each pair, either start a new subsequence or extend a previous one,
    // while also allowing skipping elements from either array.
    // Time complexity: O(n * m), Space complexity: O(n * m).
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;

        int[][] dp = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int product = nums1[i] * nums2[j];

                int bestWithPair = product;
                if (i > 0 && j > 0) {
                    bestWithPair += Math.max(dp[i - 1][j - 1], 0);
                }

                int skipNums1 = (i > 0) ? dp[i - 1][j] : Integer.MIN_VALUE;
                int skipNums2 = (j > 0) ? dp[i][j - 1] : Integer.MIN_VALUE;

                dp[i][j] = Math.max(
                    bestWithPair,
                    Math.max(skipNums1, skipNums2)
                );
            }
        }

        return dp[n - 1][m - 1];
    }
}
