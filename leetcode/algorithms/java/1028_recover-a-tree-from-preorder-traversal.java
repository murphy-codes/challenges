// Source: https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-21

/****************************************
* 
* We run a preorder depth-first search (DFS) on the `root` of a binary tree.
* At each node in this traversal, we output `D` dashes (where `D` is the depth of this node), 
* then we output the value of this node. If the depth of a node is `D`, the depth of its 
* immediate child is `D + 1`.  The depth of the root node is `0`.
* 
* If a node has only one child, that child is guaranteed to be the left child.
* Given the output `traversal` of this traversal, recover the tree and return its `root`.
* 
* Example 1:
*   [image: https://assets.leetcode.com/uploads/2024/09/10/recover_tree_ex1.png]
* Input: traversal = "1-2--3--4-5--6--7"
* Output: [1,2,5,3,4,6,7]
* 
* Example 2:
*   [image: https://assets.leetcode.com/uploads/2024/09/10/recover_tree_ex2.png]
* Input: traversal = "1-2--3---4-5--6---7"
* Output: [1,2,5,3,null,6,null,4,null,7]
* 
* Example 3:
*   [image: https://assets.leetcode.com/uploads/2024/09/10/recover_tree_ex3.png]
* Input: traversal = "1-401--349---90--88"
* Output: [1,401,null,349,88,90]
* 
* Constraints:
* • The number of nodes in the original tree is in the range `[1, 1000]`.
* • 1 <= Node.val <= 10^9
* 
****************************************/

import java.util.Stack;

class Solution {
    // This solution reconstructs a binary tree from a preorder traversal string
    // using a stack to track parent-child relationships. We determine depth by
    // counting dashes and extract node values to build the tree iteratively.
    // The stack ensures efficient traversal, maintaining correct parent-child links.
    // Time Complexity: O(n), as we process each character once.
    // Space Complexity: O(n), for storing nodes in the stack at max depth.
    public TreeNode recoverFromPreorder(String traversal) {
        Stack<TreeNode> stack = new Stack<>();
        int i = 0, n = traversal.length();

        while (i < n) {
            int depth = 0;
            while (i < n && traversal.charAt(i) == '-') {
                depth++; // Count dashes to determine depth
                i++;
            }

            int value = 0;
            while (i < n && Character.isDigit(traversal.charAt(i))) {
                value = value * 10 + (traversal.charAt(i) - '0'); // Extract node value
                i++;
            }

            TreeNode node = new TreeNode(value);

            while (stack.size() > depth) {
                stack.pop(); // Move up the tree if needed
            }

            if (!stack.isEmpty()) {
                if (stack.peek().left == null) {
                    stack.peek().left = node; // Assign left child
                } else {
                    stack.peek().right = node; // Assign right child
                }
            }

            stack.push(node);
        }

        // The root is the first element inserted in the stack
        while (stack.size() > 1) {
            stack.pop();
        }
        return stack.pop();
    }
}