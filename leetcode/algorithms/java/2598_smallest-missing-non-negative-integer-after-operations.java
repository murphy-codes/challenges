// Source: https://leetcode.com/problems/smallest-missing-non-negative-integer-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-16
// At the time of submission:
//   Runtime 61 ms Beats 34.01%
//   Memory 63.82 MB Beats 21.83%

/****************************************
* 
* You are given a 0-indexed integer array `nums` and an integer `value`.
* In one operation, you can add or subtract `value` from any element of `nums`.
* • For example, if `nums = [1,2,3]` and `value = 2`, you can choose to subtract
* __ `value` from `nums[0]` to make `nums = [-1,2,3]`.
* The MEX (minimum excluded) of an array is the smallest missing non-negative
* _ integer in it.
* • For example, the MEX of `[-1,2,3]` is `0` while the MEX of `[1,0,3]` is `2`.
* Return the maximum MEX of `nums` after applying the mentioned operation
* _ any number of times.
*
* Example 1:
* Input: nums = [1,-10,7,13,6,8], value = 5
* Output: 4
* Explanation: One can achieve this result by applying the following operations:
* - Add value to nums[1] twice to make nums = [1,0,7,13,6,8]
* - Subtract value from nums[2] once to make nums = [1,0,2,13,6,8]
* - Subtract value from nums[3] twice to make nums = [1,0,2,3,6,8]
* The MEX of nums is 4. It can be shown that 4 is the maximum MEX we can achieve.
*
* Example 2:
* Input: nums = [1,-10,7,13,6,8], value = 7
* Output: 2
* Explanation: One can achieve this result by applying the following operation:
* - subtract value from nums[2] once to make nums = [1,-10,0,13,6,8]
* The MEX of nums is 2. It can be shown that 2 is the maximum MEX we can achieve.
*
* Constraints:
* • `1 <= nums.length, value <= 10^5`
* • `-109 <= nums[i] <= 10^9`
* 
****************************************/

import java.util.HashMap;

class Solution {
    // Counts nums by remainder mod value, since we can shift by ±value.
    // Each remainder can fill positions r, r+value, r+2*value, etc.
    // Build mex greedily using available remainders until one is missing.
    // Time O(n), Space O(value).
    public int findSmallestInteger(int[] nums, int value) {
        HashMap<Integer, Integer> freq = new HashMap<>();
        
        // Count frequencies of remainders
        for (int num : nums) {
            int r = ((num % value) + value) % value;  // safe mod for negatives
            freq.put(r, freq.getOrDefault(r, 0) + 1);
        }

        // Build MEX
        int mex = 0;
        while (true) {
            int r = mex % value;
            if (freq.getOrDefault(r, 0) > 0) {
                freq.put(r, freq.get(r) - 1);
                mex++;
            } else {
                break;
            }
        }

        return mex;
    }
}
