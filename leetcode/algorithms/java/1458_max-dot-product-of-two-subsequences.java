// Source: https://leetcode.com/problems/max-dot-product-of-two-subsequences/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-07
// At the time of submission:
//   Runtime 7 ms Beats 99.26%
//   Memory 46.25 MB Beats 85.93%

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
    // Uses dynamic programming with space optimization to compute the maximum
    // dot product of non-empty subsequences. Each dp state represents the best
    // result up to given indices, allowing either starting a new pair or
    // extending a previous subsequence while handling negative-only cases.
    // Time complexity: O(n * m), Space complexity: O(m).
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int NEGATIVE_INF = -1_000_000_000;

        // dp[j] = best result using nums1[0..i-1] and nums2[0..j-1]
        int[] prevRow = new int[m + 1];
        for (int j = 0; j <= m; j++) prevRow[j] = NEGATIVE_INF;

        for (int i = 1; i <= n; i++) {
            int[] currRow = new int[m + 1];
            for (int j = 0; j <= m; j++) currRow[j] = NEGATIVE_INF;

            for (int j = 1; j <= m; j++) {
                int product = nums1[i - 1] * nums2[j - 1];

                // Either start new subsequence or extend previous one
                int takePair = (prevRow[j - 1] == NEGATIVE_INF)
                        ? product
                        : Math.max(product, prevRow[j - 1] + product);

                // Best of taking pair, skipping nums1, or skipping nums2
                int best = takePair;
                best = Math.max(best, prevRow[j]);
                best = Math.max(best, currRow[j - 1]);

                currRow[j] = best;
            }

            prevRow = currRow;
        }

        return prevRow[m];
    }
}
