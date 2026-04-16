// Source: https://leetcode.com/problems/closest-equal-element-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-15
// At the time of submission:
//   Runtime 110 ms Beats 54.55%
//   Memory 186.78 MB Beats 62.63%

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
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

class Solution {
    // We map each value to a sorted list of its indices. For each query,
    // we binary search to find the current index within its value's list,
    // then check the closest neighbors in that list. Since the array is
    // circular, distance is computed as min(|i-j|, n-|i-j|). Each query
    // runs in O(log n), total time is O(n + q log n), space is O(n).
    public List<Integer> solveQueries(int[] nums, int[] queries) {

        int n = nums.length;

        // Step 1: map value -> list of indices
        HashMap<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        List<Integer> result = new ArrayList<>();

        // Step 2: process each query
        for (int q : queries) {

            int val = nums[q];
            List<Integer> list = map.get(val);

            if (list.size() == 1) {
                result.add(-1);
                continue;
            }

            // Step 3: binary search
            int pos = Collections.binarySearch(list, q);

            int size = list.size();

            // neighbors (circular in the list)
            int leftIdx = list.get((pos - 1 + size) % size);
            int rightIdx = list.get((pos + 1) % size);

            // Step 4: compute circular distances
            int leftDist = Math.abs(q - leftIdx);
            leftDist = Math.min(leftDist, n - leftDist);

            int rightDist = Math.abs(q - rightIdx);
            rightDist = Math.min(rightDist, n - rightDist);

            result.add(Math.min(leftDist, rightDist));
        }

        return result;
    }
}