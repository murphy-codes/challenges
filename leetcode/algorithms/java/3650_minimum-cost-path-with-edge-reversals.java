// Source: https://leetcode.com/problems/minimum-cost-path-with-edge-reversals/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-26
// At the time of submission:
//   Runtime 70 ms Beats 93.12%
//   Memory 276.63 MB Beats 16.19%

/****************************************
* 
* You are given a directed, weighted graph with `n` nodes labeled from 0 to
* _ `n - 1`, and an array edges where `edges[i] = [u_i, v_i, w_i]` represents
* _ a directed edge from node `u_i` to node `v_i` with cost `w_i`.
* Each node `u_i` has a switch that can be used at most once: when you arrive
* _ at `u_i` and have not yet used its switch, you may activate it on one of
* _ its incoming edges `v_i → u_i` reverse that edge to `u_i → v_i` and
* _ immediately traverse it.
* The reversal is only valid for that single move, and using a reversed edge
* _ costs `2 * w_i`.
* Return the minimum total cost to travel from node 0 to node `n - 1`. If it
* _ is not possible, return -1.
*
* Example 1:
* Input: n = 4, edges = [[0,1,3],[3,1,1],[2,3,4],[0,2,2]]
* Output: 5
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/05/07/e1drawio.png]
* Use the path `0 → 1` (cost 3).
* At node 1 reverse the original edge `3 → 1` into `1 → 3` and traverse it at cost `2 * 1 = 2`.
* Total cost is `3 + 2 = 5`.
*
* Example 2:
* Input: n = 4, edges = [[0,2,1],[2,1,1],[1,3,1],[2,3,3]]
* Output: 3
* Explanation:
* No reversal is needed. Take the path `0 → 2` (cost 1), then `2 → 1` (cost 1), then `1 → 3` (cost 1).
* Total cost is `1 + 1 + 1 = 3`.
*
* Constraints:
* • `2 <= n <= 5 * 10^4`
* • `1 <= edges.length <= 10^5`
* • `edges[i] = [u_i, v_i, w_i]`
* • `0 <= u_i, v_i <= n - 1`
* • `1 <= w_i <= 1000`
* 
****************************************/

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    // Convert the graph by adding both original edges (u→v, w)
    // and reversible edges (v→u, 2w), modeling switch usage implicitly.
    // This removes the need to track switch states per node.
    // Run Dijkstra on the expanded graph to find the minimum cost path.
    // Time: O((n + m) log n), Space: O(n + m).
    public int minCost(int n, int[][] edges) {
        // Build adjacency list
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Add original edges and their reversible counterparts
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            graph[u].add(new int[]{v, w});        // normal edge
            graph[v].add(new int[]{u, 2 * w});    // reversed edge via switch
        }

        // Dijkstra setup
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) dist[i] = Integer.MAX_VALUE;
        dist[0] = 0;

        PriorityQueue<int[]> pq =
            new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{0, 0}); // {node, cost}

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int node = cur[0], cost = cur[1];

            if (cost > dist[node]) continue;
            if (node == n - 1) return cost;

            for (int[] next : graph[node]) {
                int nextNode = next[0];
                int nextCost = cost + next[1];
                if (nextCost < dist[nextNode]) {
                    dist[nextNode] = nextCost;
                    pq.offer(new int[]{nextNode, nextCost});
                }
            }
        }

        return -1;
    }
}
