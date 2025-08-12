// Source: https://leetcode.com/problems/reordered-power-of-2/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-11
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.76 MB Beats 79.73%

/****************************************
* 
* You are given an integer n. We reorder the digits in any order
* _ (including the original order) such that the leading digit is not zero.
* Return `true` if and only if we can do this so that the resulting number
* _ is a power of two.
*
* Example 1:
* Input: n = 1
* Output: true
*
* Example 2:
* Input: n = 10
* Output: false
*
* Constraints:
* • 1 <= n <= 10^9
* 
****************************************/

import java.util.HashSet;
import java.util.Set;

class Solution {
    // This solution compares n's sorted digit signature with all powers of two
    // up to 10^9 (max constraint). Sorting digits gives a unique pattern for
    // any permutation, avoiding expensive permutation generation. If any power
    // of two's signature matches n's, return true. Time complexity: O(logM * DlogD)
    // where M=10^9 and D is number of digits (~10), giving ~O(1) in practice.
    // Space complexity: O(1), only using small fixed extra space.
    public boolean reorderedPowerOf2(int n) {
        String sig = signature(n);
        for (int i = 1; i <= 1_000_000_000; i <<= 1) {
            if (sig.equals(signature(i))) return true;
        }
        return false;
    }

    // Creates a sorted string of the digits of num
    private String signature(int num) {
        char[] digits = String.valueOf(num).toCharArray();
        java.util.Arrays.sort(digits);
        return new String(digits);
    }
}
