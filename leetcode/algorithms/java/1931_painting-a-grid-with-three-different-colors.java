// Source: https://leetcode.com/problems/painting-a-grid-with-three-different-colors/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-18
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 40.30 MB Beats 100.00%

/****************************************
* 
* You are given two integers `m` and `n`. Consider an `m x n` grid where each cell
* _ is initially white. You can paint each cell red, green, or blue. All cells
* _ must be painted.
* Return the number of ways to color the grid with no two adjacent cells having
* _ the same color. Since the answer can be very large,
* _ return it modulo `10^9 + 7`.
*
* Example 1:
* [An image showing 3 squares (each 1x1): 1 red, 1 green, & 1 blue]
* Input: m = 1, n = 1
* Output: 3
* Explanation: The three possible colorings are shown in the image above.
*
* Example 2:
* [An image showing 6 rectangles, each a 1x2 grid:
* 1 red + green, 1 red + blue, 1 blue, 1 green + red,
* 1 green + blue, 1 blue + red, & 1 blue + green]
* Input: m = 1, n = 2
* Output: 6
* Explanation: The six possible colorings are shown in the image above.
*
* Example 3:
* Input: m = 5, n = 5
* Output: 580986
*
* Constraints:
* • 1 <= m <= 5
* • 1 <= n <= 1000
* 
****************************************/

class Solution {
    // This solution uses matrix exponentiation to solve the coloring problem
    // for fixed m values up to 5 by precomputing valid transitions.
    // Each transition matrix represents how states can follow one another.
    // Time complexity: O(k³ log n), where k = # of valid states for given m.
    // Space complexity: O(k²), since we store and multiply square matrices.

    static final int MOD = (int) 1e9 + 7;

    // Precomputed transition matrices for m = 1 to 5
    static final long[][][] TRANSITION_MATRICES = {
        { { 2 } },  // m = 1
        { { 3 } },  // m = 2
        { { 3, 2 }, { 2, 2 } }, // m = 3
        {
            { 3, 2, 1, 2 },
            { 2, 2, 1, 2 },
            { 1, 1, 2, 1 },
            { 2, 2, 1, 2 },
        }, // m = 4
        {
            { 3, 2, 2, 1, 0, 1, 2, 2 },
            { 2, 2, 2, 1, 1, 1, 1, 1 },
            { 2, 2, 2, 1, 0, 1, 2, 2 },
            { 1, 1, 1, 2, 1, 1, 1, 1 },
            { 0, 1, 0, 1, 2, 1, 0, 1 },
            { 1, 1, 1, 1, 1, 2, 1, 1 },
            { 2, 1, 2, 1, 0, 1, 2, 1 },
            { 2, 1, 2, 1, 1, 1, 1, 2 },
        }, // m = 5
    };

    public int colorTheGrid(int m, int n) {
        int stateCount = m == 1 ? 1 : (int) Math.pow(2, m - 2);

        // Initialize the DP vector (column 0)
        long[][] dpVector = new long[stateCount][1];
        for (long[] row : dpVector) {
            row[0] = m == 1 ? 3 : 6;
        }

        long[][] transitionMatrix = TRANSITION_MATRICES[m - 1];

        // Multiply the transition matrix (n - 1) times using matrix exponentiation
        dpVector = multiply(
            matrixPower(transitionMatrix, n - 1, MOD),
            dpVector,
            MOD
        );

        // Sum the values in the resulting DP vector
        long totalWays = 0;
        for (long[] row : dpVector) {
            totalWays = (totalWays + row[0]) % MOD;
        }

        return (int) totalWays;
    }

    // Matrix multiplication (modular)
    private long[][] multiply(long[][] a, long[][] b, long mod) {
        int rows = a.length, cols = b[0].length, inner = b.length;
        long[][] product = new long[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < inner; k++) {
                    product[i][j] = (product[i][j] + a[i][k] * b[k][j] % mod) % mod;
                }
            }
        }
        return product;
    }

    // Matrix exponentiation by squaring
    private long[][] matrixPower(long[][] matrix, long exponent, long mod) {
        int size = matrix.length;
        long[][] result = new long[size][size];
        for (int i = 0; i < size; i++) result[i][i] = 1; // Identity matrix

        while (exponent > 0) {
            if ((exponent & 1) != 0) {
                result = multiply(result, matrix, mod);
            }
            matrix = multiply(matrix, matrix, mod);
            exponent >>= 1;
        }
        return result;
    }
}
