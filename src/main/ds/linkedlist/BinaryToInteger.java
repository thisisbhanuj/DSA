package main.ds.linkedlist;

public class BinaryToInteger {
    public int convertBinaryToInteger(SingleLinkedListNode head) {
        int result = 0;
        SingleLinkedListNode current = head;

        while (current != null) {
            result = result * 2 + current.val; // Multiply previous result by 2 and add current digit
            current = current.next;
        }

        return result;
    }
}
