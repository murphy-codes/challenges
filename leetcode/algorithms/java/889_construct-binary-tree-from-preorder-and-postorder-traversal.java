// Source: https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-23

/****************************************
* 
* Given two integer arrays, `preorder` and `postorder` where `preorder` is the preorder traversal of a binary tree of distinct values and `postorder` is the postorder traversal of the same tree, reconstruct and return the binary tree.
* 
* If there exist multiple answers, you can return any of them.
* 
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/07/24/lc-prepost.jpg]
* Input: preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
* Output: [1,2,3,4,5,6,7]
* 
* Example 2:
* Input: preorder = [1], postorder = [1]
* Output: [1]
* 
* Constraints:
* • 1 <= preorder.length <= 30
* • 1 <= preorder[i] <= preorder.length
* • 1 <= postorder[i] <= postorder.length
* • postorder.length == preorder.length
* • All the values of `preorder` are unique.
* • All the values of `postorder` are unique.
* • It is guaranteed that `preorder` and `postorder` are the preorder traversal and postorder traversal of the same binary tree.
* 
****************************************/

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

import java.util.HashMap;

class Solution {
    // Time Complexity: O(N), as each node is processed once using HashMap lookup (O(1)).
    // Space Complexity: O(N), due to recursive stack depth and HashMap storage.
    private HashMap<Integer, Integer> postIndexMap;
    private int preIndex = 0;

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        postIndexMap = new HashMap<>();
        
        // Store indices of postorder elements for quick lookup
        for (int i = 0; i < postorder.length; i++) {
            postIndexMap.put(postorder[i], i);
        }
        
        return buildTree(preorder, postorder, 0, postorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int[] postorder, int left, int right) {
        if (left > right) return null;

        TreeNode root = new TreeNode(preorder[preIndex++]);

        if (left == right) return root; // If only one element, return the node

        // Find the left child in postorder to determine left subtree boundary
        int leftChildIndex = postIndexMap.get(preorder[preIndex]);

        // Recursively build left and right subtrees
        root.left = buildTree(preorder, postorder, left, leftChildIndex);
        root.right = buildTree(preorder, postorder, leftChildIndex + 1, right - 1);

        return root;
    }
}
