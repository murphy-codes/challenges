// Source: https://leetcode.com/problems/binary-gap/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-21
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.24 MB Beats 67.49%

/****************************************
* 
* Given a positive integer `n`, find and return the longest distance between
* _ any two adjacent `1`'s in the binary representation of `n`. If there are
* _ no two adjacent `1`'s, return `0`.
* Two `1`'s are adjacent if there are only `0`'s separating them (possibly
* _ no `0`'s). The distance between two `1`'s is the absolute difference
* _ between their bit positions. For example, the two `1`'s in `"1001"`
* _ have a distance of `3`.
*
* Example 1:
* Input: n = 22
* Output: 2
* Explanation: 22 in binary is "10110".
* The first adjacent pair of 1's is "10110" with a distance of 2.
* The second adjacent pair of 1's is "10110" with a distance of 1.
* The answer is the largest of these two distances, which is 2.
* Note that "10110" is not a valid pair since there is a 1 separating the two 1's underlined.
*
* Example 2:
* Input: n = 8
* Output: 0
* Explanation: 8 in binary is "1000".
* There are not any adjacent pairs of 1's in the binary representation of 8, so we return 0.
*
* Example 3:
* Input: n = 5
* Output: 2
* Explanation: 5 in binary is "101".
*
* Constraints:
* • `1 <= n <= 10^9`
* 
****************************************/

class Solution {
    // We scan the bits of n from right to left using bitwise operations.
    // Each time we see a '1', we compute the distance from the previous '1'.
    // The maximum distance between consecutive '1' bits is tracked and returned.
    // Time complexity is O(log n), since we process each bit once.
    // Space complexity is O(1), using only a few integer variables.
    public int binaryGap(int n) {
        int lastOne = -1;
        int maxGap = 0;
        int position = 0;
        while (n > 0) {
            if ((n & 1) == 1) {
                if (lastOne != -1) {
                    maxGap = Math.max(maxGap, position - lastOne);
                }
                lastOne = position;
            }
            n >>= 1;
            position++;
        }

        return maxGap;
    }
}