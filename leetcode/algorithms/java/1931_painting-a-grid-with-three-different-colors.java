// Source: https://leetcode.com/problems/painting-a-grid-with-three-different-colors/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-18
// At the time of submission:
//   Runtime 35 ms Beats 81.71%
//   Memory 44.82 MB Beats 30.49%

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    // Dynamic programming over columns, with vertical coloring patterns
    // Precompute all valid vertical patterns (no adjacent same colors)
    // Build a transition graph for pattern-to-pattern compatibility
    // For each column, update dp based on compatible prior patterns
    // Time: O(n * P^2), Space: O(P); where P ≈ 243 valid patterns (m ≤ 5)

    private static final int MOD = 1_000_000_007;

    public int colorTheGrid(int m, int n) {
        List<int[]> patterns = new ArrayList<>();
        generatePatterns(m, 0, new int[m], patterns);

        Map<Integer, List<Integer>> transitions = new HashMap<>();
        for (int i = 0; i < patterns.size(); i++) {
            transitions.put(i, new ArrayList<>());
            for (int j = 0; j < patterns.size(); j++) {
                if (isCompatible(patterns.get(i), patterns.get(j))) {
                    transitions.get(i).add(j);
                }
            }
        }

        int P = patterns.size();
        int[] dp = new int[P];
        for (int i = 0; i < P; i++) dp[i] = 1;

        for (int col = 1; col < n; col++) {
            int[] newDp = new int[P];
            for (int i = 0; i < P; i++) {
                for (int j : transitions.get(i)) {
                    newDp[i] = (newDp[i] + dp[j]) % MOD;
                }
            }
            dp = newDp;
        }

        int total = 0;
        for (int val : dp) {
            total = (total + val) % MOD;
        }
        return total;
    }

    private void generatePatterns(int m, int pos, int[] current, List<int[]> patterns) {
        if (pos == m) {
            patterns.add(current.clone());
            return;
        }
        for (int color = 0; color < 3; color++) {
            if (pos > 0 && current[pos - 1] == color) continue;
            current[pos] = color;
            generatePatterns(m, pos + 1, current, patterns);
        }
    }

    private boolean isCompatible(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == b[i]) return false;
        }
        return true;
    }
}
