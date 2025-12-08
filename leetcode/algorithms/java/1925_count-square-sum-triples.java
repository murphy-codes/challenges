// Source: https://leetcode.com/problems/count-square-sum-triples/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-07
// At the time of submission:
//   Runtime 7 ms Beats 88.94%
//   Memory 41.68 MB Beats 67.22%

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
    // Iterate over all possible hypotenuses c from 1 to n.  
    // For each c, iterate a from 1 to c-1, compute b^2 = c^2 - a^2.  
    // If b is an integer and b <= n, then (a,b,c) forms a valid square triple.  
    // This counts all valid triples including (a,b,c) and (b,a,c) separately.  
    // Time complexity: O(n^2), Space complexity: O(1) using only constant extra space.
    public int countTriples(int n) {
        int count = 0;

        for (int c = 1; c <= n; c++) {
            int c2 = c * c;
            for (int a = 1; a < c; a++) {
                int b2 = c2 - a * a;
                int b = (int) Math.sqrt(b2);
                if (b * b == b2 && b <= n) {
                    count++;
                }
            }
        }

        return count;
    }
}


