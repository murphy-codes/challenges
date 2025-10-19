// Source: https://leetcode.com/problems/maximum-number-of-distinct-elements-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-19
// At the time of submission:
//   Runtime 64 ms Beats 77.78%
//   Memory 47.63 MB Beats 73.33%

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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    // Use BFS to explore all reachable strings by applying two operations:
    // (1) Add 'a' to all digits at odd indices (mod 10), and (2) rotate right by 'b'.
    // Maintain a visited set to avoid reprocessing states. Track the smallest
    // lexicographic string encountered. Time: O(n * 100) from limited state space;
    // Space: O(n * 100) for storing visited configurations.
    public String findLexSmallestString(String s, int a, int b) {
        HashSet<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        String smallest = s;

        queue.offer(s);
        visited.add(s);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.compareTo(smallest) < 0) {
                smallest = curr;
            }

            // Operation 1: Add 'a' to all odd indices
            char[] chars = curr.toCharArray();
            for (int i = 1; i < chars.length; i += 2) {
                chars[i] = (char) ((chars[i] - '0' + a) % 10 + '0');
            }
            String addOp = new String(chars);
            if (visited.add(addOp)) {
                queue.offer(addOp);
            }

            // Operation 2: Rotate string to the right by 'b' positions
            String rotateOp = curr.substring(curr.length() - b) + curr.substring(0, curr.length() - b);
            if (visited.add(rotateOp)) {
                queue.offer(rotateOp);
            }
        }

        return smallest;
    }
}
