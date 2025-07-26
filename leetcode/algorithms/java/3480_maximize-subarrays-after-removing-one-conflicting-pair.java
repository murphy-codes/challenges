// Source: https://leetcode.com/problems/maximize-subarrays-after-removing-one-conflicting-pair/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-26
// At the time of submission:
//   Runtime 15 ms Beats 100.00%
//   Memory 140.22 MB Beats 17.95%

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

class Solution {
    // For each a, track its two smallest conflicting b's.
    // Sweep from n → 1, tracking the most restrictive conflict window.
    // For each i, count valid subarrays [i..r) that don’t include any conflict.
    // Try removing the tightest conflict to see if we can unlock more subarrays.
    // Time: O(n + m), Space: O(n + m) — extremely fast with no nested loops.
    public long maxSubarrays(int n, int[][] conflictingPairs) {
        int[] minConflictB = new int[n + 1];
        int[] secondMinConflictB = new int[n + 1];
        Arrays.fill(minConflictB, Integer.MAX_VALUE);
        Arrays.fill(secondMinConflictB, Integer.MAX_VALUE);

        // Track the two smallest conflicting 'b' values for each 'a'
        for (int[] pair : conflictingPairs) {
            int a = Math.min(pair[0], pair[1]);
            int b = Math.max(pair[0], pair[1]);
            if (minConflictB[a] > b) {
                secondMinConflictB[a] = minConflictB[a];
                minConflictB[a] = b;
            } else if (secondMinConflictB[a] > b) {
                secondMinConflictB[a] = b;
            }
        }

        long totalValid = 0;
        int bestA = n;
        int secondBestB = Integer.MAX_VALUE;
        long[] gainIfRemove = new long[n + 1];

        for (int i = n; i >= 1; i--) {
            // Maintain the most restrictive 'a' (bestA) and second best 'b'
            if (minConflictB[bestA] > minConflictB[i]) {
                secondBestB = Math.min(secondBestB, minConflictB[bestA]);
                bestA = i;
            } else {
                secondBestB = Math.min(secondBestB, minConflictB[i]);
            }

            int validEnd = Math.min(minConflictB[bestA], n + 1);
            totalValid += validEnd - i;

            int altEnd = Math.min(Math.min(secondBestB, secondMinConflictB[bestA]), n + 1);
            gainIfRemove[bestA] += altEnd - validEnd;
        }

        long maxGain = 0;
        for (long gain : gainIfRemove) {
            maxGain = Math.max(maxGain, gain);
        }

        return totalValid + maxGain;
    }
}
