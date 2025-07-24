// Source: https://leetcode.com/problems/minimum-score-after-removals-on-a-tree/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-22
// At the time of submission:
//   Runtime 87 ms Beats 62.07%
//   Memory 44.41 MB Beats 93.10%

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

import java.util.ArrayList;
import java.util.List;

class Solution {
    // DFS assigns in/out timestamps and computes XOR of each subtree
    // For each pair of edges, we simulate cutting the tree into 3 parts
    // We use in/out times to determine ancestor relationships in O(1)
    // Based on inclusion, we compute the 3 component XORs from subtreeXor[]
    // Finally, we track the min score across all such edge pairings
    
    private int time = 0;
    private int[] in, out, subtreeXor;
    private List<List<Integer>> graph = new ArrayList<>();

    public int minimumScore(int[] nums, int[][] edges) {
        int n = nums.length;
        in = new int[n];
        out = new int[n];
        subtreeXor = new int[n];

        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        // Build adjacency list
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        // DFS to compute subtree XORs and in/out times
        dfs(0, -1, nums);

        int totalXor = subtreeXor[0];
        int minScore = Integer.MAX_VALUE;

        // Try all pairs of edges (v1-parent1), (v2-parent2)
        for (int[] e1 : edges) {
            int v1 = getChild(e1[0], e1[1]);
            for (int[] e2 : edges) {
                if (e1 == e2) continue;
                int v2 = getChild(e2[0], e2[1]);

                int xor1 = subtreeXor[v1];
                int xor2 = subtreeXor[v2];
                int xor3;

                if (isAncestor(v1, v2)) {
                    xor2 = subtreeXor[v2];
                    xor1 = subtreeXor[v1] ^ xor2;
                    xor3 = totalXor ^ subtreeXor[v1];
                } else if (isAncestor(v2, v1)) {
                    xor1 = subtreeXor[v1];
                    xor2 = subtreeXor[v2] ^ xor1;
                    xor3 = totalXor ^ subtreeXor[v2];
                } else {
                    xor3 = totalXor ^ xor1 ^ xor2;
                }

                int max = Math.max(xor1, Math.max(xor2, xor3));
                int min = Math.min(xor1, Math.min(xor2, xor3));
                minScore = Math.min(minScore, max - min);
            }
        }

        return minScore;
    }

    private int dfs(int node, int parent, int[] nums) {
        in[node] = time++;
        subtreeXor[node] = nums[node];
        for (int neighbor : graph.get(node)) {
            if (neighbor != parent) {
                subtreeXor[node] ^= dfs(neighbor, node, nums);
            }
        }
        out[node] = time++;
        return subtreeXor[node];
    }

    private boolean isAncestor(int u, int v) {
        return in[u] <= in[v] && out[v] <= out[u];
    }

    private int getChild(int a, int b) {
        // Always return the child (the one deeper in DFS)
        return in[a] > in[b] ? a : b;
    }
}
