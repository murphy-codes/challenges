// Source: https://leetcode.com/problems/find-x-sum-of-all-k-long-subarrays-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-03
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 46.66 MB Beats 7.59%

/****************************************
* 
* You are given an array `nums` of `n` integers and two integers `k` and `x`.
* The x-sum of an array is calculated by the following procedure:
* • Count the occurrences of all elements in the array.
* • Keep only the occurrences of the top `x` most frequent elements. If two
* __ elements have the same number of occurrences, the element with the bigger
* __ value is considered more frequent.
* • Calculate the sum of the resulting array.
* Note that if an array has less than `x` distinct elements, its x-sum is the
* _ sum of the array.
* Return an integer array `answer` of length `n - k + 1` where `answer[i]`
* _ is the x-sum of the subarray `nums[i..i + k - 1]`.
*
* Example 1:
* Input: nums = [1,1,2,2,3,4,2,3], k = 6, x = 2
* Output: [6,10,12]
* Explanation:
* For subarray [1, 1, 2, 2, 3, 4], only elements 1 and 2 will be kept in the resulting array. Hence, answer[0] = 1 + 1 + 2 + 2.
* For subarray [1, 2, 2, 3, 4, 2], only elements 2 and 4 will be kept in the resulting array. Hence, answer[1] = 2 + 2 + 2 + 4. Note that 4 is kept in the array since it is bigger than 3 and 1 which occur the same number of times.
* For subarray [2, 2, 3, 4, 2, 3], only elements 2 and 3 are kept in the resulting array. Hence, answer[2] = 2 + 2 + 2 + 3 + 3.
*
* Example 2:
* Input: nums = [3,8,7,8,7,5], k = 2, x = 2
* Output: [11,15,15,15,12]
* Explanation:
* Since k == x, answer[i] is equal to the sum of the subarray nums[i..i + k - 1].
*
* Constraints:
* • `1 <= n == nums.length <= 50`
* • `1 <= nums[i] <= 50`
* • `1 <= x <= k <= nums.length`
* 
****************************************/

class Solution {
    // For each subarray of length k, count element frequencies (O(k*50)).
    // If fewer than x distinct elements, sum the whole subarray. Otherwise,
    // repeatedly pick the most frequent (and largest on tie) x elements and
    // add their contributions. Runs in O(n*k*50) time and O(50) extra space.
    public int[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] result = new int[n - k + 1];

        for (int start = 0; start < result.length; start++) {
            int end = start + k - 1;
            result[start] = findXSumOfWindow(nums, start, end, x);
        }

        return result;
    }

    private int findXSumOfWindow(int[] nums, int left, int right, int x) {
        int totalSum = 0, distinctCount = 0;
        int[] freq = new int[51]; // frequency array for values 1–50

        // Count frequencies in this window and track distinct elements
        for (int i = left; i <= right; i++) {
            totalSum += nums[i];
            if (freq[nums[i]] == 0) distinctCount++;
            freq[nums[i]]++;
        }

        // If fewer than x distinct elements, return full subarray sum
        if (distinctCount < x) return totalSum;

        // Otherwise, select x most frequent elements (higher value breaks ties)
        int xSum = 0;
        for (int picks = 0; picks < x; picks++) {
            int bestFreq = -1, bestVal = -1;

            for (int val = 50; val >= 1; val--) {
                if (freq[val] > bestFreq) {
                    bestFreq = freq[val];
                    bestVal = val;
                }
            }

            if (bestVal != -1) {
                xSum += bestVal * bestFreq;
                freq[bestVal] = 0; // mark as used
            }
        }

        return xSum;
    }
}
