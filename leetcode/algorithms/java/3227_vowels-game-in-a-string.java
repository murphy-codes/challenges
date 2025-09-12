// Source: https://leetcode.com/problems/vowels-game-in-a-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-12
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 45.70 MB Beats 45.15%

/****************************************
* 
* Alice and Bob are playing a game on a string.
* You are given a string `s`, Alice and Bob will take turns playing the following
* _ game where Alice starts first:
* • On Alice's turn, she has to remove any non-empty substring from `s` that
* __ contains an odd number of vowels.
* • On Bob's turn, he has to remove any non-empty substring from `s` that
* __ contains an even number of vowels.
* The first player who cannot make a move on their turn loses the game.
* _ We assume that both Alice and Bob play optimally.
* Return `true` if Alice wins the game, and `false` otherwise.
* The English vowels are: `a`, `e`, `i`, `o`, and `u`.
*
* Example 1:
* Input: s = "leetcoder"
* Output: true
* Explanation:
* Alice can win the game as follows:
* Alice plays first, she can delete the underlined substring in s = "leetcoder" which contains 3 vowels. The resulting string is s = "der".
* Bob plays second, he can delete the underlined substring in s = "der" which contains 0 vowels. The resulting string is s = "er".
* Alice plays third, she can delete the whole string s = "er" which contains 1 vowel.
* Bob plays fourth, since the string is empty, there is no valid play for Bob. So Alice wins the game.
*
* Example 2:
* Input: s = "bbcd"
* Output: false
* Explanation:
* There is no valid play for Alice in her first turn, so Alice loses the game.
*
* Constraints:
* • 1 <= s.length <= 10^5
* • `s` consists only of lowercase English letters.
* 
****************************************/

class Solution {
    // This solution checks each character in the string directly using
    // charAt() and a switch statement. If a vowel is found, we return
    // true immediately since Alice can make a valid move. If no vowels
    // exist, Alice loses and we return false. Time complexity is O(n)
    // since we may scan the whole string, and space complexity is O(1).
    public boolean doesAliceWin(String str) {
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) { // If we encounter any vowel, Alice wins immediately
                case 'a', 'e', 'i', 'o', 'u': return true; 
            }
        }
        // No vowels found → Alice cannot move → she loses
        return false;
    }
}