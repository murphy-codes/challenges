// Source: https://leetcode.com/find-numbers-with-even-number-of-digits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-29
// At the time of submission:
//   Runtime 1 ms Beats 98.76%
//   Memory 43.38 MB Beats 26.57%

/****************************************
* 
* Given an array `nums` of integers, return how many of them contain an even number of digits.
*
* Example 1:
* Input: nums = [12,345,2,6,7896]
* Output: 2
* Explanation:
* 12 contains 2 digits (even number of digits).
* 345 contains 3 digits (odd number of digits).
* 2 contains 1 digit (odd number of digits).
* 6 contains 1 digit (odd number of digits).
* 7896 contains 4 digits (even number of digits).
* Therefore only 12 and 7896 contain an even number of digits.
*
* Example 2:
* Input: nums = [555,901,482,1771]
* Output: 1
* Explanation:
* Only 1771 contains an even number of digits.
*
* Constraints:
* • 1 <= nums.length <= 500
* • 1 <= nums[i] <= 10^5
* 
****************************************/

class Solution {
    // Iterate over each number in the array and count its digits by
    // dividing by 10 repeatedly. If the digit count is even, increment
    // the result counter. This avoids using string conversion and keeps
    // memory usage minimal. Time complexity is O(n * d), where d is the
    // number of digits (max 6); space complexity is O(1).
    public int findNumbers(int[] nums) {
        int count = 0;
        for (int num : nums) {
            int digits = 0;
            while (num > 0) {
                num /= 10;
                digits++;
            }
            if (digits % 2 == 0) {
                count++;
            }
        }
        return count;
    }
}
