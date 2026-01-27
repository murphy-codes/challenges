// Source: https://leetcode.com/problems/minimum-cost-path-with-edge-reversals/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-26
// At the time of submission:
//   Runtime 67 ms Beats 97.17%
//   Memory 275.87 MB Beats 36.03%

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
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    // Each directed edge is expanded into two edges: the original edge
    // and a reversible edge with double cost, modeling switch usage.
    // This eliminates the need to track switch states explicitly.
    // Dijkstra's algorithm finds the minimum cost path efficiently.
    // Time: O((n + m) log n), Space: O(n + m).

    static class Edge {
        int to;
        int cost;

        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public int minCost(int n, int[][] edges) {
        List<Edge>[] graph = new ArrayList[n];

        // Initialize adjacency list
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Add original and reversible edges
        for (int[] e : edges) {
            int from = e[0];
            int to = e[1];
            int weight = e[2];

            graph[from].add(new Edge(to, weight));       // normal edge
            graph[to].add(new Edge(from, 2 * weight));   // reversed edge
        }

        // Distance array: shortest known cost to each node
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;

        // Min-heap ordered by distance
        PriorityQueue<int[]> pq =
            new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[]{0, 0}); // {node, cost}

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int costSoFar = current[1];

            // Early exit: destination reached
            if (node == n - 1) return costSoFar;

            for (Edge edge : graph[node]) {
                int nextNode = edge.to;
                int newCost = costSoFar + edge.cost;

                if (newCost < dist[nextNode]) {
                    dist[nextNode] = newCost;
                    pq.add(new int[]{nextNode, newCost});
                }
            }
        }

        return -1;
    }
}
