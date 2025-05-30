// Source: https://leetcode.com/problems/find-closest-node-to-given-two-nodes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-30
// At the time of submission:
//   Runtime 11 ms Beats 89.90%
//   Memory 58.20 MB Beats 79.79%

/****************************************
* 
* You are given a directed graph of `n` nodes numbered from `0` to `n - 1`, where
* _ each node has at most one outgoing edge.
* The graph is represented with a given 0-indexed array `edges` of size `n`,
* _ indicating that there is a directed edge from node `i` to node `edges[i]`.
* _ If there is no outgoing edge from `i`, then `edges[i] == -1`.
* You are also given two integers `node1` and `node2`.
* Return the index of the node that can be reached from both `node1` and `node2`,
* _ such that the maximum between the distance from `node1` to that node, and from
* _ `node2` to that node is minimized. If there are multiple answers, return the
* _ node with the smallest index, and if no possible answer exists, return `-1`.
* Note that `edges` may contain cycles.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2022/06/07/graph4drawio-2.png]
* Input: edges = [2,2,3,-1], node1 = 0, node2 = 1
* Output: 2
* Explanation: The distance from node 0 to node 2 is 1, and the distance from node 1 to node 2 is 1.
* The maximum of those two distances is 1. It can be proven that we cannot get a node with a smaller maximum distance than 1, so we return node 2.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2022/06/07/graph4drawio-4.png]
* Input: edges = [1,2,-1], node1 = 0, node2 = 2
* Output: 2
* Explanation: The distance from node 0 to node 2 is 2, and the distance from node 2 to itself is 0.
* The maximum of those two distances is 2. It can be proven that we cannot get a node with a smaller maximum distance than 2, so we return node 2.
*
* Constraints:
* • n == edges.length
* • 2 <= n <= 10^5
* • -1 <= edges[i] < n
* • edges[i] != i
* • 0 <= node1, node2 < n
* 
****************************************/

import java.util.HashMap;

class Solution {
    // Traverse from both node1 and node2, recording distances
    // to all reachable nodes from each. Then, for each node
    // reachable by both, compute the max of the two distances.
    // Return the node with the smallest such max distance.
    // Time: O(n), Space: O(n), where n is number of nodes.
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int n = edges.length;

        // Distance from node1 to every reachable node
        int[] dist1 = getDistances(edges, node1, n);
        int[] dist2 = getDistances(edges, node2, n);

        int result = -1;
        int minDist = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (dist1[i] != -1 && dist2[i] != -1) {
                int maxDist = Math.max(dist1[i], dist2[i]);
                if (maxDist < minDist) {
                    minDist = maxDist;
                    result = i;
                }
            }
        }

        return result;
    }

    private int[] getDistances(int[] edges, int start, int n) {
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        int curr = start;
        int d = 0;

        // Initialize distances to -1 (unreachable)
        for (int i = 0; i < n; i++) dist[i] = -1;

        while (curr != -1 && !visited[curr]) {
            dist[curr] = d++;
            visited[curr] = true;
            curr = edges[curr];
        }

        return dist;
    }
}
