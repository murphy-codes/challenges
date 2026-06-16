// Source: https://leetcode.com/problems/block-placement-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-30
// At the time of submission:
//   Runtime 116 ms Beats 99.00%
//   Memory 282.12 MB Beats 52.00%

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
import java.util.List;

class Solution {
    // Dynamic segment tree stores nearest right obstacle and max free span.
    // Obstacle inserts update affected ranges; queries search valid starts
    // within [0, x - sz] and check whether any free segment reaches sz.
    // Time: O(q log M), Space: O(k log M), M=max coordinate, k=obstacles.

    class SegmentTreeNode {
        SegmentTreeNode leftChild;
        SegmentTreeNode rightChild;

        int start;
        int end;

        // Maximum obstacle-free segment length within this range.
        int maxFreeSpace = 0;

        // Closest obstacle strictly to the right of this range.
        int nearestObstacle = Integer.MAX_VALUE;

        SegmentTreeNode(int start, int end, int obstaclePosition) {
            this.start = start;
            this.end = end;
            this.nearestObstacle = obstaclePosition;

            this.maxFreeSpace =
                obstaclePosition == Integer.MAX_VALUE
                    ? Integer.MAX_VALUE
                    : obstaclePosition - start;
        }
    }

    public List<Boolean> getResults(int[][] queries) {
        int maxCoordinate = 0;

        for (int[] query : queries) {
            if (query[0] == 1) {
                maxCoordinate = Math.max(maxCoordinate, query[1]);
            }
        }

        SegmentTreeNode root =
            new SegmentTreeNode(0, maxCoordinate, Integer.MAX_VALUE);

        List<Boolean> results = new ArrayList<>();

        for (int[] query : queries) {
            if (query[0] == 1) {
                addObstacle(root, query[1]);
            } else {
                int latestStart = query[1] - query[2];

                if (latestStart >= root.end) {
                    results.add(true);
                } else {
                    results.add(
                        isBlockPlaceable(root, latestStart, query[2])
                    );
                }
            }
        }

        return results;
    }

    boolean isBlockPlaceable(
        SegmentTreeNode node,
        int latestStart,
        int blockSize
    ) {
        if (node.leftChild == null && node.rightChild == null) {

            if (latestStart >= node.end) {
                return blockSize <= node.maxFreeSpace;
            }

            if (latestStart < node.start) {
                return false;
            }

            return blockSize <= (node.nearestObstacle - node.start);
        }

        if (node.rightChild.end <= latestStart) {
            if (node.rightChild.maxFreeSpace >= blockSize) {
                return true;
            }
        }

        if (node.leftChild.end <= latestStart) {
            if (node.leftChild.maxFreeSpace >= blockSize) {
                return true;
            }
        } else {
            return isBlockPlaceable(
                node.leftChild,
                latestStart,
                blockSize
            );
        }

        if (
            node.rightChild.start <= latestStart
                && node.rightChild.end >= latestStart
        ) {
            return isBlockPlaceable(
                node.rightChild,
                latestStart,
                blockSize
            );
        }

        return false;
    }
    int addObstacle(
        SegmentTreeNode node,
        int obstaclePosition
    ) {
        if (node.end == node.start) {

            node.nearestObstacle =
                (node.end < obstaclePosition
                    && obstaclePosition < node.nearestObstacle)
                        ? obstaclePosition
                        : node.nearestObstacle;

            node.maxFreeSpace =
                node.nearestObstacle == Integer.MAX_VALUE
                    ? node.nearestObstacle
                    : node.nearestObstacle - node.start;

            return node.maxFreeSpace;
        }

        if (obstaclePosition <= node.start) {
            return node.maxFreeSpace;
        }

        if (obstaclePosition > node.end) {

            if (obstaclePosition < node.nearestObstacle) {

                node.nearestObstacle = obstaclePosition;

                if (
                    node.leftChild == null
                        && node.rightChild == null
                ) {
                    node.maxFreeSpace =
                        obstaclePosition - node.start;
                } else {
                    node.maxFreeSpace = Math.max(
                        addObstacle(node.leftChild, obstaclePosition),
                        addObstacle(node.rightChild, obstaclePosition)
                    );
                }
            }

            return node.maxFreeSpace;
        }

        if (node.leftChild != null && node.rightChild != null) {
            node.maxFreeSpace = Math.max(
                addObstacle(node.leftChild, obstaclePosition),
                addObstacle(node.rightChild, obstaclePosition)
            );

            return node.maxFreeSpace;
        }

        int mid = (node.end - node.start) / 2 + node.start;

        node.leftChild =
            new SegmentTreeNode(
                node.start,
                mid,
                node.nearestObstacle
            );

        node.rightChild =
            new SegmentTreeNode(
                mid + 1,
                node.end,
                node.nearestObstacle
            );

        node.maxFreeSpace = Math.max(
            addObstacle(node.leftChild, obstaclePosition),
            addObstacle(node.rightChild, obstaclePosition)
        );

        return node.maxFreeSpace;
    }
}
