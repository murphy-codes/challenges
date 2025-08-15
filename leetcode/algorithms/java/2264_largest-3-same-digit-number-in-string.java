// Source: https://leetcode.com/problems/largest-3-same-digit-number-in-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-14
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.72 MB Beats 92.17%

/****************************************
* 
* You are given a string `num` representing a large integer. An integer is good
* _ if it meets the following conditions:
* • It is a substring of `num` with length `3`.
* • It consists of only one unique digit.
* Return the maximum good integer as a string or an empty string `""` if
* _ no such integer exists.
* Note:
* • A substring is a contiguous sequence of characters within a string.
* • There may be leading zeroes in `num` or a good integer.
*
* Example 1:
* Input: num = "6777133339"
* Output: "777"
* Explanation: There are two distinct good integers: "777" and "333".
* 777 is the largest, so we return "777".
*
* Example 2:
* Input: num = "2300019"
* Output: "000"
* Explanation: "000" is the only good integer.
*
* Example 3:
* Input: num = "42352338"
* Output: ""
* Explanation: No substring of length 3 consists of only one unique digit. Therefore, there are no good integers.
*
* Constraints:
* • 3 <= num.length <= 1000
* • num only consists of digits. 
* 
****************************************/

class Solution {
    // Checks for triplet digits in num from largest to smallest.
    // Patterns are pre-sorted so the first match is the largest good integer.
    // Uses String.contains() for clarity and early return for efficiency.
    // Time Complexity: O(10 * n) → O(n), where n = length of num.
    // Space Complexity: O(1) extra space, ignoring constant pattern array.
    public String largestGoodInteger(String num) {
        // Possible "good integers", ordered from largest to smallest
        String[] patterns = { "999","888","777","666","555", "444","333","222","111","000" };
        // Check each pattern in order; return the first one found
        for (String pattern : patterns) {
            if (num.contains(pattern)) {
                return pattern; // First match is the largest possible
            }
        }
        // If no pattern found, return empty string
        return "";
    }
}
