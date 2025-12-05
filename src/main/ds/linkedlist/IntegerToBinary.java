package main.ds.linkedlist;

public class IntegerToBinary {

    /**
     * Converts a positive integer to a linked list of its binary digits,
     * with the most significant bit (MSB) at the head.
     *
     * @param data the integer to convert
     * @return head of the linked list representing the binary number
     */
    public static SingleLinkedListNode convertToBinary(int data) {
        if (data == 0) {
            return new SingleLinkedListNode(0); // Handle zero explicitly
        }

        SingleLinkedListNode head = null;

        while (data > 0) {
            int remainder = data % 2;
            data = data / 2;

            // Prepend each new bit to ensure MSB is first
            SingleLinkedListNode newNode = new SingleLinkedListNode(remainder);
            newNode.next = head;
            head = newNode;
        }

        return head;
    }

    public static void main(String[] args) {
        SingleLinkedListNode binaryList = convertToBinary(128);
        System.out.print("Binary representation: ");
        SingleLinkedListNode.printList(binaryList);
    }
}
