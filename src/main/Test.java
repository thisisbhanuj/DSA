package main;

public class Test {
    static class SingleLinkedListNode {
        public int value;
        public SingleLinkedListNode next;

        SingleLinkedListNode(int p_value) {
            this.value = p_value;
        }
    }

    static SingleLinkedListNode mergeSort(SingleLinkedListNode head1, SingleLinkedListNode head2){
       if (head1 == null && head2 == null) return null;
       if (head1 == null) return head2;
       if (head2 == null) return head1;

       if (head1.value <= head2.value) {
            head1.next = mergeSort(head1.next, head2);
            return head1;
       } else {
            head2.next = mergeSort(head2.next, head1);
            return head2;
       }
    }

    public static void main(String[] args){
        SingleLinkedListNode head1 = new SingleLinkedListNode(1);
        SingleLinkedListNode node1 = new SingleLinkedListNode(4);
        SingleLinkedListNode node2 = new SingleLinkedListNode(5);

        SingleLinkedListNode head2 = new SingleLinkedListNode(3);
        SingleLinkedListNode node3 = new SingleLinkedListNode(6);
        SingleLinkedListNode node4 = new SingleLinkedListNode(8);

        head1.next = node1;
        node1.next = node2;
        head2.next = node3;
        node3.next = node4;

        SingleLinkedListNode mergedList = mergeSort(head1, head2);
        while (mergedList != null) {
            System.out.println(mergedList.value);
            mergedList = mergedList.next;
        }
    }
}
