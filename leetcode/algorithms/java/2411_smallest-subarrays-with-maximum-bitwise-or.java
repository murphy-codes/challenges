// Source: https://leetcode.com/problems/smallest-subarrays-with-maximum-bitwise-or/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-29
// At the time of submission:
//   Runtime 28 ms Beats 48.65%
//   Memory 61.58 MB Beats 44.59%

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
    // Traverse the array backwards while tracking the last seen index for each bit.
    // At each index, determine the farthest point needed to include all bits
    // seen so far. The difference gives the minimal subarray length for max OR.
    // Bitwise checks and updates ensure linear time.
    // Time: O(n * 32) ~ O(n), Space: O(32) ~ O(1)
    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int[] lastSeen = new int[32]; // Last index each bit (0-31) was seen

        // Traverse the array backwards
        for (int i = n - 1; i >= 0; i--) {
            // Update the lastSeen positions for bits set in nums[i]
            for (int b = 0; b < 32; b++) {
                if ((nums[i] & (1 << b)) != 0) {
                    lastSeen[b] = i;
                }
            }

            // The farthest index to reach all bits from this point
            int farthest = i;
            for (int b = 0; b < 32; b++) {
                farthest = Math.max(farthest, lastSeen[b]);
            }

            result[i] = farthest - i + 1;
        }

        return result;
    }
}
