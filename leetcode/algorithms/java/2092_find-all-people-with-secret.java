// Source: https://leetcode.com/problems/find-all-people-with-secret/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-19
// At the time of submission:
//   Runtime 28 ms Beats 96.15%
//   Memory 201.93 MB Beats 21.16%

/****************************************
* 
* You are given an integer `n` indicating there are `n` people numbered from
* _ `0 to n - 1`. You are also given a 0-indexed 2D integer array `meetings`
* _ where `meetings[i] = [x_i, y_i, time_i]` indicates that person `x_i` and
* _ person `y_i` have a meeting at `time_i`. A person may attend multiple
* _ meetings at the same time. Finally, you are given an integer `firstperson`.
* Person `0` has a secret and initially shares the secret with a person
* _ `firstPerson` at time `0`. This secret is then shared every time a meeting
* _ takes place with a person that has the secret. More formally, for every
* _ meeting, if a person `x_i` has the secret at `time_i`, then they will share
* _ the secret with person `y_i`, and vice versa.
* The secrets are shared instantaneously. That is, a person may receive the
* _ secret and share it with people in other meetings within the same time frame.
* Return a list of all the people that have the secret after all the meetings
* _ have taken place. You may return the answer in any order.
*
* Example 1:
* Input: n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
* Output: [0,1,2,3,5]
* Explanation:
* At time 0, person 0 shares the secret with person 1.
* At time 5, person 1 shares the secret with person 2.
* At time 8, person 2 shares the secret with person 3.
* At time 10, person 1 shares the secret with person 5.​​​​
* Thus, people 0, 1, 2, 3, and 5 know the secret after all the meetings.
*
* Example 2:
* Input: n = 4, meetings = [[3,1,3],[1,2,2],[0,3,3]], firstPerson = 3
* Output: [0,1,3]
* Explanation:
* At time 0, person 0 shares the secret with person 3.
* At time 2, neither person 1 nor person 2 know the secret.
* At time 3, person 3 shares the secret with person 0 and person 1.
* Thus, people 0, 1, and 3 know the secret after all the meetings.
*
* Example 3:
* Input: n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], firstPerson = 1
* Output: [0,1,2,3,4]
* Explanation:
* At time 0, person 0 shares the secret with person 1.
* At time 1, person 1 shares the secret with person 2, and person 2 shares the secret with person 3.
* Note that person 2 can share the secret at the same time as receiving it.
* At time 2, person 3 shares the secret with person 4.
* Thus, people 0, 1, 2, 3, and 4 know the secret after all the meetings.
*
* Constraints:
* • `2 <= n <= 10^5`
* • `1 <= meetings.length <= 10^5`
* • `meetings[i].length == 3`
* • `0 <= x_i, y_i <= n - 1`
* • `x_i != y_i`
* • `1 <= time_i <= 105`
* • `1 <= firstPerson <= n - 1`
* 
****************************************/

class Solution {
    // Meetings are processed in chronological order using time buckets.
    // For each time, all participants are unioned, allowing instant sharing.
    // Afterward, unions not connected to person 0 are rolled back to prevent
    // secrets from leaking across time boundaries.
    // Time complexity: O(m α(n) + T), Space complexity: O(n + m).
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        // Union-Find parent array; parent[i] == representative of i
        // Using n + 1 so root "0" can act as the secret-holder component
        int[] parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }

        // Person 0 initially shares the secret with firstPerson
        parent[firstPerson] = 0;

        // Find the maximum meeting time to bucket meetings by time
        int maxTime = 0;
        for (int[] meeting : meetings) {
            maxTime = Math.max(maxTime, meeting[2]);
        }

        // timeBuckets[t] = list of meetings happening at time t
        List<int[]>[] timeBuckets = new List[maxTime + 1];
        for (int[] meeting : meetings) {
            int time = meeting[2];
            if (timeBuckets[time] == null) {
                timeBuckets[time] = new ArrayList<>();
            }
            timeBuckets[time].add(new int[]{meeting[0], meeting[1]});
        }

        // Process meetings in increasing time order
        for (int time = 1; time < timeBuckets.length; time++) {
            if (timeBuckets[time] == null) continue;

            // Step 1: union all pairs meeting at this time
            for (int[] pair : timeBuckets[time]) {
                union(pair[0], pair[1], parent);
            }

            // Step 2: rollback connections that are not connected to root 0
            for (int[] pair : timeBuckets[time]) {
                int u = pair[0];
                int v = pair[1];

                if (find(u, parent) != 0) parent[u] = u;
                if (find(v, parent) != 0) parent[v] = v;
            }
        }

        // Collect all people connected to root 0
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == 0) {
                result.add(i);
            }
        }

        return result;
    }

    // Union two nodes by representative
    private void union(int a, int b, int[] parent) {
        int rootA = find(a, parent);
        int rootB = find(b, parent);
        if (rootA < rootB) {
            parent[rootB] = rootA;
        } else {
            parent[rootA] = rootB;
        }
    }

    // Find with path compression
    private int find(int x, int[] parent) {
        if (parent[x] != x) {
            parent[x] = find(parent[x], parent);
        }
        return parent[x];
    }
}
