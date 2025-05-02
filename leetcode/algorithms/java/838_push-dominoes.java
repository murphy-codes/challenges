// Source: https://leetcode.com/problems/push-dominoes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-01
// At the time of submission:
//   Runtime 28 ms Beats 30.94%
//   Memory 45.38 MB Beats 91.70%

/****************************************
* 
* There are `n` dominoes in a line, and we place each domino vertically
* _ upright. In the beginning, we simultaneously push some of the dominoes
* _ either to the left or to the right.
* After each second, each domino that is falling to the left pushes the
* _ adjacent domino on the left. Similarly, the dominoes falling to the
* _ right push their adjacent dominoes standing on the right.
* When a vertical domino has dominoes falling on it from both sides, it
* _ stays still due to the balance of the forces.
* For the purposes of this question, we will consider that a falling domino
* _ expends no additional force to a falling or already fallen domino.
* You are given a string `dominoes` representing the initial state where:
* • `dominoes[i] = 'L'`, if the `i^th` domino has been pushed to the left,
* • `dominoes[i] = 'R'`, if the `i^th` domino has been pushed to the right, and
* • `dominoes[i] = '.'`, if the `i^th` domino has not been pushed.
* Return a string representing the final state.
*
* Example 1:
* Input: dominoes = "RR.L"
* Output: "RR.L"
* Explanation: The first domino expends no additional force on the second domino.
*
* Example 2:
* Input: dominoes = ".L.R...LR..L.."
* Output: "LL.RR.LLRRLL.."
*
* Constraints:
* • n == dominoes.length
* • 1 <= n <= 10 ^5
* • `dominoes[i]` is either `'L'`, `'R'`, or `'.'`.
* 
****************************************/

class Solution {
    // Time: O(n), where n is the length of the domino string.
    // We iterate through the dominoes twice (left to right and right to left).
    // Space: O(n) for the forces array. Final string is built in linear time.
    public String pushDominoes(String dominoes) {
        int n = dominoes.length();
        int[] forces = new int[n];
        int force = 0;

        // Left to right pass: apply positive force from 'R'
        for (int i = 0; i < n; i++) {
            if (dominoes.charAt(i) == 'R') {
                force = n;
            } else if (dominoes.charAt(i) == 'L') {
                force = 0;
            } else {
                force = Math.max(force - 1, 0);
            }
            forces[i] += force;
        }

        // Right to left pass: apply negative force from 'L'
        force = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (dominoes.charAt(i) == 'L') {
                force = n;
            } else if (dominoes.charAt(i) == 'R') {
                force = 0;
            } else {
                force = Math.max(force - 1, 0);
            }
            forces[i] -= force;
        }

        // Build result string based on net force
        StringBuilder result = new StringBuilder();
        for (int f : forces) {
            if (f > 0) {
                result.append('R');
            } else if (f < 0) {
                result.append('L');
            } else {
                result.append('.');
            }
        }

        return result.toString();
    }
}
