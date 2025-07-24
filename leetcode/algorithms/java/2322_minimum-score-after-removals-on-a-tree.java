// Source: https://leetcode.com/problems/minimum-score-after-removals-on-a-tree/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-22
// At the time of submission:
//   Runtime 20 ms Beats 93.10%
//   Memory 44.66 MB Beats 82.76%

/****************************************
* 
* There is an undirected connected tree with `n` nodes labeled from `0` to `n - 1`
* _ and `n - 1` edges.
* You are given a 0-indexed integer array `nums` of length `n` where `nums[i]`
* _ represents the value of the `i^th` node. You are also given a 2D integer
* _ array `edges` of length `n - 1` where `edges[i] = [a_i, b_i]` indicates that
* _ there is an edge between nodes `a_i` and `b_i` in the tree.
* Remove two distinct edges of the tree to form three connected components. For
* _ a pair of removed edges, the following steps are defined:
* 1. Get the XOR of all the values of the nodes for each of the three components
* _ respectively.
* 2. The difference between the largest XOR value and the smallest XOR value is
* _ the score of the pair.
* • For example, say the three components have the node values: `[4,5,7]`,
* _ `[1,9]`, and `[3,3,3]`. The three XOR values are `4 ^ 5 ^ 7 = 6`, `1 ^ 9 = 8`,
* _ and `3 ^ 3 ^ 3 = 3`. The largest XOR value is `8` and the smallest XOR value is
* _ `3`. The score is then `8 - 3 = 5`.
* Return the minimum score of any possible pair of edge removals on the given tree.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2022/05/03/ex1drawio.png]
* Input: nums = [1,5,5,4,11], edges = [[0,1],[1,2],[1,3],[3,4]]
* Output: 9
* Explanation: The diagram above shows a way to make a pair of removals.
* - The 1st component has nodes [1,3,4] with values [5,4,11]. Its XOR value is 5 ^ 4 ^ 11 = 10.
* - The 2nd component has node [0] with value [1]. Its XOR value is 1 = 1.
* - The 3rd component has node [2] with value [5]. Its XOR value is 5 = 5.
* The score is the difference between the largest and smallest XOR value which is 10 - 1 = 9.
* It can be shown that no other pair of removals will obtain a smaller score than 9.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2022/05/03/ex2drawio.png]
* Input: nums = [5,5,2,4,4,2], edges = [[0,1],[1,2],[5,2],[4,3],[1,3]]
* Output: 0
* Explanation: The diagram above shows a way to make a pair of removals.
* - The 1st component has nodes [3,4] with values [4,4]. Its XOR value is 4 ^ 4 = 0.
* - The 2nd component has nodes [1,0] with values [5,5]. Its XOR value is 5 ^ 5 = 0.
* - The 3rd component has nodes [2,5] with values [2,2]. Its XOR value is 2 ^ 2 = 0.
* The score is the difference between the largest and smallest XOR value which is 0 - 0 = 0.
* We cannot obtain a smaller score than 0.
*
* Constraints:
* • n == nums.length
* • 3 <= n <= 1000
* • 1 <= nums[i] <= 10^8
* • edges.length == n - 1
* • edges[i].length == 2
* • 0 <= a_i, b_i < n
* • a_i != b_i
* • `edges` represents a valid tree.
* 
****************************************/

class Solution {
    // Perform DFS to compute subtree XORs and entry/exit times.
    // Use entry/exit times to determine subtree relationships in O(1).
    // Check all pairs (u, v) to partition the tree into 3 parts.
    // Calculate XOR for each part and find the min score (max - min).
    // Time: O(n^2), Space: O(n), where n = number of nodes.

    public int minimumScore(int[] nums, int[][] edges) {
        int n = nums.length;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] xorSum = new int[n];   // xorSum[i] = XOR of subtree rooted at i
        int[] entryTime = new int[n]; // entry time in DFS traversal
        int[] exitTime = new int[n];  // exit time in DFS traversal
        int[] time = {0};             // mutable counter used during DFS

        dfs(0, -1, nums, graph, xorSum, entryTime, exitTime, time);

        int minScore = Integer.MAX_VALUE;

        for (int u = 1; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                // Check if one is in the subtree of the other using in/out times
                if (entryTime[v] > entryTime[u] && entryTime[v] < exitTime[u]) {
                    // v is in subtree of u
                    minScore = Math.min(
                        minScore,
                        computeScore(xorSum[0] ^ xorSum[u], xorSum[u] ^ xorSum[v], xorSum[v])
                    );
                } else if (entryTime[u] > entryTime[v] && entryTime[u] < exitTime[v]) {
                    // u is in subtree of v
                    minScore = Math.min(
                        minScore,
                        computeScore(xorSum[0] ^ xorSum[v], xorSum[v] ^ xorSum[u], xorSum[u])
                    );
                } else {
                    // u and v are in different subtrees
                    minScore = Math.min(
                        minScore,
                        computeScore(xorSum[0] ^ xorSum[u] ^ xorSum[v], xorSum[u], xorSum[v])
                    );
                }
            }
        }

        return minScore;
    }

    // Compute the score as max - min among three partition XORs
    private int computeScore(int part1, int part2, int part3) {
        return Math.max(part1, Math.max(part2, part3)) -
               Math.min(part1, Math.min(part2, part3));
    }

    // DFS to compute subtree XOR and entry/exit times for Euler Tour logic
    private void dfs(
        int node,
        int parent,
        int[] nums,
        List<List<Integer>> graph,
        int[] xorSum,
        int[] entryTime,
        int[] exitTime,
        int[] time
    ) {
        entryTime[node] = time[0]++;
        xorSum[node] = nums[node];

        for (int neighbor : graph.get(node)) {
            if (neighbor == parent) continue;
            dfs(neighbor, node, nums, graph, xorSum, entryTime, exitTime, time);
            xorSum[node] ^= xorSum[neighbor];
        }

        exitTime[node] = time[0];
    }
}
