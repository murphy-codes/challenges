// Source: https://leetcode.com/problems/maximum-candies-you-can-get-from-boxes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-02
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 57.58 MB Beats 43.78%

/****************************************
* 
* You have `n` boxes labeled from `0` to `n - 1`. You are given four arrays:
* _ `status`, `candies`, `keys`, and `containedBoxes` where:
* • `status[i]` is `1` if the `i^th` box is open and `0` if the `i^th` box is closed,
* • `candies[i]` is the number of candies in the `i^th` box,
* • `keys[i]` is a list of the labels of the boxes you can open after opening the `i^th` box.
* • `containedBoxes[i]` is a list of the boxes you found inside the `i^th` box.
* You are also given an integer array `initialBoxes` that contains the labels of
* _ the boxes you initially have. You can take all the candies in any open box
* _ and you can use the keys in it to open new boxes and you also can use
* _ the boxes you find in it.
* Return the maximum number of candies you can get following the rules above.
*
* Example 1:
* Input: status = [1,0,1,0], candies = [7,5,4,100], keys = [[],[],[1],[]], containedBoxes = [[1,2],[3],[],[]], initialBoxes = [0]
* Output: 16
* Explanation: You will be initially given box 0. You will find 7 candies in it and boxes 1 and 2.
* Box 1 is closed and you do not have a key for it so you will open box 2. You will find 4 candies and a key to box 1 in box 2.
* In box 1, you will find 5 candies and box 3 but you will not find a key to box 3 so box 3 will remain closed.
* Total number of candies collected = 7 + 4 + 5 = 16 candy.
*
* Example 2:
* Input: status = [1,0,0,0,0,0], candies = [1,1,1,1,1,1], keys = [[1,2,3,4,5],[],[],[],[],[]], containedBoxes = [[1,2,3,4,5],[],[],[],[],[]], initialBoxes = [0]
* Output: 6
* Explanation: You have initially box 0. Opening it you can find boxes 1,2,3,4 and 5 and their keys.
* The total number of candies will be 6.
*
* Constraints:
* • n == status.length == candies.length == keys.length == containedBoxes.length
* • 1 <= n <= 1000
* • `status[i]` is either `0` or `1`.
* • 1 <= candies[i] <= 1000
* • 0 <= keys[i].length <= n
* • 0 <= keys[i][j] < n
* • All values of `keys[i]` are unique.
* • 0 <= containedBoxes[i].length <= n
* • 0 <= containedBoxes[i][j] < n
* • All values of `containedBoxes[i]` are unique.
* • Each box is contained in one box at most.
* • 0 <= initialBoxes.length <= n
* • 0 <= initialBoxes[i] < n
* 
****************************************/

class Solution {
    // DFS-based solution to traverse all reachable boxes recursively.
    // Keys unlock more boxes and allow deeper traversal through the graph.
    // Candies are collected only from boxes that are both visited and open.
    // Time: O(n + total keys + total contained boxes), Space: O(n)
    // Efficient for sparse connections and avoids queue overhead.
    public int maxCandies(int[] status, int[] candies, int[][] keys,
                          int[][] containedBoxes, int[] initialBoxes) {

        int totalCandies = 0;
        boolean[] visited = new boolean[status.length];

        // Start DFS on all initial boxes regardless of open/closed status
        for (int box : initialBoxes) {
            dfs(box, status, keys, containedBoxes, visited);
        }

        // After DFS, collect candies from visited & open boxes
        for (int i = 0; i < candies.length; i++) {
            if (visited[i] && status[i] == 1) {
                totalCandies += candies[i];
            }
        }

        return totalCandies;
    }

    // Recursive DFS function to mark boxes as visited and unlock more boxes
    private void dfs(int box, int[] status, int[][] keys,
                     int[][] containedBoxes, boolean[] visited) {
        visited[box] = true;

        // Collect keys and mark those boxes as open
        for (int key : keys[box]) {
            if (key != box) {
                status[key] = 1;
            }
        }

        // Recursively visit all newly found boxes
        for (int contained : containedBoxes[box]) {
            if (!visited[contained]) {
                dfs(contained, status, keys, containedBoxes, visited);
            }
        }
    }
}
