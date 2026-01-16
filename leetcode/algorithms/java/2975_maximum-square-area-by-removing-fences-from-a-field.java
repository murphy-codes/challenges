// Source: https://leetcode.com/problems/maximum-square-area-by-removing-fences-from-a-field/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-14
// At the time of submission:
//   Runtime 379 ms Beats 95.24%
//   Memory 181.38 MB Beats 46.43%

/****************************************
* 
* There is a large `(m - 1) x (n - 1)` rectangular field with corners at
* _ `(1, 1)` and `(m, n)` containing some horizontal and vertical fences given
* _ in arrays `hFences` and `vFences` respectively.
* Horizontal fences are from the coordinates `(hFences[i], 1)` to
* _ `(hFences[i], n)` and vertical fences are from the coordinates
* _ `(1, vFences[i])` to `(m, vFences[i])`.
* Return the maximum area of a square field that can be formed by removing
* _ some fences (possibly none) or `-1` if it is impossible to make a square field.
* Since the answer may be large, return it modulo `10^9 + 7`.
* Note: The field is surrounded by two horizontal fences from the coordinates
* _ `(1, 1)` to `(1, n)` and `(m, 1)` to `(m, n)` and two vertical fences from
* _ the coordinates `(1, 1)` to `(m, 1)` and `(1, n)` to `(m, n)`.
* _ These fences cannot be removed.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2023/11/05/screenshot-from-2023-11-05-22-40-25.png]
* Input: m = 4, n = 3, hFences = [2,3], vFences = [2]
* Output: 4
* Explanation: Removing the horizontal fence at 2 and the vertical fence at 2 will give a square field of area 4.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2023/11/22/maxsquareareaexample1.png]
* Input: m = 6, n = 7, hFences = [2], vFences = [4]
* Output: -1
* Explanation: It can be proved that there is no way to create a square field by removing fences.
*
* Constraints:
* • `3 <= m, n <= 10^9`
* • `1 <= hFences.length, vFences.length <= 600`
* • `1 < hFences[i] < m`
* • `1 < vFences[i] < n`
* • `hFences` and `vFences` are unique.
* 
****************************************/

import java.util.Arrays;
import java.util.HashSet;

class Solution {
    // Add boundary fences so all possible field edges are represented.
    // Any square side length must be a difference between two horizontal
    // fences and also two vertical fences.
    // We compute all vertical distances, then scan horizontal distances
    // to find the maximum common value.
    // Time: O(H^2 + V^2), Space: O(V^2).

    private static final int MOD = 1_000_000_007;

    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        int[] h = addBounds(hFences, 1, m);
        int[] v = addBounds(vFences, 1, n);

        HashSet<Long> verticalDistances = new HashSet<>();
        for (int i = 0; i < v.length; i++) {
            for (int j = i + 1; j < v.length; j++) {
                verticalDistances.add((long) v[j] - v[i]);
            }
        }

        long maxSide = -1;
        for (int i = 0; i < h.length; i++) {
            for (int j = i + 1; j < h.length; j++) {
                long side = (long) h[j] - h[i];
                if (verticalDistances.contains(side)) {
                    maxSide = Math.max(maxSide, side);
                }
            }
        }

        if (maxSide == -1) return -1;
        return (int) ((maxSide % MOD) * (maxSide % MOD) % MOD);
    }

    private int[] addBounds(int[] fences, int low, int high) {
        int[] res = Arrays.copyOf(fences, fences.length + 2);
        res[fences.length] = low;
        res[fences.length + 1] = high;
        Arrays.sort(res);
        return res;
    }
}
