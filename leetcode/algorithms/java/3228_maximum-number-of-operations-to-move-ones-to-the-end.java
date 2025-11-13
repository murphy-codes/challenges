// Source: https://leetcode.com/problems/maximum-number-of-operations-to-move-ones-to-the-end/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-12
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 47.57 MB Beats 17.78%

/****************************************
* 
* You are given a binary string `s`.
* You can perform the following operation on the string any number of times:
* • Choose any index `i` from the string where `i + 1 < s.length` such that
* __ `s[i] == '1'` and `s[i + 1] == '0'`.
* • Move the character s[i] to the right until it reaches the end of the string
* __ or another '1'. For example, for s = "010010", if we choose `i = 1`, the
* __ resulting string will be `s = "000110"`.
* Return the maximum number of operations that you can perform.
*
* Example 1:
* Input: s = "1001101"
* Output: 4
* Explanation:
* We can perform the following operations:
* Choose index i = 0. The resulting string is s = "0011101".
* Choose index i = 4. The resulting string is s = "0011011".
* Choose index i = 3. The resulting string is s = "0010111".
* Choose index i = 2. The resulting string is s = "0001111".
*
* Example 2:
* Input: s = "00111"
* Output: 0
*
* Constraints:
* • `1 <= s.length <= 10^5`
* • `s[i]` is either `'0'` or `'1'`.
* 
****************************************/

class Solution {
    // Greedy reverse traversal approach.
    // Scan from right to left, counting how many zero-groups lie to the right.
    // Each '1' adds that count to the total, since it can move past all such groups.
    // This efficiently accumulates all valid operations in one pass.
    // Time complexity: O(n); Space complexity: O(1).
    public int maxOperations(String s) {
        int i = s.length() - 1; // start from the end of the string
        int totalOps = 0;       // total number of operations
        int zeroGroups = 0;     // number of zero-groups to the right
        while (i >= 0) {
            while (i >= 0 && s.charAt(i) == '1') { // Process all consecutive '1's
                totalOps += zeroGroups; // each '1' contributes for all zero-groups to its right
                i--;
                if (i < 0) return totalOps;
            }
            while (i >= 0 && s.charAt(i) == '0') { // Process all consecutive '0's
                i--;
                if (i < 0) return totalOps;
            }
            zeroGroups++; // One full zero-group was passed
        }
        return totalOps;
    }
}
