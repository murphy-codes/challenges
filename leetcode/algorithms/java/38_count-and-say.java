// Source: https://leetcode.com/count-and-say/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-17
// At the time of submission:
//   Runtime 8 ms Beats 38.96%
//   Memory 41.23 MB Beats 79.02%

/****************************************
* 
* The count-and-say sequence is a sequence of digit strings defined by the recursive formula:
* • `countAndSay(1) = "1"`
* • `countAndSay(n)` is the run-length encoding of `countAndSay(n - 1)`.
* Run-length encoding (RLE) is a string compression method that works by replacing
* _ consecutive identical characters (repeated 2 or more times) with the concatenation
* _ of the character and the number marking the count of the characters (length of the run).
* _ For example, to compress the string `"3322251"` we replace `"33"` with `"23"`, replace
* _ `"222"` with `"32"`, replace `"5"` with `"15"` and replace `"1"` with `"11"`.
* _ Thus the compressed string becomes `"23321511"`.
* Given a positive integer `n`, return the `nth` element of the count-and-say sequence.
*
* Example 1:
* Input: n = 4
* Output: "1211"
* Explanation:
* countAndSay(1) = "1"
* countAndSay(2) = RLE of "1" = "11"
* countAndSay(3) = RLE of "11" = "21"
* countAndSay(4) = RLE of "21" = "1211"
*
* Example 2:
* Input: n = 1
* Output: "1"
* Explanation:
* This is the base case.
*
* Constraints:
* • 1 <= n <= 30
*
* Follow up: Could you solve it iteratively?
* 
****************************************/

class Solution {
    // Builds the count-and-say sequence iteratively up to n.
    // Each step encodes the previous string using run-length encoding.
    // Time: O(n * m), where m is length of current term (grows with n).
    // Space: O(m), since we construct one new term at a time.
    // Efficient for n ≤ 30 — no need for memoization or optimization.
    public String countAndSay(int n) {
        String result = "1";
        
        for (int i = 2; i <= n; i++) {
            StringBuilder next = new StringBuilder();
            int count = 1;
            for (int j = 1; j < result.length(); j++) {
                if (result.charAt(j) == result.charAt(j - 1)) {
                    count++;
                } else {
                    next.append(count).append(result.charAt(j - 1));
                    count = 1;
                }
            }
            next.append(count).append(result.charAt(result.length() - 1));
            result = next.toString();
        }
        
        return result;
    }
}
