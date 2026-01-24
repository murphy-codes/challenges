// Source: https://leetcode.com/problems/minimize-maximum-pair-sum-in-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-23
// At the time of submission:
//   Runtime 7 ms Beats 99.25%
//   Memory 98.25 MB Beats 5.16%

/****************************************
* 
* The pair sum of a pair `(a,b)` is equal to `a + b`. The maximum pair sum is
* _ the largest pair sum in a list of pairs.
* • For example, if we have pairs `(1,5)`, `(2,3)`, and `(4,4)`, the maximum
* __ pair sum would be `max(1+5, 2+3, 4+4) = max(6, 5, 8) = 8`.
* Given an array `nums` of even length `n`, pair up the elements of `nums`
* _ into `n / 2` pairs such that:
* • Each element of `nums` is in exactly one pair, and
* • The maximum pair sum is minimized.
* Return the minimized maximum pair sum after optimally pairing up the elements.
*
* Example 1:
* Input: nums = [3,5,2,3]
* Output: 7
* Explanation: The elements can be paired up into pairs (3,3) and (5,2).
* The maximum pair sum is max(3+3, 5+2) = max(6, 7) = 7.
*
* Example 2:
* Input: nums = [3,5,4,2,4,6]
* Output: 8
* Explanation: The elements can be paired up into pairs (3,5), (4,4), and (6,2).
* The maximum pair sum is max(3+5, 4+4, 6+2) = max(8, 8, 8) = 8.
*
* Constraints:
* • `n == nums.length`
* • `2 <= n <= 10^5`
* • `n` is even.
* • `1 <= nums[i] <= 10^5`
* 
****************************************/

class Solution {
    // Count frequencies instead of sorting to enable linear-time pairing.
    // Use two pointers to always pair smallest and largest available values.
    // Track the maximum sum encountered across all pairs.
    // Time complexity: O(n + max(nums)).
    // Space complexity: O(max(nums)).
    public int minPairSum(int[] nums) {
        int maxValue = 0;

        // Find maximum value to size frequency array
        for (int n : nums) {
            maxValue = Math.max(maxValue, n);
        }

        // Frequency array for counting occurrences
        int[] freq = new int[maxValue + 1];
        for (int n : nums) {
            freq[n]++;
        }

        int left = 0, right = maxValue;
        int maxPairSum = 0;

        // Pair smallest and largest remaining values
        while (left < right) {
            while (freq[left] == 0) left++;
            while (freq[right] == 0) right--;

            maxPairSum = Math.max(maxPairSum, left + right);

            if (freq[right] > freq[left]) {
                freq[right] -= freq[left++];
            } else if (freq[left] > freq[right]) {
                freq[left] -= freq[right--];
            } else {
                left++;
                right--;
            }
        }

        return maxPairSum;
    }
}
