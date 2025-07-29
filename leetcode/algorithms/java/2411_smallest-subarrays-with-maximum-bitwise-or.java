// Source: https://leetcode.com/problems/smallest-subarrays-with-maximum-bitwise-or/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-29
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 61.69 MB Beats 41.89%

/****************************************
* 
* You are given a 0-indexed array `nums` of length `n`, consisting of non-negative
* _ integers. For each index `i` from `0` to `n - 1`, you must determine the size
* _ of the minimum sized non-empty subarray of `nums` starting at `i` (inclusive)
* _ that has the maximum possible bitwise OR.
* • In other words, let `B_ij` be the bitwise OR of the subarray `nums[i...j]`.
* __ You need to find the smallest subarray starting at `i`, such that bitwise OR
* __ of this subarray is equal to `max(B_ik)` where `i <= k <= n - 1`.
* The bitwise OR of an array is the bitwise OR of all the numbers in it.
* Return an integer array `answer` of size `n` where `answer[i]` is the length of
* _ the minimum sized subarray starting at `i` with maximum bitwise OR.
* A subarray is a contiguous non-empty sequence of elements within an array.
*
* Example 1:
* Input: nums = [1,0,2,1,3]
* Output: [3,3,2,2,1]
* Explanation:
* The maximum possible bitwise OR starting at any index is 3.
* - Starting at index 0, the shortest subarray that yields it is [1,0,2].
* - Starting at index 1, the shortest subarray that yields the maximum bitwise OR is [0,2,1].
* - Starting at index 2, the shortest subarray that yields the maximum bitwise OR is [2,1].
* - Starting at index 3, the shortest subarray that yields the maximum bitwise OR is [1,3].
* - Starting at index 4, the shortest subarray that yields the maximum bitwise OR is [3].
* Therefore, we return [3,3,2,2,1].
*
* Example 2:
* Input: nums = [1,2]
* Output: [2,1]
* Explanation:
* Starting at index 0, the shortest subarray that yields the maximum bitwise OR is of length 2.
* Starting at index 1, the shortest subarray that yields the maximum bitwise OR is of length 1.
* Therefore, we return [2,1].
*
* Constraints:
* • n == nums.length
* • 1 <= n <= 10^5
* • 0 <= nums[i] <= 10^9
* 
****************************************/

class Solution {
    // Iterate through the array and use the current element to update
    // all previous positions where adding this element increases the OR.
    // This ensures that each index reflects the minimum subarray length
    // needed to reach the maximum possible OR from that point onward.
    // Time: O(n * 30) worst-case, amortized O(n); Space: O(1) extra space.
    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        for (int curr = 0; curr < n; ++curr) {
            int runningOr = nums[curr];
            result[curr] = 1;

            // Walk backward to update prior subarrays
            for (int prev = curr - 1; prev >= 0 && (nums[prev] | runningOr) != nums[prev]; --prev) {
                nums[prev] |= runningOr;  // Include current OR into previous
                result[prev] = curr - prev + 1;  // Update length to current subarray
            }
        }

        return result;
    }
}
