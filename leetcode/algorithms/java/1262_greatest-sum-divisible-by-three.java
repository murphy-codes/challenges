// Source: https://leetcode.com/problems/greatest-sum-divisible-by-three/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-22
// At the time of submission:
//   Runtime 3 ms Beats 99.69%
//   Memory 49.48 MB Beats 45.34%

/****************************************
* 
* Given an integer array `nums`, return the maximum possible sum of elements of
* _ the array such that it is divisible by three.
*
* Example 1:
* Input: nums = [3,6,5,1,8]
* Output: 18
* Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum divisible by 3).
*
* Example 2:
* Input: nums = [4]
* Output: 0
* Explanation: Since 4 is not divisible by 3, do not pick any number.
*
* Example 3:
* Input: nums = [1,2,3,4,4]
* Output: 12
* Explanation: Pick numbers 1, 3, 4 and 4 their sum is 12 (maximum sum divisible by 3).
*
* Constraints:
* • `1 <= nums.length <= 4 * 10^4`
* • `1 <= nums[i] <= 10^4`
* 
****************************************/

class Solution {
    // This solution sums all numbers and tracks the two smallest values for
    // each remainder class (mod 3). If the total is not divisible by three,
    // we either remove the smallest number with matching remainder or the
    // smallest pair from the other remainder class. This ensures the best
    // possible remaining sum. Time O(n), space O(1).
    public int maxSumDivThree(int[] nums) {
        int sum = 0;
        // Track smallest and 2nd smallest numbers with remainder 1 and 2
        int r1a = Integer.MAX_VALUE, r1b = Integer.MAX_VALUE;
        int r2a = Integer.MAX_VALUE, r2b = Integer.MAX_VALUE;
        for (int n : nums) {
            sum += n;
            int r = n % 3;
            if (r == 1) {
                if (n < r1a) {
                    r1b = r1a;
                    r1a = n;
                } else if (n < r1b) {
                    r1b = n;
                }
            } else if (r == 2) {
                if (n < r2a) {
                    r2b = r2a;
                    r2a = n;
                } else if (n < r2b) {
                    r2b = n;
                }
            }
        }
        int mod = sum % 3;
        if (mod == 0) return sum;
        int option1 = Integer.MIN_VALUE;
        int option2 = Integer.MIN_VALUE;
        if (mod == 1) {
            if (r1a != Integer.MAX_VALUE) option1 = sum - r1a;
            if (r2b != Integer.MAX_VALUE) option2 = sum - (r2a + r2b);
        } else { // mod == 2
            if (r2a != Integer.MAX_VALUE) option1 = sum - r2a;
            if (r1b != Integer.MAX_VALUE) option2 = sum - (r1a + r1b);
        }
        return Math.max(option1, option2);
    }
}
