// Source: https://leetcode.com/problems/count-special-triplets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-08
// At the time of submission:
//   Runtime 17 ms Beats 100.00%
//   Memory 120.52 MB Beats 58.14%

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

class Solution {
    // For each value n as the middle index j, we count how many i<j and k>j
    // satisfy nums[i] = nums[k] = 2*n.  We track counts before j in freqPrev
    // and counts after j in freqNext.  Each j contributes freqPrev[t] *
    // freqNext[t], where t = 2*n.  Using static freq arrays avoids allocation
    // and yields O(n) time and O(1) extra space beyond fixed-size arrays.
    
    // Prefix frequency (elements before j)
    static int[] freqPrev = new int[100001];

    // Suffix frequency (elements after j)
    static int[] freqNext = new int[100001];

    final int MOD = 1_000_000_007;

    public int specialTriplets(int[] nums) {

        // Initialize suffix counts with all nums
        for (int x : nums) {
            freqNext[x]++;
        }

        int result = 0;

        for (int x : nums) {
            // x is now serving as the middle element j
            freqNext[x]--;

            // target = 2 * x
            int target = x << 1;

            // If target is within bounds, accumulate triplets
            if (target < freqPrev.length) {
                long ways = 1L * freqPrev[target] * freqNext[target];
                result = (result + (int)(ways % MOD)) % MOD;
            }

            // Now x becomes part of the prefix for future j's
            freqPrev[x]++;
        }

        // Reset freqPrev entries used in this testcase
        for (int x : nums) {
            freqPrev[x] = 0;
        }

        return result;
    }
}
