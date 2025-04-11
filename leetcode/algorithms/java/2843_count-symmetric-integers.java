// Source: https://leetcode.com/problems/count-symmetric-integers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-11
// At the time of submission:
//   Runtime 25 ms Beats 90.62%
//   Memory 45.03 MB Beats 13.42%

/****************************************
* 
* You are given two positive integers `low` and `high`.
* An integer `x` consisting of `2 * n` digits is symmetric if the sum of
* _ the first n digits of `x` is equal to the sum of the last n digits of `x`.
* _ Numbers with an odd number of digits are never symmetric.
* Return the number of symmetric integers in the range `[low, high]`.
*
* Example 1:
* Input: low = 1, high = 100
* Output: 9
* Explanation: There are 9 symmetric integers between 1 and 100: 11, 22, 33, 44, 55, 66, 77, 88, and 99.
*
* Example 2:
* Input: low = 1200, high = 1230
* Output: 4
* Explanation: There are 4 symmetric integers between 1200 and 1230: 1203, 1212, 1221, and 1230.
*
* Constraints:
* • 1 <= low <= high <= 10^4
* 
****************************************/

class Solution {
    // This solution iterates through numbers from `low` to `high` (O(n)).
    // Each number is converted to a string and processed in O(1) since max digits = 5.
    // The total complexity is O(n), which is optimal given the constraints.
    // Space complexity is O(1) since we use only a few integer variables.
    public int countSymmetricIntegers(int low, int high) {
        int count = 0;
        
        for (int num = low; num <= high; num++) {
            String s = String.valueOf(num);
            int len = s.length();
            
            // Only consider numbers with even digit lengths
            if (len % 2 == 0) {
                int mid = len / 2;
                int sumLeft = 0, sumRight = 0;
                
                // Compute sum of the first and last halves
                for (int i = 0; i < mid; i++) {
                    sumLeft += s.charAt(i) - '0';
                    sumRight += s.charAt(i + mid) - '0';
                }
                
                if (sumLeft == sumRight) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
