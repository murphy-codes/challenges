// Source: https://leetcode.com/problems/divide-an-array-into-subarrays-with-minimum-cost-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-31
// At the time of submission:
//   Runtime 246 ms Beats 65.22%
//   Memory 94.51 MB Beats 43.48%

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

class Solution {
    // We always include nums[0], then use a sliding window to choose the remaining
    // k−1 subarray starts within the allowed distance constraint. Two TreeMaps
    // maintain the k−1 smallest values (left) and the rest (right), while tracking
    // the sum of the left partition. As the window slides, we rebalance to keep
    // exactly k−1 elements on the left. Time: O(n log n), Space: O(n).
    
    // TreeMap to store the k smallest elements (left partition)
    private final TreeMap<Integer, Integer> leftPartition = new TreeMap<>();
    // TreeMap to store elements larger than the k smallest (right partition)
    private final TreeMap<Integer, Integer> rightPartition = new TreeMap<>();
    // Sum of elements in the left partition
    private long currentSum;
    // Number of elements in the left partition
    private int leftPartitionSize;

    public long minimumCost(int[] nums, int k, int dist) {
        // Adjust k since nums[0] is always included
        --k;
      
        // Initialize with nums[0] as it's always part of the answer
        currentSum = nums[0];
      
        // Add all elements in the initial window to the left partition
        for (int i = 1; i < dist + 2; ++i) {
            currentSum += nums[i];
            leftPartition.merge(nums[i], 1, Integer::sum);
        }
      
        // Initial window size (excluding nums[0])
        leftPartitionSize = dist + 1;
      
        // Balance the partitions to keep exactly k elements in left partition
        while (leftPartitionSize > k) {
            moveLeftToRight();
        }
      
        // Initialize answer with the sum of first valid window
        long answer = currentSum;
      
        // Slide the window through the rest of the array
        for (int i = dist + 2; i < nums.length; ++i) {
            // Remove the element that's going out of the window
            int elementToRemove = nums[i - dist - 1];
          
            // Check if element to remove is in left partition
            if (leftPartition.containsKey(elementToRemove)) {
                // Remove from left partition and update sum
                if (leftPartition.merge(elementToRemove, -1, Integer::sum) == 0) {
                    leftPartition.remove(elementToRemove);
                }
                currentSum -= elementToRemove;
                --leftPartitionSize;
            } else {
                // Remove from right partition
                if (rightPartition.merge(elementToRemove, -1, Integer::sum) == 0) {
                    rightPartition.remove(elementToRemove);
                }
            }
          
            // Add the new element entering the window
            int elementToAdd = nums[i];
          
            // Determine which partition to add the new element to
            if (elementToAdd < leftPartition.lastKey()) {
                // Add to left partition if it's smaller than the largest in left
                leftPartition.merge(elementToAdd, 1, Integer::sum);
                ++leftPartitionSize;
                currentSum += elementToAdd;
            } else {
                // Otherwise add to right partition
                rightPartition.merge(elementToAdd, 1, Integer::sum);
            }
          
            // Rebalance partitions to maintain exactly k elements in left
            while (leftPartitionSize < k) {
                moveRightToLeft();
            }
            while (leftPartitionSize > k) {
                moveLeftToRight();
            }
          
            // Update minimum answer
            answer = Math.min(answer, currentSum);
        }
      
        return answer;
    }

    /**
     * Move the largest element from left partition to right partition
     */
    private void moveLeftToRight() {
        // Get the largest element from left partition
        int elementToMove = leftPartition.lastKey();
      
        // Update sum as element leaves left partition
        currentSum -= elementToMove;
      
        // Remove from left partition
        if (leftPartition.merge(elementToMove, -1, Integer::sum) == 0) {
            leftPartition.remove(elementToMove);
        }
        --leftPartitionSize;
      
        // Add to right partition
        rightPartition.merge(elementToMove, 1, Integer::sum);
    }

    /**
     * Move the smallest element from right partition to left partition
     */
    private void moveRightToLeft() {
        // Get the smallest element from right partition
        int elementToMove = rightPartition.firstKey();
      
        // Remove from right partition
        if (rightPartition.merge(elementToMove, -1, Integer::sum) == 0) {
            rightPartition.remove(elementToMove);
        }
      
        // Add to left partition
        leftPartition.merge(elementToMove, 1, Integer::sum);
      
        // Update sum as element enters left partition
        currentSum += elementToMove;
        ++leftPartitionSize;
    }
}
