// Source: https://leetcode.com/problems/delete-nodes-from-linked-list-present-in-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-01
// At the time of submission:
//   Runtime 32 ms Beats 5.29%
//   Memory 147.10 MB Beats 5.52%

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
  public ListNode modifiedList(int[] nums, ListNode head) {
    ListNode temp = new ListNode(0, head);
    Set<Integer> numsSet = Arrays.stream(nums).boxed().collect(Collectors.toSet());
    for (ListNode curr = temp; curr.next != null;)
      if (numsSet.contains(curr.next.val))
        curr.next = curr.next.next;
      else
        curr = curr.next;
    return temp.next;
  }
}