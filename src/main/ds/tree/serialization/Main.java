package main.ds.tree.serialization;

public class Main {
    public static void main(String[] args) {
        BinaryTreeSerializer serializer = new BinaryTreeSerializer();
        BinaryTreeDeSerializer deSerializer = new BinaryTreeDeSerializer();

        // Create the binary tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);

        // Serialize the binary tree via Pre Order
        String serializedData = serializer.serializePreOrder(root);
        System.out.println("Serialized: " + serializedData);

        // Deserialize the string back to the binary tree
        TreeNode deserializedRoot = deSerializer.deserialize(serializedData);
        deSerializer.display(deserializedRoot);

        // Serialize the binary tree via Level Order
        serializedData = serializer.serializeLevelOrder(root);
        System.out.println("Serialized: " + serializedData);

        // Deserialize the string back to the binary tree
        deserializedRoot = deSerializer.deserializeBFS(serializedData);
        deSerializer.display(deserializedRoot);
    }
}
