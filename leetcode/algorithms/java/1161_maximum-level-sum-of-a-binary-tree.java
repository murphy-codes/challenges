// Source: https://leetcode.com/problems/four-divisors/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-05
// At the time of submission:
//   Runtime 8 ms Beats 93.82%
//   Memory 49.69 MB Beats 8.06%

/****************************************
* 
* Given the `root` of a binary tree, the level of its root is `1`, the level of
* _ its children is `2`, and so on.
* Return the smallest level `x` such that the sum of all the values of nodes at
* _ level `x` is maximal.
*
* Example 1:
* Input: root = [1,7,0,7,-8,null,null]
* Output: 2
* Explanation:
* Level 1 sum = 1.
* Level 2 sum = 7 + 0 = 7.
* Level 3 sum = 7 + -8 = -1.
* So we return the level with the maximum sum which is level 2.
*
* Example 2:
* Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
* Output: 2
*
* Constraints:
* • The number of nodes in the tree is in the range [1, 10^4].`
* • `-10^5 <= Node.val <= 10^5`
* 
****************************************/

import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    // Perform BFS to process the tree level by level.
    // Compute the sum of node values at each level.
    // Track the maximum sum and the smallest level where it occurs.
    // Update only when a strictly larger sum is found.
    // Time: O(n), Space: O(w) where w is the tree's max width.
    public int maxLevelSum(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int level = 1;
        int bestLevel = 1;
        int maxSum = root.val;

        while (!queue.isEmpty()) {
            int size = queue.size();
            int currentSum = 0;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                currentSum += node.val;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            if (currentSum > maxSum) {
                maxSum = currentSum;
                bestLevel = level;
            }

            level++;
        }

        return bestLevel;
    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */