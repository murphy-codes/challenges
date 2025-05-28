// Source: https://leetcode.com/problems/maximize-the-number-of-target-nodes-after-connecting-trees-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-27
// At the time of submission:
//   Runtime 378 ms Beats 51.90%
//   Memory 47.11 MB Beats 55.70%

/****************************************
* 
* There exist two undirected trees with `n` and `m` nodes, with distinct labels
* _ in ranges `[0, n - 1]` and `[0, m - 1]`, respectively.
* You are given two 2D integer arrays `edges1` and `edges2` of lengths `n - 1`
* _ and `m - 1`, respectively, where `edges1[i] = [a_i, b_i]` indicates that
* _ there is an edge between nodes `a_i` and `b_i` in the first tree and
* _ `edges2[i] = [u_i, v_i]` indicates that there is an edge between nodes
* _ `u_i` and `v_i` in the second tree. You are also given an integer `k`.
* Node `u` is target to node `v` if the number of edges on the path from `u`
* _ to `v` is less than or equal to `k`. Note that a node is always target
* _ to itself.
* Return an array of `n` integers `answer`, where `answer[i]` is the maximum
* _ possible number of nodes target to node `i` of the first tree if you have to
* _ connect one node from the first tree to another node in the second tree.
* Note that queries are independent from each other. That is, for every query
* _ you will remove the added edge before proceeding to the next query.
*
* Example 1:
* Input: edges1 = [[0,1],[0,2],[2,3],[2,4]], edges2 = [[0,1],[0,2],[0,3],[2,7],[1,4],[4,5],[4,6]], k = 2
* Output: [9,7,9,8,8]
* Explanation:
* • For `i = 0`, connect node 0 from the first tree to node 0 from the second tree.
* • For `i = 1`, connect node 1 from the first tree to node 0 from the second tree.
* • For `i = 2`, connect node 2 from the first tree to node 4 from the second tree.
* • For `i = 3`, connect node 3 from the first tree to node 4 from the second tree.
* • For `i = 4`, connect node 4 from the first tree to node 4 from the second tree.
* [Image: https://assets.leetcode.com/uploads/2024/09/24/3982-1.png]
*
* Example 2:
* Input: edges1 = [[0,1],[0,2],[0,3],[0,4]], edges2 = [[0,1],[1,2],[2,3]], k = 1
* Output: [6,3,3,3,3]
* Explanation:
* For every `i`, connect node `i` of the first tree with any node of the second tree.
* [Image: https://assets.leetcode.com/uploads/2024/09/24/3928-2.png]
*
* Constraints:
* • 2 <= n, m <= 1000
* • edges1.length == n - 1
* • edges2.length == m - 1
* • edges1[i].length == edges2[i].length == 2
* • edges1[i] = [a_i, b_i]
* • 0 <= a_i, b_i < n
* • edges2[i] = [u_i, v_i]
* • 0 <= u_i, v_i < m
* • The input is generated such that `edges1` and `edges2` represent valid trees.
* • 0 <= k <= 1000
* 
****************************************/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    // For each node in Tree2, precompute how many nodes are within k dist.
    // For each node in Tree1, compute reachable nodes in ≤k distance.
    // Try connecting to each node in Tree2 and check combined coverage
    // with a remaining distance of k-1 across the bridge edge.
    // Time: O(n * m + (n + m)^2), Space: O(n + m) for graph and BFS.
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;

        ArrayList<ArrayList<Integer>> tree1 = buildTree(n, edges1);
        ArrayList<ArrayList<Integer>> tree2 = buildTree(m, edges2);

        // Precompute reachable counts in Tree2 from every node
        int[] reachableInTree2 = new int[m];
        for (int j = 0; j < m; j++) {
            reachableInTree2[j] = bfsCountWithinDistance(tree2, j, k - 1);
        }

        int[] result = new int[n];
        // For each node in Tree1, run BFS once, then combine with precomputed values
        for (int i = 0; i < n; i++) {
            int reachableInTree1 = bfsCountWithinDistance(tree1, i, k);
            int maxReach = 0;
            for (int j = 0; j < m; j++) {
                int combined = reachableInTree1 + reachableInTree2[j];
                maxReach = Math.max(maxReach, combined);
            }
            result[i] = maxReach;
        }
        return result;
    }

    private ArrayList<ArrayList<Integer>> buildTree(int size, int[][] edges) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < size; i++) graph.add(new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    private int bfsCountWithinDistance(ArrayList<ArrayList<Integer>> graph, int start, int maxDist) {
        boolean[] visited = new boolean[graph.size()];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{start, 0});
        visited[start] = true;

        int count = 0;
        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            int u = node[0], dist = node[1];
            if (dist > maxDist) continue;
            count++;

            for (int v : graph.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(new int[]{v, dist + 1});
                }
            }
        }
        return count;
    }
}
