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
    public ListNode swapPairs(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        ListNode next = head == null ? null : head.next;

        head = head == null ? null : (head.next == null ? head : head.next);

        while (curr != null && next != null) {
            if (prev != null) prev.next = next;
            curr.next = next.next;
            next.next = curr;

            prev = curr;
            curr = curr.next;
            if (curr != null) next = curr.next;
        }

        return head;
    }
}
