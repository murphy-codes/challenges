// Source: https://leetcode.com/problems/longest-harmonious-subsequence/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-29
// At the time of submission:
//   Runtime 16 ms Beats 62.12%
//   Memory 45.26 MB Beats 94.01%

/****************************************
* 
* We define a harmonious array as an array where the difference between its
* _ maximum value and its minimum value is exactly `1`.
* Given an integer array `nums`, return the length of its longest harmonious
* _ subsequence among all its possible subsequences.
*
* Example 1:
* Input: nums = [1,3,2,2,5,2,3,7]
* Output: 5
* Explanation:
* The longest harmonious subsequence is [3,2,2,2,3].
*
* Example 2:
* Input: nums = [1,2,3,4]
* Output: 2
* Explanation:
* The longest harmonious subsequences are [1,2], [2,3], and [3,4], all of which have a length of 2.
*
* Example 3:
* Input: nums = [1,1,1,1]
* Output: 0
* Explanation:
* No harmonic subsequence exists.
*
* Constraints:
* • 1 <= nums.length <= 2 * 10^4
* • -10^9 <= nums[i] <= 10^9
* 
****************************************/

import java.util.HashMap;
import java.util.Map;

class Solution {
    // Count the frequency of each number in the array.
    // For each number, check if num + 1 exists; if so, compute the length
    // of the subsequence formed by both. Track the maximum seen.
    // Time: O(n) for counting and scanning. Space: O(n) for the map.
    // This approach ensures linear time with constant-time lookups.
    public int findLHS(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();

        // Count frequency of each number
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        int maxLen = 0;

        // For each number, check if neighbor (num + 1) exists
        for (int num : freq.keySet()) {
            if (freq.containsKey(num + 1)) {
                int currLen = freq.get(num) + freq.get(num + 1);
                maxLen = Math.max(maxLen, currLen);
            }
        }

        return maxLen;
    }
}
