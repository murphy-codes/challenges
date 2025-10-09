// Source: https://leetcode.com/problems/avoid-flood-in-the-city/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-06
// At the time of submission:
//   Runtime 63 ms Beats 94.52%
//   Memory 61.02 MB Beats 89.50%

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

import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    // Greedy + TreeSet solution.
    // We track full lakes using a map (lake → last rain day), and keep a TreeSet
    // of available dry days. When a lake rains again, we find the next dry day
    // after its last rain to dry it beforehand. If none exist, flooding is
    // unavoidable. Runs in O(n log n) time and O(n) space.
    public int[] avoidFlood(int[] rains) {
        int n = rains.length;
        int[] ans = new int[n];
        Map<Integer, Integer> fullLakes = new HashMap<>();  // lake -> last rain day
        TreeSet<Integer> dryDays = new TreeSet<>();         // indices of dry days
        
        for (int i = 0; i < n; i++) {
            if (rains[i] == 0) {
                dryDays.add(i);
                ans[i] = 1; // temporary default, will update if we choose to dry a specific lake
            } else {
                int lake = rains[i];
                ans[i] = -1; // raining day
                
                if (fullLakes.containsKey(lake)) {
                    int lastRainDay = fullLakes.get(lake);
                    Integer dryDay = dryDays.higher(lastRainDay); // find earliest dry day after last rain
                    if (dryDay == null) {
                        return new int[0]; // impossible to avoid flood
                    }
                    ans[dryDay] = lake; // dry this lake on that dry day
                    dryDays.remove(dryDay);
                }
                fullLakes.put(lake, i); // mark lake as full today
            }
        }
        return ans;
    }
}