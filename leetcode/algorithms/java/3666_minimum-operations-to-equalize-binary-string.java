// Source: https://leetcode.com/problems/minimum-operations-to-equalize-binary-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-27
// At the time of submission:
//   Runtime 509 ms Beats 53.33%
//   Memory 114.16 MB Beats 50.00%

/****************************************
* 
You are given a binary string `s`, and an integer `k`.
In one operation, you must choose exactly `k` different indices and flip 
_ each `'0'` to `'1'` and each `'1'` to `'0'`.
Return the minimum number of operations required to make all characters in 
_ the string equal to `'1'`. If it is not possible, return -1.

Example 1:
Input: s = "110", k = 1
Output: 1
Explanation:
There is one '0' in s.
Since k = 1, we can flip it directly in one operation.

Example 2:
Input: s = "0101", k = 3
Output: 2
Explanation:
One optimal set of operations choosing k = 3 indices in each operation is:
Operation 1: Flip indices [0, 1, 3]. s changes from "0101" to "1000".
Operation 2: Flip indices [1, 2, 3]. s changes from "1000" to "1111".
Thus, the minimum number of operations is 2.

Example 3:
Input: s = "101", k = 2
Output: -1
Explanation:
Since k = 2 and s has only one '0', it is impossible to flip exactly k indices to make all '1'. Hence, the answer is -1.

Constraints:
• `1 <= s.length <= 10​​​​​​​^5`
• `s[i]` is either `'0'` or `'1'`.
• `1 <= k <= s.length`
* 
****************************************/

class Solution {
    public int minOperations(String s, int k) {
        int n = s.length();
        int zeros = 0;
        for (char c : s.toCharArray()) {
            if (c == '0') zeros++;
        }

        if (zeros == 0) return 0;

        // Distance array
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);

        // Unvisited states grouped by parity
        TreeSet<Integer> even = new TreeSet<>();
        TreeSet<Integer> odd  = new TreeSet<>();

        for (int i = 0; i <= n; i++) {
            if ((i & 1) == 0) even.add(i);
            else odd.add(i);
        }

        Queue<Integer> q = new ArrayDeque<>();
        q.add(zeros);
        dist[zeros] = 0;

        if ((zeros & 1) == 0) even.remove(zeros);
        else odd.remove(zeros);

        while (!q.isEmpty()) {
            int z = q.poll();
            int d = dist[z];

            int minI = Math.max(0, k - (n - z));
            int maxI = Math.min(k, z);

            int L = z + k - 2 * maxI;
            int R = z + k - 2 * minI;

            TreeSet<Integer> target =
                ((z + k) & 1) == 0 ? even : odd;

            for (Integer nz = target.ceiling(L);
                 nz != null && nz <= R;
                 nz = target.ceiling(L)) {

                target.remove(nz);
                dist[nz] = d + 1;
                if (nz == 0) return d + 1;
                q.add(nz);
            }
        }

        return -1;
    }
}