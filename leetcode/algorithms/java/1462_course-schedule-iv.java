// Source: https://leetcode.com/problems/course-schedule-iv/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-26

/****************************************
* 
* There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i]` = `[ai, bi]` indicates that you must take course ai first if you want to take course bi.
* 
* • For example, the pair `[0, 1]` indicates that you have to take course `0` before you can take course `1`.
* 
* Prerequisites can also be indirect. If course `a` is a prerequisite of course `b`, and course `b` is a prerequisite of course `c`, then course `a` is a prerequisite of course `c`.
* 
* You are also given an array `queries` where `queries[j] = [uj, vj]`. For the `jth` query, you should answer whether course `uj` is a prerequisite of course `vj` or not.
* 
* Return a boolean array `answer`, where `answer[j]` is the answer to the `jth` query.
* 
* Example 1:
*   [Image depicting a 2 node graph with 1 pointing towards 0: https://assets.leetcode.com/uploads/2021/05/01/courses4-1-graph.jpg]
* Input: numCourses = 2, prerequisites = [[1,0]], queries = [[0,1],[1,0]]
* Output: [false,true]
* Explanation: The pair [1, 0] indicates that you have to take course 1 before you can take course 0.
* Course 0 is not a prerequisite of course 1, but the opposite is true.
* 
* Example 2:
* Input: numCourses = 2, prerequisites = [], queries = [[1,0],[0,1]]
* Output: [false,false]
* Explanation: There are no prerequisites, and each course is independent.
* 
* Example 3:
*   [Image depicting a 3 node graph with 2 pointing towards 0 and 1 pointing towards 0 & 2: https://assets.leetcode.com/uploads/2021/05/01/courses4-3-graph.jpg]
* Input: numCourses = 3, prerequisites = [[1,2],[1,0],[2,0]], queries = [[1,0],[1,2]]
* Output: [true,true]
*  
* Constraints:
* • 2 <= numCourses <= 100
* • 0 <= prerequisites.length <= (numCourses * (numCourses - 1) / 2)
* • prerequisites[i].length == 2
* • 0 <= a_i, b_i <= numCourses - 1
* • a_i != b_i
* • All the pairs [a_i, b_i] are unique.
* • The prerequisites graph has no cycles.
* • 1 <= queries.length <= 10^4
* • 0 <= u_i, v_i <= numCourses - 1
* • u_i != v_i
* 
****************************************/

class Solution {
    // This solution uses the Floyd-Warshall algorithm to compute the transitive closure of the graph. 
    // A 2D boolean matrix `isPrerequisite[i][j]` tracks if course `i` is a prerequisite of course `j`.
    // First, we populate the matrix with direct prerequisites, then update it for indirect prerequisites.
    // Queries are answered in O(1) by simply checking the matrix.
    // Time Complexity: O(n^3 + q), where n is the number of courses and q is the number of queries.
    // Space Complexity: O(n^2 + q) due to the matrix and result storage.
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // Step 1: Initialize the transitive closure matrix
        boolean[][] isPrerequisite = new boolean[numCourses][numCourses];
        
        // Step 2: Populate the direct prerequisites from the input
        for (int[] prerequisite : prerequisites) {
            int a = prerequisite[0], b = prerequisite[1];
            isPrerequisite[a][b] = true; // a is a prerequisite of b
        }
        
        // Step 3: Compute transitive closure using Floyd-Warshall algorithm
        for (int k = 0; k < numCourses; ++k) {
            for (int i = 0; i < numCourses; ++i) {
                for (int j = 0; j < numCourses; ++j) {
                    if (isPrerequisite[i][k] && isPrerequisite[k][j]) {
                        isPrerequisite[i][j] = true;
                    }
                }
            }
        }
        
        // Step 4: Answer the queries
        List<Boolean> result = new ArrayList<>();
        for (int[] query : queries) {
            result.add(isPrerequisite[query[0]][query[1]]);
        }
        
        return result;
    }
}
