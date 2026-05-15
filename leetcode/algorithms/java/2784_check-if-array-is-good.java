// Source: https://leetcode.com/problems/check-if-array-is-good/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-15
// At the time of submission:
//   Runtime 1 ms Beats 91.05%
//   Memory 44.28 MB Beats 95.43%

/****************************************
* 
* You are given an integer array `nums`. We consider an array good if it is a
* _ permutation of an array `base[n]`.
* `base[n] = [1, 2, ..., n - 1, n, n]` (in other words, it is an array of
* _ length `n + 1` which contains `1` to `n - 1` exactly once, plus two
* _ occurrences of `n`). For example, `base[1] = [1, 1]` and
* _ `base[3] = [1, 2, 3, 3]`.
* Return `true` if the given array is good, otherwise return `false`.
* Note: A permutation of integers represents an arrangement of these numbers.
*
* Example 1:
* Input: nums = [2, 1, 3]
* Output: false
* Explanation: Since the maximum element of the array is 3, the only candidate n for which this array could be a permutation of base[n], is n = 3. However, base[3] has four elements but array nums has three. Therefore, it can not be a permutation of base[3] = [1, 2, 3, 3]. So the answer is false.
*
* Example 2:
* Input: nums = [1, 3, 3, 2]
* Output: true
* Explanation: Since the maximum element of the array is 3, the only candidate n for which this array could be a permutation of base[n], is n = 3. It can be seen that nums is a permutation of base[3] = [1, 2, 3, 3] (by swapping the second and fourth elements in nums, we reach base[3]). Therefore, the answer is true.
*
* Example 3:
* Input: nums = [1, 1]
* Output: true
* Explanation: Since the maximum element of the array is 1, the only candidate n for which this array could be a permutation of base[n], is n = 1. It can be seen that nums is a permutation of base[1] = [1, 1]. Therefore, the answer is true.
*
* Example 4:
* Input: nums = [3, 4, 4, 1, 2, 1]
* Output: false
* Explanation: Since the maximum element of the array is 4, the only candidate n for which this array could be a permutation of base[n], is n = 4. However, base[4] has five elements but array nums has six. Therefore, it can not be a permutation of base[4] = [1, 2, 3, 4, 4]. So the answer is false.
*
* Constraints:
* • `1 <= nums.length <= 100`
* • `1 <= num[i] <= 200`
* 
****************************************/

class Solution {
    // Count occurrences and validate against base[n] frequency rules.
    // Values 1..n-1 must appear once, while n appears exactly twice.
    // Array length determines expected max value: n = nums.length - 1.
    // Time: O(n), Space: O(n)
    public boolean isGood(int[] nums) {
        int n = nums.length-1;
        int[] cnt = new int[n+1];
        for (int num : nums) {
            if (num > n) return false;
            cnt[num]++;
            if (num == n) {
                if (cnt[num]>2) return false;
            } else if (cnt[num]>1) return false;
        }
        return true;
    }
}