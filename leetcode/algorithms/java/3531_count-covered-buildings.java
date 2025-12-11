// Source: https://leetcode.com/problems/count-covered-buildings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-10
// At the time of submission:
//   Runtime 14 ms Beats 81.05%
//   Memory 215.08 MB Beats 74.74%

/****************************************
* 
* You are given a positive integer `n`, representing an `n x n` city. You are
* _ also given a 2D grid `buildings`, where `buildings[i] = [x, y]` denotes a
* _ unique building located at coordinates `[x, y]`.
* A building is covered if there is at least one building in all four directions:
* _ left, right, above, and below.
* Return the number of covered buildings.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2025/03/04/telegram-cloud-photo-size-5-6212982906394101085-m.jpg]
* Input: n = 3, buildings = [[1,2],[2,2],[3,2],[2,1],[2,3]]
* Output: 1
* Explanation:
* Only building [2,2] is covered as it has at least one building:
* above ([1,2])
* below ([3,2])
* left ([2,1])
* right ([2,3])
* Thus, the count of covered buildings is 1.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2025/03/04/telegram-cloud-photo-size-5-6212982906394101086-m.jpg]
* Input: n = 3, buildings = [[1,1],[1,2],[2,1],[2,2]]
* Output: 0
* Explanation:
* No building has at least one building in all four directions.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2025/03/16/telegram-cloud-photo-size-5-6248862251436067566-x.jpg]
* Input: n = 5, buildings = [[1,3],[3,2],[3,3],[3,5],[5,3]]
* Output: 1
* Explanation:
* Only building [3,3] is covered as it has at least one building:
* above ([1,3])
* below ([5,3])
* left ([3,2])
* right ([3,5])
* Thus, the count of covered buildings is 1.
*
* Constraints:
* • `2 <= n <= 10^5`
* • `1 <= buildings.length <= 10^5`
* • `buildings[i] = [x, y]`
* • `1 <= x, y <= n`
* • All coordinates of buildings are unique.
* 
****************************************/

// import java.util.Arrays;

class Solution {
    // We track the min and max x for each column and the min and max y for each row.
    // A building is covered if its x lies strictly between the min/max x in its
    // column and its y lies strictly between the min/max y in its row. This avoids
    // sorting and allows the solution to run in O(n) time using O(n) extra space,
    // since each building is processed twice in simple array lookups.

    public int countCoveredBuildings(int n, int[][] buildings) {

        // Track min/max x for each column y
        int[] minXInColumn = new int[n + 1];
        int[] maxXInColumn = new int[n + 1];

        // Track min/max y for each row x
        int[] minYInRow = new int[n + 1];
        int[] maxYInRow = new int[n + 1];

        // Initialize mins to something larger than any valid coordinate
        Arrays.fill(minXInColumn, n + 1);
        Arrays.fill(minYInRow, n + 1);

        // First pass: compute min/max extents for each row/column
        for (int[] b : buildings) {
            int x = b[0];
            int y = b[1];

            // Column y: track smallest/largest x
            minXInColumn[y] = Math.min(minXInColumn[y], x);
            maxXInColumn[y] = Math.max(maxXInColumn[y], x);

            // Row x: track smallest/largest y
            minYInRow[x] = Math.min(minYInRow[x], y);
            maxYInRow[x] = Math.max(maxYInRow[x], y);
        }

        int coveredCount = 0;

        // Second pass: check if each building has neighbors in all 4 directions
        for (int[] b : buildings) {
            int x = b[0];
            int y = b[1];

            boolean hasAboveBelow = x > minXInColumn[y] && x < maxXInColumn[y];
            boolean hasLeftRight  = y > minYInRow[x] && y < maxYInRow[x];

            if (hasAboveBelow && hasLeftRight) {
                coveredCount++;
            }
        }

        return coveredCount;
    }
}
