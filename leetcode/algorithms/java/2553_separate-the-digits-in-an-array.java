// Source: https://leetcode.com/problems/separate-the-digits-in-an-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-10
// At the time of submission:
//   Runtime 2 ms Beats 93.38%
//   Memory 46.08 MB Beats 98.46%

/****************************************
* 
* Given an array of positive integers `nums`, return an array `answer` that
* _ consists of the digits of each integer in `nums` after separating them in
* _ the same order they appear in `nums`.
* To separate the digits of an integer is to get all the digits it has in
* _ the same order.
* • For example, for the integer `10921`, the separation of its
* __ digits is `[1,0,9,2,1]`.
*
* Example 1:
* Input: nums = [13,25,83,77]
* Output: [1,3,2,5,8,3,7,7]
* Explanation:
* - The separation of 13 is [1,3].
* - The separation of 25 is [2,5].
* - The separation of 83 is [8,3].
* - The separation of 77 is [7,7].
* answer = [1,3,2,5,8,3,7,7]. Note that answer contains the separations in the same order.
*
* Example 2:
* Input: nums = [7,1,3,9]
* Output: [7,1,3,9]
* Explanation: The separation of each integer in nums is itself.
* answer = [7,1,3,9].
*
* Constraints:
* • `1 <= nums.length <= 1000`
* • `1 <= nums[i] <= 10^5`
* 
****************************************/

class Solution {
    // First count total digits to allocate the exact output size.
    // Then extract digits left-to-right using the highest divisor
    // for each number and append them directly into the result.
    // Time: O(d), Space: O(d), where d is total digit count
    // Since d is at most 5n, this is Time: O(n), Space: O(n)

    public int[] separateDigits(int[] nums) {
    // First pass: count digits
    int digCt = 0;
    for (int n : nums) {
        if (n == 0) {
            digCt++;
        } else {
            while (n > 0) {
                digCt++;
                n /= 10;
            }
        }
    }
    // Allocate exact size
    int[] digits = new int[digCt];
    // Second pass: fill digits left-to-right
    int idx = 0;
    for (int n : nums) {
        // Find highest divisor
        int div = 1;
        while (n / div >= 10) {
            div *= 10;
        }
        // Extract digits
        while (div > 0) {
            digits[idx++] = n / div;
            n %= div;
            div /= 10;
        }
    }
    return digits;
    }
}