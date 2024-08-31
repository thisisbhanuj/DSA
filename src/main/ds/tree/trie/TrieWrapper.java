package main.ds.tree.trie;

public class TrieWrapper {
    public static void main(String[] args) {
        RadixTrieInterface trie = new RadixTrieInterface();
        trie.insert("apple");
        trie.insert("apps");
        trie.insert("application");

        System.out.println("Found : " + trie.search("bhanuj"));
        System.out.println("Starts With : " + trie.startsWith("bha"));
        System.out.println();

        trie.depthFirstSearch("app");

        trie.longestCommonPrefix();
    }
}
