// Source: https://leetcode.com/problems/minimum-difference-in-sums-after-removal-of-elements/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-18
// At the time of submission:
//   Runtime 18 ms Beats 100.00%
//   Memory 94.19 MB Beats 8.67%

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
    // This solution uses two threads to calculate prefix and suffix sums in parallel.
    // For the first k elements, it computes the minimum sum of any k-element subset
    // using a max-heap; for the last k elements, it computes the maximum sum of any
    // k-element subset using a min-heap. The minimum difference between these two
    // is returned. Time: O(n log k), Space: O(n + k)

    private long[] minPrefixSum;
    private long[] maxSuffixSum;
    private int[] nums;
    private int k;

    // Thread to compute minimum sum of k elements from left to right
    class PrefixWorker extends Thread {
        public void run() {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
            long sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                maxHeap.offer(nums[i]);

                if (maxHeap.size() > k) {
                    sum -= maxHeap.poll(); // remove the largest element
                }

                if (maxHeap.size() == k) {
                    minPrefixSum[i] = sum;
                } else {
                    minPrefixSum[i] = Long.MAX_VALUE;
                }
            }
        }
    }

    // Thread to compute maximum sum of k elements from right to left
    class SuffixWorker extends Thread {
        public void run() {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            long sum = 0;
            for (int i = nums.length - 1; i >= 0; i--) {
                sum += nums[i];
                minHeap.offer(nums[i]);

                if (minHeap.size() > k) {
                    sum -= minHeap.poll(); // remove the smallest element
                }

                if (minHeap.size() == k) {
                    maxSuffixSum[i] = sum;
                } else {
                    maxSuffixSum[i] = Long.MIN_VALUE;
                }
            }
        }
    }

    public long minimumDifference(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        this.k = n / 3;

        minPrefixSum = new long[n];
        maxSuffixSum = new long[n];

        Thread leftThread = new PrefixWorker();
        Thread rightThread = new SuffixWorker();

        leftThread.start();
        rightThread.start();

        try {
            leftThread.join();
            rightThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long result = Long.MAX_VALUE;
        for (int i = k - 1; i < n - k; i++) {
            if (minPrefixSum[i] != Long.MAX_VALUE && maxSuffixSum[i + 1] != Long.MIN_VALUE) {
                result = Math.min(result, minPrefixSum[i] - maxSuffixSum[i + 1]);
            }
        }

        return result;
    }
}
