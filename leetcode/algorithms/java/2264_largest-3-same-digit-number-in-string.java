// Source: https://leetcode.com/problems/largest-3-same-digit-number-in-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-14
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.96 MB Beats 76.79%

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
    // Check for "good integers" in descending order from "999" to "000".
    // The first found substring is the largest by value, so return it immediately.
    // Each check uses String.indexOf() → O(n), n ≤ 1000, so time is O(10n) → O(n)
    // in practical terms (10 checks max). Space complexity is O(1) as no extra data
    // structures are used.
    public String largestGoodInteger(String num) {
        if(num.indexOf("999")>-1) return "999";
        if(num.indexOf("888")>-1) return "888";
        if(num.indexOf("777")>-1) return "777";
        if(num.indexOf("666")>-1) return "666";
        if(num.indexOf("555")>-1) return "555";
        if(num.indexOf("444")>-1) return "444";
        if(num.indexOf("333")>-1) return "333";
        if(num.indexOf("222")>-1) return "222";
        if(num.indexOf("111")>-1) return "111";
        if(num.indexOf("000")>-1) return "000";
        return "";
    }
}