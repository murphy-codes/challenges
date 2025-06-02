// Source: https://leetcode.com/problems/distribute-candies-among-children-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-01
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.40 MB Beats 14.29%

/****************************************
* 
* You are given two positive integers `n` and `limit`.
* Return the total number of ways to distribute `n` candies among `3` children
* _ such that no child gets more than `limit` candies.
*
* Example 1:
* Input: n = 5, limit = 2
* Output: 3
* Explanation: There are 3 ways to distribute 5 candies such that no child gets more than 2 candies: (1, 2, 2), (2, 1, 2) and (2, 2, 1).
*
* Example 2:
* Input: n = 3, limit = 3
* Output: 10
* Explanation: There are 10 ways to distribute 3 candies such that no child gets more than 3 candies: (0, 0, 3), (0, 1, 2), (0, 2, 1), (0, 3, 0), (1, 0, 2), (1, 1, 1), (1, 2, 0), (2, 0, 1), (2, 1, 0) and (3, 0, 0).
*
* Constraints:
* • 1 <= n <= 10^6
* • 1 <= limit <= 10^6
* 
****************************************/

class Solution {
    // Uses inclusion-exclusion to count valid distributions of candies where  
    // each child gets at most 'limit' candies. The formula counts all ways  
    // to sum to 'n' with 3 non-negative integers, then subtracts cases where  
    // one or more exceed 'limit'. Time complexity is O(1), space is O(1).  
    public long distributeCandies(int n, int limit) {
        return countUnboundedWays(n)
             - 3 * countUnboundedWays(n - (limit + 1))
             + 3 * countUnboundedWays(n - 2 * (limit + 1))
             -     countUnboundedWays(n - 3 * (limit + 1));
    }

    // Returns number of non-negative integer solutions to x + y + z == sum
    private long countUnboundedWays(long sum) {
        if (sum < 0) return 0;
        return (sum + 2) * (sum + 1) / 2; // C(sum + 2, 2)
    }
}
