// Source: https://leetcode.com/problems/find-all-people-with-secret/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-19
// At the time of submission:
//   Runtime 76 ms Beats 48.08%
//   Memory 196.14 MB Beats 23.72%

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    // Meetings are processed by time, treating each timestamp as a temporary graph.
    // We union participants at the same time so secrets can spread instantly.
    // After each time batch, components not connected to person 0 are reset.
    // This prevents secrets from leaking across different times incorrectly.
    // Time: O(m log m + m α(n)), Space: O(n)
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[2], b[2]));

        UnionFind uf = new UnionFind(n);
        uf.union(0, firstPerson);

        int i = 0;
        while (i < meetings.length) {
            int time = meetings[i][2];
            Set<Integer> touched = new HashSet<>();

            // Union all meetings at this same time
            while (i < meetings.length && meetings[i][2] == time) {
                int x = meetings[i][0];
                int y = meetings[i][1];
                uf.union(x, y);
                touched.add(x);
                touched.add(y);
                i++;
            }

            // Roll back connections that are not linked to person 0
            for (int person : touched) {
                if (uf.find(person) != uf.find(0)) {
                    uf.reset(person);
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int p = 0; p < n; p++) {
            if (uf.find(p) == uf.find(0)) {
                result.add(p);
            }
        }
        return result;
    }

    static class UnionFind {
        int[] parent;

        UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int a, int b) {
            parent[find(a)] = find(b);
        }

        void reset(int x) {
            parent[x] = x;
        }
    }
}
