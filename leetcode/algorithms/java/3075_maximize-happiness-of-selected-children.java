// Source: https://leetcode.com/problems/maximize-happiness-of-selected-children/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-31
// At the time of submission:
//   Runtime 13 ms Beats 98.83%
//   Memory 109.27 MB Beats 51.59%

/****************************************
* 
* You are given an array `happiness` of length `n`, and a positive integer `k`.
* There are `n` children standing in a queue, where the `i^th` child has
* _ happiness value `happiness[i]`. You want to select `k` children from these
* _ `n` children in `k` turns.
* In each turn, when you select a child, the happiness value of all the children
* _ that have not been selected till now decreases by `1`. Note that the happiness
* _ value cannot become negative and gets decremented only if it is positive.
* Return the maximum sum of the happiness values of the selected children you can
* _ achieve by selecting `k` children.
*
* Example 1:
* Input: happiness = [1,2,3], k = 2
* Output: 4
* Explanation: We can pick 2 children in the following way:
* - Pick the child with the happiness value == 3. The happiness value of the remaining children becomes [0,1].
* - Pick the child with the happiness value == 1. The happiness value of the remaining child becomes [0]. Note that the happiness value cannot become less than 0.
* The sum of the happiness values of the selected children is 3 + 1 = 4.
*
* Example 2:
* Input: happiness = [1,1,1,1], k = 2
* Output: 1
* Explanation: We can pick 2 children in the following way:
* - Pick any child with the happiness value == 1. The happiness value of the remaining children becomes [0,0,0].
* - Pick the child with the happiness value == 0. The happiness value of the remaining child becomes [0,0].
* The sum of the happiness values of the selected children is 1 + 0 = 1.
*
* Example 3:
* Input: happiness = [2,3,4,5], k = 1
* Output: 5
* Explanation: We can pick 1 child in the following way:
* - Pick the child with the happiness value == 5. The happiness value of the remaining children becomes [1,2,3].
* The sum of the happiness values of the selected children is 5.
*
* Constraints:
* • `1 <= n == happiness.length <= 2 * 10^5`
* • `1 <= happiness[i] <= 10^8`
* • `1 <= k <= n`
* 
****************************************/

class Solution {
    // Extract k largest values without fully sorting. Binary search finds how
    // many values are needed; quickselect-style partition places the largest
    // k elements at the end. Sum applies decreasing offsets for each pick.
    // Time: avg O(n) due to partitioning, worst O(n^2) but rare. Space: O(1).
    public long maximumHappinessSum(int[] happiness, int k) {
        
        // Binary search to determine how many values we need to consider
        int low = 0, high = k;
        while (low < high) {
            int mid = (low + high) >> 1;
            if (check(happiness, mid))
                low = mid + 1;
            else
                high = mid;
        }
        
        k = low; // k becomes the final number of values we will extract
        int n = happiness.length;
        
        // Partition so that the k largest values end up at the end of the array
        quickSort(happiness, 0, n - 1, n - k);

        // Sum the k largest values, applying the incremental decrease formula
        long result = -((long) k * (k - 1)) >> 1;
        for (int i = n - 1; k-- > 0; --i)
            result += happiness[i];
        
        return result;
    }
    
    // Count how many values are >= mid; if more than mid exist, condition satisfied
    public boolean check(int[] happiness, int mid) {
        int count = 0;
        for (int value : happiness) {
            if (value < mid) continue;
            if (++count > mid)
                return true;
        }
        return false;
    }
    
    // Quickselect-style partitioning around the pivot so position n-k is correct
    private void quickSort(int[] nums, int low, int high, int k) {
        if (low == high) return;
        
        int left = low - 1, right = high + 1;
        int pivot = nums[(low + high) >> 1];
        
        while (left < right) {
            while (nums[++left] < pivot);
            while (nums[--right] > pivot);
            if (left < right) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
            }
        }
        
        if (right < k)
            quickSort(nums, right + 1, high, k);
        else
            quickSort(nums, low, right, k);
    }
}
