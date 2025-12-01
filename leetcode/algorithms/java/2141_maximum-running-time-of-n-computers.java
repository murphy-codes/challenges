// Source: https://leetcode.com/problems/maximum-running-time-of-n-computers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-30
// At the time of submission:
//   Runtime 16 ms Beats 71.43%
//   Memory 73.94 MB Beats 28.02%

/****************************************
* 
* You have `n` computers. You are given the integer `n` and a 0-indexed integer
* _ array `batteries` where the `i^th` battery can run a computer for
* _ `batteries[i]` minutes. You are interested in running all `n` computers
* _ simultaneously using the given batteries.
* Initially, you can insert at most one battery into each computer. After that
* _ and at any integer time moment, you can remove a battery from a computer and
* _ insert another battery any number of times. The inserted battery can be a
* _ totally new battery or a battery from another computer. You may assume that
* _ the removing and inserting processes take no time.
* Note that the batteries cannot be recharged.
* Return the maximum number of minutes you can run all the n computers simultaneously.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2022/01/06/example1-fit.png]
* Input: n = 2, batteries = [3,3,3]
* Output: 4
* Explanation:
* Initially, insert battery 0 into the first computer and battery 1 into the second computer.
* After two minutes, remove battery 1 from the second computer and insert battery 2 instead. Note that battery 1 can still run for one minute.
* At the end of the third minute, battery 0 is drained, and you need to remove it from the first computer and insert battery 1 instead.
* By the end of the fourth minute, battery 1 is also drained, and the first computer is no longer running.
* We can run the two computers simultaneously for at most 4 minutes, so we return 4.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2022/01/06/example2.png]
* Input: n = 2, batteries = [1,1,1,1]
* Output: 2
* Explanation:
* Initially, insert battery 0 into the first computer and battery 2 into the second computer.
* After one minute, battery 0 and battery 2 are drained so you need to remove them and insert battery 1 into the first computer and battery 3 into the second computer.
* After another minute, battery 1 and battery 3 are also drained so the first and second computers are no longer running.
* We can run the two computers simultaneously for at most 2 minutes, so we return 2.
*
* Constraints:
* • `1 <= n <= batteries.length <= 10^5`
* • `1 <= batteries[i] <= 10^9`
* 
****************************************/

// import java.util.Arrays;

class Solution {
    // Binary search the maximum minutes T such that all n computers can run.
    // For each candidate T, sum the power contributed by each battery, where
    // a battery contributes at most min(battery[i], T). If this total meets
    // n*T, T is feasible. Binary search over [0, total/n] for the maximum T.
    // Time: O(m log total), Space: O(1), where m is number of batteries.
    public long maxRunTime(int n, int[] batteries) {
        long total = 0;
        for (int b : batteries) total += b;

        long left = 0;
        long right = total / n; // upper bound for answer

        while (left < right) {
            long mid = right - (right - left) / 2; // bias upward
            if (canRun(n, batteries, mid)) {
                left = mid;   // mid works → try longer
            } else {
                right = mid - 1; // mid too long → decrease
            }
        }

        return left;
    }

    private boolean canRun(int n, int[] batteries, long time) {
        long available = 0;
        for (int b : batteries) {
            available += Math.min((long)b, time);
            if (available >= time * n) return true; // early exit
        }
        return available >= time * n;
    }
}
