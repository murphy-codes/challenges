// Source: https://leetcode.com/problems/longest-balanced-subarray-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-10
// At the time of submission:
//   Runtime 28 ms Beats 99.45%
//   Memory 47.70 MB Beats 11.40%

/****************************************
* 
* You are given an integer array `nums`.
* A subarray is called balanced if the number of distinct even numbers in the
* _ subarray is equal to the number of distinct odd numbers.
* Return the length of the longest balanced subarray.
*
* Example 1:
* Input: nums = [2,5,4,3]
* Output: 4
* Explanation:
* The longest balanced subarray is `[2, 5, 4, 3]`.
* It has 2 distinct even numbers `[2, 4]` and 2 distinct odd numbers `[5, 3]`.
* _ Thus, the answer is 4.
*
* Example 2:
* Input: nums = [3,2,2,5,4]
* Output: 5
* Explanation:
* The longest balanced subarray is `[3, 2, 2, 5, 4]`.
* It has 2 distinct even numbers `[2, 4]` and 2 distinct odd numbers `[3, 5]`.
* _ Thus, the answer is 5.
*
* Example 3:
* Input: nums = [1,2,3,2]
* Output: 3
* Explanation:
* The longest balanced subarray is `[2, 3, 2]`.
* It has 1 distinct even number `[2]` and 1 distinct odd number `[3]`.
* _ Thus, the answer is 3.
*
* Constraints:
* • `1 <= nums.length <= 1500`
* • `1 <= nums[i] <= 10^5`
* 
****************************************/

class Solution {
    // Enumerate all subarrays using a fixed left index and expanding right.
    // Track distinct evens and odds using timestamped arrays instead of sets.
    // A rolling timer simulates clearing state in O(1) time.
    // Early termination skips windows that cannot exceed the current best.
    // Time complexity: O(n^2), Space complexity: O(max(nums[i])).

    // Marker arrays to track seen values using a rolling timestamp
    int[] seenEven = new int[100001];
    int[] seenOdd  = new int[100001];

    public int longestBalanced(int[] nums) {
        int n = nums.length;
        int maxLen = 0;
        int stamp = 0;

        for (int left = 0; left < n; left++) {
            // Skip if remaining elements cannot exceed current max
            if (maxLen >= n - left) break;

            stamp++; // Acts like clearing the sets
            int evenCount = 0;
            int oddCount = 0;

            for (int right = left; right < n; right++) {
                int val = nums[right];

                if ((val & 1) == 0) {
                    if (seenEven[val] != stamp) {
                        seenEven[val] = stamp;
                        evenCount++;
                    }
                } else {
                    if (seenOdd[val] != stamp) {
                        seenOdd[val] = stamp;
                        oddCount++;
                    }
                }

                if (evenCount == oddCount) {
                    maxLen = Math.max(maxLen, right - left + 1);
                }
            }
        }
        return maxLen;
    }
}
