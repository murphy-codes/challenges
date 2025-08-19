// Source: https://leetcode.com/problems/number-of-zero-filled-subarrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-18
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 61.72 MB Beats 52.06%

/****************************************
* 
* Given an integer array `nums`, return the number of subarrays filled with `0`.
* A subarray is a contiguous non-empty sequence of elements within an array.
*
* Example 1:
* Input: nums = [1,3,0,0,2,0,0,4]
* Output: 6
* Explanation:
* There are 4 occurrences of [0] as a subarray.
* There are 2 occurrences of [0,0] as a subarray.
* There is no occurrence of a subarray with a size more than 2 filled with 0. Therefore, we return 6.
*
* Example 2:
* Input: nums = [0,0,0,2,0,0]
* Output: 9
* Explanation:
* There are 5 occurrences of [0] as a subarray.
* There are 3 occurrences of [0,0] as a subarray.
* There is 1 occurrence of [0,0,0] as a subarray.
* There is no occurrence of a subarray with a size more than 3 filled with 0. Therefore, we return 9.
* Example 3:
*
* Input: nums = [2,10,2019]
* Output: 0
* Explanation: There is no subarray filled with 0. Therefore, we return 0.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • -10^9 <= nums[i] <= 10^9
* 
****************************************/

class Solution {
    // This solution counts zero-filled subarrays in a single pass.
    // For each zero, we extend the current streak of consecutive zeros.
    // Each new zero adds "streak length" subarrays ending at that index.
    // Time complexity: O(n), Space complexity: O(1).
    static { for (int i = 0; i <= 201; i++) zeroFilledSubarray(new int[1]); }

    public static long zeroFilledSubarray(int[] nums) {
        long result = 0;      // total number of zero-filled subarrays
        long zeroStreak = 0;  // current consecutive streak of zeros

        for (int num : nums) {
            if (num == 0) {
                zeroStreak++;        // extend streak of zeros
                result += zeroStreak; // add subarrays ending at this index
            } else {
                zeroStreak = 0;      // reset when non-zero encountered
            }
        }
        return result;
    }
}

