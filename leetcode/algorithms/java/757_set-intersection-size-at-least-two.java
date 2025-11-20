// Source: https://leetcode.com/problems/set-intersection-size-at-least-two/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-19
// At the time of submission:
//   Runtime 7 ms Beats 97.96%
//   Memory 47.79 MB Beats 11.56%

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
    // Pack each interval as {end, -start} and sort by end asc, start desc. Maintain
    // the last two chosen numbers ensuring each interval contains at least two. If
    // both already inside, skip; if one inside, add one; otherwise add two. This
    // greedy choice keeps future intervals flexible. Time O(n log n), space O(1).
    public int intersectionSizeTwo(int[][] intervals) {
        int m = intervals.length;

        // Pack each interval into a single long:
        // high 32 bits = end, low 32 bits = -start
        long[] packed = new long[m];
        int idx = 0;

        for (int[] interval : intervals) {
            long end = (long) interval[1] << 32;
            long negStart = -(long) interval[0] & 0xFFFFFFFFL;
            packed[idx++] = end | negStart;
        }

        // Sort by end asc, and if tie, start desc
        Arrays.sort(packed);

        int secondLast = -2; // second most recent chosen value
        int last = -1;       // most recent chosen value
        int answer = 0;

        for (long packedValue : packed) {

            // Unpack (low 32 bits = -start)
            int start = -(int) packedValue;
            int end = (int) (packedValue >> 32);

            // Case 1: this interval already has two chosen values
            if (start <= secondLast) {
                continue;
            }

            // Case 2: exactly one chosen value fits
            if (start <= last) {
                answer += 1;
                secondLast = last;
                last = end;
            }

            // Case 3: none fit → must choose two values
            else {
                answer += 2;
                secondLast = end - 1;
                last = end;
            }
        }

        return answer;
    }
}
