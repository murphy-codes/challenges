// Source: https://leetcode.com/problems/find-the-k-th-character-in-string-game-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-02
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 43.31 MB Beats 45.12%

/****************************************
* 
* Alice and Bob are playing a game. Initially, Alice has a string `word = "a"`.
* You are given a positive integer `k`. You are also given an integer array
* _ `operations`, where `operations[i]` represents the type of the `i^th` operation.
* Now Bob will ask Alice to perform all operations in sequence:
* • If `operations[i] == 0`, append a copy of `word` to itself.
* • If `operations[i] == 1`, generate a new string by changing each character in
* __ `word` to its next character in the English alphabet, and append it to the
* __ original word. For example, performing the operation on `"c"` generates
* __ `"cd"` and performing the operation on "`zb"` generates `"zbac"`.
* Return the value of the `k^th` character in `word` after performing all the operations.
* Note that the character `'z'` can be changed to `'a'` in the second type of operation.
*
* Example 1:
* Input: k = 5, operations = [0,0,0]
* Output: "a"
* Explanation:
* Initially, word == "a". Alice performs the three operations as follows:
* Appends "a" to "a", word becomes "aa".
* Appends "aa" to "aa", word becomes "aaaa".
* Appends "aaaa" to "aaaa", word becomes "aaaaaaaa".
*
* Example 2:
* Input: k = 10, operations = [0,1,0,1]
* Output: "b"
* Explanation:
* Initially, word == "a". Alice performs the four operations as follows:
* Appends "a" to "a", word becomes "aa".
* Appends "bb" to "aa", word becomes "aabb".
* Appends "aabb" to "aabb", word becomes "aabbaabb".
* Appends "bbccbbcc" to "aabbaabb", word becomes "aabbaabbbbccbbcc".
*
*
* Constraints:
* • 1 <= k <= 10^14
* • 1 <= operations.length <= 100
* • `operations[i]` is either 0 or 1.
* • The input is generated such that `word` has at least `k` characters after all operations.
* 
****************************************/

class Solution {
    // Forward simulate all operations, tracking the string length after each step.
    // In reverse, trace back whether the k-th character originated in the original
    // or appended half at each step. Increment shift count if it came from the
    // appended half of a type-1 operation. Finally, apply all shifts to 'a',
    // wrapping around with modulo 26 to stay within 'a'-'z'.
    public char kthCharacter(long k, int[] operations) {
        int n = operations.length;
        long[] lengths = new long[n + 1];
        lengths[0] = 1;

        // Forward simulation to store lengths after each operation
        for (int i = 0; i < n; i++) {
            lengths[i + 1] = Math.min(k, lengths[i] * 2);

            // Prevent overflow since k can be up to 10^14
            if (lengths[i + 1] > k) {
                lengths[i + 1] = k;
            }
        }

        int shift = 0;

        // Reverse simulation to trace back the origin of the k-th character
        for (int i = n - 1; i >= 0; i--) {
            if (k > lengths[i]) {
                k -= lengths[i]; // In the appended half
                if (operations[i] == 1) {
                    shift++; // Only type 1 applies shift
                }
            } else {
                // In the original half; no change needed
            }
        }

        // Apply the total shift to 'a', wrap using modulo 26
        return (char) ('a' + (shift % 26));
    }
}
