// Source: https://leetcode.com/problems/rotate-list/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-04
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 44.48 MB Beats 25.10%

/****************************************
* 
* Given the `head` of a linked list, rotate the list to the right by `k` places.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/11/13/rotate1.jpg]
* Input: head = [1,2,3,4,5], k = 2
* Output: [4,5,1,2,3]
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/11/13/roate2.jpg]
* Input: head = [0,1,2], k = 4
* Output: [2,0,1]
*
* Constraints:
* • The number of nodes in the list is in the range `[0, 500]`.
* • `-100 <= Node.val <= 100`
* • `0 <= k <= 2 * 10^9`
* 
****************************************/

class Solution {
    // Compute length and connect tail to head to form a cycle.
    // New head is (n - k % n) steps from original head.
    // Traverse to new tail, break the cycle to finalize rotation.
    // Time: O(n), Space: O(1)
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // Step 1: compute length and get tail
        int n = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            n++;
        }

        // Step 2: reduce k
        k %= n;
        if (k == 0) return head;

        // Step 3: make circular
        tail.next = head;

        // Step 4: find new tail (n - k - 1 steps from head)
        ListNode newTail = head;
        for (int i = 0; i < n - k - 1; i++) {
            newTail = newTail.next;
        }

        // Step 5: break the cycle
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }
}

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