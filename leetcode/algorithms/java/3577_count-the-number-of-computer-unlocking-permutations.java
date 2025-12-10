// Source: https://leetcode.com/problems/count-the-number-of-computer-unlocking-permutations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-09
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 92.00 MB Beats 36.00%

/****************************************
* 
* You are given an array `complexity` of length `n`.
* There are `n` locked computers in a room with labels from 0 to `n - 1`, each
* _ with its own unique password. The password of the computer `i` has a
* _ complexity `complexity[i]`.
* The password for the computer labeled 0 is already decrypted and serves as
* _ the root. All other computers must be unlocked using it or another previously
* _ unlocked computer, following this information:
* • You can decrypt the password for the computer `i` using the password for
* __ computer `j`, where `j` is any integer less than `i` with a lower complexity.
* __ (i.e. `j < i` and `complexity[j] < complexity[i]`)
* • To decrypt the password for computer `i`, you must have already unlocked a
* __ computer `j` such that `j < i` and `complexity[j] < complexity[i]`.
* Find the number of permutations of [0, 1, 2, ..., (n - 1)] that represent a
* _ valid order in which the computers can be unlocked, starting from computer 0
* _ as the only initially unlocked one.
* Since the answer may be large, return it modulo `10^9 + 7`.
* Note that the password for the computer with label 0 is decrypted, and not
* _ the computer with the first position in the permutation.
*
* Example 1:
* Input: complexity = [1,2,3]
* Output: 2
* Explanation:
* The valid permutations are:
* • [0, 1, 2]
* _ • Unlock computer 0 first with root password.
* _ • Unlock computer 1 with password of computer 0 since complexity[0] < complexity[1].
* _ • Unlock computer 2 with password of computer 1 since complexity[1] < complexity[2].
* • [0, 2, 1]
* _ • Unlock computer 0 first with root password.
* _ • Unlock computer 2 with password of computer 0 since complexity[0] < complexity[2].
* _ • Unlock computer 1 with password of computer 0 since complexity[0] < complexity[1].
*
* Example 2:
* Input: complexity = [3,3,3,4,4,4]
* Output: 0
* Explanation:
* There are no possible permutations which can unlock all computers.
*
* Constraints:
* • `2 <= complexity.length <= 10^5`
* • `1 <= complexity[i] <= 10^9`
* 
****************************************/

class Solution {
    // Computer 0 must be unlocked first and must have the strictly smallest
    // complexity; otherwise no other computer can use it as a prerequisite and
    // unlocking is impossible. If this minimum check passes, computer 0 can
    // unlock every other index, so all remaining (n-1)! permutations are valid.
    // We compute factorial(n-1) in O(n) time with O(1) extra space.

    private static final int MOD = 1_000_000_007;

    public int countPermutations(int[] complexity) {
        int n = complexity.length;

        // Check that complexity[0] is the unique minimum
        for (int i = 1; i < n; i++) {
            if (complexity[i] <= complexity[0]) {
                return 0;
            }
        }

        // Compute factorial(n - 1) % MOD
        long ans = 1;
        for (int i = 2; i < n; i++) {
            ans = (ans * i) % MOD;
        }

        return (int) ans;
    }
}
