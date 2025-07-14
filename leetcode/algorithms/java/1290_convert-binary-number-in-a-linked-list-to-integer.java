// Source: https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-13
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.12 MB Beats 61.30%

/****************************************
* 
* Given `head` which is a reference node to a singly-linked list. The value of
* _ each node in the linked list is either `0` or `1`. The linked list holds the
* _ binary representation of a number.
* Return the decimal value of the number in the linked list.
* The most significant bit is at the head of the linked list.
*
* Example 1:
* Input: head = [1,0,1]
* Output: 5
* Explanation: (101) in base 2 = (5) in base 10
*
* Example 2:
* Input: head = [0]
* Output: 0
*
* Constraints:
* • The Linked List is not empty.
* • Number of nodes will not exceed `30`.
* • Each node's value is either `0` or `1`.
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
    // Traverse the linked list while building the decimal value in-place.
    // Left shift result to make room for the next bit, then OR it with
    // the current node's value. This mimics binary-to-decimal conversion.
    // Time: O(n), Space: O(1), where n is the number of nodes (≤ 30).
    public int getDecimalValue(ListNode head) {
        int result = 0;
        while (head != null) {
            result = (result << 1) | head.val;
            head = head.next;
        }
        return result;
    }
}
