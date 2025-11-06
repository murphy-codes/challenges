// Source: https://leetcode.com/problems/power-grid-maintenance/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-06
// At the time of submission:
//   Runtime 190 ms Beats 19.67%
//   Memory 277.63 MB Beats 5.02%

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

class Solution {
    // Precompute connected components (O(c + n)) and store all nodes of each
    // component in a TreeSet (sorted, O(c log c)). Each query runs in O(log n):
    //   - Type 1: If x is online, return x; else smallest online node in component.
    //   - Type 2: Mark x offline and remove it from its component’s TreeSet.
    // Overall time complexity: O((c + n + q) * log n); space: O(c + n).
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        // Step 1: Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= c; i++) graph.add(new ArrayList<>());
        for (int[] edge : connections) {
            int u = edge[0], v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        // Step 2: Find connected components using DFS
        int[] compId = new int[c + 1];
        int compCount = 0;
        for (int i = 1; i <= c; i++) {
            if (compId[i] == 0) {
                compCount++;
                dfs(i, compCount, graph, compId);
            }
        }

        // Step 3: Initialize TreeSets for each component (all online initially)
        Map<Integer, TreeSet<Integer>> compToOnline = new HashMap<>();
        for (int i = 1; i <= c; i++) {
            int comp = compId[i];
            compToOnline.computeIfAbsent(comp, k -> new TreeSet<>()).add(i);
        }

        // Step 4: Process queries
        List<Integer> result = new ArrayList<>();
        Set<Integer> offline = new HashSet<>();

        for (int[] q : queries) {
            int type = q[0], x = q[1];
            int comp = compId[x];
            TreeSet<Integer> onlineSet = compToOnline.get(comp);

            if (type == 1) {
                // Maintenance check query
                if (!offline.contains(x)) {
                    result.add(x); // x itself handles it
                } else if (!onlineSet.isEmpty()) {
                    result.add(onlineSet.first()); // smallest online in component
                } else {
                    result.add(-1); // no online station
                }
            } else {
                // Station x goes offline
                if (!offline.contains(x)) {
                    offline.add(x);
                    onlineSet.remove(x);
                }
            }
        }

        // Convert result list to array
        int[] ans = new int[result.size()];
        for (int i = 0; i < result.size(); i++) ans[i] = result.get(i);
        return ans;
    }

    private void dfs(int node, int comp, List<List<Integer>> graph, int[] compId) {
        compId[node] = comp;
        for (int nei : graph.get(node)) {
            if (compId[nei] == 0) dfs(nei, comp, graph, compId);
        }
    }
}
