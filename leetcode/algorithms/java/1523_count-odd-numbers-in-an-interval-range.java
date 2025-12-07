// Source: https://leetcode.com/problems/count-odd-numbers-in-an-interval-range/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-06
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.00 MB Beats 29.32%

/****************************************
* 
* Given two non-negative integers `low` and `high`. Return the count of odd
* _ numbers between `low` and `high` (inclusive).
*
* Example 1:
* Input: low = 3, high = 7
* Output: 3
* Explanation: The odd numbers between 3 and 7 are [3,5,7].
*
* Example 2:
* Input: low = 8, high = 10
* Output: 1
* Explanation: The odd numbers between 8 and 10 are [9].
*
* Constraints:
* • `0 <= low <= high <= 10^9`
* 
****************************************/

class Solution {
    // Number of odds = ceil(high - low), except +1 if both are odd
    // Runs in O(1) time w/ O(1) space
    public int countOdds(int low, int high) {
        int odds = (high - low + 1) / 2;
        if((high-low)%2==0 && low%2==1) return odds+1;
        return odds;
    }
}