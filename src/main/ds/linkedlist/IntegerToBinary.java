package main.ds.linkedlist;

public class IntegerToBinary {
    public SingleLinkedListNode convertToBinary(int data) {
        int digit = 0, remainder = 0;

        SingleLinkedListNode current = null;
        SingleLinkedListNode head = null;

        while (data > 0) {
            remainder = data % 2;
            data = data / 2;

            if (head == null) {
                head = new SingleLinkedListNode(remainder);
                current = head;
                System.out.println("Head Node : " + head.val);
            } else {
                current.next = new SingleLinkedListNode(remainder);
                current = current.next;
                System.out.println("Other Node : " + current.val);
            }
        }

        return head;
    }
}
