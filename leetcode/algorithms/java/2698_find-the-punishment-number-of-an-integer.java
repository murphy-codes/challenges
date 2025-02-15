// Source: https://leetcode.com/problems/find-the-punishment-number-of-an-integer/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-14

/****************************************
* 
* Given a positive integer `n`, return the punishment number of `n`.
* The punishment number of `n` is defined as the sum of the squares of all integers `I` such that:
* • `1 <= i <= n`
* • The decimal representation of `i * i` can be partitioned into contiguous substrings such that the sum of the integer values of these substrings equals `I`.
* 
* Example 1:
* Input: n = 10
* Output: 182
* Explanation: There are exactly 3 integers i in the range [1, 10] that satisfy the conditions in the statement:
* - 1 since 1 * 1 = 1
* - 9 since 9 * 9 = 81 and 81 can be partitioned into 8 and 1 with a sum equal to 8 + 1 == 9.
* - 10 since 10 * 10 = 100 and 100 can be partitioned into 10 and 0 with a sum equal to 10 + 0 == 10.
* Hence, the punishment number of 10 is 1 + 81 + 100 = 182
* 
* Example 2:
* Input: n = 37
* Output: 1478
* Explanation: There are exactly 4 integers i in the range [1, 37] that satisfy the conditions in the statement:
* - 1 since 1 * 1 = 1. 
* - 9 since 9 * 9 = 81 and 81 can be partitioned into 8 + 1. 
* - 10 since 10 * 10 = 100 and 100 can be partitioned into 10 + 0. 
* - 36 since 36 * 36 = 1296 and 1296 can be partitioned into 1 + 29 + 6.
* Hence, the punishment number of 37 is 1 + 81 + 100 + 1296 = 1478
* 
* Constraints:
* • 1 <= n <= 1000
* 
****************************************/

class Solution {
    // Iterate from 1 to n, checking if i^2 can be split into parts summing to i.
    // Use recursion to try partitioning the square's digits while reducing target.
    // Prune branches where the current number exceeds the remaining target sum.
    // Time: O(n * 2^d), where d ≤ 7 (digits in i^2), making it feasible for n=1000.
    // Space: O(d) for recursion depth, effectively O(1) since d is at most 7.
    public int punishmentNumber(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            int square = i * i;
            if (canPartition(Integer.toString(square), 0, i)) {
                sum += square;
            }
        }
        return sum;
    }

    private boolean canPartition(String s, int index, int target) {
        if (index == s.length()) return target == 0;
        
        int num = 0;
        for (int j = index; j < s.length(); j++) {
            num = num * 10 + (s.charAt(j) - '0');
            if (num > target) break; // Pruning: stop if num exceeds target
            if (canPartition(s, j + 1, target - num)) return true;
        }
        return false;
    }
}
