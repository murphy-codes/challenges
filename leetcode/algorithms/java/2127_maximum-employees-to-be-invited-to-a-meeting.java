// Source: https://leetcode.com/problems/maximum-employees-to-be-invited-to-a-meeting/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-26

/****************************************
* 
* A company is organizing a meeting and has a list of `n` employees, waiting to be invited. They have arranged for a large circular table, capable of seating any number of employees.
* 
* The employees are numbered from `0` to `n - 1`. Each employee has a favorite person and they will attend the meeting only if they can sit next to their favorite person at the table. The favorite person of an employee is not themself.
* 
* Given a 0-indexed integer array `favorite`, where `favorite[i]` denotes the favorite person of the `ith` employee, return the maximum number of employees that can be invited to the meeting.
* 
* Example 1:
*   [An image showing employees 2, 1, and 0 sit equidistant around a large table.]
* Input: favorite = [2,2,1,2]
* Output: 3
* Explanation:
* The above figure shows how the company can invite employees 0, 1, and 2, and seat them at the round table.
* All employees cannot be invited because employee 2 cannot sit beside employees 0, 1, and 3, simultaneously.
* Note that the company can also invite employees 1, 2, and 3, and give them their desired seats.
* The maximum number of employees that can be invited to the meeting is 3. 
* 
* Example 2:
* Input: favorite = [1,2,0]
* Output: 3
* Explanation: 
* Each employee is the favorite person of at least one other employee, and the only way the company can invite them is if they invite every employee.
* The seating arrangement will be the same as that in the figure given in example 1:
* - Employee 0 will sit between employees 2 and 1.
* - Employee 1 will sit between employees 0 and 2.
* - Employee 2 will sit between employees 1 and 0.
* The maximum number of employees that can be invited to the meeting is 3.
* 
* Example 3:
*   [An image showing employees 4, 1, 0, and 3 sit equidistant around a large table.]
* Input: favorite = [3,0,1,4,1]
* Output: 4
* Explanation:
* The above figure shows how the company will invite employees 0, 1, 3, and 4, and seat them at the round table.
* Employee 2 cannot be invited because the two spots next to their favorite employee 1 are taken.
* So the company leaves them out of the meeting.
* The maximum number of employees that can be invited to the meeting is 4.
*  
* Constraints:
* • n == favorite.length
* • 2 <= n <= 10^5
* • 0 <= favorite[i] <= n - 1
* • favorite[i] != i
* 
****************************************/

class Solution {
    // Approach:
    // 1. Calculate the in-degree of each person to identify entry points for topological sorting.
    // 2. Use a queue to process all non-cycle nodes, computing the longest path depth leading into cycles.
    // 3. Detect cycles in the remaining graph (nodes with in-degree > 0). For cycles of length 2, 
    //    add the combined depth of paths feeding into the two nodes. For longer cycles, 
    //    track the maximum cycle length.
    // 4. Return the larger value between the longest simple cycle and the total invitations 
    //    from paths feeding into 2-cycles.
    // Time: O(n);  Space: O(n)
    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] inDegree = new int[n];

        // Calculate in-degree for each person: how many people consider this person as their "favorite"
        for (int person = 0; person < n; ++person) {
            inDegree[favorite[person]]++;
        }

        // Topological sort to remove nodes not in cycles and compute depth for nodes leading into cycles
        Queue<Integer> queue = new LinkedList<>();
        for (int person = 0; person < n; ++person) {
            if (inDegree[person] == 0) {
                queue.offer(person);
            }
        }

        int[] depth = new int[n]; // Maximum depth (length of path) leading to each node
        Arrays.fill(depth, 1);   // Initialize all depths to 1 (self-loop at minimum)

        while (!queue.isEmpty()) {
            int currentPerson = queue.poll();
            int nextPerson = favorite[currentPerson];
            depth[nextPerson] = Math.max(depth[nextPerson], depth[currentPerson] + 1);
            if (--inDegree[nextPerson] == 0) {
                queue.offer(nextPerson);
            }
        }

        int longestCycle = 0;     // Track the longest cycle length in the graph
        int twoCycleInvitations = 0; // Total invitations for paths leading to 2-cycles

        // Detect cycles by checking nodes with remaining in-degree > 0 (part of a cycle)
        for (int person = 0; person < n; ++person) {
            if (inDegree[person] == 0) continue; // Skip already processed nodes

            int cycleLength = 0;
            int current = person;
            while (inDegree[current] != 0) {
                inDegree[current] = 0; // Mark as visited
                cycleLength++;
                current = favorite[current];
            }

            // Special handling for 2-cycles: Combine depth of both cycle nodes
            if (cycleLength == 2) {
                twoCycleInvitations += depth[person] + depth[favorite[person]];
            } else {
                longestCycle = Math.max(longestCycle, cycleLength);
            }
        }

        // Return the maximum between the longest simple cycle and the 2-cycle invitations
        return Math.max(longestCycle, twoCycleInvitations);
    }
}
