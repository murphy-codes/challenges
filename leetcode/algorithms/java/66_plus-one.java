// Source: https://leetcode.com/problems/plus-one/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-31
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.50 MB Beats 37.47%

/****************************************
* 
* You are given a large integer represented as an integer array `digits`, where
* _ each `digits[i]` is the `i^th` digit of the integer. The digits are ordered
* _ from most significant to least significant in left-to-right order. The large
* _ integer does not contain any leading `0`'s.
* Increment the large integer by one and return the resulting array of digits.
*
* Example 1:
* Input: digits = [1,2,3]
* Output: [1,2,4]
* Explanation: The array represents the integer 123.
* Incrementing by one gives 123 + 1 = 124.
* Thus, the result should be [1,2,4].
*
* Example 2:
* Input: digits = [4,3,2,1]
* Output: [4,3,2,2]
* Explanation: The array represents the integer 4321.
* Incrementing by one gives 4321 + 1 = 4322.
* Thus, the result should be [4,3,2,2].
*
* Example 3:
* Input: digits = [9]
* Output: [1,0]
* Explanation: The array represents the integer 9.
* Incrementing by one gives 9 + 1 = 10.
* Thus, the result should be [1,0].
*
* Constraints:
* • `1 <= digits.length <= 100`
* • `0 <= digits[i] <= 9`
* • `digits does not contain any leading `0`'s.
* 
****************************************/

class Solution {
    // Add one starting from the least significant digit, carrying as needed.
    // Stop early if a digit increments without overflow. If all digits were 9,
    // allocate a new array with leading 1 and zeros following.
    // Time: O(n) in worst case when carrying through whole array.
    // Space: O(1) except O(n) in the rare case of allocating a new result.
    public int[] plusOne(int[] digits) {
        
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;      // increment and exit if no carry
                return digits;
            }
            digits[i] = 0;        // set to zero and keep carrying
        }
        
        // all digits were 9: need a new array with leading 1
        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }
}
