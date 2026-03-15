// Source: https://leetcode.com/problems/minimum-number-of-seconds-to-make-mountain-height-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-13
// At the time of submission:
//   Runtime 5 ms Beats 100.00%
//   Memory 47.76 MB Beats 28.50%

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

class Solution {
    // Binary search the minimum time needed to reduce the mountain height.
    // If a worker has time t, removing x layers costs t*x(x+1)/2 seconds.
    // For a candidate time T we compute the maximum x each worker can remove
    // using the quadratic formula and subtract it from the remaining height.
    // If the total removed height ≥ mountainHeight, T is feasible.
    // Time: O(W log T), Space: O(1).

    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {

        int maxWorkerTime = max(workerTimes);

        // Estimate average height each worker must remove
        int avgHeightPerWorker =
                (mountainHeight - 1) / workerTimes.length + 1;

        // Upper bound on time if the slowest worker did that work
        long left = 1;
        long right =
                (long) maxWorkerTime *
                avgHeightPerWorker *
                (avgHeightPerWorker + 1) >> 1;

        while (left <= right) {

            long mid = (left + right) >>> 1;

            if (canFinish(mountainHeight, workerTimes, mid))
                right = mid - 1;
            else
                left = mid + 1;
        }

        return left;
    }

    private static boolean canFinish(
            int mountainHeight,
            int[] workerTimes,
            long time) {

        for (int workerTime : workerTimes) {

            // Solve x(x+1)/2 * t <= time
            // Using quadratic formula
            mountainHeight -=
                    (int) (-1 + Math.sqrt(1 + (time << 3) / workerTime)) >> 1;

            if (mountainHeight <= 0)
                return true;
        }

        return false;
    }

    private static int max(int[] arr) {

        int maxValue = 0;

        for (int v : arr)
            maxValue = Math.max(maxValue, v);

        return maxValue;
    }
}