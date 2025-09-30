// Source: https://leetcode.com/problems/find-triangular-sum-of-an-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-29
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 44.50 MB Beats 70.44%

/****************************************
* 
* You are given a 0-indexed integer array `nums`, where `nums[i]` is a digit
* _ between `0` and `9` (inclusive).
* The triangular sum of `nums` is the value of the only element present in
* _ `nums` after the following process terminates:
* 1. Let `nums` comprise of `n` elements. If `n == 1`, end the process.
* __ Otherwise, create a new 0-indexed integer array `newNums` of length `n - 1`.
* 2. For each index `i`, where `0 <= i < n - 1`, assign the value of
* __ `newNums[i]` as `(nums[i] + nums[i+1]) % 10`, where `%` denotes modulo operator.
* 3. Replace the array `nums` with `newNums`.
* 4. Repeat the entire process starting from step 1.
* Return the triangular sum of `nums`.
*
* Example 1:
* [image: https://assets.leetcode.com/uploads/2022/02/22/ex1drawio.png]
* Input: nums = [1,2,3,4,5]
* Output: 8
* Explanation:
* The above diagram depicts the process from which we obtain the triangular sum of the array.
*
* Example 2:
* Input: nums = [5]
* Output: 5
* Explanation:
* Since there is only one element in nums, the triangular sum is the value of that element itself.
*
* Constraints:
* • 1 <= nums.length <= 1000
* • 0 <= nums[i] <= 9
* 
****************************************/

import java.util.Arrays;

class Solution {
    // This solution computes the triangular sum using binomial coefficients
    // modulo 10, instead of simulating the O(n^2) summing process. It uses
    // Lucas' theorem for mod 5, a bitwise trick for mod 2, and the Chinese
    // Remainder Theorem to combine results into mod 10. Time complexity is
    // O(n log n), space complexity is O(1) (ignoring the fixed C5 table).

    // Precompute Pascal's triangle mod 5 for values up to 4
    private static final int[][] C5 = buildPascalMod5();

    public int triangularSum(int[] nums) {
        int n = nums.length;
        int N = n - 1;
        int result = 0;

        // Each element contributes with binomial coefficient C(N, i) mod 10
        for (int i = 0; i < n; i++) {
            int mod2 = combMod2(N, i);    // C(N, i) mod 2
            int mod5 = combMod5(N, i);    // C(N, i) mod 5
            int mod10 = combineCRT(mod2, mod5); // C(N, i) mod 10
            result = (result + mod10 * nums[i]) % 10;
        }
        return result;
    }

    // Compute C(N, i) mod 2: odd iff no carries in i + (N - i)
    private static int combMod2(int N, int i) {
        return ((i & (N - i)) == 0) ? 1 : 0;
    }

    // Compute C(N, i) mod 5 using Lucas' theorem
    private static int combMod5(int N, int i) {
        int result = 1;
        int n = N, k = i;
        while (n > 0 || k > 0) {
            int nd = n % 5, kd = k % 5;
            if (kd > nd) return 0;
            result = (result * C5[nd][kd]) % 5;
            n /= 5;
            k /= 5;
        }
        return result;
    }

    // Combine residues using Chinese Remainder Theorem
    private static int combineCRT(int r2, int r5) {
        int r = r5;                  // Candidate in [0..4]
        if ((r & 1) != r2) r += 5;   // Adjust to match parity
        return r;                    // Final result in [0..9]
    }

    // Build Pascal's triangle mod 5 for a,b <= 4
    private static int[][] buildPascalMod5() {
        int[][] c = new int[5][5];
        for (int a = 0; a < 5; a++) {
            c[a][0] = c[a][a] = 1;
            for (int b = 1; b < a; b++) {
                c[a][b] = (c[a - 1][b - 1] + c[a - 1][b]) % 5;
            }
        }
        return c;
    }

    // Reference O(n^2) check (slower, not used in final solution)
    static int triangularSumSlow(int[] nums) {
        int[] arr = Arrays.copyOf(nums, nums.length);
        for (int len = arr.length; len > 1; len--) {
            for (int j = 0; j < len - 1; j++) {
                arr[j] = (arr[j] + arr[j + 1]) % 10;
            }
        }
        return arr[0];
    }
}
