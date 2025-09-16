// Source: https://leetcode.com/problems/replace-non-coprime-numbers-in-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-15
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.82 MB Beats 96.29%

/****************************************
* 
* You are given an array of integers `nums`. Perform the following steps:
* 1. Find any two adjacent numbers in `nums` that are non-coprime.
* 2. If no such numbers are found, stop the process.
* 3. Otherwise, delete the two numbers and replace them with their
* __ LCM (Least Common Multiple).
* 4. Repeat this process as long as you keep finding two adjacent
* __ non-coprime numbers.
* Return the final modified array. It can be shown that replacing adjacent
* _ non-coprime numbers in any arbitrary order will lead to the same result.
* The test cases are generated such that the values in the final array are less
* _ than or equal to `10^8`.
* Two values `x` and `y` are non-coprime if `GCD(x, y) > 1` where `GCD(x, y)` is
* _ the Greatest Common Divisor of `x` and `y`.
*
* Example 1:
* Input: nums = [6,4,3,2,7,6,2]
* Output: [12,7,6]
* Explanation:
* - (6, 4) are non-coprime with LCM(6, 4) = 12. Now, nums = [12,3,2,7,6,2].
* - (12, 3) are non-coprime with LCM(12, 3) = 12. Now, nums = [12,2,7,6,2].
* - (12, 2) are non-coprime with LCM(12, 2) = 12. Now, nums = [12,7,6,2].
* - (6, 2) are non-coprime with LCM(6, 2) = 6. Now, nums = [12,7,6].
* There are no more adjacent non-coprime numbers in nums.
* Thus, the final modified array is [12,7,6].
* Note that there are other ways to obtain the same resultant array.
*
* Example 2:
* Input: nums = [2,2,1,1,3,3,3]
* Output: [2,1,1,3]
* Explanation:
* - (3, 3) are non-coprime with LCM(3, 3) = 3. Now, nums = [2,2,1,1,3,3].
* - (3, 3) are non-coprime with LCM(3, 3) = 3. Now, nums = [2,2,1,1,3].
* - (2, 2) are non-coprime with LCM(2, 2) = 2. Now, nums = [2,1,1,3].
* There are no more adjacent non-coprime numbers in nums.
* Thus, the final modified array is [2,1,1,3].
* Note that there are other ways to obtain the same resultant array.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^5
* • The test cases are generated such that the values in the final array are
* _ less than or equal to `10^8`.
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // This solution uses a stack to greedily merge adjacent non-coprime nums.
    // Each number is pushed, and while the top two share GCD > 1, we replace
    // them with their LCM and repeat. Order of merges does not matter, so this
    // greedy approach always yields the correct result. Time: O(n log M) where
    // M = max(nums[i]) due to GCD. Space: O(n) for the stack.
    public List<Integer> replaceNonCoprimes(int[] nums) {
        List<Integer> stack = new ArrayList<>();
        for (int num : nums) {
            stack.add(num);
            // Try merging with previous numbers as long as GCD > 1
            while (stack.size() > 1) {
                int a = stack.get(stack.size() - 2);
                int b = stack.get(stack.size() - 1);
                int g = gcd(a, b);
                if (g == 1) break; // coprime → stop merging
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                long lcm = (long)a / g * b; // safe from overflow
                stack.add((int)lcm);
            }
        }
        return stack;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
}
