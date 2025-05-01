// Source: https://leetcode.com/problems/maximum-number-of-tasks-you-can-assign/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-30
// At the time of submission:
//   Runtime 63 ms Beats 82.86%
//   Memory 55.22 MB Beats 82.86%

/****************************************
* 
* You have `n` tasks and `m` workers.
* _ Each task has a strength requirement stored in a 0-indexed integer
* _ array `tasks`, with the `i^th` task requiring `tasks[i]` strength to
* _ complete. The strength of each worker is stored in a 0-indexed integer
* _ array `workers`, with the `j^th` worker having `workers[j]` strength.
* _ Each worker can only be assigned to a single task and must have a
* _ strength greater than or equal to the task's strength requirement
* _ (i.e., `workers[j] >= tasks[i]`).
* Additionally, you have `pills` magical pills that will increase a worker's
* _ strength by `strength`. You can decide which workers receive the magical
* _ pills, however, you may only give each worker at most one magical pill.
* Given the 0-indexed integer arrays `tasks` and `workers` and the integers
* _ `pills` and `strength`, return the maximum number of tasks that can be completed.
*
* Example 1:
* Input: tasks = [3,2,1], workers = [0,3,3], pills = 1, strength = 1
* Output: 3
* Explanation:
* We can assign the magical pill and tasks as follows:
* - Give the magical pill to worker 0.
* - Assign worker 0 to task 2 (0 + 1 >= 1)
* - Assign worker 1 to task 1 (3 >= 2)
* - Assign worker 2 to task 0 (3 >= 3)
*
* Example 2:
* Input: tasks = [5,4], workers = [0,0,0], pills = 1, strength = 5
* Output: 1
* Explanation:
* We can assign the magical pill and tasks as follows:
* - Give the magical pill to worker 0.
* - Assign worker 0 to task 0 (0 + 5 >= 5)
*
* Example 3:
* Input: tasks = [10,15,30], workers = [0,10,10,10,10], pills = 3, strength = 10
* Output: 2
* Explanation:
* We can assign the magical pills and tasks as follows:
* - Give the magical pill to worker 0 and worker 1.
* - Assign worker 0 to task 0 (0 + 10 >= 10)
* - Assign worker 1 to task 1 (10 + 10 >= 15)
* The last pill is not given because it will not make any worker strong enough for the last task.
*
* Constraints:
* • n == tasks.length
* • m == workers.length
* • 1 <= n, m <= 5 * 10^4
* • 0 <= pills <= m
* • 0 <= tasks[i], workers[j], strength <= 10^9
* 
****************************************/

class Solution {
    // Binary search for the max number of tasks that can be assigned.
    // For each guess, check feasibility by assigning tasks in descending order
    // to workers who can complete them (with or without pills), using a deque
    // for efficient matching. Time: O(n log n + m log m), Space: O(min(n, m)).
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        Arrays.sort(tasks); // Ascending: easier tasks first
        Arrays.sort(workers); // Ascending: weakest to strongest

        int left = 1, right = Math.min(tasks.length, workers.length), answer = 0;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (canAssign(tasks, workers, pills, strength, mid)) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

    private boolean canAssign(int[] tasks, int[] workers, int pills, int strength, int taskCount) {
        int p = pills;
        int m = workers.length;
        Deque<Integer> eligibleWorkers = new ArrayDeque<>();
        int workerPtr = m - 1;

        // Try to assign the hardest task down to the easiest
        for (int i = taskCount - 1; i >= 0; --i) {
            // Add all workers who can complete the task with a pill
            while (workerPtr >= m - taskCount && workers[workerPtr] + strength >= tasks[i]) {
                eligibleWorkers.addFirst(workers[workerPtr]);
                --workerPtr;
            }

            if (eligibleWorkers.isEmpty()) {
                return false; // No worker can complete this task
            } else if (eligibleWorkers.getLast() >= tasks[i]) {
                // Assign task to the strongest available worker without a pill
                eligibleWorkers.pollLast();
            } else {
                // Must use a pill on the weakest eligible worker
                if (p == 0) return false;
                eligibleWorkers.pollFirst();
                --p;
            }
        }

        return true;
    }
}
