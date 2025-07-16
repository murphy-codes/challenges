// Source: https://leetcode.com/problems/find-the-maximum-length-of-valid-subsequence-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-15
// At the time of submission:
//   Runtime 16 ms Beats 14.75%
//   Memory 62.43 MB Beats 38.52%

/****************************************
* 
* You are given an integer array `nums`.
* A subsequence `sub` of `nums` with length `x` is called valid if it satisfies:
* • `(sub[0] + sub[1]) % 2 == (sub[1] + sub[2]) % 2 == ... == (sub[x - 2] + sub[x - 1]) % 2.`
* Return the length of the longest valid subsequence of `nums`.
* A subsequence is an array that can be derived from another array by deleting
* _ some or no elements without changing the order of the remaining elements.
*
* Example 1:
* Input: nums = [1,2,3,4]
* Output: 4
* Explanation:
* The longest valid subsequence is [1, 2, 3, 4].
*
* Example 2:
* Input: nums = [1,2,1,1,2,1,2]
* Output: 6
* Explanation:
* The longest valid subsequence is [1, 2, 1, 2, 1, 2].
*
* Example 3:
* Input: nums = [1,3]
* Output: 2
* Explanation:
* The longest valid subsequence is [1, 3].
*
* Constraints:
* • 2 <= nums.length <= 2 * 10^5
* • 1 <= nums[i] <= 10^7
* 
****************************************/

class Solution {
    // Try all 4 valid parity patterns: even-only, odd-only,
    // even-odd alternating, and odd-even alternating.
    // Iterate greedily through the array to build the longest valid
    // subsequence under each pattern and return the maximum length.
    // Time: O(n), Space: O(1), where n = nums.length
    public int maximumLength(int[] nums) {
        int maxLen = 0;

        // Try all 4 starting patterns:
        // 0 = even, 1 = odd
        for (int start = 0; start <= 1; start++) {
            for (int alt = 0; alt <= 1; alt++) {
                int len = 0;
                int expect = start;

                for (int num : nums) {
                    if (num % 2 == expect) {
                        len++;
                        expect ^= alt; // flip between 0 and 1 if alternating
                    }
                }

                maxLen = Math.max(maxLen, len);
            }
        }

        return maxLen;
    }
}
