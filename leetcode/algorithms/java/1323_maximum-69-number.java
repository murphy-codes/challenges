// Source: https://leetcode.com/problems/maximum-69-number/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-15
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.62 MB Beats 47.88%

/****************************************
* 
* You are given a positive integer num consisting only of digits `6` and `9`.
* Return the maximum number you can get by changing at most one digit
* _ (`6` becomes `9`, and `9` becomes `6`).
*
* Example 1:
* Input: num = 9669
* Output: 9969
* Explanation:
* Changing the first digit results in 6669.
* Changing the second digit results in 9969.
* Changing the third digit results in 9699.
* Changing the fourth digit results in 9666.
* The maximum number is 9969.
*
* Example 2:
* Input: num = 9996
* Output: 9999
* Explanation: Changing the last digit 6 to 9 results in the maximum number.
*
* Example 3:
* Input: num = 9999
* Output: 9999
* Explanation: It is better not to apply any change.
*
* Constraints:
* • 1 <= num <= 10^4
* • `num` consists of only `6` and `9` digits.
* 
****************************************/

class Solution {
    // This solution converts the number into a StringBuilder for easy editing.
    // It scans from left to right and replaces the first '6' with '9', since
    // changing the leftmost digit maximizes the result. Only one change is made.
    // Time Complexity: O(d), where d is the number of digits (at most 4 here).
    // Space Complexity: O(d), due to the StringBuilder storing the digits.
    public int maximum69Number(int num) {
        // Convert the integer into a mutable sequence of characters
        StringBuilder number = new StringBuilder();
        number.append(num);
        
        // Find the first '6' and change it to '9'
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == '6') {
                number.setCharAt(i, '9');
                break; // Only one change allowed
            }
        }
        
        // Convert back to integer and return
        return Integer.parseInt(number.toString());
    }
}
