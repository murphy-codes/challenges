// Source: https://leetcode.com/problems/maximum-number-of-distinct-elements-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-19
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 41.92 MB Beats 97.78%

/****************************************
* 
* You are given a string `s` of even length consisting of digits from `0` to `9`,
* _ and two integers `a` and `b`.
* You can apply either of the following two operations any number of times and
* _ in any order on `s`:
* • Add `a` to all odd indices of `s` (0-indexed). Digits post `9` are cycled
* __ back to `0`. For example, if `s = "3456"` and `a = 5`, `s` becomes `"3951"`.
* • Rotate `s` to the right by `b` positions. For example, if `s = "3456"` and
* __ `b = 1`, `s` becomes `"6345"`.
* Return the lexicographically smallest string you can obtain by applying the
* _ above operations any number of times on `s`.
* A string `a` is lexicographically smaller than a string `b` (of the same
* _ length) if in the first position where `a` and `b` differ, string `a` has
* _ a letter that appears earlier in the alphabet than the corresponding letter
* _ in `b`. For example, `"0158"` is lexicographically smaller than `"0190"`
* _ because the first position they differ is at the third letter, and `'5'`
* _ comes before `'9'`.
*
* Example 1:
* Input: s = "5525", a = 9, b = 2
* Output: "2050"
* Explanation: We can apply the following operations:
* Start:  "5525"
* Rotate: "2555"
* Add:    "2454"
* Add:    "2353"
* Rotate: "5323"
* Add:    "5222"
* Add:    "5121"
* Rotate: "2151"
* Add:    "2050"​​​​​
* There is no way to obtain a string that is lexicographically smaller than "2050".
*
* Example 2:
* Input: s = "74", a = 5, b = 1
* Output: "24"
* Explanation: We can apply the following operations:
* Start:  "74"
* Rotate: "47"
* ​​​​​​​Add:    "42"
* ​​​​​​​Rotate: "24"​​​​​​​​​​​​
* There is no way to obtain a string that is lexicographically smaller than "24".
*
* Example 3:
* Input: s = "0011", a = 4, b = 2
* Output: "0011"
* Explanation: There are no sequence of operations that will give us a lexicographically smaller string than "0011".
*
* Constraints:
* • `2 <= s.length <= 100`
* • `s.length` is even.
* • `s` consists of digits from `0` to `9` only.
* • `1 <= a <= 9`
* • `1 <= b <= s.length - 1`
* 
****************************************/

class Solution {
    // This solution uses math-based optimization with gcd to reduce state space.
    // Rotations repeat every gcd(b, n), and digit additions cycle every gcd(a, 10).
    // For each unique rotation, we simulate valid digit additions to minimize lexic.
    // Time Complexity: O(n^2) due to rotation & modification checks.
    // Space Complexity: O(n) for character arrays and string copies.
    public String findLexSmallestString(String original, int addStep, int rotateStep) {
        char[] chars = original.toCharArray();
        int n = chars.length;
        char[] rotated = new char[n];
        int rotationCycle = gcd(rotateStep, n);
        int addCycle = gcd(addStep, 10);
        String smallest = null;

        // Try each unique rotation based on gcd(b, n)
        for (int i = 0; i < n; i += rotationCycle) {
            // Rotate the string by i positions to the right
            System.arraycopy(chars, i, rotated, 0, n - i);
            System.arraycopy(chars, 0, rotated, n - i, i);

            // Modify odd indices (1, 3, 5...) by addStep cyclically
            applyAddOperation(rotated, 1, addCycle);
            
            // If rotation step is odd, even indices (0, 2, 4...) can also vary
            if (rotationCycle % 2 == 1) {
                applyAddOperation(rotated, 0, addCycle);
            }

            // Check if this rotation yields a smaller string
            String candidate = new String(rotated);
            if (smallest == null || candidate.compareTo(smallest) < 0) {
                smallest = candidate;
            }
        }

        return smallest;
    }

    // Applies the addition operation to every other digit starting at index 'start'
    private void applyAddOperation(char[] arr, int start, int addCycle) {
        int currentDigit = arr[start] - '0';
        int increment = currentDigit % addCycle - currentDigit + 10;
        for (int i = start; i < arr.length; i += 2) {
            arr[i] = (char) ('0' + (arr[i] - '0' + increment) % 10);
        }
    }

    // Helper: Compute greatest common divisor (GCD)
    private int gcd(int a, int b) {
        while (a != 0) {
            int tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }
}
