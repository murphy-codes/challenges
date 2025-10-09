// Source: https://leetcode.com/problems/avoid-flood-in-the-city/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-06
// At the time of submission:
//   Runtime 24 ms Beats 99.09%
//   Memory 63.49 MB Beats 19.18%

/****************************************
* 
* Your country has an infinite number of lakes. Initially, all the lakes are
* _ empty, but when it rains over the `nth` lake, the `nth` lake becomes full of
* _ water. If it rains over a lake that is full of water, there will be a flood.
* _ Your goal is to avoid floods in any lake.
* Given an integer array `rains` where:
* • `rains[i] > 0` means there will be rains over the `rains[i]` lake.
* • `rains[i] == 0` means there are no rains this day and you can choose one
* __ lake this day and dry it.
* Return an array `ans` where:
* • `ans.length == rains.length`
* • `ans[i] == -1` if `rains[i] > 0`.
* • `ans[i]` is the lake you choose to dry in the `ith` day if `rains[i] == 0`.
* If there are multiple valid answers return any of them. If it is impossible to
* _ avoid flood return an empty array.
* Notice that if you chose to dry a full lake, it becomes empty, but if you
* _ chose to dry an empty lake, nothing changes.
*
* Example 1:
* Input: rains = [1,2,3,4]
* Output: [-1,-1,-1,-1]
* Explanation: After the first day full lakes are [1]
* After the second day full lakes are [1,2]
* After the third day full lakes are [1,2,3]
* After the fourth day full lakes are [1,2,3,4]
* There's no day to dry any lake and there is no flood in any lake.
*
* Example 2:
* Input: rains = [1,2,0,0,2,1]
* Output: [-1,-1,2,1,-1,-1]
* Explanation: After the first day full lakes are [1]
* After the second day full lakes are [1,2]
* After the third day, we dry lake 2. Full lakes are [1]
* After the fourth day, we dry lake 1. There is no full lakes.
* After the fifth day, full lakes are [2].
* After the sixth day, full lakes are [1,2].
* It is easy that this scenario is flood-free. [-1,-1,1,2,-1,-1] is another acceptable scenario.
*
* Example 3:
* Input: rains = [1,2,0,1,2]
* Output: []
* Explanation: After the second day, full lakes are  [1,2]. We have to dry one lake in the third day.
* After that, it will rain over lakes [1,2]. It's easy to prove that no matter which lake you choose to dry in the 3rd day, the other one will flood.
*
* Constraints:
* • `1 <= rains.length <= 10^5`
* • `0 <= rains[i] <= 10^9`
* 
****************************************/

import java.util.HashMap;
import java.util.Map;

class Solution {
    // Greedy + Union-Find solution.
    // Tracks last rain day per lake and uses Union-Find to quickly locate the
    // next available dry day after that rain. When a lake rains again, we must
    // find and assign an earlier unused dry day to empty it; otherwise, flood
    // occurs. Runs in O(n) amortized time and O(n) space.
    public int[] avoidFlood(int[] rains) {
        int n = rains.length;
        int[] parent = new int[n + 1]; // Union-Find parent array
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }

        int[] ans = new int[n];
        Map<Integer, Integer> lastRainDay = new HashMap<>(); // lake -> last rain index

        for (int i = 0; i < n; i++) {
            int lake = rains[i];
            if (lake == 0) {
                // Arbitrary default value; may be updated later when assigned
                ans[i] = 1;
                continue;
            }

            Integer prevRainDay = lastRainDay.get(lake);
            if (prevRainDay != null) {
                // Find earliest available dry day after last rain of this lake
                int dryDay = find(prevRainDay + 1, parent);
                if (dryDay >= i) {
                    // No available dry day to prevent flood
                    return new int[0];
                }

                // Assign the found dry day to dry this lake
                ans[dryDay] = lake;
                parent[dryDay] = find(dryDay + 1, parent); // mark dryDay as used
            }

            // Mark today as raining day (no drying)
            ans[i] = -1;
            parent[i] = i + 1; // mark current day as used
            lastRainDay.put(lake, i);
        }

        return ans;
    }

    // Union-Find 'find' function with path compression
    private int find(int x, int[] parent) {
        if (parent[x] != x) {
            parent[x] = find(parent[x], parent);
        }
        return parent[x];
    }
}