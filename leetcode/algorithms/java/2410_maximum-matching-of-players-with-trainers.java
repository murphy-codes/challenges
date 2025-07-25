// Source: https://leetcode.com/problems/maximum-matching-of-players-with-trainers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-13
// At the time of submission:
//   Runtime 25 ms Beats 94.18%
//   Memory 65.83 MB Beats 10.35%

/****************************************
* 
* You are given a 0-indexed integer array `players`, where `players[i]` represents
* _ the ability of the `i^th` player. You are also given a 0-indexed integer array
* _ `trainers`, where `trainers[j]` represents the training capacity of the `j^th`
* _ trainer.
* The `i^th` player can match with the `j^th` trainer if the player's ability is
* _ less than or equal to the trainer's training capacity. Additionally, the `i^th`
* _ player can be matched with at most one trainer, and the `j^th` trainer can be
* _ matched with at most one player.
* Return the maximum number of matchings between `players` and `trainers` that
* _ satisfy these conditions.
*
* Example 1:
* Input: players = [4,7,9], trainers = [8,2,5,8]
* Output: 2
* Explanation:
* One of the ways we can form two matchings is as follows:
* - players[0] can be matched with trainers[0] since 4 <= 8.
* - players[1] can be matched with trainers[3] since 7 <= 8.
* It can be proven that 2 is the maximum number of matchings that can be formed.
*
* Example 2:
* Input: players = [1,1,1], trainers = [10]
* Output: 1
* Explanation:
* The trainer can be matched with any of the 3 players.
* Each player can only be matched with one trainer, so the maximum answer is 1.
*
* Constraints:
* • 1 <= players.length, trainers.length <= 10^5
* • 1 <= players[i], trainers[j] <= 10^9
*
* Note: This question is the same as 445: Assign Cookies.
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort players and trainers to enable greedy matching from weakest upward.
    // Use two pointers to iterate through both arrays. For each player, find
    // the smallest available trainer who can train them. This ensures the
    // maximum number of matches possible. Time: O(n log n), Space: O(1).
    public int matchPlayersAndTrainers(int[] players, int[] trainers) {
        Arrays.sort(players);
        Arrays.sort(trainers);

        int i = 0, j = 0, matchCount = 0;

        while (i < players.length && j < trainers.length) {
            if (players[i] <= trainers[j]) {
                // Match found
                matchCount++;
                i++;
                j++;
            } else {
                // Try next trainer
                j++;
            }
        }

        return matchCount;
    }
}
