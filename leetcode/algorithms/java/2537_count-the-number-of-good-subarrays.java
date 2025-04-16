// Source: https://leetcode.com/count-the-number-of-good-subarrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-15
// At the time of submission:
//   Runtime 46 ms Beats 80.33%
//   Memory 60.30 MB Beats 49.00%

/****************************************
* 
* Given an integer array `nums` and an integer `k`,
* _ return the number of good subarrays of `nums`.
* A subarray `arr` is good if there are at least `k` pairs of
* _ indices `(i, j)` such that `i < j` and `arr[i] == arr[j]`.
* A subarray is a contiguous non-empty sequence of elements within an array.
*
* Example 1:
* Input: nums = [1,1,1,1,1], k = 10
* Output: 1
* Explanation: The only good subarray is the array nums itself.
*
* Example 2:
* Input: nums = [3,1,4,3,2,2,4], k = 2
* Output: 4
* Explanation: There are 4 different good subarrays:
* - [3,1,4,3,2,2] that has 2 pairs.
* - [3,1,4,3,2,2,4] that has 3 pairs.
* - [1,4,3,2,2,4] that has 2 pairs.
* - [4,3,2,2,4] that has 2 pairs.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i], k <= 10^9
* 
****************************************/

import java.util.HashMap;

class Solution {
    // Use a sliding window and a frequency map to count equal pairs.
    // For each right index, expand window and count new pairs from matching values.
    // Shrink window from left while pairs >= k, and count valid subarrays ending at right.
    // This gives O(n) average time due to amortized sliding of both pointers.
    // Time: O(n), Space: O(n)
    public long countGood(int[] nums, int k) {
        int n = nums.length;
        HashMap<Integer, Integer> freq = new HashMap<>();
        long res = 0;
        long pairs = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {
            int val = nums[right];
            int count = freq.getOrDefault(val, 0);
            pairs += count;
            freq.put(val, count + 1);

            while (pairs >= k) {
                res += n - right; // all subarrays from [left..right] to [right..right] are valid
                int leftVal = nums[left];
                int freqLeft = freq.get(leftVal);
                pairs -= freqLeft - 1;
                freq.put(leftVal, freqLeft - 1);
                left++;
            }
        }

        return res;
    }
}
