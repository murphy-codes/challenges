// Source: https://leetcode.com/problems/zero-array-transformation-iii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-21
// At the time of submission:
//   Runtime 79 ms Beats 34.81%
//   Memory 92.98 MB Beats 70.89%

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
import java.util.PriorityQueue;
import java.util.Collections;

class Solution {
    // Traverse nums left to right, applying minimum necessary queries.
    // Use a max-heap to greedily apply the queries with largest right
    // endpoints that cover current index. Track active operations using
    // a difference array. Queries not used in this process are removable.
    // Time: O(n + m * log m), Space: O(n + m)
    public int maxRemoval(int[] nums, int[][] queries) {
        // Sort queries by left endpoint
        Arrays.sort(queries, (a, b) -> Integer.compare(a[0], b[0]));

        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        int[] deltaArray = new int[nums.length + 1];
        int operations = 0;

        for (int i = 0, j = 0; i < nums.length; i++) {
            operations += deltaArray[i];

            // Push all queries with left endpoint == i into the heap
            while (j < queries.length && queries[j][0] == i) {
                heap.offer(queries[j][1]);
                j++;
            }

            // Add enough queries to satisfy nums[i] using largest right endpoints
            while (operations < nums[i] && !heap.isEmpty() && heap.peek() >= i) {
                int r = heap.poll();
                operations += 1;
                deltaArray[r + 1] -= 1;
            }

            // Not enough operations available — nums[i] can't be reduced
            if (operations < nums[i]) {
                return -1;
            }
        }

        // Remaining queries in heap were not used — can be removed
        return heap.size();
    }
}
