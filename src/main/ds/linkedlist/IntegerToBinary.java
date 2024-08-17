package main.ds.linkedlist;

public class IntegerToBinary {
    public static SingleLinkedListNode convertToBinary(int data) {
        int remainder = 0;

        SingleLinkedListNode current = null;
        SingleLinkedListNode head = null;

        while (data > 0) {
            remainder = data % 2;
            data = data / 2;

            if (head == null) {
                head = new SingleLinkedListNode(remainder);
                current = head;
            } else {
                current.next = new SingleLinkedListNode(remainder);
                current = current.next;
            }
        }

        return head;
    }

    public static void main(String[] args) {
        SingleLinkedListNode.printList(convertToBinary(127));
    }
}
