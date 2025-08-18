// Source: https://leetcode.com/problems/24-game/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-17
// At the time of submission:
//   Runtime 5 ms Beats 80.47%
//   Memory 44.54 MB Beats 62.63%

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

import java.util.ArrayList;
import java.util.List;

class Solution {
    // Use backtracking to test all pairings of numbers and operations.
    // Repeatedly combine two numbers with +, -, *, or / and recurse.
    // Base case: if one number remains and is ≈ 24, return true.
    // Time complexity is bounded (~O(4! * 4^3)), feasible for 4 cards.
    // Space complexity is O(n) for recursion depth and lists.
    private static final double EPSILON = 1e-6;

    public boolean judgePoint24(int[] cards) {
        List<Double> nums = new ArrayList<>();
        for (int c : cards) nums.add((double) c);
        return dfs(nums);
    }

    private boolean dfs(List<Double> nums) {
        if (nums.size() == 1) {
            return Math.abs(nums.get(0) - 24.0) < EPSILON;
        }

        int n = nums.size();
        // Try all pairs of numbers
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                List<Double> next = new ArrayList<>();
                // Add remaining numbers except i and j
                for (int k = 0; k < n; k++) {
                    if (k != i && k != j) next.add(nums.get(k));
                }

                double a = nums.get(i), b = nums.get(j);
                // Try all possible results from a and b
                for (double val : compute(a, b)) {
                    next.add(val);
                    if (dfs(next)) return true;
                    next.remove(next.size() - 1);
                }
            }
        }
        return false;
    }

    private List<Double> compute(double a, double b) {
        List<Double> results = new ArrayList<>();
        results.add(a + b);
        results.add(a - b);
        results.add(b - a);
        results.add(a * b);
        if (Math.abs(b) > EPSILON) results.add(a / b);
        if (Math.abs(a) > EPSILON) results.add(b / a);
        return results;
    }
}
