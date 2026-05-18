// Source: https://leetcode.com/problems/jump-game-iv/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-17
// At the time of submission:
//   Runtime 42 ms Beats 99.40%
//   Memory 75.49 MB Beats 92.64%

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    // BFS from n-1 to 0 using adjacent and same-value jumps.
    // Map each value to its indices, then remove buckets after use
    // so equal-value edges are processed only once globally.
    // Time: O(n), Space: O(n)
    public int minJumps(int[] arr) {
        int n = arr.length;

        if (n == 1) {
            return 0;
        }

        // Map value -> all indices containing that value
        HashMap<Integer, List<Integer>> valueToIndices = new HashMap<>();

        for (int i = 0; i < n; i++) {
            if (!valueToIndices.containsKey(arr[i])) {
                valueToIndices.put(arr[i], new ArrayList<>());
            }

            valueToIndices.get(arr[i]).add(i);
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];

        // Start BFS from target index
        queue.add(n - 1);
        visited[n - 1] = true;

        int steps = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            steps++;

            for (int i = 0; i < levelSize; i++) {
                int index = queue.poll();

                // Jump to index - 1
                int next = index - 1;

                if (next == 0) {
                    return steps;
                }

                if (next >= 0 && !visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }

                // Jump to index + 1
                next = index + 1;

                if (next == 0) {
                    return steps;
                }

                if (next < n && !visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }

                // Jump to same-value indices
                List<Integer> sameValueIndices =
                    valueToIndices.get(arr[index]);

                if (sameValueIndices != null) {
                    for (int sameIndex : sameValueIndices) {
                        if (!visited[sameIndex]) {
                            if (sameIndex == 0) {
                                return steps;
                            }

                            visited[sameIndex] = true;
                            queue.add(sameIndex);
                        }
                    }
                }

                // Prevent reprocessing same-value group
                valueToIndices.remove(arr[index]);
            }
        }

        return -1;
    }
}