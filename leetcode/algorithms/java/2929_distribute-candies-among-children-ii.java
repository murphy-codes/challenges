// Source: https://leetcode.com/problems/distribute-candies-among-children-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-01
// At the time of submission:
//   Runtime 1 ms Beats 90.00%
//   Memory 41.14 MB Beats 32.86%

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
    // Use inclusion-exclusion to count valid (x, y, z) where  
    // x + y + z == n and all ≤ limit. Base is C(n+2,2), the  
    // count of non-negative integer solutions. Subtract cases  
    // where one or more variables exceed limit.  
    // Time: O(1), Space: O(1), using closed-form combinations.
    public long distributeCandies(int n, int limit) {
        long total = comb(n + 2, 2);
        total -= 3 * comb(n - limit - 1 + 2, 2);
        total += 3 * comb(n - 2 * (limit + 1) + 2, 2);
        total -= comb(n - 3 * (limit + 1) + 2, 2);
        return total;
    }

    private long comb(int k, int r) {
        if (r > k || k < 0) return 0;
        if (r == 2) return (long) k * (k - 1) / 2;
        return 0;
    }
}
