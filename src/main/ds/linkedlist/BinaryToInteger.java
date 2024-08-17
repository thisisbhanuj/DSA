package main.ds.linkedlist;

public class BinaryToInteger {
    private static int convertBinaryToInteger(SingleLinkedListNode head) {
        int result = 0;
        SingleLinkedListNode current = head;

        while (current != null) {
            result = result * 2 + current.val; // Multiply previous result by 2 and add current digit
            current = current.next;
        }

        return result;
    }

    public static void main(String[] args) {
        SingleLinkedListNode head = new SingleLinkedListNode(1);
        SingleLinkedListNode d1 = new SingleLinkedListNode(1);
        SingleLinkedListNode d2 = new SingleLinkedListNode(1);
        SingleLinkedListNode d3 = new SingleLinkedListNode(1);
        SingleLinkedListNode d4 = new SingleLinkedListNode(1);
        SingleLinkedListNode d5 = new SingleLinkedListNode(1);
        SingleLinkedListNode d6 = new SingleLinkedListNode(1);

        head.next = d1;
        d1.next = d2;
        d2.next = d3;
        d3.next = d4;
        d4.next = d5;
        d5.next = d6;
        d6.next = null;

        int result = convertBinaryToInteger(head);
        System.out.println(result);
    }
}
