// Source: https://leetcode.com/problems/divide-an-array-into-subarrays-with-minimum-cost-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-01
// At the time of submission:
//   Runtime 104 ms Beats 100.00%
//   Memory 87.25 MB Beats 65.22%

/****************************************
* 
* You are given a 0-indexed array of integers `nums` of length `n`, and two
* _ positive integers `k` and `dist`.
* The cost of an array is the value of its first element. For example, the
* _ cost of `[1,2,3]` is `1` while the cost of `[3,4,1]` is 3.
* You need to divide `nums` into `k` disjoint contiguous subarrays, such that
* _ the difference between the starting index of the second subarray and the
* _ starting index of the `kth` subarray should be less than or equal to `dist`.
* _ In other words, if you divide `nums` into the subarrays `nums[0..(i_1 - 1)]`,
* _ `nums[i_1..(i_2 - 1)]`, ..., `nums[i_k-1..(n - 1)]`, then `i_k-1 - i_1 <= dist`.
* Return the minimum possible sum of the cost of these subarrays.
*
* Example 1:
* Input: nums = [1,3,2,6,4,2], k = 3, dist = 3
* Output: 5
* Explanation: The best possible way to divide nums into 3 subarrays is: [1,3], [2,6,4], and [2]. This choice is valid because ik-1 - i1 is 5 - 2 = 3 which is equal to dist. The total cost is nums[0] + nums[2] + nums[5] which is 1 + 2 + 2 = 5.
* It can be shown that there is no possible way to divide nums into 3 subarrays at a cost lower than 5.
*
* Example 2:
* Input: nums = [10,1,2,2,2,1], k = 4, dist = 3
* Output: 15
* Explanation: The best possible way to divide nums into 4 subarrays is: [10], [1], [2], and [2,2,1]. This choice is valid because ik-1 - i1 is 3 - 1 = 2 which is less than dist. The total cost is nums[0] + nums[1] + nums[2] + nums[3] which is 10 + 1 + 2 + 2 = 15.
* The division [10], [1], [2,2,2], and [1] is not valid, because the difference between ik-1 and i1 is 5 - 1 = 4, which is greater than dist.
* It can be shown that there is no possible way to divide nums into 4 subarrays at a cost lower than 15.
*
* Example 3:
* Input: nums = [10,8,18,9], k = 3, dist = 1
* Output: 36
* Explanation: The best possible way to divide nums into 4 subarrays is: [10], [8], and [18,9]. This choice is valid because ik-1 - i1 is 2 - 1 = 1 which is equal to dist.The total cost is nums[0] + nums[1] + nums[2] which is 10 + 8 + 18 = 36.
* The division [10], [8,18], and [9] is not valid, because the difference between ik-1 and i1 is 3 - 1 = 2, which is greater than dist.
* It can be shown that there is no possible way to divide nums into 3 subarrays at a cost lower than 36.
*
* Constraints:
* • `3 <= n <= 10^5`
* • `1 <= nums[i] <= 10^9`
* • `3 <= k <= n`
* • `k - 2 <= dist <= n - 2`
* 
****************************************/

import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;

class Solution {
    // We fix nums[0] as the first subarray cost and slide a window of size dist+1.
    // Two heaps maintain the smallest k−1 elements in the window and their sum.
    // A lazy-deletion map handles removals efficiently as the window moves.
    // At each valid window, we update the minimum cost using the heap sum.
    // Time: O(n log n), Space: O(n).
    public long minimumCost(int[] nums, int k, int dist) {
        int n = nums.length;

        // Max-heap: holds the smallest (k - 1) values in the window
        PriorityQueue<Integer> leftMaxHeap =
            new PriorityQueue<>((a, b) -> b - a);

        // Min-heap: holds remaining values
        PriorityQueue<Integer> rightMinHeap =
            new PriorityQueue<>();

        // Lazy deletion map: value -> pending removals
        Map<Integer, Integer> lazyRemove = new HashMap<>();

        int leftSize = 0;      // valid elements in left heap
        long leftSum = 0;      // sum of left heap values
        long answer = Long.MAX_VALUE;

        for (int i = 1; i < n; i++) {

            // Remove element that slides out of window
            if (i >= dist + 2) {
                int outgoing = nums[i - dist - 1];

                if (outgoing < leftMaxHeap.peek()) {
                    lazyRemove.merge(outgoing, 1, Integer::sum);
                    leftSize--;
                    leftSum -= outgoing;
                } else if (outgoing == leftMaxHeap.peek()) {
                    leftMaxHeap.poll();
                    leftSize--;
                    leftSum -= outgoing;
                } else if (!rightMinHeap.isEmpty()
                           && outgoing == rightMinHeap.peek()) {
                    rightMinHeap.poll();
                } else {
                    lazyRemove.merge(outgoing, 1, Integer::sum);
                }
            }

            // Insert new element
            if (i <= k - 1 || nums[i] <= leftMaxHeap.peek()) {
                leftMaxHeap.offer(nums[i]);
                leftSize++;
                leftSum += nums[i];
            } else {
                rightMinHeap.offer(nums[i]);
            }

            // Rebalance heaps
            if (i > k - 1) {
                if (leftSize < k - 1) {
                    int v = rightMinHeap.poll();
                    leftMaxHeap.offer(v);
                    leftSize++;
                    leftSum += v;
                } else if (leftSize > k - 1) {
                    int v = leftMaxHeap.poll();
                    leftSize--;
                    leftSum -= v;
                    rightMinHeap.offer(v);
                }
            }

            // Clean invalid elements from heap tops
            while (!leftMaxHeap.isEmpty()
                   && lazyRemove.getOrDefault(leftMaxHeap.peek(), 0) > 0) {
                int v = leftMaxHeap.poll();
                lazyRemove.merge(v, -1, Integer::sum);
            }

            while (!rightMinHeap.isEmpty()
                   && lazyRemove.getOrDefault(rightMinHeap.peek(), 0) > 0) {
                int v = rightMinHeap.poll();
                lazyRemove.merge(v, -1, Integer::sum);
            }

            // Update result once window is valid
            if (i >= dist + 1) {
                answer = Math.min(answer, leftSum);
            }
        }

        // nums[0] is always included
        return answer + nums[0];
    }
}
