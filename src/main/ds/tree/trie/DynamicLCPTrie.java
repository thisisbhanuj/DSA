package main.ds.tree.trie;

/**
 *  You're given a stream of strings where you need to maintain the longest common prefix as strings are added dynamically.
 */
public class DynamicLCPTrie {
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEndOfWord;
    }
    private final TrieNode root;
    private String lcp;

    public DynamicLCPTrie() {
        root = new TrieNode();
        lcp = "";
    }

    public void insert(String word) {
        if (word == null || word.isEmpty()) return;

        TrieNode currentNode = root;
        StringBuilder currentLCP = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';

            if (currentNode.children[index] == null) {
                currentNode.children[index] = new TrieNode();
            }

            currentNode = currentNode.children[index];

            // Update LCP only if current character matches LCP so far
            if (lcp.isEmpty() || (i < lcp.length() && lcp.charAt(i) == c)) {
                currentLCP.append(c);
            } else {
                break;
            }
        }

        currentNode.isEndOfWord = true;

        // Set the LCP to the current valid prefix
        lcp = currentLCP.toString();
    }

    public String getLCP() {
        return lcp;
    }

    public static void main(String[] args) {
        DynamicLCPTrie trie = new DynamicLCPTrie();
        trie.insert("flower");
        System.out.println(trie.getLCP());

        trie.insert("fly");
        System.out.println(trie.getLCP());

        trie.insert("flight");
        System.out.println(trie.getLCP());
    }
}
