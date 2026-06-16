// Source: https://leetcode.com/problems/block-placement-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-30
// At the time of submission:
//   Runtime 484 ms Beats 41.00%
//   Memory 282.38 MB Beats 51.00%

/****************************************
* 
* There exists an infinite number line, with its origin at 0 and extending
* _ towards the positive x-axis.
* You are given a 2D array `queries`, which contains two types of queries:
* 1. For a query of type 1, `queries[i] = [1, x]`. Build an obstacle at
* __ distance `x` from the origin. It is guaranteed that there is no obstacle
* __ at distance `x` when the query is asked.
* 2. For a query of type 2, `queries[i] = [2, x, sz]`. Check if it is possible
* __ to place a block of size `sz` anywhere in the range `[0, x]` on the line,
* __ such that the block entirely lies in the range `[0, x]`. A block cannot be
* __ placed if it intersects with any obstacle, but it may touch it. Note that
* __ you do not actually place the block. Queries are separate.
* Return a boolean `array` results, where `results[i]` is `true` if you can
* _ place the block specified in the `i^th` query of type 2, and `false` otherwise.
*
* Example 1:
* Input: queries = [[1,2],[2,3,3],[2,3,1],[2,2,2]]
* Output: [false,true,true]
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/04/22/example0block.png]
* For query 0, place an obstacle at `x = 2`. A block of size at most 2 can be placed before `x = 3`.
*
* Example 2:
* Input: queries = [[1,7],[2,7,6],[1,2],[2,7,5],[2,7,6]]
* Output: [true,true,false]
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/04/22/example1block.png]
* Place an obstacle at `x = 7` for query 0. A block of size at most 7 can be placed before `x = 7`.
* Place an obstacle at `x = 2` for query 2. Now, a block of size at most 5 can be placed before `x = 7`, and a block of size at most 2 before `x = 2`.
*
* Constraints:
* • `1 <= queries.length <= 15 * 10^4`
* • `2 <= queries[i].length <= 3`
* • `1 <= queries[i][0] <= 2`
* • `1 <= x, sz <= min(5 * 10^4, 3 * queries.length)`
* • The input is generated such that for queries of type 1, no obstacle exists at distance `x` when the query is asked.
* • The input is generated such that there is at least one query of type 2.
* 
****************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

class Solution {
    // Process queries in reverse, turning obstacle insertions into removals.
    // Store obstacle gaps in a segment tree keyed by gap endpoints.
    // For [0,x], combine the largest completed gap and the tail to x.
    // Time: O(q log M), Space: O(M), where M is max queried position.

    static class SegmentTree {
        private final int[] tree;
        private final int n;

        SegmentTree(int n) {
            this.n = n;
            this.tree = new int[n * 4];
        }

        void update(int index, int value) {
            update(1, 0, n - 1, index, value);
        }

        private void update(int node, int left, int right,
                            int index, int value) {

            if (left == right) {
                tree[node] = value;
                return;
            }

            int mid = (left + right) >>> 1;

            if (index <= mid) {
                update(node * 2, left, mid, index, value);
            } else {
                update(node * 2 + 1, mid + 1, right,
                       index, value);
            }

            tree[node] = Math.max(
                tree[node * 2],
                tree[node * 2 + 1]
            );
        }

        int query(int ql, int qr) {
            return query(1, 0, n - 1, ql, qr);
        }

        private int query(int node, int left, int right,
                          int ql, int qr) {

            if (qr < left || right < ql) {
                return 0;
            }

            if (ql <= left && right <= qr) {
                return tree[node];
            }

            int mid = (left + right) >>> 1;

            return Math.max(
                query(node * 2, left, mid, ql, qr),
                query(node * 2 + 1, mid + 1, right, ql, qr)
            );
        }
    }

    public List<Boolean> getResults(int[][] queries) {

        int maxX = 0;

        for (int[] q : queries) {
            maxX = Math.max(maxX, q[1]);
        }

        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);
        obstacles.add(maxX + 1);

        for (int[] q : queries) {
            if (q[0] == 1) {
                obstacles.add(q[1]);
            }
        }

        SegmentTree seg = new SegmentTree(maxX + 2);

        Integer prev = null;

        for (int pos : obstacles) {
            if (prev != null) {
                seg.update(pos, pos - prev);
            }
            prev = pos;
        }

        List<Boolean> answer = new ArrayList<>();

        for (int i = queries.length - 1; i >= 0; i--) {

            int[] q = queries[i];

            if (q[0] == 2) {

                int x = q[1];
                int sz = q[2];

                int bestGap = seg.query(0, x);

                Integer lastObstacle = obstacles.floor(x);

                bestGap = Math.max(
                    bestGap,
                    x - lastObstacle
                );

                answer.add(bestGap >= sz);

            } else {

                int pos = q[1];

                Integer left = obstacles.lower(pos);
                Integer right = obstacles.higher(pos);

                obstacles.remove(pos);

                seg.update(right, right - left);
                seg.update(pos, 0);
            }
        }

        java.util.Collections.reverse(answer);
        return answer;
    }
}