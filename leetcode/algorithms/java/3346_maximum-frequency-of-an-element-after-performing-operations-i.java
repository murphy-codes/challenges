// Source: https://leetcode.com/problems/maximum-frequency-of-an-element-after-performing-operations-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-20
// At the time of submission:
//   Runtime 459 ms Beats 24.34%
//   Memory 76.31 MB Beats 17.10%

/****************************************
* 
* You are given an integer array `nums` and two integers `k` and `numOperations`.
* You must perform an operation `numOperations` times on `nums`, where in
* _ each operation you:
* • Select an index `i` that was not selected in any previous operations.
* • Add an integer in the range `[-k, k]` to `nums[i]`.
* Return the maximum possible frequency of any element in `nums` after
* _ performing the operations.
*
* Example 1:
* Input: nums = [1,4,5], k = 1, numOperations = 2
* Output: 2
* Explanation:
* We can achieve a maximum frequency of two by:
* Adding 0 to nums[1]. nums becomes [1, 4, 5].
* Adding -1 to nums[2]. nums becomes [1, 4, 4].
*
* Example 2:
* Input: nums = [5,11,20,20], k = 5, numOperations = 1
* Output: 2
* Explanation:
* We can achieve a maximum frequency of two by:
* Adding 0 to nums[1].
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^5`
* • `0 <= k <= 10^5`
* • `0 <= numOperations <= nums.length`
* 
****************************************/

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

class Solution {
    // Use difference‐array / sweep‐line approach: each element x defines an 
    // interval [x − k, x + k] of values it can become. We mark +1 at start and 
    // −1 after end, sort all events, and sweep to track how many elements’  
    // intervals are activeat each target value x. Then max frequency at 
    // x = freqMap[x] + min(numOperations, active-freqMap[x]).
    // Time Complexity: O(n log n) for sorting the events. Space Complexity: O(n).
    public int maxFrequency(int[] nums, int k, int numOperations) {
        int n = nums.length;
        // Map to count how many elements are exactly value x
        Map<Integer, Integer> freqMap = new HashMap<>();
        // TreeMap for the difference‐array / sweep events: position → delta
        TreeMap<Integer, Integer> events = new TreeMap<>();

        for (int x : nums) {
            // count original frequency
            freqMap.put(x, freqMap.getOrDefault(x, 0) + 1);
            // ensure x appears in events (so we check that point)
            events.putIfAbsent(x, 0);

            // mark range [x – k, x + k] where x’s value can be made equal to
            int start = x - k;
            int end   = x + k;   // inclusive range
            events.merge(start, +1, Integer::sum);
            events.merge(end + 1, -1, Integer::sum);
        }

        int active = 0;
        int best  = 1;  // at least one element
        for (Map.Entry<Integer, Integer> e : events.entrySet()) {
            active += e.getValue();
            int x     = e.getKey();
            int cntX  = freqMap.getOrDefault(x, 0);
            // At value x, `active` = number of intervals (elements) that can reach x
            // We can form frequency = cntX + min(numOperations, active – cntX)
            int convertible = active - cntX;
            if (convertible < 0) convertible = 0;
            int candidate = cntX + Math.min(numOperations, convertible);
            if (candidate > best) best = candidate;
        }

        return best;
    }
}
