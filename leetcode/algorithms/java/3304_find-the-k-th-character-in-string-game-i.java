// Source: https://leetcode.com/problems/find-the-k-th-character-in-string-game-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-02
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.92 MB Beats 73.52%

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
    // This solution avoids building the full string by simulating the process
    // through bit manipulation. Each round effectively doubles the string length,
    // forming a tree structure. We trace back how many increments from 'a' are
    // needed to reach the k-th character using the highest power of 2 ≤ k.
    // Time: O(log k), since we reduce k by powers of 2 each step.
    // Space: O(1), constant extra space is used.
    public char kthCharacter(int k) {
        int incrementCount = 0;
        int mostSignificantBit;

        while (k != 1) {
            // Find the position of the most significant bit in k
            mostSignificantBit = 31 - Integer.numberOfLeadingZeros(k);

            // If k is a power of two, decrement the bit position by 1
            if ((1 << mostSignificantBit) == k) {
                mostSignificantBit--;
            }

            // Subtract the largest power of two less than k
            k -= (1 << mostSignificantBit);
            incrementCount++;
        }

        return (char) ('a' + incrementCount);
    }
}
