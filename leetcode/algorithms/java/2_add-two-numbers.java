// Source: https://leetcode.com/problems/add-two-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-27

/****************************************
* 
* You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
* 
* You may assume the two numbers do not contain any leading zero, except the number 0 itself.
* 
* Example 1:
* Input: l1 = [2,4,3], l2 = [5,6,4]
* Output: [7,0,8]
* Explanation: 342 + 465 = 807.
* 
* Example 2:
* Input: l1 = [0], l2 = [0]
* Output: [0]
* 
* Example 3:
* Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
* Output: [8,9,9,9,0,0,0,1]
* 
* Constraints:
* 
* The number of nodes in each linked list is in the range [1, 100].
* 0 <= Node.val <= 9
* It is guaranteed that the list represents a number that does not have leading zeros.
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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0); // Dummy head to simplify list construction
        ListNode current = dummyHead; // Pointer to build the result list
        int carry = 0; // To store carry-over values
        
        while (l1 != null || l2 != null || carry != 0) {
            int val1 = (l1 != null) ? l1.val : 0; // Get value from l1 or 0 if null
            int val2 = (l2 != null) ? l2.val : 0; // Get value from l2 or 0 if null
            int sum = val1 + val2 + carry; // Calculate sum
            
            carry = sum / 10; // Update carry
            int digit = sum % 10; // Current digit to add to the result
            
            current.next = new ListNode(digit); // Append new node to result
            current = current.next; // Move pointer
            
            // Advance input lists
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        return dummyHead.next; // Return the actual result list, skipping dummy head
    }
}
