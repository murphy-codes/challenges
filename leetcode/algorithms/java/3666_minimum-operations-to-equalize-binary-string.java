// Source: https://leetcode.com/problems/minimum-operations-to-equalize-binary-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-27
// At the time of submission:
//   Runtime 6 ms Beats 93.33%
//   Memory 47.29 MB Beats 96.67%

/****************************************
* 
* You are given a binary string `s`, and an integer `k`.
* In one operation, you must choose exactly `k` different indices and flip
* _ each `'0'` to `'1'` and each `'1'` to `'0'`.
* Return the minimum number of operations required to make all characters in
* _ the string equal to `'1'`. If it is not possible, return -1.
*
* Example 1:
* Input: s = "110", k = 1
* Output: 1
* Explanation:
* There is one '0' in s.
* Since k = 1, we can flip it directly in one operation.
*
* Example 2:
* Input: s = "0101", k = 3
* Output: 2
* Explanation:
* One optimal set of operations choosing k = 3 indices in each operation is:
* Operation 1: Flip indices [0, 1, 3]. s changes from "0101" to "1000".
* Operation 2: Flip indices [1, 2, 3]. s changes from "1000" to "1111".
* Thus, the minimum number of operations is 2.
*
* Example 3:
* Input: s = "101", k = 2
* Output: -1
* Explanation:
* Since k = 2 and s has only one '0', it is impossible to flip exactly k indices to make all '1'. Hence, the answer is -1.
*
* Constraints:
* • `1 <= s.length <= 10​​​​​​​^5`
* • `s[i]` is either `'0'` or `'1'`.
* • `1 <= k <= s.length`
* 
****************************************/

class Solution {
    // This solution reduces the problem to counting zeros and analyzing parity.
    // Each operation flips k bits, changing the zero count deterministically.
    // The minimum number of operations is derived using ceiling bounds and parity.
    // No BFS or simulation is needed, yielding an O(n) time solution.
    // Space usage is O(1), aside from input storage.
    public int minOperations(String s, int k) {
        int n = s.length();

        // Count number of zeros
        int zeroCount = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '0') zeroCount++;
        }

        // Already all ones
        if (zeroCount == 0) return 0;

        // Special case: must flip all bits each time
        if (k == n) {
            return zeroCount == n ? 1 : -1;
        }

        int answer = Integer.MAX_VALUE;

        // Case 1: Even number of operations possible
        if (zeroCount % 2 == 0) {
            int minOps = Math.max(
                (zeroCount + k - 1) / k,
                (zeroCount + (n - k) - 1) / (n - k)
            );
            answer = (minOps % 2 == 0) ? minOps : minOps + 1;
        }

        // Case 2: Odd number of operations possible
        if (zeroCount % 2 == k % 2) {
            int minOps = Math.max(
                (zeroCount + k - 1) / k,
                ((n - zeroCount) + (n - k) - 1) / (n - k)
            );
            minOps = (minOps % 2 != 0) ? minOps : minOps + 1;
            answer = Math.min(answer, minOps);
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}