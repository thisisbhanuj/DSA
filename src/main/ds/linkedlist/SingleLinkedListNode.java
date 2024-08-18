package main.ds.linkedlist;

public class SingleLinkedListNode {
    public int val;
    public SingleLinkedListNode next;

    public SingleLinkedListNode(int val) {
        this.val = val;
    }

    public static void printList(SingleLinkedListNode head) {
        SingleLinkedListNode current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static SingleLinkedListNode sampleLinkedList () {
        SingleLinkedListNode head = new SingleLinkedListNode(1);
        SingleLinkedListNode node2 = new SingleLinkedListNode(2);
        SingleLinkedListNode node3 = new SingleLinkedListNode(3);
        SingleLinkedListNode node4 = new SingleLinkedListNode(4);
        SingleLinkedListNode node5 = new SingleLinkedListNode(5);

        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        return head;
    }
}
