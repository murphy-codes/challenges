// Source: https://leetcode.com/problems/binary-prefix-divisible-by-5/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-24
// At the time of submission:
//   Runtime 3 ms Beats 99.20%
//   Memory 47.24 MB Beats 40.96%

/****************************************
* 
* You are given a binary array `nums` (0-indexed).
*
* We define `x_i` as the number whose binary representation is the subarray
* _ `nums[0..i]` (from most-significant-bit to least-significant-bit).
* • For example, if `nums = [1,0,1]`, then `x_0 = 1`, `x_1 = 2`, and `x_2 = 5`.
* Return an array of booleans `answer` where `answer[i]` is `true` if `x_i`
* _ is divisible by `5`.
*
* Example 1:
* Input: nums = [0,1,1]
* Output: [true,false,false]
* Explanation: The input numbers in binary are 0, 01, 011; which are 0, 1, and 3 in base-10.
* Only the first number is divisible by 5, so answer[0] is true.
*
* Example 2:
* Input: nums = [1,1,1]
* Output: [false,false,false]
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `nums[i]` is either `0` or `1`.
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // We iterate through the binary digits and maintain the prefix number
    // modulo 5, since (X*2 + bit) % 5 gives the next prefix value efficiently.
    // If the current mod equals zero, the prefix is divisible by five. This
    // avoids large number construction and runs in O(n) time with O(1) space.
    public List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> result = new ArrayList<>(nums.length);

        int mod = 0; // tracks current prefix value modulo 5

        for (int bit : nums) {
            mod = (mod * 2 + bit) % 5;   // update prefix mod value
            result.add(mod == 0);        // divisible by 5 if mod == 0
        }

        return result;
    }
}
