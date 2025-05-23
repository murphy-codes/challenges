// Source: https://leetcode.com/problems/most-profitable-path-in-a-tree/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-23

/****************************************
* 
* There is an undirected tree with `n` nodes labeled from `0` to `n - 1`, rooted at node `0`. You are given a 2D integer array `edges` of length `n - 1` where `edges[i] = [a_i, b_i]` indicates that there is an edge between nodes `a_i` and `b_i` in the tree.
* 
* At every node `I`, there is a gate. You are also given an array of even integers `amount`, where `amount[i]` represents:
* 
* • the price needed to open the gate at node `I`, if `amount[i]` is negative, or,
* • the cash reward obtained on opening the gate at node `i`, otherwise.
* 
* The game goes on as follows:
* • Initially, Alice is at node `0` and Bob is at node `bob`.
* • At every second, Alice and Bob each move to an adjacent node. Alice moves towards some leaf node, while Bob moves towards node `0`.
* • For every node along their path, Alice and Bob either spend money to open the gate at that node, or accept the reward. Note that:
* --• If the gate is already open, no price will be required, nor will there be any cash reward.
* --• If Alice and Bob reach the node simultaneously, they share the price/reward for opening the gate there. In other words, if the price to open the gate is `c`, then both Alice and Bob pay `c / 2` each. Similarly, if the reward at the gate is `c`, both of them receive `c / 2` each.
* • If Alice reaches a leaf node, she stops moving. Similarly, if Bob reaches node `0`, he stops moving. Note that these events are independent of each other.
* 
* Return the maximum net income Alice can have if she travels towards the optimal leaf node.
* 
* Example 1:
* [image: https://assets.leetcode.com/uploads/2022/10/29/eg1.png]
* Input: edges = [[0,1],[1,2],[1,3],[3,4]], bob = 3, amount = [-2,4,2,-4,6]
* Output: 6
* Explanation:
* The above diagram represents the given tree. The game goes as follows:
* - Alice is initially on node 0, Bob on node 3. They open the gates of their respective nodes.
* Alice's net income is now -2.
* - Both Alice and Bob move to node 1.
* Since they reach here simultaneously, they open the gate together and share the reward.
* Alice's net income becomes -2 + (4 / 2) = 0.
* - Alice moves on to node 3. Since Bob already opened its gate, Alice's income remains unchanged.
* Bob moves on to node 0, and stops moving.
* - Alice moves on to node 4 and opens the gate there. Her net income becomes 0 + 6 = 6.
* Now, neither Alice nor Bob can make any further moves, and the game ends.
* It is not possible for Alice to get a higher net income.
* 
* Example 2:
* [image: https://assets.leetcode.com/uploads/2022/10/29/eg2.png]
* Input: edges = [[0,1]], bob = 1, amount = [-7280,2350]
* Output: -7280
* Explanation:
* Alice follows the path 0->1 whereas Bob follows the path 1->0.
* Thus, Alice opens the gate at node 0 only. Hence, her net income is -7280.
* 
* Constraints:
* 
* • 2 <= n <= 10^5
* • edges.length == n - 1
* • edges[i].length == 2
* • 0 <= a_i, b_i < n
* • a_i != b_i
* • `edges` represents a valid tree.
* • 1 <= bob < n
* • amount.length == n
* • `amount[i]` is an even integer in the range `[-10^4, 10^4]`
* 
****************************************/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

class Solution {
    // This solution builds a tree from the given edges and finds Bob's path to node 0
    // using DFS. Then, it uses BFS to traverse Alice's path, calculating her income
    // at each node based on when Bob arrives. Alice maximizes income by exploring
    // all paths to leaf nodes. The solution runs in O(N) time and space, as each
    // node and edge is processed at most once.
    private Map<Integer, Integer> bobPath;
    private boolean[] visited;
    private List<List<Integer>> tree;

    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        int n = amount.length;
        tree = new ArrayList<>(n);
        bobPath = new HashMap<>();
        visited = new boolean[n];

        // Initialize adjacency list for tree representation
        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }

        // Build the tree from the given edges
        for (int[] edge : edges) {
            tree.get(edge[0]).add(edge[1]);
            tree.get(edge[1]).add(edge[0]);
        }

        // Find Bob's path to node 0 using DFS
        findBobPath(bob, 0);

        // Use BFS to determine Alice's most profitable path
        return findMaxProfit(amount);
    }

    private boolean findBobPath(int node, int time) {
        bobPath.put(node, time);
        visited[node] = true;

        if (node == 0) return true; // Bob has reached node 0

        for (int neighbor : tree.get(node)) {
            if (!visited[neighbor] && findBobPath(neighbor, time + 1)) {
                return true;
            }
        }

        bobPath.remove(node); // Not part of Bob's final path
        return false;
    }

    private int findMaxProfit(int[] amount) {
        Arrays.fill(visited, false);
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0, 0, 0}); // {node, time, income}
        int maxIncome = Integer.MIN_VALUE;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int node = curr[0], time = curr[1], income = curr[2];

            // Determine Alice's earnings at this node
            if (!bobPath.containsKey(node) || time < bobPath.get(node)) {
                income += amount[node]; // Alice arrives first
            } else if (time == bobPath.get(node)) {
                income += amount[node] / 2; // Alice and Bob arrive together
            }

            // Check if this is a leaf node (excluding the root)
            if (tree.get(node).size() == 1 && node != 0) {
                maxIncome = Math.max(maxIncome, income);
            }

            // Explore unvisited neighbors
            for (int neighbor : tree.get(node)) {
                if (!visited[neighbor]) {
                    queue.add(new int[] {neighbor, time + 1, income});
                }
            }

            visited[node] = true; // Mark node as visited
        }

        return maxIncome;
    }
}
