// Source: https://leetcode.com/problems/find-the-maximum-length-of-valid-subsequence-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-15
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 62.33 MB Beats 52.46%

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
    // Traverse the array while tracking:
    // - The count of even and odd numbers (same-parity subsequences)
    // - The length of the longest alternating subsequence ending in each parity
    // Update each parity's alternating chain based on the previous opposite parity
    // Time: O(n), Space: O(1) — constant extra space and linear traversal
    public int maximumLength(int[] nums) {
        int[] parityCount = new int[2];  // parityCount[0] = even, parityCount[1] = odd
        int[] alternatingEnd = new int[2]; // alternatingEnd[0] = alt seq ending in even, etc.

        for (int num : nums) {
            int parity = num % 2;
            parityCount[parity]++;
            alternatingEnd[parity] = alternatingEnd[1 - parity] + 1;
        }

        return Math.max(
            Math.max(parityCount[0], parityCount[1]),
            Math.max(alternatingEnd[0], alternatingEnd[1])
        );
    }
}
