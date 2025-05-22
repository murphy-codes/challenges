// Source: https://leetcode.com/problems/zero-array-transformation-iii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-21
// At the time of submission:
//   Runtime 18 ms Beats 98.73%
//   Memory 110.07 MB Beats 10.76%

/****************************************
* 
* You are given an integer array `nums` of length `n` and a 2D array `queries`
* _ where `queries[i] = [l_i, r_i]`.
* Each `queries[i]` represents the following action on `nums`:
* • Decrement the value at each index in the range `[l_i, r_i]`
* _ in `nums` by at most 1.
* • The amount by which the value is decremented can be chosen
* _ independently for each index.
* A Zero Array is an array with all its elements equal to 0.
* Return the maximum number of elements that can be removed from `queries`, such
* _ that `nums` can still be converted to a zero array using the remaining queries.
* _ If it is not possible to convert `nums` to a zero array, return -1.
*
* Example 1:
* Input: nums = [2,0,2], queries = [[0,2],[0,2],[1,1]]
* Output: 1
* Explanation:
* After removing queries[2], nums can still be converted to a zero array.
* Using queries[0], decrement nums[0] and nums[2] by 1 and nums[1] by 0.
* Using queries[1], decrement nums[0] and nums[2] by 1 and nums[1] by 0.
*
* Example 2:
* Input: nums = [1,1,1,1], queries = [[1,3],[0,2],[1,3],[1,2]]
* Output: 2
* Explanation:
* We can remove queries[2] and queries[3].
*
* Example 3:
* Input: nums = [1,2,3,4], queries = [[0,3]]
* Output: -1
* Explanation:
* nums cannot be converted to a zero array even after using all the queries.
*
* Constraints:
* • 1 <= nums.length <= 105
* • 0 <= nums[i] <= 105
* • 1 <= queries.length <= 105
* • queries[i].length == 2
* • 0 <= li <= ri < nums.length
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Greedy + Max-Heap + Difference Array solution.
    // At each index, greedily apply queries with largest right-ends
    // that cover the current index. Track active operations with a
    // difference array. Queries not used can be safely removed.
    // Time: O(n + m + m * log m)
    // Space: O(n + m)

    public int maxRemoval(int[] nums, int[][] queries) {
        int n = nums.length;
        int m = queries.length;

        // Custom max-heap for ints: avoids boxing/unboxing overhead
        IntMaxHeap maxHeap = new IntMaxHeap(m + 1);
        maxHeap.add(-1); // Sentinel value to prevent empty-heap access

        // Preprocess: For each index i, store a list of query right-ends that start at i
        int[][] rightEndsByStart = preprocessRightEnds(n, queries);

        int[] delta = new int[n + 1]; // Difference array for efficient range updates
        int usedQueries = 0;

        for (int i = 0; i < n; i++) {
            if (i > 0) {
                delta[i] += delta[i - 1]; // Apply prefix sum to get actual value at index i
            }

            // Add all queries starting at index i into the heap
            for (int r : rightEndsByStart[i]) {
                maxHeap.add(r);
            }

            // Use heap to greedily cover this index until nums[i] is satisfied
            while (delta[i] < nums[i]) {
                int end = maxHeap.poll();
                if (end < i) return -1; // Cannot cover index i with any remaining queries
                delta[i]++;
                delta[end + 1]--; // Mark the end of the operation's effect
                usedQueries++;
            }
        }

        return m - usedQueries; // Number of queries that can be removed (not used)
    }

    // Groups right-end values by their left index
    private int[][] preprocessRightEnds(int len, int[][] queries) {
        int[] counts = new int[len];
        for (int[] q : queries) {
            counts[q[0]]++;
        }

        int[][] ends = new int[len][];
        for (int i = 0; i < len; i++) {
            ends[i] = new int[counts[i]];
        }

        // Populate the 2D array in reverse (using decrement trick)
        for (int[] q : queries) {
            ends[q[0]][--counts[q[0]]] = q[1];
        }

        return ends;
    }

    // Custom Max Heap for integers — avoids autoboxing overhead of PriorityQueue<Integer>
    private static class IntMaxHeap {
        int[] heap;
        int size;

        IntMaxHeap(int capacity) {
            heap = new int[capacity];
        }

        void add(int val) {
            heap[size] = val;
            siftUp(size++);
        }

        int poll() {
            int top = heap[0];
            siftDown(0, heap[--size]);
            return top;
        }

        private void siftUp(int idx) {
            while (idx > 0) {
                int parent = (idx - 1) / 2;
                if (heap[idx] <= heap[parent]) break;
                swap(idx, parent);
                idx = parent;
            }
        }

        private void siftDown(int idx, int val) {
            while (true) {
                int left = 2 * idx + 1;
                if (left >= size) break;
                int right = left + 1;
                int largest = (right < size && heap[right] > heap[left]) ? right : left;
                if (val >= heap[largest]) break;
                heap[idx] = heap[largest];
                idx = largest;
            }
            heap[idx] = val;
        }

        private void swap(int i, int j) {
            int tmp = heap[i];
            heap[i] = heap[j];
            heap[j] = tmp;
        }
    }
}