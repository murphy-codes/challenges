// Source: https://leetcode.com/problems/count-covered-buildings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-10
// At the time of submission:
//   Runtime 175 ms Beats 62.11%
//   Memory 272.74 MB Beats 38.95 %

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

// import java.util.HashMap;
// import java.util.ArrayList;
// import java.util.Collections;

class Solution {
    // We group buildings by row and column, then sort each group so we can
    // identify which buildings have neighbors on all four sides. A building
    // is covered if its y-value lies strictly between the min/max y in its
    // row, and its x-value lies strictly between the min/max x in its column.
    // This runs in O(n log n) time for sorting, with O(n) extra space.

    public int countCoveredBuildings(int n, int[][] buildings) {

        // Group buildings by rows and columns
        HashMap<Integer, ArrayList<Integer>> rowMap = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> colMap = new HashMap<>();

        for (int[] b : buildings) {
            int x = b[0], y = b[1];

            rowMap.computeIfAbsent(x, k -> new ArrayList<>()).add(y);
            colMap.computeIfAbsent(y, k -> new ArrayList<>()).add(x);
        }

        // Sort rows and columns
        for (ArrayList<Integer> list : rowMap.values()) {
            Collections.sort(list);
        }
        for (ArrayList<Integer> list : colMap.values()) {
            Collections.sort(list);
        }

        int covered = 0;

        // Check each building
        for (int[] b : buildings) {
            int x = b[0];
            int y = b[1];

            ArrayList<Integer> row = rowMap.get(x);
            ArrayList<Integer> col = colMap.get(y);

            // A building is covered in its row if y is not min or max
            boolean coveredRow = y != row.get(0) && y != row.get(row.size() - 1);

            // Covered in column if x is not min or max
            boolean coveredCol = x != col.get(0) && x != col.get(col.size() - 1);

            if (coveredRow && coveredCol) {
                covered++;
            }
        }

        return covered;
    }
}
