// Source: https://leetcode.com/problems/maximize-area-of-square-hole-in-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-14
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 44.94 MB Beats 94.12%

/****************************************
* 
* You are given the two integers, `n` and `m` and two integer arrays, `hBars`
* _ and `vBars`. The grid has `n + 2` horizontal and `m + 2` vertical bars,
* _ creating 1 x 1 unit cells. The bars are indexed starting from `1`.
* You can remove some of the bars in `hBars` from horizontal bars and some of
* _ the bars in `vBars` from vertical bars. Note that other bars are fixed and
* _ cannot be removed.
* Return an integer denoting the maximum area of a square-shaped hole in the
* _ grid, after removing some bars (possibly none).
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2023/11/05/screenshot-from-2023-11-05-22-40-25.png]
* Input: n = 2, m = 1, hBars = [2,3], vBars = [2]
* Output: 4
* Explanation:
* The left image shows the initial grid formed by the bars. The horizontal bars are `[1,2,3,4]`, and the vertical bars are `[1,2,3]`.
* One way to get the maximum square-shaped hole is by removing horizontal bar 2 and vertical bar 2.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2023/11/04/screenshot-from-2023-11-04-17-01-02.png]
* Input: n = 1, m = 1, hBars = [2], vBars = [2]
* Output: 4
* Explanation:
* To get the maximum square-shaped hole, we remove horizontal bar 2 and vertical bar 2.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2024/03/12/unsaved-image-2.png]
* Input: n = 2, m = 3, hBars = [2,3], vBars = [2,4]
* Output: 4
* Explanation:
* One way to get the maximum square-shaped hole is by removing horizontal bar 3, and vertical bar 4.
*
* Constraints:
* • `1 <= n <= 10^9`
* • `1 <= m <= 10^9`
* • `1 <= hBars.length <= 100`
* • `2 <= hBars[i] <= n + 1`
* • `1 <= vBars.length <= 100`
* • `2 <= vBars[i] <= m + 1`
* • All values in `hBars` are distinct.
* • All values in `vBars` are distinct.
* 
****************************************/

import java.util.Arrays;

class Solution {
    // We only care about removable bars, since fixed bars cannot change the hole size.
    // Sorting lets us find the longest run of consecutive bars we can remove.
    // Removing k consecutive bars creates a gap of k + 1 grid units.
    // The largest square side is the min of horizontal and vertical gaps.
    // Time: O(h log h + v log v), Space: O(1) extra.

    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        int maxH = maxConsecutiveSpan(hBars);
        int maxV = maxConsecutiveSpan(vBars);
        int side = Math.min(maxH, maxV);
        return side * side;
    }

    private int maxConsecutiveSpan(int[] bars) {
        Arrays.sort(bars);

        int longest = 1;
        int current = 1;

        for (int i = 1; i < bars.length; i++) {
            if (bars[i] == bars[i - 1] + 1) {
                current++;
            } else {
                current = 1;
            }
            longest = Math.max(longest, current);
        }

        // Removing k consecutive bars creates k + 1 units of space
        return longest + 1;
    }
}
