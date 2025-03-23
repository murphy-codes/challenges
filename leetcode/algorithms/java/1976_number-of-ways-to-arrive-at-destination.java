// Source: https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-23

/****************************************
* 
* You are in a city that consists of `n` intersections numbered from `0` to `n - 1`
* with bi-directional roads between some intersections. The inputs are generated
* such that you can reach any intersection from any other intersection and that
* there is at most one road between any two intersections.
* You are given an integer `n` and a 2D integer array `roads` where
* `roads[i] = [u_i, v_i, time_i]` means that there is a road between intersections
* `u_i` and `v_i` that takes `time_i` minutes to travel. You want to know in how
* many ways you can travel from intersection `0` to intersection `n - 1` in
* the shortest amount of time.
* Return the number of ways you can arrive at your destination in the shortest
* amount of time. Since the answer may be large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
* Output: 4
* Explanation: The shortest amount of time it takes to go from intersection 0 to intersection 6 is 7 minutes.
* The four ways to get there in 7 minutes are:
* - 0 ➝ 6
* - 0 ➝ 4 ➝ 6
* - 0 ➝ 1 ➝ 2 ➝ 5 ➝ 6
* - 0 ➝ 1 ➝ 3 ➝ 5 ➝ 6
*
* Example 2:
* Input: n = 2, roads = [[1,0,10]]
* Output: 1
* Explanation: There is only one way to go from intersection 0 to intersection 1, and it takes 10 minutes.
*
* Constraints:
* • 1 <= n <= 200
* • n - 1 <= roads.length <= n * (n - 1) / 2
* • roads[i].length == 3
* • 0 <= u_i, v_i <= n - 1
* • 1 <= time_i <= 10^9
* • u_i != v_i
* • There is at most one road connecting any two intersections.
* • You can reach any intersection from any other intersection.
* 
****************************************/

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Arrays;

class Solution {
    public int countPaths(int n, int[][] roads) {
        final int MOD = 1_000_000_007;
        
        // Graph representation using adjacency list
        HashMap<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());
        
        for (int[] road : roads) {
            graph.get(road[0]).add(new int[]{road[1], road[2]});
            graph.get(road[1]).add(new int[]{road[0], road[2]});
        }
        
        // Min-heap priority queue: {distance, node}
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.offer(new long[]{0, 0}); // {distance, node}
        
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        
        int[] ways = new int[n];
        ways[0] = 1;
        
        while (!pq.isEmpty()) {
            long[] current = pq.poll();
            long time = current[0];
            int node = (int) current[1];
            
            if (time > dist[node]) continue; // Skip outdated distances
            
            for (int[] neighbor : graph.get(node)) {
                int nextNode = neighbor[0];
                long newTime = time + neighbor[1];
                
                if (newTime < dist[nextNode]) {
                    dist[nextNode] = newTime;
                    ways[nextNode] = ways[node]; // Reset count for new shortest path
                    pq.offer(new long[]{newTime, nextNode});
                } else if (newTime == dist[nextNode]) {
                    ways[nextNode] = (ways[nextNode] + ways[node]) % MOD; // Add count
                }
            }
        }
        
        return ways[n - 1];
    }
}
