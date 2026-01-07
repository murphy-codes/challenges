// Source: https://leetcode.com/problems/maximum-product-of-splitted-binary-tree/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-06
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 56.48 MB Beats 100.00%

/****************************************
* 
* Given the `root` of a binary tree, split the binary tree into two subtrees by
* _ removing one edge such that the product of the sums of the subtrees is maximized.
* Return the maximum product of the sums of the two subtrees. Since the answer may
* _ be too large, return it modulo `10^9 + 7`.
* Note that you need to maximize the answer before taking the mod and not after taking it.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/01/21/sample_1_1699.png]
* Input: root = [1,2,3,4,5,6]
* Output: 110
* Explanation: Remove the red edge and get 2 binary trees with sum 11 and 10. Their product is 110 (11*10)
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/01/21/sample_2_1699.png]
* Input: root = [1,null,2,3,4,null,null,5,6]
* Output: 90
* Explanation: Remove the red edge and get 2 binary trees with sum 15 and 6.Their product is 90 (15*6)
*
* Constraints:
* • The number of nodes in the tree is in the range `[2, 5 * 10^4]`.
* • `1 <= Node.val <= 10^4`
* 
****************************************/

class Solution {
    // First DFS computes the total sum of all nodes in the tree.
    // Second DFS computes each subtree sum and treats it as a cut point.
    // For each subtree, compute subtreeSum * (totalSum - subtreeSum).
    // Track the maximum product across all possible cuts.
    // Time: O(n), Space: O(h) due to recursion stack.

    static long totalSum;
    static long maxProduct;
    static final int MODULO = 1_000_000_007;

    // Computes the sum of all nodes in the tree
    private static long computeTotalSum(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.val
             + computeTotalSum(node.left)
             + computeTotalSum(node.right);
    }

    // Computes subtree sums and updates max product for each possible cut
    private static long computeSubtreeSum(TreeNode node) {
        if (node == null) {
            return 0;
        }

        long leftSum = computeSubtreeSum(node.left);
        long rightSum = computeSubtreeSum(node.right);

        long subtreeSum = leftSum + rightSum + node.val;
        long product = (totalSum - subtreeSum) * subtreeSum;

        if (product > maxProduct) {
            maxProduct = product;
        }

        return subtreeSum;
    }

    public static int maxProduct(TreeNode root) {
        maxProduct = 0;
        totalSum = computeTotalSum(root);
        computeSubtreeSum(root);
        return (int) (maxProduct % MODULO);
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