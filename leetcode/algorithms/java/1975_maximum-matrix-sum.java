// Source: https://leetcode.com/problems/maximum-matrix-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-04
// At the time of submission:
//   Runtime 4 ms Beats 95.06%
//   Memory 68.21 MB Beats 14.20%

/****************************************
* 
* You are given an `n x n` integer `matrix`. You can do the following operation
* _ any number of times:
* • Choose any two adjacent elements of `matrix` and multiply each of them by `-1`.
* Two elements are considered adjacent if and only if they share a border.
* Your goal is to maximize the summation of the matrix's elements. Return the
* _ maximum sum of the matrix's elements using the operation mentioned above.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/07/16/pc79-q2ex1.png]
* Input: matrix = [[1,-1],[-1,1]]
* Output: 4
* Explanation: We can follow the following steps to reach sum equals 4:
* - Multiply the 2 elements in the first row by -1.
* - Multiply the 2 elements in the first column by -1.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/07/16/pc79-q2ex2.png]
* Input: matrix = [[1,2,3],[-1,-2,-3],[1,2,3]]
* Output: 16
* Explanation: We can follow the following step to reach sum equals 16:
* - Multiply the 2 last elements in the second row by -1.
*
* Constraints:
* • `n == matrix.length == matrix[i].length`
* • `2 <= n <= 250`
* • `-10^5 <= matrix[i][j] <= 10^5`
* 
****************************************/

class Solution {
    // Each operation flips two adjacent cells, preserving negative parity.
    // We maximize the sum by taking absolute values of all elements.
    // If the number of negatives is odd, one smallest value must remain negative.
    // In that case, subtract twice the smallest absolute value.
    // Time: O(n^2), Space: O(1).
    public long maxMatrixSum(int[][] matrix) {
        int minAbsValue = 100001;   // Smallest absolute value in matrix
        long totalSum = 0L;         // Sum of absolute values
        int negativeCount = 0;      // Number of negative elements

        for (int[] row : matrix) {
            for (int value : row) {

                // Convert to absolute value and count negatives
                if (value < 0) {
                    negativeCount++;
                    value = -value;
                }

                totalSum += value;
                minAbsValue = Math.min(minAbsValue, value);
            }
        }

        // If negatives are odd, one smallest absolute value must stay negative
        return (negativeCount % 2 == 0)
                ? totalSum
                : totalSum - 2L * minAbsValue;
    }
}
