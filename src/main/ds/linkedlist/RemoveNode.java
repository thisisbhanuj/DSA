package main.ds.linkedlist;

public class RemoveNode {

    public static SingleLinkedListNode removeNode (SingleLinkedListNode start, int value) {
        if (start.next == null) return start;
        start.next = removeNode(start.next, value);
        return start.val == value ? start.next : start;
    }

    public static void main(String[] args) {
        SingleLinkedListNode head = SingleLinkedListNode.sampleLinkedList();
        if ( head == null ) return;
        head = removeNode(head, 2);
        SingleLinkedListNode.printList(head);
    }
}
