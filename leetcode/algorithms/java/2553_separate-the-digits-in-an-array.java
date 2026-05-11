// Source: https://leetcode.com/problems/separate-the-digits-in-an-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-10
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 46.80 MB Beats 36.46%

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
    // Extract digits using %10 and fill the result array backwards
    // so digits remain in original left-to-right order.
    // Time: O(d), Space: O(d), where d is total digit count
    // Since d is at most 5n, this is Time: O(n), Space: O(n)
    public int[] separateDigits(int[] nums) {

        int totalDigits = 0;

        // Count total digits needed for result array.
        for (int num : nums) {

            int value = num;

            if (value == 0) {
                totalDigits++;
            } else {
                while (value > 0) {
                    totalDigits++;
                    value /= 10;
                }
            }
        }

        int[] result = new int[totalDigits];

        // Fill from the end since %10 extracts digits in reverse order.
        int writeIndex = totalDigits - 1;

        for (int i = nums.length - 1; i >= 0; i--) {

            int value = nums[i];

            if (value == 0) {
                result[writeIndex--] = 0;
            } else {
                while (value > 0) {
                    result[writeIndex--] = value % 10;
                    value /= 10;
                }
            }
        }

        return result;
    }
}