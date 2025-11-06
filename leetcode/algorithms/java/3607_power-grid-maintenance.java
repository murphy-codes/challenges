// Source: https://leetcode.com/problems/power-grid-maintenance/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-06
// At the time of submission:
//   Runtime 19 ms Beats 99.58%
//   Memory 246.06 MB Beats 8.79%

/****************************************
* 
* You are given an integer `c` representing `c` power stations, each with a
* _ unique identifier `id` from 1 to `c` (1‑based indexing).
* These stations are interconnected via `n` bidirectional cables, represented by
* _ a 2D array `connections`, where each element `connections[i] = [u_i, v_i]`
* _ indicates a connection between station `u_i` and station `v_i`. Stations
* _ that are directly or indirectly connected form a power grid.
* Initially, all stations are online (operational).
* You are also given a 2D array `queries`, where each query is one of the
* _ following two types:
* • `[1, x]`: A maintenance check is requested for station `x`. If station `x`
* __ is online, it resolves the check by itself. If station `x` is offline, the
* __ check is resolved by the operational station with the smallest `id` in the
* __ same power grid as `x`. If no operational station exists in that grid, return -1.
* • `[2, x]`: Station `x` goes offline (i.e., it becomes non-operational).
* Return an array of integers representing the results of each query of type
* _ `[1, x]` in the order they appear.
* Note: The power grid preserves its structure; an offline (non‑operational) node
* _ remains part of its grid and taking it offline does not alter connectivity.
*
* Example 1:
* Input: c = 5, connections = [[1,2],[2,3],[3,4],[4,5]], queries = [[1,3],[2,1],[1,1],[2,2],[1,2]]
* Output: [3,2,3]
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/04/15/powergrid.jpg]
* Initially, all stations {1, 2, 3, 4, 5} are online and form a single power grid.
* Query [1,3]: Station 3 is online, so the maintenance check is resolved by station 3.
* Query [2,1]: Station 1 goes offline. The remaining online stations are {2, 3, 4, 5}.
* Query [1,1]: Station 1 is offline, so the check is resolved by the operational station with the smallest id among {2, 3, 4, 5}, which is station 2.
* Query [2,2]: Station 2 goes offline. The remaining online stations are {3, 4, 5}.
* Query [1,2]: Station 2 is offline, so the check is resolved by the operational station with the smallest id among {3, 4, 5}, which is station 3.
*
* Example 2:
* Input: c = 3, connections = [], queries = [[1,1],[2,1],[1,1]]
* Output: [1,-1]
* Explanation:
* There are no connections, so each station is its own isolated grid.
* Query [1,1]: Station 1 is online in its isolated grid, so the maintenance check is resolved by station 1.
* Query [2,1]: Station 1 goes offline.
* Query [1,1]: Station 1 is offline and there are no other stations in its grid, so the result is -1.
*
* Constraints:
* • `1 <= c <= 10^5`
* • `0 <= n == connections.length <= min(10^5, c * (c - 1) / 2)`
* • `connections[i].length == 2`
* • `1 <= u_i, v_i <= c`
* • `u_i != v_i`
* • `1 <= queries.length <= 2 * 10^5`
* • `queries[i].length == 2`
* • `queries[i][0]` is either 1 or 2.
* • `1 <= queries[i][1] <= c`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Uses a Union-Find (DSU) to group stations into connected components in O(c + n).
    // Each component’s members are flattened contiguously into an array, allowing O(1)
    // access to its online stations. Queries are processed in O(log n) amortized time:
    //   - [1, x]: if x offline, scan its component range for the next online station.
    //   - [2, x]: mark x offline in O(1).
    // Total time: O(c + n + q); space: O(c + n).

    public int[] processQueries(int n, int[][] connections, int[][] queries) {
        n++; // Use 1-based indexing for convenience

        // Union-Find parent array: l[i] gives the representative of i
        final int[] parent = new int[n];
        for (int i = 1; i < n; i++) parent[i] = i;

        // Union all connected nodes
        for (int[] edge : connections) {
            parent[getLabel(parent, edge[0])] = parent[getLabel(parent, edge[1])];
        }

        // Count number of nodes per component
        final int[] compCount = new int[n];
        for (int i = 0; i < n; i++) compCount[getLabel(parent, i)]++;

        // Compute prefix sums to mark starting indices of each component
        updateCounts(compCount);

        // Make a copy to preserve original starts
        final int[] compStart = compCount.clone();

        // Flatten components: group nodes of same component contiguously
        final int[] flat = new int[n];
        for (int i = 0; i < n; i++) {
            flat[compCount[parent[i]]++] = i;
        }

        // Process queries
        final int[] result = new int[queries.length];
        int resLen = 0;
        final boolean[] offline = new boolean[n];

        for (var q : queries) {
            int type = q[0], x = q[1];
            if (type == 1) { // Maintenance check
                if (offline[x]) {
                    int label = parent[x];
                    int start = compStart[label];
                    int end = compCount[label];

                    // Advance start pointer to first online station
                    while (start < end && offline[flat[start]]) start++;
                    compStart[label] = start;

                    result[resLen++] = (start == end) ? -1 : flat[start];
                } else {
                    result[resLen++] = x; // Station handles its own query
                }
            } else {
                offline[x] = true; // Station goes offline
            }
        }

        return Arrays.copyOf(result, resLen);
    }

    // Path compression for Union-Find
    static int getLabel(int[] parent, int i) {
        return (parent[i] == i || parent[i] < 0)
            ? i
            : (parent[i] = getLabel(parent, parent[i]));
    }

    // Convert counts to prefix-sum form for flattened indexing
    private static void updateCounts(int[] count) {
        int sum = 0;
        for (int i = 0; i < count.length; i++) {
            int next = sum + count[i];
            count[i] = sum;
            sum = next;
        }
    }
}
