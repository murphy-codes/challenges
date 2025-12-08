// Source: https://leetcode.com/problems/count-square-sum-triples/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-07
// At the time of submission:
//   Runtime 37 ms Beats 29.02%
//   Memory 42.03 MB Beats 49.69%

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
    // Iterate over all pairs (a,b) with 1 <= a,b <= n and check if a^2+b^2 is 
    // a perfect square. If sqrt(a^2+b^2) is an integer ≤ n, increment the count.
    // This counts both (a,b,c) and (b,a,c) separately when a != b.
    // Time complexity: O(n^2), since we check all a,b pairs.  
    // Space complexity: O(1), only constant extra space is used.
    public int countTriples(int n) {
        int countST = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                double d = Math.sqrt((double) (i*i + j*j));
                if (isWhole(d)) {
                    if (d <= n) countST++;
                }
            }
        }
        return countST;
    }

    public static boolean isWhole(double d) {
        return d % 1 == 0;
    }
}

