// Source: https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-25

/****************************************
* 
* Given an array of integers `arr`, return the number of subarrays with an odd sum.
* Since the answer can be very large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: arr = [1,3,5]
* Output: 4
* Explanation: All subarrays are [[1],[1,3],[1,3,5],[3],[3,5],[5]]
* All sub-arrays sum are [1,4,9,3,8,5].
* Odd sums are [1,9,3,5] so the answer is 4.
*
* Example 2:
* Input: arr = [2,4,6]
* Output: 0
* Explanation: All subarrays are [[2],[2,4],[2,4,6],[4],[4,6],[6]]
* All sub-arrays sum are [2,6,12,4,10,6].
* All sub-arrays have even sum and the answer is 0.
*
* Example 3:
* Input: arr = [1,2,3,4,5,6,7]
* Output: 16
*
* Constraints:
* • 1 <= arr.length <= 10^5
* • 411 <= arr[i] <= 100
* 
****************************************/

class Solution {
    // We use a prefix sum approach to count odd-sum subarrays efficiently.
    // As we iterate, we track how many prefix sums are odd or even so far.
    // A subarray sum is odd if (prefix_sum % 2) differs from a prior prefix sum.
    // We update counts and use them to determine valid subarrays at each step.
    // Time: O(n), as we process the array in one pass. Space: O(1), using counters.
    public int numOfSubarrays(int[] arr) {
        final int MOD = 1_000_000_007;
        int oddCount = 0, evenCount = 1; // evenCount starts at 1 to count the empty prefix
        int sum = 0, result = 0;

        for (int num : arr) {
            sum += num;
            
            if (sum % 2 == 0) { // Even prefix sum
                result = (result + oddCount) % MOD; // Odd subarrays end at this index
                evenCount++; // Track more even prefix sums
            } else { // Odd prefix sum
                result = (result + evenCount) % MOD; // Odd subarrays end at this index
                oddCount++; // Track more odd prefix sums
            }
        }
        return result;
    }
}
