// Source: https://leetcode.com/problems/minimize-maximum-pair-sum-in-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-23
// At the time of submission:
//   Runtime 57 ms Beats 93.05%
//   Memory 97.02 MB Beats 40.09%

/****************************************
* 
* The pair sum of a pair `(a,b)` is equal to `a + b`. The maximum pair sum is
* _ the largest pair sum in a list of pairs.
* • For example, if we have pairs `(1,5)`, `(2,3)`, and `(4,4)`, the maximum
* __ pair sum would be `max(1+5, 2+3, 4+4) = max(6, 5, 8) = 8`.
* Given an array `nums` of even length `n`, pair up the elements of `nums`
* _ into `n / 2` pairs such that:
* • Each element of `nums` is in exactly one pair, and
* • The maximum pair sum is minimized.
* Return the minimized maximum pair sum after optimally pairing up the elements.
*
* Example 1:
* Input: nums = [3,5,2,3]
* Output: 7
* Explanation: The elements can be paired up into pairs (3,3) and (5,2).
* The maximum pair sum is max(3+3, 5+2) = max(6, 7) = 7.
*
* Example 2:
* Input: nums = [3,5,4,2,4,6]
* Output: 8
* Explanation: The elements can be paired up into pairs (3,5), (4,4), and (6,2).
* The maximum pair sum is max(3+5, 4+4, 6+2) = max(8, 8, 8) = 8.
*
* Constraints:
* • `n == nums.length`
* • `2 <= n <= 10^5`
* • `n` is even.
* • `1 <= nums[i] <= 10^5`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort nums and pair smallest with largest using a two-pointer approach.
    // This greedy strategy minimizes the maximum pair sum by balancing values.
    // Track the maximum sum encountered across all n/2 pairs.
    // Time complexity: O(n log n) from sorting.
    // Space complexity: O(1) extra space.
    public int minPairSum(int[] nums) {
        int minMax = 0;
        Arrays.sort(nums);
        int i = 0, j = nums.length-1;
        while (i < j) minMax = Math.max(minMax, nums[i++]+nums[j--]);
        return minMax;
    }
}