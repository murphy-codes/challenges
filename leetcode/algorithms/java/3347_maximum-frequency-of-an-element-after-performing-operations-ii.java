// Source: https://leetcode.com/problems/maximum-frequency-of-an-element-after-performing-operations-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-21
// At the time of submission:
//   Runtime 227 ms Beats 54.17%
//   Memory 64.79 MB Beats 62.50%

/****************************************
* 
* You are given an integer array `nums` and two integers `k` and `numOperations`.
* You must perform an operation `numOperations` times on `nums`, where
* _ in each operation you:
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
* Adding 0 to nums[1], after which nums becomes [1, 4, 5].
* Adding -1 to nums[2], after which nums becomes [1, 4, 4].
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
* • `1 <= nums[i] <= 10^9`
* • `0 <= k <= 10^9`
* • `0 <= numOperations <= nums.length`
* 
****************************************/

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    // Sort nums and use a sliding window to find the largest group that can be
    // equalized within numOperations operations. Each operation allows changing
    // an element by at most k, so the total allowable adjustment is numOps * k.
    // Expand right, tracking cost to match nums[right]; shrink left when exceeded.
    // Time: O(n log n) from sorting; Space: O(1) auxiliary.
    public int maxFrequency(int[] nums, int k, int numOperations) {
        int n = nums.length;
        Arrays.sort(nums);

        // Frequency map for exact equals
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) freq.put(x, freq.getOrDefault(x, 0) + 1);

        int ans = 1;

        // Helper: binary search lower bound
        java.util.function.IntUnaryOperator lb = target -> {
            int l = 0, r = n;
            while (l < r) {
                int m = (l + r) >>> 1;
                if (nums[m] < target) l = m + 1;
                else r = m;
            }
            return l;
        };

        // Helper: binary search upper bound
        java.util.function.IntUnaryOperator ub = target -> {
            int l = 0, r = n;
            while (l < r) {
                int m = (l + r) >>> 1;
                if (nums[m] <= target) l = m + 1;
                else r = m;
            }
            return l;
        };

        // For each index, consider candidates nums[i] - k, nums[i], nums[i] + k
        for (int x : nums) {
            int[] candidates = new int[] { x - k, x, x + k };
            for (int t : candidates) {
                // Number of elements v with |v - t| <= k  => v in [t-k, t+k]
                int L = lb.applyAsInt(t - k);
                int R = ub.applyAsInt(t + k);
                int reachable = R - L;

                int equal = freq.getOrDefault(t, 0);
                int canConvert = Math.min(numOperations, Math.max(0, reachable - equal));
                ans = Math.max(ans, equal + canConvert);
            }
        }

        return ans;
    }
}
