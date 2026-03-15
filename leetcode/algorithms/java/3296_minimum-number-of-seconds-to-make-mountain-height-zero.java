// Source: https://leetcode.com/problems/minimum-number-of-seconds-to-make-mountain-height-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-13
// At the time of submission:
//   Runtime 8 ms Beats 79.77%
//   Memory 47.79 MB Beats 28.50%

/****************************************
* 
* You are given an integer `mountainHeight` denoting the height of a mountain.
* You are also given an integer array `workerTimes` representing the work time of
* _ workers in seconds.
* The workers work simultaneously to reduce the height of the mountain. For worker `i`:
* • To decrease the mountain's height by `x`, it takes
* __ `workerTimes[i] + workerTimes[i] * 2 + ... + workerTimes[i] * x` seconds. For example:
* __ • To reduce the height of the mountain by 1, it takes `workerTimes[i]` seconds.
* __ • To reduce the height of the mountain by 2, it takes
* ___ `workerTimes[i] + workerTimes[i] * 2` seconds, and so on.
* Return an integer representing the minimum number of seconds required for the
* _ workers to make the height of the mountain 0.
*
* Example 1:
* Input: mountainHeight = 4, workerTimes = [2,1,1]
* Output: 3
* Explanation:
* One way the height of the mountain can be reduced to 0 is:
* Worker 0 reduces the height by 1, taking workerTimes[0] = 2 seconds.
* Worker 1 reduces the height by 2, taking workerTimes[1] + workerTimes[1] * 2 = 3 seconds.
* Worker 2 reduces the height by 1, taking workerTimes[2] = 1 second.
* Since they work simultaneously, the minimum time needed is max(2, 3, 1) = 3 seconds.
*
* Example 2:
* Input: mountainHeight = 10, workerTimes = [3,2,2,4]
* Output: 12
* Explanation:
* Worker 0 reduces the height by 2, taking workerTimes[0] + workerTimes[0] * 2 = 9 seconds.
* Worker 1 reduces the height by 3, taking workerTimes[1] + workerTimes[1] * 2 + workerTimes[1] * 3 = 12 seconds.
* Worker 2 reduces the height by 3, taking workerTimes[2] + workerTimes[2] * 2 + workerTimes[2] * 3 = 12 seconds.
* Worker 3 reduces the height by 2, taking workerTimes[3] + workerTimes[3] * 2 = 12 seconds.
* The number of seconds needed is max(9, 12, 12, 12) = 12 seconds.
*
* Example 3:
* Input: mountainHeight = 5, workerTimes = [1]
* Output: 15
* Explanation:
* There is only one worker in this example, so the answer is workerTimes[0] + workerTimes[0] * 2 + workerTimes[0] * 3 + workerTimes[0] * 4 + workerTimes[0] * 5 = 15.
*
* Constraints:
* • `1 <= mountainHeight <= 10^5`
* • `1 <= workerTimes.length <= 10^4`
* • `1 <= workerTimes[i] <= 10^6`
* 
****************************************/

import java.lang.Math;

class Solution {
    // Binary search the minimum time T required to reduce the mountain height
    // to zero. For a worker with time t, reducing x layers costs
    // t * (1 + 2 + ... + x) = t * x(x+1)/2 seconds. For a given T we compute
    // the maximum x each worker can remove using the quadratic formula and
    // sum the contributions. If total ≥ mountainHeight, T is feasible.
    // Time: O(W log T), Space: O(1).

    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {

        long left = 1;
        long right = (long)1e18;
        long ans = right;

        while (left <= right) {

            long mid = left + (right - left) / 2;

            if (canFinish(mid, mountainHeight, workerTimes)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private boolean canFinish(long time, int height, int[] workerTimes) {

        long removed = 0;

        for (int t : workerTimes) {

            long k = (long)(2 * time / t);

            long x = (long)((Math.sqrt(1 + 4 * k) - 1) / 2);

            removed += x;

            if (removed >= height)
                return true;
        }

        return false;
    }
}