// Source: https://leetcode.com/problems/minimum-number-of-people-to-teach/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-09
// At the time of submission:
//   Runtime 15 ms Beats 100.00%
//   Memory 55.65 MB Beats 100.00%

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

// import java.util.BitSet;
// import java.util.HashSet;
// import java.util.Set;
// import java.util.Arrays;
// import java.util.stream.IntStream;

class Solution {
    // For each friendship, check if the two users share a language using BitSet.
    // If not, mark both users as needing teaching. Then, count for each language
    // how many of these users already know it. The minimum required teaches is
    // the number of problematic users minus the max overlap with one language.
    // Time complexity: O(m * n + friendships * n) ~ O(m * n), Space: O(m * n).
    public int minimumTeachings(int n, int[][] languages, int[][] friendships) {
        // Step 1: Store each user's known languages in a BitSet
        BitSet[] userLanguages = new BitSet[languages.length];
        Arrays.setAll(userLanguages, i -> new BitSet(n + 1));
        for (int i = 0; i < languages.length; i++) {
            for (int lang : languages[i]) {
                userLanguages[i].set(lang);
            }
        }

        // Step 2: Find all users involved in friendships where no shared language exists
        Set<Integer> toTeach = new HashSet<>();
        for (int[] f : friendships) {
            BitSet common = (BitSet) userLanguages[f[0] - 1].clone();
            common.and(userLanguages[f[1] - 1]);
            if (common.isEmpty()) {
                toTeach.add(f[0] - 1);
                toTeach.add(f[1] - 1);
            }
        }

        // Step 3: Count, for each language, how many of these users already know it
        int[] languageCount = new int[n + 1];
        for (int person : toTeach) {
            for (int lang : languages[person]) {
                languageCount[lang]++;
            }
        }

        // Step 4: Minimum teaches = total problem users - best language coverage
        return toTeach.size() - Arrays.stream(languageCount).max().getAsInt();
    }
}
