package main.ds.tree.trie.massive.dataset;

import java.util.HashMap;
import java.util.Map;

/**
 * A Compressed / Patricia Trie is a more memory-efficient version of a standard Trie,
 * where nodes with only one child are merged with their parent. This compression reduces
 * the depth of the Trie and minimizes the number of nodes, making it ideal for large datasets.
 *
 * @Optimizations:
 * @Path-Compression: The merging of nodes with only one child ensures that the Trie remains shallow, reducing memory usage and speeding up both insertion and lookup.
 * @Efficient-Data-Structures: Using a HashMap for the children allows for fast access and insertion.
 * @Memory-Management: By compressing the Trie, you save on memory, making it feasible to handle very large datasets.
 */
public class CompressedTrie {
    static class TrieNode {
        String part;  // Part of the string stored in this node
        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        TrieNode(String part) {
            this.part = part;
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }
    }

    TrieNode root;

    CompressedTrie() {
        root = new TrieNode("");
    }

    /**
     * The insert method ensures that nodes with common prefixes are merged,
     * and nodes with partial matches are split, preserving the efficiency of the Trie.
     */
    void insert(String word) {
        TrieNode node = root;
        int i = 0;

        while (i < word.length()) {
            char currentChar = word.charAt(i);
            if (node.children.containsKey(currentChar)) {
                TrieNode child = node.children.get(currentChar);
                String childPart = child.part;

                // Find the common prefix between the word part and the existing node's part
                int j = 0;
                while (j < childPart.length() && i < word.length() && word.charAt(i) == childPart.charAt(j)) {
                    i++;
                    j++;
                }

                // If the entire child part is a common prefix, move deeper
                if (j == childPart.length()) {
                    node = child;
                    continue;
                }

                // Split the node if there's only a partial match
                String commonPrefix = childPart.substring(0, j);
                String remainingChildPart = childPart.substring(j);
                String remainingWordPart = word.substring(i);

                TrieNode newChild = new TrieNode(remainingChildPart);
                newChild.children.putAll(child.children);
                newChild.isEndOfWord = child.isEndOfWord;

                child.part = commonPrefix;
                child.children.clear();
                child.children.put(remainingChildPart.charAt(0), newChild);
                child.isEndOfWord = false;

                if (remainingWordPart.length() > 0) {
                    TrieNode newWordNode = new TrieNode(remainingWordPart);
                    newWordNode.isEndOfWord = true;
                    child.children.put(remainingWordPart.charAt(0), newWordNode);
                } else {
                    child.isEndOfWord = true;
                }
                return;
            } else {
                // No match found, create a new child
                TrieNode newNode = new TrieNode(word.substring(i));
                newNode.isEndOfWord = true;
                node.children.put(currentChar, newNode);
                return;
            }
        }
        node.isEndOfWord = true; // Mark the end of the word
    }

    /**
     * The findLongestCommonPrefix method traverses the Trie as long as there is only one child and no end of the word is reached.
     * This path represents the longest common prefix shared by all inserted strings.
     */
    String findLongestCommonPrefix() {
        TrieNode node = root;
        StringBuilder lcp = new StringBuilder();

        while (node != null && node.children.size() == 1 && !node.isEndOfWord) {
            Map.Entry<Character, TrieNode> entry = node.children.entrySet().iterator().next();
            lcp.append(entry.getValue().part);
            node = entry.getValue();
        }

        return lcp.toString();
    }

    public static void main(String[] args) {
        CompressedTrie trie = new CompressedTrie();
        trie.insert("flower");
        trie.insert("flow");
        trie.insert("flight");

        String lcp = trie.findLongestCommonPrefix();
        System.out.println("Longest Common Prefix: " + lcp);  // Output: "fl"
    }
}
