// Source: https://leetcode.com/problems/rotated-digits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-01
// At the time of submission:
//   Runtime 3 ms Beats 91.63%
//   Memory 41.76 MB Beats 96.61%

/****************************************
* 
* An integer `x` is a good if after rotating each digit individually by 180
* _ degrees, we get a valid number that is different from `x`. Each digit must
* _ be rotated - we cannot choose to leave it alone.
* A number is valid if each digit remains a digit after rotation. For example:
* • `0`, `1`, and `8` rotate to themselves,
* • `2` and `5` rotate to each other (in this case they are rotated in a
* __ different direction, in other words, `2` or `5` gets mirrored),
* • `6` and `9` rotate to each other, and
* • the rest of the numbers do not rotate to any other number and become invalid.
* Given an integer `n`, return the number of good integers in the range `[1, n]`.
*
* Example 1:
* Input: n = 10
* Output: 4
* Explanation: There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
* Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
*
* Example 2:
* Input: n = 1
* Output: 0
*
* Example 3:
* Input: n = 2
* Output: 1
*
* Constraints:
* • `1 <= n <= 10^4`
* 
****************************************/

class Solution {
    // Iterate 1..n and validate each number digit-by-digit.
    // Reject if any digit is invalid (3,4,7); track if any digit changes.
    // A number is good if valid and has at least one changing digit.
    // Time: O(n * log n), Space: O(1)
    public int rotatedDigits(int n) {
        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (isGood(i)) {
                count++;
            }
        }

        return count;
    }

    private boolean isGood(int num) {
        boolean changed = false;

        while (num > 0) {
            int digit = num % 10;

            // invalid digits
            if (digit == 3 || digit == 4 || digit == 7) {
                return false;
            }

            // digits that change after rotation
            if (digit == 2 || digit == 5 || digit == 6 || digit == 9) {
                changed = true;
            }

            num /= 10;
        }

        return changed;
    }
}