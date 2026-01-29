// Source: https://leetcode.com/problems/minimum-cost-to-convert-string-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-28
// At the time of submission:
//   Runtime 24 ms Beats 56.27%
//   Memory 48.17 MB Beats 30.82%

/****************************************
* 
* You are given two 0-indexed strings `source` and `target`, both of length `n`
* _ and consisting of lowercase English letters. You are also given two 0-indexed
* _ character arrays `original` and `changed`, and an integer array `cost`, where
* _ `cost[i]` represents the cost of changing the character `original[i]` to the
* _ character `changed[i]`.
* You start with the string `source`. In one operation, you can pick a character
* _ `x` from the string and change it to the character `y` at a cost of `z` if
* _ there exists any index `j` such that `cost[j] == z`, `original[j] == x`,
* _ and `changed[j] == y`.
* Return the minimum cost to convert the string `source` to the string `target`
* _ using any number of operations. If it is impossible to convert `source` to
* _ `target`, return `-1`.
* Note that there may exist indices `i`, `j` such that
* _ `original[j] == original[i]` and `changed[j] == changed[i]`.
*
* Example 1:
* Input: source = "abcd", target = "acbe", original = ["a","b","c","c","e","d"], changed = ["b","c","b","e","b","e"], cost = [2,5,5,1,2,20]
* Output: 28
* Explanation: To convert the string "abcd" to string "acbe":
* - Change value at index 1 from 'b' to 'c' at a cost of 5.
* - Change value at index 2 from 'c' to 'e' at a cost of 1.
* - Change value at index 2 from 'e' to 'b' at a cost of 2.
* - Change value at index 3 from 'd' to 'e' at a cost of 20.
* The total cost incurred is 5 + 1 + 2 + 20 = 28.
* It can be shown that this is the minimum possible cost.
*
* Example 2:
* Input: source = "aaaa", target = "bbbb", original = ["a","c"], changed = ["c","b"], cost = [1,2]
* Output: 12
* Explanation: To change the character 'a' to 'b' change the character 'a' to 'c' at a cost of 1, followed by changing the character 'c' to 'b' at a cost of 2, for a total cost of 1 + 2 = 3. To change all occurrences of 'a' to 'b', a total cost of 3 * 4 = 12 is incurred.
*
* Example 3:
* Input: source = "abcd", target = "abce", original = ["a"], changed = ["e"], cost = [10000]
* Output: -1
* Explanation: It is impossible to convert source to target because the value at index 3 cannot be changed from 'd' to 'e'.
*
* Constraints:
* • `1 <= source.length == target.length <= 10^5`
* • `source`, `target` consist of lowercase English letters.
* • `1 <= cost.length == original.length == changed.length <= 2000`
* • `original[i]`, `changed[i]` are lowercase English letters.
* • `1 <= cost[i] <= 10^6`
* • `original[i] != changed[i]`
* 
****************************************/

class Solution {
    // Treat each character as a node in a directed graph where edges represent
    // allowed transformations with associated costs. We precompute all-pairs
    // shortest paths using Floyd–Warshall since the graph has only 26 nodes.
    // Each character in source is converted independently using the precomputed
    // costs. If any conversion is unreachable, return -1.
    // Time Complexity: O(26^3 + n)
    // Space Complexity: O(26^2)
    public long minimumCost(
        String source,
        String target,
        char[] original,
        char[] changed,
        int[] cost
    ) {
        final int ALPHABET = 26;
        final long INF = Long.MAX_VALUE / 4;

        long[][] dist = new long[ALPHABET][ALPHABET];

        // Initialize distances
        for (int i = 0; i < ALPHABET; i++) {
            for (int j = 0; j < ALPHABET; j++) {
                dist[i][j] = (i == j) ? 0 : INF;
            }
        }

        // Build graph: keep the cheapest edge per (u, v)
        for (int i = 0; i < original.length; i++) {
            int u = original[i] - 'a';
            int v = changed[i] - 'a';
            dist[u][v] = Math.min(dist[u][v], cost[i]);
        }

        // Floyd–Warshall: all-pairs shortest paths
        for (int k = 0; k < ALPHABET; k++) {
            for (int i = 0; i < ALPHABET; i++) {
                for (int j = 0; j < ALPHABET; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        // Compute total conversion cost
        long totalCost = 0;
        for (int i = 0; i < source.length(); i++) {
            int from = source.charAt(i) - 'a';
            int to = target.charAt(i) - 'a';
            if (dist[from][to] == INF) {
                return -1;
            }
            totalCost += dist[from][to];
        }

        return totalCost;
    }
}
