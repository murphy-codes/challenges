// Source: https://leetcode.com/problems/count-number-of-bad-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-09

/****************************************
* 
* You are given a 0-indexed integer array `nums`. A pair of indices `(i, j)` is a bad pair if `i < j` and `j - i != nums[j] - nums[i]`.
* 
* Return the total number of bad pairs in `nums`.
* 
* Example 1:
* Input: nums = [4,1,3,3]
* Output: 5
* Explanation: The pair (0, 1) is a bad pair since 1 - 0 != 1 - 4.
* The pair (0, 2) is a bad pair since 2 - 0 != 3 - 4, 2 != -1.
* The pair (0, 3) is a bad pair since 3 - 0 != 3 - 4, 3 != -1.
* The pair (1, 2) is a bad pair since 2 - 1 != 3 - 1, 1 != 2.
* The pair (2, 3) is a bad pair since 3 - 2 != 3 - 3, 1 != 0.
* There are a total of 5 bad pairs, so we return 5.
* 
* Example 2:
* Input: nums = [1,2,3,4,5]
* Output: 0
* Explanation: There are no bad pairs.
* 
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^9
* 
****************************************/

import java.util.HashMap;
import java.util.Map;

class Solution {
    // This solution optimizes bad pair counting using a HashMap.
    // We transform nums[i] into nums[i] - i and count occurrences.
    // Good pairs are those with the same transformed value, found in O(1).
    // Total pairs are (n * (n-1)) / 2, and bad pairs = total - good pairs.
    // Time: O(n), Space: O(n), making it feasible for large inputs.
    public long countBadPairs(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        long totalPairs = (long) nums.length * (nums.length - 1) / 2;
        long goodPairs = 0;

        for (int i = 0; i < nums.length; i++) {
            int diff = nums[i] - i;
            goodPairs += countMap.getOrDefault(diff, 0);
            countMap.put(diff, countMap.getOrDefault(diff, 0) + 1);
        }

        return totalPairs - goodPairs;
    }
}
