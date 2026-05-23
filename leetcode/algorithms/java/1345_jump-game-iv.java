// Source: https://leetcode.com/problems/jump-game-iv/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-22
// At the time of submission:
//   Runtime 7 ms Beats 100.00%
//   Memory 63.76 MB Beats 95.03%

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

import java.util.Arrays;

public class Solution {
    // Bidirectional BFS from both ends for minimum jumps in implicit graph.
    // Uses custom primitive hash map to group indices by equal values.
    // Each value group is processed once to avoid O(n^2) expansion.
    // Time: O(n), Space: O(n)
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n <= 1) return 0;

        // -------- FAST PRIMITIVE HASH MAP SETUP --------
        int capacity = 1;
        while (capacity < n * 2) capacity <<= 1;
        int mask = capacity - 1;

        int[] keyAt = new int[capacity];
        int[] bucketIdAt = new int[capacity];
        Arrays.fill(bucketIdAt, -1);

        int[] nextIndex = new int[n];
        int bucketCount = 0;

        int[] indexToBucket = new int[n];

        // Build value → bucket mapping
        for (int i = 0; i < n; i++) {
            int val = arr[i];
            int hash = (val * 0x9E3779B9) & mask;

            while (bucketIdAt[hash] != -1 && keyAt[hash] != val) {
                hash = (hash + 1) & mask;
            }

            if (bucketIdAt[hash] == -1) {
                keyAt[hash] = val;
                bucketIdAt[hash] = bucketCount++;
            }

            indexToBucket[i] = bucketIdAt[hash];
        }

        // -------- BUILD BUCKET LINKED LISTS --------
        int[] head = new int[bucketCount];
        Arrays.fill(head, -1);

        for (int i = 0; i < n; i++) {
            int b = indexToBucket[i];
            nextIndex[i] = head[b];
            head[b] = i;
        }

        // -------- BIDIRECTIONAL BFS --------
        int[] queue = new int[n];
        int[] visited = new int[n]; // 0=unvisited,1=start,2=end

        int startL = 0, startR = 0;
        int endL = n - 1, endR = n - 1;

        queue[startR++] = 0;
        visited[0] = 1;

        queue[endR--] = n - 1;
        visited[n - 1] = 2;

        int steps = 0;

        while (startL < startR && endL > endR) {
            int sizeStart = startR - startL;
            int sizeEnd = endL - endR;

            if (sizeStart <= sizeEnd) {
                for (int i = 0; i < sizeStart; i++) {
                    int curr = queue[startL++];
                    int b = indexToBucket[curr];

                    // same-value jumps
                    if (head[b] != -1) {
                        int idx = head[b];
                        while (idx != -1) {
                            if (visited[idx] == 2) return steps + 1;
                            if (visited[idx] == 0) {
                                visited[idx] = 1;
                                queue[startR++] = idx;
                            }
                            idx = nextIndex[idx];
                        }
                        head[b] = -1;
                    }

                    // i+1
                    if (curr + 1 < n) {
                        if (visited[curr + 1] == 2) return steps + 1;
                        if (visited[curr + 1] == 0) {
                            visited[curr + 1] = 1;
                            queue[startR++] = curr + 1;
                        }
                    }
                    // i-1
                    if (curr - 1 >= 0) {
                        if (visited[curr - 1] == 2) return steps + 1;
                        if (visited[curr - 1] == 0) {
                            visited[curr - 1] = 1;
                            queue[startR++] = curr - 1;
                        }
                    }
                }
            } else {
                for (int i = 0; i < sizeEnd; i++) {
                    int curr = queue[endL--];
                    int b = indexToBucket[curr];

                    if (head[b] != -1) {
                        int idx = head[b];
                        while (idx != -1) {
                            if (visited[idx] == 1) return steps + 1;
                            if (visited[idx] == 0) {
                                visited[idx] = 2;
                                queue[endR--] = idx;
                            }
                            idx = nextIndex[idx];
                        }
                        head[b] = -1;
                    }

                    if (curr + 1 < n) {
                        if (visited[curr + 1] == 1) return steps + 1;
                        if (visited[curr + 1] == 0) {
                            visited[curr + 1] = 2;
                            queue[endR--] = curr + 1;
                        }
                    }

                    if (curr - 1 >= 0) {
                        if (visited[curr - 1] == 1) return steps + 1;
                        if (visited[curr - 1] == 0) {
                            visited[curr - 1] = 2;
                            queue[endR--] = curr - 1;
                        }
                    }
                }
            }

            steps++;
        }

        return -1;
    }
}
