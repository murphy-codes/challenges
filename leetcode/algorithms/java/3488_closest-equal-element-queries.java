// Source: https://leetcode.com/problems/closest-equal-element-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-15
// At the time of submission:
//   Runtime 47 ms Beats 98.99%
//   Memory 157.30 MB Beats 100.00%

/****************************************
* 
* You are given a circular array `nums` and an array `queries`.
* For each query `i`, you have to find the following:
* • The minimum distance between the element at index `queries[i]` and any
* __ other index `j` in the circular array, where `nums[j] == nums[queries[i]]`.
* __ If no such index exists, the answer for that query should be `-1`.
* Return an `array` answer of the same size as `queries`, where `answer[i]`
* _ represents the result for query `i`.
*
* Example 1:
* Input: nums = [1,3,1,4,1,3,2], queries = [0,3,5]
* Output: [2,-1,3]
* Explanation:
* • Query 0: The element at `queries[0] = 0` is `nums[0] = 1`. The nearest index with the same value is 2, and the distance between them is 2.
* • Query 1: The element at `queries[1] = 3` is ``nums[3] = 4`. No other index contains 4, so the result is -1.
* • Query 2: The element at `queries[2] = 5` is `nums[5] = 3`. The nearest index with the same value is 1, and the distance between them is 3 (following the circular path: 5 -> 6 -> 0 -> 1`).
*
* Example 2:
* Input: nums = [1,2,3,4], queries = [0,1,2,3]
* Output: [-1,-1,-1,-1]
* Explanation:
* Each value in `nums` is unique, so no index shares the same value as the queried element. This results in -1 for all queries.
*
* Constraints:
* • `1 <= queries.length <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^6`
* • `0 <= queries[i] < nums.length`
* 
****************************************/

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

class Solution {
    // We track the first and most recent occurrences of each value while
    // iterating through the array. For each new occurrence, we update the
    // minimum circular distances for the previous, first, and current
    // indices. This effectively links occurrences in a circular manner.
    // After preprocessing in O(n), each query is answered in O(1).

    // Distance moving clockwise from 'from' to 'to'
    private int circularDistance(int from, int to, int n) {
        if (from <= to) return to - from;
        return n - from + to;
    }

    public List<Integer> solveQueries(int[] nums, int[] queries) {

        int n = nums.length;

        int[] minDistances = new int[n];
        Map<Integer, int[]> lastSeen = new HashMap<>();

        for (int i = 0; i < n; i++) {

            minDistances[i] = Integer.MAX_VALUE;

            int val = nums[i];

            if (lastSeen.containsKey(val)) {

                int firstIdx = lastSeen.get(val)[0];
                int prevIdx  = lastSeen.get(val)[1];

                // Update previous occurrence
                minDistances[prevIdx] = Math.min(
                    minDistances[prevIdx],
                    circularDistance(prevIdx, i, n)
                );

                // Update first occurrence (wrap-around)
                minDistances[firstIdx] = Math.min(
                    minDistances[firstIdx],
                    circularDistance(i, firstIdx, n)
                );

                // Update current index
                minDistances[i] = Math.min(
                    circularDistance(prevIdx, i, n),
                    circularDistance(i, firstIdx, n)
                );

                // Update last seen indices
                lastSeen.put(val, new int[]{firstIdx, i});

            } else {
                lastSeen.put(val, new int[]{i, i});
            }
        }

        List<Integer> result = new ArrayList<>();

        for (int q : queries) {
            result.add(minDistances[q] == Integer.MAX_VALUE ? -1 : minDistances[q]);
        }

        return result;
    }
}