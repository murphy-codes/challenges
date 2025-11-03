// Source: https://leetcode.com/problems/delete-nodes-from-linked-list-present-in-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-01
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 147.13 MB Beats 59.90%

/****************************************
* 
* You are given an array of integers `nums` and the `head` of a linked list.
* _ Return the `head` of the modified linked list after removing all nodes from
* _ the linked list that have a value that exists in `nums`.
*
* Example 1:
* Input: nums = [1,2,3], head = [1,2,3,4,5]
* Output: [4,5]
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/06/11/linkedlistexample0.png]
* Remove the nodes with values 1, 2, and 3.
*
* Example 2:
* Input: nums = [1], head = [1,2,1,2,1,2]
* Output: [2,2,2]
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/06/11/linkedlistexample1.png]
* Remove the nodes with value 1.
*
* Example 3:
* Input: nums = [5], head = [1,2,3,4]
* Output: [1,2,3,4]
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/06/11/linkedlistexample2.png]
* No node has value 5.
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^5`
* • All elements in `nums` are unique.
* • The number of nodes in the given list is in the range `[1, 10^5]`.
* • `1 <= Node.val <= 10^5`
* • The input is generated such that there is at least one node in the linked list that has a value not present in `nums`.
* 
****************************************/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    // This solution removes nodes whose values appear in nums[] using a boolean
    // lookup table for O(1) value checks. First, it finds the max value in nums
    // to size the boolean array, then flags all nums[i] = true. While traversing
    // the linked list, nodes whose values are not flagged are linked forward.
    // Runs in O(n + m) time and O(max(nums)) space, where n=list size, m=nums.length.
    public ListNode modifiedList(int[] nums, ListNode head) {
        // Find the maximum value in nums to size our boolean lookup array
        int maxVal = -1;
        for (int num : nums) if (num > maxVal) maxVal = num;

        // Mark all values that should be deleted
        boolean[] toDelete = new boolean[maxVal + 1];
        for (int num : nums) toDelete[num] = true;

        // Use a dummy head to handle possible deletions at the list start
        ListNode dummyHead = new ListNode();
        ListNode current = dummyHead;

        // Traverse and rebuild list with nodes NOT in 'toDelete'
        while (head != null) {
            if (head.val >= toDelete.length || !toDelete[head.val]) {
                current.next = head;
                current = current.next;
            }
            head = head.next;
        }

        // Terminate the rebuilt list
        current.next = null;
        return dummyHead.next;
    }
}
