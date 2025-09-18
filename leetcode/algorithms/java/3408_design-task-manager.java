// Source: https://leetcode.com/problems/design-task-manager/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-17
// At the time of submission:
//   Runtime 280 ms Beats 91.72%
//   Memory 172.20 MB Beats 36.69%

/****************************************
* 
* There is a task management system that allows users to manage their tasks,
* _ each associated with a priority. The system should efficiently handle
* _ adding, modifying, executing, and removing tasks.
*
* Implement the `TaskManager` class:
* • `TaskManager(List<List<Integer>> tasks)` initializes the task manager with a
* __ list of user-task-priority triples. Each element in the input list is of the
* __ form `[userId, taskId, priority]`, which adds a task to the specified user
* __ with the given priority.
* • `void add(int userId, int taskId, int priority)` adds a task with the
* __ specified `taskId` and `priority` to the user with `userId`. It is guaranteed
* __ that `taskId` does not exist in the system.
* • `void edit(int taskId, int newPriority)` updates the priority of the existing
* __ `taskId` to `newPriority`. It is guaranteed that `taskId` exists in the system.
* • `void rmv(int taskId)` removes the task identified by `taskId` from the system.
* __ It is guaranteed that `taskId` exists in the system.
* • `int execTop()` executes the task with the highest priority across all users.
* __ If there are multiple tasks with the same highest priority, execute the one
* __ with the highest `taskId`. After executing, the `taskId` is removed from the
* __ system. Return the `userId` associated with the executed task. If no tasks
* __ are available, return -1.
* Note that a user may be assigned multiple tasks.
*
* Example 1:
* Input:
* ["TaskManager", "add", "edit", "execTop", "rmv", "add", "execTop"]
* [[[[1, 101, 10], [2, 102, 20], [3, 103, 15]]], [4, 104, 5], [102, 8], [], [101], [5, 105, 15], []]
* Output:
* [null, null, null, 3, null, null, 5]
* Explanation
* TaskManager taskManager = new TaskManager([[1, 101, 10], [2, 102, 20], [3, 103, 15]]); // Initializes with three tasks for Users 1, 2, and 3.
* taskManager.add(4, 104, 5); // Adds task 104 with priority 5 for User 4.
* taskManager.edit(102, 8); // Updates priority of task 102 to 8.
* taskManager.execTop(); // return 3. Executes task 103 for User 3.
* taskManager.rmv(101); // Removes task 101 from the system.
* taskManager.add(5, 105, 15); // Adds task 105 with priority 15 for User 5.
* taskManager.execTop(); // return 5. Executes task 105 for User 5.
*
* Constraints:
* • 1 <= tasks.length <= 10^5
* • 0 <= userId <= 10^5
* • 0 <= taskId <= 10^5
* • 0 <= priority <= 10^9
* • 0 <= newPriority <= 10^9
* • At most `2 * 10^5` calls will be made in total to `add`, `edit`, `rmv`, and `execTop` methods.
* • The input is generated such that `taskId` will be valid.
* 
****************************************/

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class TaskManager {
    // This solution maps taskId → current Task object, and stores all tasks in
    // a max heap ordered by (priority desc, taskId desc). For add/edit, new
    // tasks are inserted into the heap in O(log n). Removals are lazy, only
    // deleting from the map (O(1)), with stale entries skipped later. execTop
    // polls until finding a valid task in O(log n) amortized. Space is O(n).

    static class Task {
        int userId;
        int taskId;
        int priority;

        Task(int userId, int taskId, int priority) {
            this.userId = userId;
            this.taskId = taskId;
            this.priority = priority;
        }
    }

    // Map from taskId → latest Task object (with current priority)
    private Map<Integer, Task> taskMap;

    // Max heap ordered by priority desc, then taskId desc
    private PriorityQueue<Task> maxHeap;

    public TaskManager(List<List<Integer>> tasks) {
        taskMap = new HashMap<>();
        maxHeap = new PriorityQueue<>(
            (a, b) -> {
                if (a.priority != b.priority) {
                    return b.priority - a.priority; // higher priority first
                }
                return b.taskId - a.taskId; // higher taskId first
            }
        );

        for (List<Integer> t : tasks) {
            int userId = t.get(0), taskId = t.get(1), priority = t.get(2);
            Task task = new Task(userId, taskId, priority);
            taskMap.put(taskId, task);
            maxHeap.add(task);
        }
    }

    public void add(int userId, int taskId, int priority) {
        Task task = new Task(userId, taskId, priority);
        taskMap.put(taskId, task);
        maxHeap.add(task);
    }

    public void edit(int taskId, int newPriority) {
        Task oldTask = taskMap.get(taskId);
        Task updatedTask = new Task(oldTask.userId, taskId, newPriority);

        // Update taskMap
        taskMap.put(taskId, updatedTask);

        // Insert new version (lazy deletion handles old version)
        maxHeap.add(updatedTask);
    }

    public void rmv(int taskId) {
        // Remove from taskMap (heap entry will be lazily skipped later)
        taskMap.remove(taskId);
    }

    public int execTop() {
        while (!maxHeap.isEmpty()) {
            Task top = maxHeap.poll();
            Task latest = taskMap.get(top.taskId);

            if (latest == top) { // ensure exact match
                taskMap.remove(top.taskId);
                return top.userId;
            }
            // Else stale entry → skip
        }
        return -1;
    }
}
