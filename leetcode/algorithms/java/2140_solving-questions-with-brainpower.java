// Source: https://leetcode.com/problems/solving-questions-with-brainpower/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-01
// At the time of submission:
//   Runtime 5 ms Beats 97.12%
//   Memory 108.23 MB Beats 65.41%

/****************************************
* 
* You are given a 0-indexed 2D integer array `questions` where `questions[i] = [points_i, brainpower_i]`.
* The array describes the questions of an exam, where you have to process the questions
* _ in order (i.e., starting from question 0) and make a decision whether to solve or
* _ skip each question. Solving question `i` will earn you `points_i` points but you will
* _ be unable to solve each of the next `brainpower_i` questions. If you skip question `i`,
* _ you get to make the decision on the next question.
* • For example, given `questions = [[3, 2], [4, 3], [4, 4], [2, 5]]`:
* _ • If question `0` is solved, you will earn `3` points but you will be unable to solve questions `1` and `2`.
* _ • If instead, question `0` is skipped and question `1` is solved, you will earn `4` points but you will
* _ _ be unable to solve questions `2` and `3`.
* Return the maximum points you can earn for the exam.
*
* Example 1:
* Input: questions = [[3,2],[4,3],[4,4],[2,5]]
* Output: 5
* Explanation: The maximum points can be earned by solving questions 0 and 3.
* - Solve question 0: Earn 3 points, will be unable to solve the next 2 questions
* - Unable to solve questions 1 and 2
* - Solve question 3: Earn 2 points
* Total points earned: 3 + 2 = 5. There is no other way to earn 5 or more points.
*
* Example 2:
* Input: questions = [[1,1],[2,2],[3,3],[4,4],[5,5]]
* Output: 7
* Explanation: The maximum points can be earned by solving questions 1 and 4.
* - Skip question 0
* - Solve question 1: Earn 2 points, will be unable to solve the next 2 questions
* - Unable to solve questions 2 and 3
* - Solve question 4: Earn 5 points
* Total points earned: 2 + 5 = 7. There is no other way to earn 7 or more points.
*
* Constraints:
* • 1 <= questions.length <= 10^5
* • questions[i].length == 2
* • 1 <= points_i, brainpower_i <= 10^5
* 
****************************************/

class Solution {
    // This solution uses a bottom-up Dynamic Programming approach to find the
    // maximum points achievable while following the constraints. We traverse the
    // questions in reverse, storing the best possible score at each step in a DP
    // array. At each index, we choose to either solve or skip the question. This
    // results in O(n) time complexity and O(n) space complexity.
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        long[] dp = new long[n + 1]; // DP array to store max points

        // Traverse from the last question to the first
        for (int i = n - 1; i >= 0; i--) {
            int points = questions[i][0];
            int brainpower = questions[i][1];
            int next = i + brainpower + 1;

            // If next is within bounds, add dp[next]; otherwise, just take points
            long solve = points + (next < n ? dp[next] : 0);
            long skip = dp[i + 1]; // Skipping this question

            dp[i] = Math.max(solve, skip);
        }

        return dp[0]; // Maximum points starting from question 0
    }
}
