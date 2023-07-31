import ds.linkedlist.HashMapUsingVectorAndLinkedList;

public class Test {
    public static void main(String[] args){
        /*ArrayStack arrayStack = new ArrayStack(5);
        arrayStack.push(new Employee("Bhanuj", "Kashap", 1));
        arrayStack.push(new Employee("Zach", "O'Brien", 2));
        arrayStack.printStack();
        System.out.println("*****************************************************");
        arrayStack.pop();
        arrayStack.printStack();*/

        // Create a linked list representing the binary number: 1 -> 0 -> 1 -> 0
        /*ListNode head = new ListNode(1);
        head.next = new ListNode(0);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(0);

        BinaryToInteger converter = new BinaryToInteger();
        int result = converter.convertBinaryToInteger(head);
        // System.out.println("Converted integer: " + result);

        IntegerToBinary convertedBinary = new IntegerToBinary();
        head = convertedBinary.convertToBinary(10);*/

        HashMapUsingVectorAndLinkedList<String, Integer> hashSet = new HashMapUsingVectorAndLinkedList<>(10);

        hashSet.put("Apple", 5);
        hashSet.put("Banana", 10);
        hashSet.put("Orange", 7);

        System.out.println("Size: " + hashSet.size());
        System.out.println("Value for 'Banana': " + hashSet.get("Banana"));

        boolean removed = hashSet.remove("Apple");
        System.out.println("Is removed? " + removed);
        System.out.println("Size: " + hashSet.size());

        hashSet.clear();
        System.out.println("Is empty after clear? " + hashSet.size());

    }

}
