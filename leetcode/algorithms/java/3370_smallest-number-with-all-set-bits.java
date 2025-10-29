// Source: https://leetcode.com/problems/make-array-elements-equal-to-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-28
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.05 MB Beats 26.18%

/****************************************
* 
* You are given a positive number `n`.
* Return the smallest number `x` greater than or equal to `n`, such
* _ that the binary representation of `x` contains only set bits.
*
* Example 1:
* Input: n = 5
* Output: 7
* Explanation:
* The binary representation of 7 is "111".
*
* Example 2:
* Input: n = 10
* Output: 15
* Explanation:
* The binary representation of 15 is "1111".
*
* Example 3:
* Input: n = 3
* Output: 3
* Explanation:
* The binary representation of 3 is "11".
*
* Constraints:
* • `1 <= n <= 1000`
* 
****************************************/

class Solution {
    // Start from 1 ("1" in binary), then repeatedly left-shift and set the
    // lowest bit to 1 until the number is >= n. Each step doubles the value
    // and adds 1, effectively building numbers like 1, 3, 7, 15, 31, etc.
    // Time Complexity: O(log n), since each shift adds one binary digit.
    // Space Complexity: O(1), as only a few integer variables are used.
    public int smallestNumber(int n) {
        int x = 1;
        while (x < n) x = (x << 1) | 1; // left-shift and fill with 1
        return x;
    }
}
