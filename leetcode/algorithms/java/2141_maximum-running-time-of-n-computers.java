// Source: https://leetcode.com/problems/maximum-running-time-of-n-computers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-30
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 73.97 MB Beats 28.02%

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

class Solution {
    // This uses binary search to find the maximum uniform runtime all computers
    // can sustain. For each candidate time, we check feasibility by counting
    // batteries that individually meet the target and pooling the rest to cover
    // remaining machines. The check runs in O(n), and binary search over the time
    // range yields an overall complexity of O(n log(total/n)) with O(1) extra space.
    public long maxRunTime(int n, int[] batteries) {

        // Total capacity of all batteries
        long totalCapacity = 0;

        // Track the largest single battery
        int maxBattery = 0;

        for (int b : batteries) {
            totalCapacity += b;
            if (b > maxBattery) {
                maxBattery = b;
            }
        }

        // Maximum possible time if perfectly distributed
        long right = totalCapacity / n;

        // If even distribution already meets or exceeds max battery,
        // no need for binary search—this is the answer.
        if (right >= maxBattery) {
            return right;
        }

        long left = 0;
        long bestTime = 0;

        // Binary search for max feasible runtime
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (canCoverAllComputer(batteries, n, mid)) {
                bestTime = mid;      // mid works, try to go higher
                left = mid + 1;
            } else {
                right = mid - 1;     // mid too high, reduce search space
            }
        }

        return bestTime;
    }

    // Determine whether all n computers can be powered for 'time'
    public boolean canCoverAllComputer(int[] batteries, int n, long time) {
        long smallBatterySum = 0;

        for (int b : batteries) {

            // Batteries that can individually power a machine for 'time'
            if (b >= time) {
                n--;   // covers one machine completely
            } else {
                // Otherwise contribute to shared pool
                smallBatterySum += b;
            }

            // If shared pool can support the remaining machines, success
            if (smallBatterySum >= (long) n * time) {
                return true;
            }
        }

        return false;
    }
}
