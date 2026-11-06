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
        if (head == null || head.next == null) {
            return head;
        }

        ListNode answer = head.next;
        ListNode prev = null;

        while (head != null) {
            ListNode pre = head;

            if (head.next == null) {
                break;
            }

            ListNode next = head.next;
            ListNode temp = next.next;

            pre.next = temp;
            next.next = pre;

            if (prev != null) {
                prev.next = next;
            }

            prev = pre;
            head = temp;
        }

        return answer;
    }
}