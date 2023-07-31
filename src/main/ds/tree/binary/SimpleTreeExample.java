package ds.tree.binary;

public class SimpleTreeExample {
    public static class TreeNode {
        private TreeNode left, right;
        private int value;
        public TreeNode(int data){
            this.left = null;
            this.right = null;
            this.value = data;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(1);
        root.right = new TreeNode(10);

        // Adding nodes to left child
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(2);
        // Adding nodes to right child
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(11);

        System.out.println();
    }
}
