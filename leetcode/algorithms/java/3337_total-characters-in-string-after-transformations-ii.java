// Source: https://leetcode.com/problems/total-characters-in-string-after-transformations-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-13
// At the time of submission:
//   Runtime 75 ms Beats 78.38%
//   Memory 45.30 MB Beats 89.19%

/****************************************
* 
* You are given a string `s` consisting of lowercase English letters, an integer
* _ `t` representing the number of transformations to perform, and an array `nums`
* _ of size 26. In one transformation, every character in `s` is replaced
* _ according to the following rules:
* • Replace `s[i]` with the next `nums[s[i] - 'a']` consecutive characters in
* _ the alphabet. For example, if `s[i] = 'a'` and `nums[0] = 3`, the character
* _ `'a'` transforms into the next 3 consecutive characters ahead of it, which
* _ results in `"bcd"`.
* • The transformation wraps around the alphabet if it exceeds `'z'`. For example,
* _ if `s[i] = 'y'` and `nums[24] = 3`, the character `'y'` transforms into the
* _ next 3 consecutive characters ahead of it, which results in `"zab"`.
* Return the length of the resulting string after exactly `t` transformations.
* Since the answer may be very large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: s = "abcyy", t = 2, nums = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2]
* Output: 7
* Explanation:
* First Transformation (t = 1):
* a' becomes 'b' as nums[0] == 1
* b' becomes 'c' as nums[1] == 1
* c' becomes 'd' as nums[2] == 1
* y' becomes 'z' as nums[24] == 1
* y' becomes 'z' as nums[24] == 1
* String after the first transformation: "bcdzz"
* Second Transformation (t = 2):
* b' becomes 'c' as nums[1] == 1
* c' becomes 'd' as nums[2] == 1
* d' becomes 'e' as nums[3] == 1
* z' becomes 'ab' as nums[25] == 2
* z' becomes 'ab' as nums[25] == 2
* String after the second transformation: "cdeabab"
* Final Length of the string: The string is "cdeabab", which has 7 characters.
*
* Example 2:
* Input: s = "azbk", t = 1, nums = [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]
* Output: 8
* Explanation:
* First Transformation (t = 1):
* a' becomes 'bc' as nums[0] == 2
* z' becomes 'ab' as nums[25] == 2
* b' becomes 'cd' as nums[1] == 2
* k' becomes 'lm' as nums[10] == 2
* String after the first transformation: "bcabcdlm"
* Final Length of the string: The string is "bcabcdlm", which has 8 characters.
*
* Constraints:
* • 1 <= s.length <= 10^5
* • s consists only of lowercase English letters.
* • 1 <= t <= 10^9
* • nums.length == 26
* • 1 <= nums[i] <= 25
* 
****************************************/

import java.util.List;

class Solution {
    // Use matrix exponentiation to simulate transformations efficiently.
    // Each letter transforms into a linear combination of others (via nums).
    // Represent this as a 26x26 matrix and raise it to the power of t.
    // Multiply initial character counts by the matrix to get final counts.
    // Time: O(26^3 * log t), Space: O(26^2) → handles large t efficiently.

    private static final int MOD = 1_000_000_007;
    private static final int ALPHABET = 26;

    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        // Step 1: Build initial count vector
        long[] count = new long[ALPHABET];
        for (char c : s.toCharArray())
            count[c - 'a']++;

        // Step 2: Build transformation matrix
        long[][] trans = new long[ALPHABET][ALPHABET];
        for (int from = 0; from < ALPHABET; from++) {
            int len = nums.get(from);
            for (int j = 1; j <= len; j++) {
                int to = (from + j) % ALPHABET;
                trans[to][from] = (trans[to][from] + 1) % MOD;
            }
        }

        // Step 3: Exponentiate the matrix
        long[][] powered = matrixPow(trans, t);

        // Step 4: Multiply matrix with initial count vector
        long[] result = new long[ALPHABET];
        for (int i = 0; i < ALPHABET; i++) {
            for (int j = 0; j < ALPHABET; j++) {
                result[i] = (result[i] + powered[i][j] * count[j]) % MOD;
            }
        }

        // Step 5: Sum result
        long total = 0;
        for (long v : result)
            total = (total + v) % MOD;

        return (int) total;
    }

    // Helper: matrix exponentiation
    private long[][] matrixPow(long[][] matrix, int exp) {
        long[][] result = identityMatrix(ALPHABET);
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = matrixMultiply(result, matrix);
            matrix = matrixMultiply(matrix, matrix);
            exp >>= 1;
        }
        return result;
    }

    // Helper: matrix multiplication
    private long[][] matrixMultiply(long[][] A, long[][] B) {
        long[][] res = new long[ALPHABET][ALPHABET];
        for (int i = 0; i < ALPHABET; i++) {
            for (int k = 0; k < ALPHABET; k++) {
                if (A[i][k] == 0) continue; // Skip multiplications by 0
                for (int j = 0; j < ALPHABET; j++) {
                    res[i][j] = (res[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }
        return res;
    }

    // Helper: identity matrix
    private long[][] identityMatrix(int size) {
        long[][] id = new long[size][size];
        for (int i = 0; i < size; i++)
            id[i][i] = 1;
        return id;
    }
}