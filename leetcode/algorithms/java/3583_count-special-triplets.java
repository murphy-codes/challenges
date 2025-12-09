// Source: https://leetcode.com/problems/count-special-triplets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-08
// At the time of submission:
//   Runtime 31 ms Beats 96.90%
//   Memory 120.50 MB Beats 58.14%

/****************************************
* 
* You are given an integer array `nums`.
* A special triplet is defined as a triplet of indices `(i, j, k)` such that:
* • `0 <= i < j < k < n`, where `n = nums.length`
* • `nums[i] == nums[j] * 2`
* • `nums[k] == nums[j] * 2`
* Return the total number of special triplets in the array.
* Since the answer may be large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: nums = [6,3,6]
* Output: 1
* Explanation:
* The only special triplet is (i, j, k) = (0, 1, 2), where:
* nums[0] = 6, nums[1] = 3, nums[2] = 6
* nums[0] = nums[1] * 2 = 3 * 2 = 6
* nums[2] = nums[1] * 2 = 3 * 2 = 6
*
* Example 2:
* Input: nums = [0,1,0,0]
* Output: 1
* Explanation:
* The only special triplet is (i, j, k) = (0, 2, 3), where:
* nums[0] = 0, nums[2] = 0, nums[3] = 0
* nums[0] = nums[2] * 2 = 0 * 2 = 0
* nums[3] = nums[2] * 2 = 0 * 2 = 0
*
* Example 3:
* Input: nums = [8,4,2,8,4]
* Output: 2
* Explanation:
* There are exactly two special triplets:
* (i, j, k) = (0, 1, 3)
* nums[0] = 8, nums[1] = 4, nums[3] = 8
* nums[0] = nums[1] * 2 = 4 * 2 = 8
* nums[3] = nums[1] * 2 = 4 * 2 = 8
* (i, j, k) = (1, 2, 4)
* nums[1] = 4, nums[2] = 2, nums[4] = 4
* nums[1] = nums[2] * 2 = 2 * 2 = 4
* nums[4] = nums[2] * 2 = 2 * 2 = 4
*
* Constraints:
* • `3 <= n == nums.length <= 10^5`
* • `0 <= nums[i] <= 10^5`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // For each index j, count how many i<j and k>j satisfy nums[i]=nums[k]=2*nums[j].
    // Maintain freqPrev for values before j and freqNext for values after j.
    // Contribution at j is freqPrev[target] * freqNext[target], summed over j.
    // Frequency arrays allow O(1) lookup, giving overall O(n) time.
    // Space is O(maxValue) = 100000 for the frequency arrays.
    public int specialTriplets(int[] nums) {
        final int MOD = 1_000_000_007;
        int max = 100000;

        int[] freqPrev = new int[max + 1];
        int[] freqNext = new int[max + 1];

        // Count all frequencies initially (for freqNext)
        for (int x : nums) {
            freqNext[x]++;
        }

        long ans = 0;

        // Iterate j from left to right
        for (int j = 0; j < nums.length; j++) {
            int valJ = nums[j];
            freqNext[valJ]--;       // j is now the "middle" element

            int target = valJ * 2;
            if (target <= max) {
                ans += (long) freqPrev[target] * freqNext[target];
                ans %= MOD;
            }

            freqPrev[valJ]++;       // nums[j] now counts as previous
        }

        return (int) ans;
    }
}
