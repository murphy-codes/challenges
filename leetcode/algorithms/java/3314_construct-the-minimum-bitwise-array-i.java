// Source: https://leetcode.com/problems/construct-the-minimum-bitwise-array-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-19
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 46.83 MB Beats 30.70%

/****************************************
* 
* You are given an array `nums` consisting of `n` prime integers.
* You need to construct an array `ans` of length `n`, such that, for each
* _ index `i`, the bitwise `OR` of `ans[i]` and `ans[i] + 1` is equal to
* _ `nums[i]`, i.e. `ans[i] OR (ans[i] + 1) == nums[i]`.
* Additionally, you must minimize each value of `ans[i]` in the resulting array.
* If it is not possible to find such a value for `ans[i]` that satisfies the
* _ condition, then set `ans[i] = -1`.
*
* Example 1:
* Input: nums = [2,3,5,7]
* Output: [-1,1,4,3]
* Explanation:
* For i = 0, as there is no value for ans[0] that satisfies ans[0] OR (ans[0] + 1) = 2, so ans[0] = -1.
* For i = 1, the smallest ans[1] that satisfies ans[1] OR (ans[1] + 1) = 3 is 1, because 1 OR (1 + 1) = 3.
* For i = 2, the smallest ans[2] that satisfies ans[2] OR (ans[2] + 1) = 5 is 4, because 4 OR (4 + 1) = 5.
* For i = 3, the smallest ans[3] that satisfies ans[3] OR (ans[3] + 1) = 7 is 3, because 3 OR (3 + 1) = 7.
*
* Example 2:
* Input: nums = [11,13,31]
* Output: [9,12,15]
* Explanation:
* For i = 0, the smallest ans[0] that satisfies ans[0] OR (ans[0] + 1) = 11 is 9, because 9 OR (9 + 1) = 11.
* For i = 1, the smallest ans[1] that satisfies ans[1] OR (ans[1] + 1) = 13 is 12, because 12 OR (12 + 1) = 13.
* For i = 2, the smallest ans[2] that satisfies ans[2] OR (ans[2] + 1) = 31 is 15, because 15 OR (15 + 1) = 31.
*
* Constraints:
* • `1 <= nums.length <= 100`
* • `2 <= nums[i] <= 1000`
* • `nums[i] is a prime number.`
* 
****************************************/

// import java.util.List;

class Solution {
    // For each prime x, we want the smallest ans such that ans | (ans + 1) == x.
    // Adding 1 flips the first 0 bit (from LSB) to 1 and clears lower bits.
    // We scan x's bits from LSB upward; while bits are 1, we try clearing them.
    // The smallest valid ans is x minus the lowest removable 1 bit.
    // Time: O(n log m), Space: O(1) extra.
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            int candidate = -1;
            int bit = 1;

            // Scan bits from least significant upward
            // While the current bit in x is 1, we can clear it
            while ((x & bit) != 0) {
                candidate = x - bit;
                bit <<= 1;
            }

            result[i] = candidate;
        }
        return result;
    }
}
