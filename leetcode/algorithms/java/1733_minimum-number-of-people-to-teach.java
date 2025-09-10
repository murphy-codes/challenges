// Source: https://leetcode.com/problems/minimum-number-of-people-to-teach/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-09
// At the time of submission:
//   Runtime 51 ms Beats 67.24%
//   Memory 59.74 MB Beats 50.00%

/****************************************
* 
* On a social network consisting of `m` users and some friendships between users,
* _ two users can communicate with each other if they know a common language.
* You are given an integer `n`, an array `languages`, and an array `friendships`
* _ where:
* • There are `n` languages numbered `1` through `n`,
* • `languages[i]` is the set of languages the `i​​​​​^​th`​​​​ user knows, and
* • `friendships[i] = [u​​​​​​_i​​​, v​​​​​​_i]` denotes a friendship between
* __ the users `u​​​​​​​​​​​_i​​​​​` and `v_i`.
* You can choose one language and teach it to some users so that all friends can
* _ communicate with each other.
* _ Return the minimum number of users you need to teach.
* Note that friendships are not transitive, meaning if `x` is a friend of `y`
* _ and `y` is a friend of `z`, this doesn't guarantee that `x` is a friend of `z`.
*
* Example 1:
* Input: n = 2, languages = [[1],[2],[1,2]], friendships = [[1,2],[1,3],[2,3]]
* Output: 1
* Explanation: You can either teach user 1 the second language or user 2 the first language.
*
* Example 2:
* Input: n = 3, languages = [[2],[1,3],[1,2],[3]], friendships = [[1,4],[1,2],[3,4],[2,3]]
* Output: 2
* Explanation: Teach the third language to users 1 and 3, yielding two users to teach.
*
* Constraints:
* • 2 <= n <= 500
* • languages.length == m
* • 1 <= m <= 500
* • 1 <= languages[i].length <= n
* • 1 <= languages[i][j] <= n
* • 1 <= u​​​​​​_i < v​​​​​​_i <= languages.length
* • 1 <= friendships.length <= 500
* • All tuples `(u​​​​​_i, v_​​​​​​i)` are unique
* • `languages[i]` contains only unique values
* 
****************************************/

import java.util.HashSet;
import java.util.Set;

class Solution {
    // For each friendship, check if the two users share a language.
    // If not, mark both as candidates to be taught. Then, for each
    // language, count how many of these users would need to learn it.
    // The minimum across all languages is the result.
    // Time complexity: O(m * n + friendships * n) ~ O(m * n), 
    // Space complexity: O(m * n) in the worst case.
    public int minimumTeachings(int n, int[][] languages, int[][] friendships) {
        int m = languages.length;
        // Convert each user's known languages into a set for quick lookup
        Set<Integer>[] userLangs = new HashSet[m + 1]; 
        for (int i = 0; i < m; i++) {
            userLangs[i + 1] = new HashSet<>();
            for (int lang : languages[i]) {
                userLangs[i + 1].add(lang);
            }
        }

        // Step 1: Find all users in friendships that cannot currently communicate
        Set<Integer> needTeach = new HashSet<>();
        for (int[] f : friendships) {
            int u = f[0], v = f[1];
            // Check if they share a language
            boolean canTalk = false;
            for (int lang : userLangs[u]) {
                if (userLangs[v].contains(lang)) {
                    canTalk = true;
                    break;
                }
            }
            if (!canTalk) {
                needTeach.add(u);
                needTeach.add(v);
            }
        }

        // Step 2: For each language, count how many of these users need to learn it
        int minTeach = m; // worst case: teach all
        for (int lang = 1; lang <= n; lang++) {
            int count = 0;
            for (int user : needTeach) {
                if (!userLangs[user].contains(lang)) {
                    count++;
                }
            }
            minTeach = Math.min(minTeach, count);
        }

        return minTeach;
    }
}
