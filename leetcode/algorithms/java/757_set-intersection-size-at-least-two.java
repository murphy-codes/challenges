// Source: https://leetcode.com/problems/set-intersection-size-at-least-two/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-19
// At the time of submission:
//   Runtime 9 ms Beats 93.20%
//   Memory 47.57 MB Beats 19.05%

/****************************************
* 
* You are given a 2D integer array `intervals` where `intervals[i] = [start_i, end_i]`
* represents all the integers from `start_i` to `end_i` inclusively.
* A containing set is an array `nums` where each interval from `intervals` has
* _ at least two integers in `nums`.
* • For example, if `intervals = [[1,3], [3,7], [8,9]]`, then `[1,2,4,7,8,9]`
* __ and `[2,3,4,8,9]` are containing sets.
* Return the minimum possible size of a containing set.
*
* Example 1:
* Input: intervals = [[1,3],[3,7],[8,9]]
* Output: 5
* Explanation: let nums = [2, 3, 4, 8, 9].
* It can be shown that there cannot be any containing array of size 4.
*
* Example 2:
* Input: intervals = [[1,3],[1,4],[2,5],[3,5]]
* Output: 3
* Explanation: let nums = [2, 3, 4].
* It can be shown that there cannot be any containing array of size 2.
*
* Example 3:
* Input: intervals = [[1,2],[2,3],[2,4],[4,5]]
* Output: 5
* Explanation: let nums = [1, 2, 3, 4, 5].
* It can be shown that there cannot be any containing array of size 4.
*
* Constraints:
* • `1 <= intervals.length <= 3000`
* • `intervals[i].length == 2`
* • `0 <= start_i < end_i <= 10^8`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Greedy solution: sort intervals by end asc, start desc. For each interval,
    // ensure it has at least two chosen numbers. Keep track of the two largest
    // previously chosen values and add minimal new values (near interval end) to
    // satisfy the requirement. Time: O(n log n) for sorting; space: O(1) extra.
    public int intersectionSizeTwo(int[][] intervals) {

        // Sort intervals by end asc, start desc
        Arrays.sort(intervals, (a, b) ->
            a[1] != b[1] ? Integer.compare(a[1], b[1]) 
                         : Integer.compare(b[0], a[0])
        );

        int count = 0;

        // p1 = largest selected element so far
        // p2 = second largest selected element
        int p1 = -1;
        int p2 = -1;

        for (int[] interval : intervals) {
            int start = interval[0];
            int end   = interval[1];

            // Case 1: both already inside interval
            if (p2 >= start) {
                continue;
            }

            // Case 2: only p1 is inside, add one number
            if (p1 >= start) {
                count += 1;
                p2 = p1;
                p1 = end;
            }

            // Case 3: none inside, add two numbers
            else {
                count += 2;
                p2 = end - 1;
                p1 = end;
            }
        }

        return count;
    }
}
