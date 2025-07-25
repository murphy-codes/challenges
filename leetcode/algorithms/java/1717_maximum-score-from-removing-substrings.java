// Source: https://leetcode.com/problems/maximum-score-from-removing-substrings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-22
// At the time of submission:
//   Runtime 19 ms Beats 99.32%
//   Memory 45.67 MB Beats 65.75%

/****************************************
* 
* You are given a string `s` and two integers `x` and `y`. You can perform two
* _ types of operations any number of times.
* • Remove substring `"ab"` and gain `x` points.
* _ • For example, when removing `"ab"` from `"cabxbae"` it becomes `"cxbae"`.
* • Remove substring `"ba"` and gain `y` points.
* _ • For example, when removing `"ba"` from `"cabxbae"` it becomes `"cabxe"`.
* Return the maximum points you can gain after applying the above operations on `s`.
*
* Example 1:
* Input: s = "cdbcbbaaabab", x = 4, y = 5
* Output: 19
* Explanation:
* - Remove the "ba" underlined in "cdbcbbaaabab". Now, s = "cdbcbbaaab" and 5 points are added to the score.
* - Remove the "ab" underlined in "cdbcbbaaab". Now, s = "cdbcbbaa" and 4 points are added to the score.
* - Remove the "ba" underlined in "cdbcbbaa". Now, s = "cdbcba" and 5 points are added to the score.
* - Remove the "ba" underlined in "cdbcba". Now, s = "cdbc" and 5 points are added to the score.
* Total score = 5 + 4 + 5 + 5 = 19.
*
* Example 2:
* Input: s = "aabbaaxybbaabb", x = 5, y = 4
* Output: 20
*
* Constraints:
* • 1 <= s.length <= 10^5
* • 1 <= x, y <= 10^4
* • `s` consists of lowercase English letters.
* 
****************************************/

class Solution {
    // Greedy linear scan to remove high-value substrings first.
    // Always remove "ab" before "ba" by adjusting input and points.
    // Uses counters to track potential matches, no extra space.
    // Time Complexity: O(n), Space Complexity: O(1).
    // Very efficient for large strings due to minimal memory use.
    public int maximumGain(String s, int x, int y) {
        // Always treat "ab" as the higher value pair
        if (x < y) {
            int temp = x; x = y; y = temp;
            s = new StringBuilder(s).reverse().toString();
        }

        int total = 0, aCount = 0, bCount = 0;

        for (char c : s.toCharArray()) {
            if (c == 'a') {
                aCount++;
            } else if (c == 'b') {
                if (aCount > 0) {
                    aCount--;
                    total += x;
                } else {
                    bCount++;
                }
            } else {
                // When hitting a non-a/b character, resolve "ba" combinations
                total += Math.min(aCount, bCount) * y;
                aCount = bCount = 0;
            }
        }

        // Handle remaining "ba" combinations at the end
        total += Math.min(aCount, bCount) * y;

        return total;
    }
}
