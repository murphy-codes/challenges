// Source: https://leetcode.com/problems/maximum-number-of-operations-to-move-ones-to-the-end/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-12
// At the time of submission:
//   Runtime 7 ms Beats 78.52%
//   Memory 47.49 MB Beats 20.74%

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
    // Greedy + Counting approach.
    // Traverse the string left-to-right, tracking how many '1's have appeared.
    // For each group of consecutive '0's, every previous '1' contributes one
    // operation (since it can be "blocked" by these zeros). Sum these up as ans.
    // Time complexity: O(n), scanning the string once.
    // Space complexity: O(1), using only a few integer variables.
    public int maxOperations(String s) {
        int countOne = 0;  // number of '1's seen so far
        int ans = 0;       // total operations count
        int i = 0;

        while (i < s.length()) {
            if (s.charAt(i) == '0') {
                // Skip through this run of consecutive zeros
                while (i + 1 < s.length() && s.charAt(i + 1) == '0') {
                    i++;
                }
                // Each zero-run adds one operation per '1' seen before it
                ans += countOne;
            } else {
                // Count another '1' that may contribute to future operations
                countOne++;
            }
            i++;
        }

        return ans;
    }
}
