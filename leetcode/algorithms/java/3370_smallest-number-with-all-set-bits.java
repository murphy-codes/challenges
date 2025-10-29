// Source: https://leetcode.com/problems/make-array-elements-equal-to-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-28
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.53 MB Beats 87.17%

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
    // Precompute all numbers whose binary form is all 1's (1, 3, 7, 15, ...).
    // Iterate through this small fixed array and return the first number ≥ n.
    // Since the array is constant-sized (≤ 10 elements for n ≤ 1000), runtime is O(1).
    // Space complexity is also O(1) since only a small static array is used.
    private static final int[] setBits = new int[] {1, 3, 7, 15, 31, 63, 127, 255, 511, 1023};
    public int smallestNumber(int n) {
        for (int i = 0; i < setBits.length; i++) {
            if (setBits[i] == n) return n;
            if (setBits[i] > n) return setBits[i];
        }
        return 2047; // should never hit this due to constraints
    }
}