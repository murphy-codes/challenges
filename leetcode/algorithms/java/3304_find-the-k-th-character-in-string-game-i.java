// Source: https://leetcode.com/problems/find-the-k-th-character-in-string-game-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-02
// At the time of submission:
//   Runtime 5 ms Beats 33.13%
//   Memory 42.21 MB Beats 66.34%

/****************************************
* 
* Alice and Bob are playing a game. Initially, Alice has a string `word = "a"`.
* You are given a positive integer `k`.
* Now Bob will ask Alice to perform the following operation forever:
* • Generate a new string by changing each character in `word` to its next
* _ character in the English alphabet, and append it to the original `word`.
* For example, performing the operation on `"c"` generates `"cd"` and performing
* _ the operation on `"zb"` generates `"zbac"`.
* Return the value of the `k^th` character in `word`, after enough operations have
* _ been done for `word` to have at least `k` characters.
* Note that the character `'z'` can be changed to `'a'` in the operation.
*
* Example 1:
* Input: k = 5
* Output: "b"
* Explanation:
* Initially, `word = "a"`. We need to do the operation three times:
* Generated string is `"b"`, word becomes `"ab"`.
* Generated string is `"bc"`, word becomes `"abbc"`.
* Generated string is `"bccd"`, word becomes `"abbcbccd"`.
*
* Example 2:
* Input: k = 10
* Output: "c"
*
* Constraints:
* • 1 <= k <= 500
* 
****************************************/

class Solution {
    // Simulate the string construction step-by-step by doubling and incrementing.
    // For each round, generate the incremented copy of the current string and append.
    // Stop when the string length reaches or exceeds k, then return k-th char.
    // Time: O(k), since we build the string up to k characters.
    // Space: O(k), since we store the string in a StringBuilder.
    public char kthCharacter(int k) {
        StringBuilder word = new StringBuilder("a");
        while (word.length() < k) {
            int len = word.length();
            for (int i = 0; i < len; i++) {
                char ch = word.charAt(i);
                char next = (char) ((ch == 'z') ? 'a' : (ch + 1));
                word.append(next);
            }
        }
        return word.charAt(k - 1);
    }
}
