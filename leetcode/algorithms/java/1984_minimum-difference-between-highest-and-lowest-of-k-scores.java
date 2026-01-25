// Source: https://leetcode.com/problems/minimum-difference-between-highest-and-lowest-of-k-scores/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-25
// At the time of submission:
//   Runtime 4 ms Beats 99.35%
//   Memory 46.26 MB Beats 99.05%

/****************************************
* 
* You are given a 0-indexed integer array `nums`, where `nums[I]` represents
* _ the score of the `ith` student. You are also given an integer `k`.
* Pick the scores of any `k` students from the array so that the difference
* _ between the highest and the lowest of the `k` scores is minimized.
* Return the minimum possible difference.
*
* Example 1:
* Input: nums = [90], k = 1
* Output: 0
* Explanation: There is one way to pick score(s) of one student:
* - [90]. The difference between the highest and lowest score is 90 - 90 = 0.
* The minimum possible difference is 0.
*
* Example 2:
* Input: nums = [9,4,1,7], k = 2
* Output: 2
* Explanation: There are six ways to pick score(s) of two students:
* - [9,4,1,7]. The difference between the highest and lowest score is 9 - 4 = 5.
* - [9,4,1,7]. The difference between the highest and lowest score is 9 - 1 = 8.
* - [9,4,1,7]. The difference between the highest and lowest score is 9 - 7 = 2.
* - [9,4,1,7]. The difference between the highest and lowest score is 4 - 1 = 3.
* - [9,4,1,7]. The difference between the highest and lowest score is 7 - 4 = 3.
* - [9,4,1,7]. The difference between the highest and lowest score is 7 - 1 = 6.
* The minimum possible difference is 2.
*
* Constraints:
* • `1 <= k <= nums.length <= 1000`
* • `0 <= nums[i] <= 10^5`
* 
****************************************/

class Solution {
    // Sort the array of scores using in-place QuickSort.
    // Slide a window of size k and compute the difference between 
    // max and min in that window. Track the minimum difference.
    // Time complexity: O(n log n) for QuickSort + O(n) for window scan.
    // Space complexity: O(log n) recursion depth for QuickSort.
    public int minimumDifference(int[] nums, int k) {
        if (nums.length == 1 || k == 1) return 0;

        quickSort(nums, 0, nums.length - 1); // Sort array in-place

        int minDiff = Integer.MAX_VALUE;
        for (int i = k - 1; i < nums.length; i++) {
            // Difference of the current window of size k
            minDiff = Math.min(minDiff, nums[i] - nums[i - k + 1]);
        }

        return minDiff;
    }

    // In-place QuickSort
    public void quickSort(int[] nums, int start, int end) {
        int left = start;
        int right = end;
        int pivot = nums[start];

        while (left <= right) {
            while (left <= right && nums[left] < pivot) left++;
            while (left <= right && nums[right] > pivot) right--;

            if (left <= right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }

        if (start < right) quickSort(nums, start, right);
        if (left < end) quickSort(nums, left, end);
    }
}
