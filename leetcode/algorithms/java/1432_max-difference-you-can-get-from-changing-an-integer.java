// Source: https://leetcode.com/problems/max-difference-you-can-get-from-changing-an-integer/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-15
// At the time of submission:
//   Runtime 1 ms Beats 87.59%
//   Memory 40.40 MB Beats 94.16%

/****************************************
* 
* You are given an integer `num`. You will apply the following steps to `num`
* _ two separate times:
* • Pick a digit `x (0 <= x <= 9)`.
* • Pick another digit `y (0 <= y <= 9)`. Note `y` can be equal to `x`.
* • Replace all the occurrences of `x` in the decimal representation of `num` by `y`.
* Let `a` and `b` be the two results from applying the operation
* _ to `num` independently.
* Return the max difference between `a` and `b`.
* Note that neither `a` nor `b` may have any leading zeros, and must not be 0.
*
* Example 1:
* Input: num = 555
* Output: 888
* Explanation: The first time pick x = 5 and y = 9 and store the new integer in a.
* The second time pick x = 5 and y = 1 and store the new integer in b.
* We have now a = 999 and b = 111 and max difference = 888
*
* Example 2:
* Input: num = 9
* Output: 8
* Explanation: The first time pick x = 9 and y = 9 and store the new integer in a.
* The second time pick x = 9 and y = 1 and store the new integer in b.
* We have now a = 9 and b = 1 and max difference = 8
*
* Constraints:
* • 1 <= num <= 10^8
* 
****************************************/

class Solution {
    // To find max difference, we remap one digit to get the largest and smallest
    // possible integers. To maximize, replace the first non-9 digit with 9.
    // To minimize, replace the first digit with 1 if not 1, else next non-0/1 digit
    // with 0. Return the difference between the new max and min values.
    // Time: O(n), Space: O(n) where n is the number of digits in num.
    public int maxDiff(int num) {
        String numStr = String.valueOf(num);
        char[] chars = numStr.toCharArray();

        // Maximize: replace the first non-9 digit with 9
        char toReplaceMax = ' ';
        for (char c : chars) {
            if (c != '9') {
                toReplaceMax = c;
                break;
            }
        }
        String maxStr = (toReplaceMax == ' ')
            ? numStr
            : numStr.replace(toReplaceMax, '9');

        // Minimize: replace first digit ≠ 1 with 1, else next digit ≠ 0/1 with 0
        char toReplaceMin = ' ';
        char replacementMin = ' ';
        if (chars[0] != '1') {
            toReplaceMin = chars[0];
            replacementMin = '1';
        } else {
            for (int i = 1; i < chars.length; i++) {
                if (chars[i] != '0' && chars[i] != '1') {
                    toReplaceMin = chars[i];
                    replacementMin = '0';
                    break;
                }
            }
        }
        String minStr = (toReplaceMin == ' ')
            ? numStr
            : numStr.replace(toReplaceMin, replacementMin);

        return Integer.parseInt(maxStr) - Integer.parseInt(minStr);
    }
}
