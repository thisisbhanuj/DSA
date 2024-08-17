package main.ds.tree.trie;

public class TrieWrapper {
    private RadixTrieInterface root;

    TrieWrapper() {
        root = new RadixTrieInterface();
    }

    public void insert(String word){
            root.insert(word);
    }

    public boolean search(String word){
        return root.search(word);
    }

    public boolean startsWith(String prefix) {
        return root.startsWith(prefix);
    }

    public void depthFirstSearch(String word) {
        root.depthFirstSearch(root, word);
    }

    public static void main(String[] args) {
        TrieWrapper trie = new TrieWrapper();
        trie.insert("apple");
        trie.insert("apps");
        trie.insert("application");
        trie.insert("bhanuj");

        System.out.println("Found : " + trie.search("bhanuj"));
        System.out.println("Starts With : " + trie.startsWith("bha"));
        System.out.println();

        trie.depthFirstSearch("app");
    }
}
