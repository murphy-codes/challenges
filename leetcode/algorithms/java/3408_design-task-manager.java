// Source: https://leetcode.com/problems/design-task-manager/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-17
// At the time of submission:
//   Runtime 253 ms Beats 100.00%
//   Memory 197.45 MB Beats 8.29%

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

class TaskManager {
    // TaskManager uses arrays for O(1) lookups by taskId and a max-heap for
    // retrieving highest-priority tasks. Each heap entry encodes (priority,
    // taskId) into a single long, avoiding object overhead. Lazy deletion is
    // applied: old or removed entries are skipped in execTop(). Time: O(log n)
    // per add/edit, O(log n) amortized for execTop. Space: O(n).

    private static final int MAX_TASK_ID = 100001;

    // Arrays indexed by taskId → latest priority and userId
    private int[] priorities = new int[MAX_TASK_ID];
    private int[] userIds = new int[MAX_TASK_ID];

    // Max-heap storing encoded (priority, taskId) pairs
    private PriorityQueue<Long> maxHeap = new PriorityQueue<>((a, b) -> Long.compare(b, a));

    public TaskManager(List<List<Integer>> tasks) {
        for (List<Integer> task : tasks) {
            int userId = task.get(0);
            int taskId = task.get(1);
            int priority = task.get(2);

            priorities[taskId] = priority;
            userIds[taskId] = userId;

            // Encode priority & taskId into one long for heap ordering
            maxHeap.offer((long) priority * MAX_TASK_ID + taskId);
        }
    }

    public void add(int userId, int taskId, int priority) {
        if (priorities[taskId] > 0) return; // already exists
        priorities[taskId] = priority;
        userIds[taskId] = userId;
        maxHeap.offer((long) priority * MAX_TASK_ID + taskId);
    }

    public void edit(int taskId, int newPriority) {
        priorities[taskId] = newPriority;
        maxHeap.offer((long) newPriority * MAX_TASK_ID + taskId);
    }

    public void rmv(int taskId) {
        priorities[taskId] = -1; // mark as removed
    }

    public int execTop() {
        while (!maxHeap.isEmpty()) {
            long encoded = maxHeap.poll();
            int taskId = (int) (encoded % MAX_TASK_ID);
            int priority = (int) (encoded / MAX_TASK_ID);

            // Skip stale entries
            if (priorities[taskId] != priority) continue;

            // Valid → consume it
            priorities[taskId] = -1;
            return userIds[taskId];
        }
        return -1; // no tasks
    }
}
