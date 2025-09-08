// Source: https://leetcode.com/problems/convert-integer-to-the-sum-of-two-no-zero-integers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-07
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.54 MB Beats 25.84%

/****************************************
* 
* No-Zero integer is a positive integer that does not contain any 0 in its
* _ decimal representation.
* Given an integer `n`, return a list of two integers `[a, b]` where:
* • `a` and `b` are No-Zero integers.
* • `a + b = n`
* The test cases are generated so that there is at least one valid solution.
* _ If there are many valid solutions, you can return any of them.
*
* Example 1:
* Input: n = 2
* Output: [1,1]
* Explanation: Let a = 1 and b = 1.
* Both a and b are no-zero integers, and a + b = 2 = n.
*
* Example 2:
* Input: n = 11
* Output: [2,9]
* Explanation: Let a = 2 and b = 9.
* Both a and b are no-zero integers, and a + b = 11 = n.
* Note that there are other valid answers as [8, 3] that can be accepted.
*
* Constraints:
* • 2 <= n <= 10^4
* 
****************************************/

class Solution {
    // Starts with two integers a and b summing to n, initially split evenly.
    // Iteratively adjusts a down and b up until neither contains a digit 0.
    // Helper method hasZero checks each digit of an integer for 0 in O(log n).
    // Time complexity: O(n log n) in worst case due to digit checks during iteration.
    // Space complexity: O(1) — only a few variables are used, no extra storage.
    public int[] getNoZeroIntegers(int n) {
        int a, b;
        a = n / 2;
        b = n - a;
        while (hasZero(a) || hasZero(b)){
            a--;
            b++;
        }
        return new int[] { a, b };
    }
    
    boolean hasZero(int n) {
        while (n > 0) {
            if (n % 10 == 0) return true;
            n /= 10;
        }
        return false;
    }
}
