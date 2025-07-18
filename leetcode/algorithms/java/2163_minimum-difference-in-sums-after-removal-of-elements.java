// Source: https://leetcode.com/problems/minimum-difference-in-sums-after-removal-of-elements/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-18
// At the time of submission:
//   Runtime 283 ms Beats 21.13%
//   Memory 97.18 MB Beats 7.04%

/****************************************
* 
* You are given a 0-indexed integer array `nums` consisting of `3 * n` elements.
* You are allowed to remove any subsequence of elements of size exactly `n` from
* _ `nums`. The remaining `2 * n` elements will be divided into two equal parts:
* • The first `n` elements belonging to the first part and their sum is `sum_first`.
* • The next `n` elements belonging to the second part and their sum is `sum_second`.
* The difference in sums of the two parts is denoted as `sum_first - sum_second`.
* • For example, if `sum_first = 3` and `sum_second = 2`, their difference is `1`.
* • Similarly, if `sum_first = 2` and `sum_second = 3`, their difference is `-1`.
* Return the minimum difference possible between the sums of the two parts after
* _ the removal of `n` elements.
*
* Example 1:
* Input: nums = [3,1,2]
* Output: -1
* Explanation: Here, nums has 3 elements, so n = 1.
* Thus we have to remove 1 element from nums and divide the array into two equal parts.
* - If we remove nums[0] = 3, the array will be [1,2]. The difference in sums of the two parts will be 1 - 2 = -1.
* - If we remove nums[1] = 1, the array will be [3,2]. The difference in sums of the two parts will be 3 - 2 = 1.
* - If we remove nums[2] = 2, the array will be [3,1]. The difference in sums of the two parts will be 3 - 1 = 2.
* The minimum difference between sums of the two parts is min(-1,1,2) = -1.
*
* Example 2:
* Input: nums = [7,9,5,8,1,3]
* Output: 1
* Explanation: Here n = 2. So we must remove 2 elements and divide the remaining array into two parts containing two elements each.
* If we remove nums[2] = 5 and nums[3] = 8, the resultant array will be [7,9,1,3]. The difference in sums will be (7+9) - (1+3) = 12.
* To obtain the minimum difference, we should remove nums[1] = 9 and nums[4] = 1. The resultant array becomes [7,5,8,3]. The difference in sums of the two parts is (7+5) - (8+3) = 1.
* It can be shown that it is not possible to obtain a difference smaller than 1.
*
* Constraints:
* • nums.length == 3 * n
* • 1 <= n <= 10^5
* • 1 <= nums[i] <= 10^5
* 
****************************************/

import java.util.PriorityQueue;

class Solution {
    // Maintain prefixMin[i]: sum of the smallest n elements in nums[0..i].
    // Maintain suffixMax[i]: sum of the largest n elements in nums[i..end].
    // Use max-heap for prefix, min-heap for suffix, each O(n log n).
    // Then scan all splits i in [n-1 … 2n-1]: minimize prefixMin[i] - suffixMax[i+1].
    // Overall Time: O(n log n), Space: O(n).
    public long minimumDifference(int[] nums) {
        int total = nums.length;
        int n = total / 3;

        long[] prefixMin = new long[total];
        long[] suffixMax = new long[total];

        // 1) Prefix: minimum sum of n elements among first i+1
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        long sum = 0;
        for (int i = 0; i < total; i++) {
            sum += nums[i];
            maxHeap.offer(nums[i]);
            if (maxHeap.size() > n) {
                sum -= maxHeap.poll();
            }
            prefixMin[i] = sum;
        }

        // 2) Suffix: maximum sum of n elements among last total-i elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        sum = 0;
        for (int i = total - 1; i >= 0; i--) {
            sum += nums[i];
            minHeap.offer(nums[i]);
            if (minHeap.size() > n) {
                sum -= minHeap.poll();
            }
            suffixMax[i] = sum;
        }

        // 3) Choose best split point
        long res = Long.MAX_VALUE;
        for (int i = n - 1; i < 2 * n; i++) {
            res = Math.min(res, prefixMin[i] - suffixMax[i + 1]);
        }

        return res;
    }
}
