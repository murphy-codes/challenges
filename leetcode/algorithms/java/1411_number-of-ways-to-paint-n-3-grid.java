// Source: https://leetcode.com/problems/number-of-ways-to-paint-n-3-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-02
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.10 MB Beats 76.05%

/****************************************
* 
* You have a `grid` of size `n x 3` and you want to paint each cell of the grid
* _ with exactly one of the three colors: Red, Yellow, or Green while making
* _ sure that no two adjacent cells have the same color (i.e., no two cells that
* _ share vertical or horizontal sides have the same color).
* Given `n` the number of rows of the grid, return the number of ways you can
* _ paint this `grid`. As the answer may grow large, the answer must be computed
* _ modulo `10^9 + 7`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/03/26/e1.png]
* Input: n = 1
* Output: 12
* Explanation: There are 12 possible way to paint the grid as shown.
*
* Example 2:
* Input: n = 5000
* Output: 30228214
*
* Constraints:
* • `n == grid.length
* • `1 <= n <= 5000
* 
****************************************/

class Solution {
    // The row transitions form a linear recurrence captured by a 2x2 matrix.
    // Matrix exponentiation computes the nth row state in O(log n) time.
    // Initial ABA and ABC counts are both 6 for the first row.
    // Final answer is the sum of both pattern counts modulo 1e9+7.
    // Time: O(log n), Space: O(1) since matrices are constant size.

    int MOD = 1_000_000_007;

    // Multiplies two matrices under modulo arithmetic
    public long[][] multiply(long[][] a, long[][] b) {
        long[][] result = new long[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    result[i][j] =
                        (result[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }
        return result;
    }

    // Computes matrix^power using binary exponentiation
    public long[][] power(long[][] matrix, int power) {
        long[][] result = {{1, 0}, {0, 1}}; // identity matrix
        long[][] base = matrix;

        while (power != 0) {
            if ((power & 1) == 1) {
                result = multiply(result, base);
            }
            base = multiply(base, base);
            power >>= 1;
        }
        return result;
    }

    public int numOfWays(int n) {
        // Transition matrix for ABA / ABC states
        long[][] transition = {{3, 2}, {2, 2}};

        // Raise transition matrix to (n - 1)
        long[][] transitionPower = power(transition, n - 1);

        // Initial state: [ABA(1), ABC(1)]
        long[][] initial = {{6}, {6}};

        // Final state = transition^(n-1) * initial
        long[][] result = multiply(transitionPower, initial);

        return (int)((result[0][0] + result[1][0]) % MOD);
    }
}
