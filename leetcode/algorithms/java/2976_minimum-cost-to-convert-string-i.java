// Source: https://leetcode.com/problems/minimum-cost-to-convert-string-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-28
// At the time of submission:
//   Runtime 12 ms Beats 100.00%
//   Memory 48.10 MB Beats 49.82%

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

import java.util.Arrays;

class Solution {
    // Model character conversions as a directed graph with 26 nodes.
    // Use Floyd–Warshall to compute all-pairs minimum conversion costs.
    // Each character in source is converted independently to target.
    // If any conversion is unreachable, return -1.
    // Time: O(26^3 + n), Space: O(26^2)
    public long minimumCost(
        String source,
        String target,
        char[] original,
        char[] changed,
        int[] cost
    ) {
        int[][] dist = new int[26][26];

        // Initialize distance matrix
        for (int i = 0; i < 26; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
        }

        // Keep minimum direct conversion cost per (from -> to)
        for (int i = 0; i < cost.length; i++) {
            int from = original[i] - 'a';
            int to = changed[i] - 'a';
            dist[from][to] = Math.min(dist[from][to], cost[i]);
        }

        // Floyd–Warshall: all-pairs shortest paths
        for (int via = 0; via < 26; via++) {
            for (int from = 0; from < 26; from++) {
                if (dist[from][via] == Integer.MAX_VALUE) continue;
                for (int to = 0; to < 26; to++) {
                    if (dist[via][to] == Integer.MAX_VALUE) continue;
                    dist[from][to] = Math.min(
                        dist[from][to],
                        dist[from][via] + dist[via][to]
                    );
                }
            }
        }

        // Sum conversion costs for each character position
        long totalCost = 0L;
        for (int i = 0; i < source.length(); i++) {
            int from = source.charAt(i) - 'a';
            int to = target.charAt(i) - 'a';
            if (dist[from][to] == Integer.MAX_VALUE) {
                return -1L;
            }
            totalCost += dist[from][to];
        }

        return totalCost;
    }
}
