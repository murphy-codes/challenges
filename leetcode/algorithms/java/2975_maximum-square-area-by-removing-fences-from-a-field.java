// Source: https://leetcode.com/problems/maximum-square-area-by-removing-fences-from-a-field/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-14
// At the time of submission:
//   Runtime 312 ms Beats 97.62%
//   Memory 174.50 MB Beats 46.43%

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
import java.util.Set;

class Solution {
    // Add boundary fences so all possible field edges are considered.
    // Any square side length must appear as a distance between both
    // horizontal and vertical fence coordinates.
    // Store all vertical distances in a set, then scan horizontal ones
    // to find the largest matching square area.
    // Time: O(H^2 + V^2), Space: O(V^2).
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        final long MOD = 1_000_000_007L;

        long maxArea = 0L;

        int totalY = hFences.length + 2;
        int totalX = vFences.length + 2;

        int[] yCoords = new int[totalY];
        int[] xCoords = new int[totalX];

        yCoords[0] = 1;
        yCoords[totalY - 1] = m;
        xCoords[0] = 1;
        xCoords[totalX - 1] = n;

        for (int i = 0; i < hFences.length; i++) {
            yCoords[i + 1] = hFences[i];
        }
        for (int i = 0; i < vFences.length; i++) {
            xCoords[i + 1] = vFences[i];
        }

        Arrays.sort(yCoords);
        Arrays.sort(xCoords);

        Set<Long> verticalDiffs = new HashSet<>();

        // Store all vertical distances
        for (int i = 0; i < xCoords.length; i++) {
            for (int j = i; j < xCoords.length; j++) {
                verticalDiffs.add((long) xCoords[i] - xCoords[j]);
            }
        }

        // Check horizontal distances against vertical ones
        for (int i = 0; i < yCoords.length; i++) {
            for (int j = i; j < yCoords.length; j++) {
                long side = (long) yCoords[i] - yCoords[j];
                long area = side * side;

                if (area <= maxArea) continue;

                if (verticalDiffs.contains(side)) {
                    maxArea = area;
                }
            }
        }

        return maxArea == 0 ? -1 : (int) (maxArea % MOD);
    }
}
