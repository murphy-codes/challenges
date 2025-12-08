// Source: https://leetcode.com/problems/count-square-sum-triples/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-07
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.01 MB Beats 46.69%

/****************************************
* 
* A square triple `(a,b,c)` is a triple where `a`, `b`, and `c` are integers and
* _ `a^2 + b^2 = c^2`.
* Given an integer `n`, return the number of square triples such that
* _ `1 <= a, b, c <= n`.
*
* Example 1:
* Input: n = 5
* Output: 2
* Explanation: The square triples are (3,4,5) and (4,3,5).
*
* Example 2:
* Input: n = 10
* Output: 4
* Explanation: The square triples are (3,4,5), (4,3,5), (6,8,10), and (8,6,10).
*
* Constraints:
* • `1 <= n <= 250`
* 
****************************************/

class Solution {
    // Generate all primitive Pythagorean triples using Euclid's formula.  
    // Count all multiples of each primitive triple within the range [1,n].  
    // Multiply by 2 to account for (a,b,c) and (b,a,c) ordering.  
    // Time complexity: O(n log n) approximately due to coprime checks in gcd.  
    // Space complexity: O(1) since only a few integer variables are used.
    public int countTriples(int limit) {
        int countTriples = 0;
        // Generate primitive Pythagorean triples using Euclid's formula
        for (int m = 3; m * m < limit * 2; m += 2) {           // m is odd
            for (int n = 1; n < m && m * m + n * n <= limit * 2; n += 2) { // n is odd
                if (gcd(m, n) == 1) {                          // m and n coprime
                    countTriples += limit * 2 / (m * m + n * n);
                }
            }
        }
        return countTriples * 2; // Count (a,b,c) and (b,a,c) separately
    }

    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }
}
