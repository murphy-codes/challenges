// Source: https://leetcode.com/problems/maximize-subarrays-after-removing-one-conflicting-pair/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-26
// At the time of submission:
//   Runtime 67 ms Beats 46.15%
//   Memory 114.93 MB Beats 87.18%

/****************************************
* 
* You are given an integer `n` which represents an array `nums` containing the
* _ numbers from 1 to `n` in order. Additionally, you are given a 2D array
* _ `conflictingPairs`, where `conflictingPairs[i] = [a, b]` indicates that
* _ `a` and `b` form a conflicting pair.
* Remove exactly one element from `conflictingPairs`. Afterward, count the number
* _ of non-empty subarrays of `nums` which do not contain both `a` and `b` for
* _ any remaining conflicting pair `[a, b]`.
* Return the maximum number of subarrays possible after removing exactly one
* _ conflicting pair.
*
* Example 1:
* Input: n = 4, conflictingPairs = [[2,3],[1,4]]
* Output: 9
* Explanation:
* • Remove `[2, 3]` from `conflictingPairs`. Now, `conflictingPairs = [[1, 4]]`.
* • There are 9 subarrays in `nums` where `[1, 4]` do not appear together. They are `[1]`, `[2]`, `[3]`, `[4]`, `[1, 2]`, `[2, 3]`, `[3, 4]`, `[1, 2, 3]` and `[2, 3, 4]`.
* • The maximum number of subarrays we can achieve after removing one element from `conflictingPairs` is 9.
*
* Example 2:
* Input: n = 5, conflictingPairs = [[1,2],[2,5],[3,5]]
* Output: 12
* Explanation:
* • Remove `[1, 2]` from `conflictingPairs`. Now, `conflictingPairs = [[2, 5], [3, 5]]`.
* • There are 12 subarrays in `nums` where `[2, 5]` and `[3, 5]` do not appear together.
* • The maximum number of subarrays we can achieve after removing one element from `conflictingPairs` is 12.
*
* Constraints:
* • 2 <= n <= 10^5
* • 1 <= conflictingPairs.length <= 2 * n
* • conflictingPairs[i].length == 2
* • 1 <= conflictingPairs[i][j] <= n
* • conflictingPairs[i][0] != conflictingPairs[i][1]
* 
****************************************/

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    // For each subarray ending at r, track the highest conflicting left endpoint 
    // L (& 2nd). Total valid subarrays ending at r = (r − maxLeft). If we remove 
    // the restrictive pair at maxLeft, we gain (maxLeft − secondMaxLeft) extra valid
    // subarrays. Compute base valid count + max gain across all positions. 
    // Time O(n+m), space O(n+m).
    public long maxSubarrays(int n, int[][] conflictingPairs) {
        long validSubarrays = 0;
        int maxLeft = 0, secondMaxLeft = 0;
        long[] gains = new long[n + 1];
        @SuppressWarnings("unchecked")
        List<Integer>[] conflicts = new List[n + 1];
        for (int i = 0; i <= n; i++) conflicts[i] = new ArrayList<>();

        for (int[] p : conflictingPairs) {
            int a = p[0], b = p[1];
            conflicts[Math.max(a, b)].add(Math.min(a, b));
        }

        for (int right = 1; right <= n; right++) {
            for (int left : conflicts[right]) {
                if (left > maxLeft) {
                    secondMaxLeft = maxLeft;
                    maxLeft = left;
                } else if (left > secondMaxLeft) {
                    secondMaxLeft = left;
                }
            }
            // count valid subarrays ending at 'right'
            validSubarrays += right - maxLeft;
            // if removing the most restrictive conflict at left = maxLeft,
            // we can gain extra (maxLeft - secondMaxLeft) new subarrays
            gains[maxLeft] += (maxLeft - secondMaxLeft);
        }

        long maxGain = Arrays.stream(gains).max().orElse(0L);
        return validSubarrays + maxGain;
    }
}
