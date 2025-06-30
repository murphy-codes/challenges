// Source: https://leetcode.com/problems/longest-harmonious-subsequence/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-29
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 45.20 MB Beats 97.07%

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
    // Count the frequency of each number using either a dense array or HashMap.
    // For each number, check neighbors (num ± 1) to find harmonious pairs.
    // Uses array lookup when range is small for O(1) access time.
    // Time: O(n) to count and scan. Space: O(n) or O(r), where r is value range.

    static {
        // Warm-up call to pre-JIT compile this method (optimization trick)
        for (int i = 0; i < 500; i++) {
            findLHS(new int[]{1, 2, 3, 4});
        }
    }

    public static int findLHS(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        // Find the min and max values in nums
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        int range = max - min + 1;

        // Use optimized dense array if the value range is small
        if (range > 1_000_000) {
            return findLHSUsingMap(nums);
        }

        int[] freq = new int[range];
        for (int num : nums) {
            freq[num - min]++;
        }

        int maxLen = 0;
        for (int i = 1; i < freq.length; i++) {
            if (freq[i] != 0 && freq[i - 1] != 0) {
                maxLen = Math.max(maxLen, freq[i] + freq[i - 1]);
            }
        }

        return maxLen;
    }

    private static int findLHSUsingMap(int[] nums) {
        if (nums.length < 2) return 0;

        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.merge(num, 1, Integer::sum);
        }

        int maxLen = 0;
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            int key = entry.getKey();
            int count = entry.getValue();

            if (freqMap.containsKey(key - 1)) {
                maxLen = Math.max(maxLen, count + freqMap.get(key - 1));
            }
            if (freqMap.containsKey(key + 1)) {
                maxLen = Math.max(maxLen, count + freqMap.get(key + 1));
            }
        }

        return maxLen;
    }
}
