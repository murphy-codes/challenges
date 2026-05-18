// Source: https://leetcode.com/problems/jump-game-iv/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-17
// At the time of submission:
//   Runtime 48 ms Beats 87.48%
//   Memory 75.80 MB Beats 90.46%

/****************************************
* 
* Given an array of integers `arr`, you are initially positioned at the first
* _ index of the array.
* In one step you can jump from index `i` to index:
* • `i + 1` where: `i + 1 < arr.length`.
* • `i - 1` where: `i - 1 >= 0`.
* • `j` where: `arr[i] == arr[j]` and `i != j`.
* Return the minimum number of steps to reach the last index of the array.
* Notice that you can not jump outside of the array at any time.
*
* Example 1:
* Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
* Output: 3
* Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. 
* Note that index 9 is the last index of the array.
*
* Example 2:
* Input: arr = [7]
* Output: 0
* Explanation: Start index is the last index. You do not need to jump.
*
* Example 3:
* Input: arr = [7,6,9,6,9,6,9,7]
* Output: 1
* Explanation: You can jump directly from index 0 to index 7 
* which is last index of the array.
*
* Constraints:
* • `1 <= arr.length <= 5 * 10^4`
* • `-10^8 <= arr[i] <= 10^8`
* 
****************************************/

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Solution {
    // BFS over indices with edges to neighbors and equal-value indices.
    // Map each value to its indices for O(1) access to same-value jumps.
    // Clear processed value groups to avoid redundant traversals.
    // Time: O(n), each index/group processed once; Space: O(n).
    public int minJumps(int[] arr) {
        int n = arr.length;

        if (n == 1) {
            return 0;
        }

        Map<Integer, List<Integer>> indicesByValue = new HashMap<>();

        for (int i = 0; i < n; i++) {
            indicesByValue
                .computeIfAbsent(arr[i], k -> new ArrayList<>())
                .add(i);
        }

        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n];

        queue.offer(0);
        visited[0] = true;

        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int curr = queue.poll();

                if (curr == n - 1) {
                    return steps;
                }

                // Jump to left neighbor
                int left = curr - 1;
                if (left >= 0 && !visited[left]) {
                    visited[left] = true;
                    queue.offer(left);
                }

                // Jump to right neighbor
                int right = curr + 1;
                if (right < n && !visited[right]) {
                    visited[right] = true;
                    queue.offer(right);
                }

                // Jump to same-value indices
                List<Integer> sameValue =
                    indicesByValue.get(arr[curr]);

                if (sameValue != null) {
                    for (int next : sameValue) {
                        if (!visited[next]) {
                            visited[next] = true;
                            queue.offer(next);
                        }
                    }

                    // Prevent repeated scans of same group
                    indicesByValue.remove(arr[curr]);
                }
            }

            steps++;
        }

        return -1;
    }
}