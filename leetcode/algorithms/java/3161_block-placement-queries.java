// Source: https://leetcode.com/problems/block-placement-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-30
// At the time of submission:
//   Runtime 111 ms Beats 99.00%
//   Memory 280.35 MB Beats 60.00%

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
    // Dynamic segment tree stores nearest obstacles and max free gaps.
    // Obstacle insertions update affected intervals lazily on demand.
    // Queries prune subtrees whose max gap cannot fit the requested block.
    // Time: O(q log M), Space: O(M log M), M = max obstacle position.
    class SegmentTreeNode {
        SegmentTreeNode leftChild;
        SegmentTreeNode rightChild;

        int rangeStart;
        int rangeEnd;

        // First obstacle to the right of this interval.
        int nearestObstacle = Integer.MAX_VALUE;

        // Largest block that can fit somewhere in this interval.
        int maxFreeSpace = 0;

        SegmentTreeNode(int rangeStart, int rangeEnd,
                        int obstaclePosition) {

            this.rangeStart = rangeStart;
            this.rangeEnd = rangeEnd;
            this.nearestObstacle = obstaclePosition;

            this.maxFreeSpace =
                obstaclePosition == Integer.MAX_VALUE
                    ? Integer.MAX_VALUE
                    : obstaclePosition - rangeStart;
        }
    }

    public List<Boolean> getResults(int[][] queries) {

        int largestObstaclePosition = 0;

        for (int[] query : queries) {
            if (query[0] == 1) {
                largestObstaclePosition =
                    Math.max(largestObstaclePosition, query[1]);
            }
        }

        SegmentTreeNode root =
            new SegmentTreeNode(
                0,
                largestObstaclePosition,
                Integer.MAX_VALUE
            );

        List<Boolean> results = new ArrayList<>();

        for (int[] query : queries) {

            if (query[0] == 1) {

                addObstacle(root, query[1]);

            } else {

                int latestPossibleStart =
                    query[1] - query[2];

                if (latestPossibleStart >= root.rangeEnd) {

                    results.add(true);

                } else {

                    results.add(
                        isBlockPlaceable(
                            root,
                            latestPossibleStart,
                            query[2]
                        )
                    );
                }
            }
        }

        return results;
    }
    boolean isBlockPlaceable(
        SegmentTreeNode node,
        int latestPossibleStart,
        int blockSize
    ) {

        if (node.leftChild == null &&
            node.rightChild == null) {

            if (latestPossibleStart >= node.rangeEnd) {

                return blockSize <= node.maxFreeSpace;

            } else if (latestPossibleStart < node.rangeStart) {

                return false;

            } else {

                return blockSize <=
                    (node.nearestObstacle - node.rangeStart);
            }
        }

        if (node.rightChild.rangeEnd <= latestPossibleStart) {

            if (node.rightChild.maxFreeSpace >= blockSize) {
                return true;
            }
        }

        if (node.leftChild.rangeEnd <= latestPossibleStart) {

            if (node.leftChild.maxFreeSpace >= blockSize) {
                return true;
            }

        } else {

            return isBlockPlaceable(
                node.leftChild,
                latestPossibleStart,
                blockSize
            );
        }

        if (node.rightChild.rangeStart <= latestPossibleStart &&
            node.rightChild.rangeEnd >= latestPossibleStart) {

            return isBlockPlaceable(
                node.rightChild,
                latestPossibleStart,
                blockSize
            );
        }

        return false;
    }

    int addObstacle(
        SegmentTreeNode node,
        int obstaclePosition
    ) {

        if (node.rangeEnd == node.rangeStart) {

            node.nearestObstacle =
                (node.rangeEnd < obstaclePosition &&
                 obstaclePosition < node.nearestObstacle)
                    ? obstaclePosition
                    : node.nearestObstacle;

            node.maxFreeSpace =
                node.nearestObstacle == Integer.MAX_VALUE
                    ? node.nearestObstacle
                    : node.nearestObstacle - node.rangeStart;

            return node.maxFreeSpace;
        }

        // Obstacle is not relevant to this interval.
        if (obstaclePosition <= node.rangeStart) {
            return node.maxFreeSpace;
        }

        // Obstacle lies completely to the right of this interval.
        // Update nearest obstacle information.
        if (obstaclePosition > node.rangeEnd) {

            if (obstaclePosition < node.nearestObstacle) {

                node.nearestObstacle = obstaclePosition;

                if (node.leftChild == null &&
                    node.rightChild == null) {

                    node.maxFreeSpace =
                        obstaclePosition - node.rangeStart;

                } else {

                    node.maxFreeSpace = Math.max(
                        addObstacle(
                            node.leftChild,
                            obstaclePosition
                        ),
                        addObstacle(
                            node.rightChild,
                            obstaclePosition
                        )
                    );
                }
            }

            return node.maxFreeSpace;
        }
        // Obstacle falls inside this interval.
        // Recurse into children.
        if (node.leftChild != null &&
            node.rightChild != null) {

            node.maxFreeSpace = Math.max(
                addObstacle(
                    node.leftChild,
                    obstaclePosition
                ),
                addObstacle(
                    node.rightChild,
                    obstaclePosition
                )
            );

            return node.maxFreeSpace;
        }

        int mid =
            (node.rangeEnd - node.rangeStart) / 2
            + node.rangeStart;

        node.leftChild = new SegmentTreeNode(
            node.rangeStart,
            mid,
            node.nearestObstacle
        );

        node.rightChild = new SegmentTreeNode(
            mid + 1,
            node.rangeEnd,
            node.nearestObstacle
        );

        node.maxFreeSpace = Math.max(
            addObstacle(
                node.leftChild,
                obstaclePosition
            ),
            addObstacle(
                node.rightChild,
                obstaclePosition
            )
        );

        return node.maxFreeSpace;
    }
}