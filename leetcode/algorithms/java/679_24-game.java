// Source: https://leetcode.com/problems/24-game/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-17
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.02 MB Beats 90.24%

/****************************************
* 
* You are given an integer array `cards` of length `4`. You have four cards, each
* _ containing a number in the range `[1, 9]`. You should arrange the numbers on
* _ these cards in a mathematical expression using the operators
* _ `['+', '-', '*', '/']` and the parentheses `'('` and `')'` to get the value 24.
*
* You are restricted with the following rules:
* • The division operator `'/'` represents real division, not integer division.
* — • For example, `4 / (1 - 2 / 3) = 4 / (1 / 3) = 12`.
* • Every operation done is between two numbers. In particular, we cannot use
* _ `'-'` as a unary operator.
* — • For example, if `cards = [1, 1, 1, 1]`, the expression `"-1 - 1 - 1 - 1"`
* __ is not allowed.
* • You cannot concatenate numbers together
* — • For example, if `cards = [1, 2, 1, 2]`, the expression `"12 + 12"` is
* _ not valid.
* Return `true` if you can get such expression that evaluates to `24`,
* _ and `false` otherwise.
*
* Example 1:
* Input: cards = [4,1,8,7]
* Output: true
* Explanation: (8-4) * (7-1) = 24
*
* Example 2:
* Input: cards = [1,2,1,2]
* Output: false
*
* Constraints:
* • cards.length == 4
* • 1 <= cards[i] <= 9
* 
****************************************/

class Solution {
    // This solution uses recursive backtracking to try all ways of combining 
    // two numbers with +, -, *, and / until only one number remains. If that 
    // number is close enough to 24 (within EPS), we return true. The algorithm 
    // checks O(n^2 * 6^(n-1)) possibilities in the worst case, where n = 4 here, 
    // so the search space is small and feasible. Space complexity is O(n) due 
    // to recursion depth and in-place array reuse (no extra structures created).
    private static final double EPS = 1e-6; // tolerance for floating-point comparison
    
    // Recursive backtracking: try all ways of reducing n numbers to 1 number
    private boolean backtrack(double[] nums, int n) {
        // Base case: only one number left, check if it's close to 24
        if (n == 1) return Math.abs(nums[0] - 24) < EPS;

        // Try every pair (i, j)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double num1 = nums[i], num2 = nums[j];

                // Replace nums[j] with the last element (shrink array effectively)
                nums[j] = nums[n - 1];

                // Try all possible results of combining num1 and num2
                nums[i] = num1 + num2;
                if (backtrack(nums, n - 1)) return true;

                nums[i] = num1 - num2;
                if (backtrack(nums, n - 1)) return true;

                nums[i] = num2 - num1;
                if (backtrack(nums, n - 1)) return true;

                nums[i] = num1 * num2;
                if (backtrack(nums, n - 1)) return true;

                if (Math.abs(num2) > EPS) { // avoid divide by zero
                    nums[i] = num1 / num2;
                    if (backtrack(nums, n - 1)) return true;
                }

                if (Math.abs(num1) > EPS) { // avoid divide by zero
                    nums[i] = num2 / num1;
                    if (backtrack(nums, n - 1)) return true;
                }

                // Restore original values for next loop iteration
                nums[i] = num1;
                nums[j] = num2;
            }
        }
        return false;
    }

    public boolean judgePoint24(int[] cards) {
        double[] nums = new double[cards.length];
        for (int i = 0; i < cards.length; i++) nums[i] = cards[i];
        return backtrack(nums, nums.length);
    }
}
