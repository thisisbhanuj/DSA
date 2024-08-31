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
        TrieNode node = root;  // Start from the root of the Trie
        int i = 0;  // Index for the current character in the word

        while (i < word.length()) {  // Continue until we've processed the entire word
            char currentChar = word.charAt(i);  // Get the current character

            // Check if there's already a node that starts with the current character
            if (node.children.containsKey(currentChar)) {
                TrieNode child = node.children.get(currentChar);  // Get the child node
                String childPart = child.part;  // Get the string part stored in this child node

                // Find the common prefix between the current part of the word and the child's part
                int j = 0;
                while (j < childPart.length() && i < word.length() && word.charAt(i) == childPart.charAt(j)) {
                    i++;  // Move forward in the word
                    j++;  // Move forward in the child part
                }

                // If the entire child's part is a common prefix, move deeper in the Trie
                if (j == childPart.length()) {
                    node = child;  // Move down to the child node
                    continue;  // Continue processing the remaining part of the word
                }

                // Otherwise, we need to split the node
                String commonPrefix = childPart.substring(0, j);  // Part that is common
                String remainingChildPart = childPart.substring(j);  // Part that is unique to the child
                String remainingWordPart = word.substring(i);  // Part that is unique to the word

                // Create a new node for the remaining part of the child's string
                TrieNode newChild = new TrieNode(remainingChildPart);
                newChild.children.putAll(child.children);  // Transfer children to the new node
                newChild.isEndOfWord = child.isEndOfWord;  // Copy the end-of-word flag

                // Update the child node to only contain the common prefix
                child.part = commonPrefix;
                child.children.clear();  // Clear existing children
                child.children.put(remainingChildPart.charAt(0), newChild);  // Add the new node as a child
                child.isEndOfWord = false;  // This node is no longer the end of a word

                // If there's any remaining part in the word, add it as a new child
                if (remainingWordPart.length() > 0) {
                    TrieNode newWordNode = new TrieNode(remainingWordPart);
                    newWordNode.isEndOfWord = true;
                    child.children.put(remainingWordPart.charAt(0), newWordNode);
                } else {
                    // If the entire word matched the common prefix, mark this node as the end of the word
                    child.isEndOfWord = true;
                }
                return;  // We're done inserting
            } else {
                // No match found, create a new node with the rest of the word
                TrieNode newNode = new TrieNode(word.substring(i));
                newNode.isEndOfWord = true;  // Mark the new node as the end of a word
                node.children.put(currentChar, newNode);  // Add the new node as a child of the current node
                return;  // Insertion is complete
            }
        }
        node.isEndOfWord = true;  // Mark the node as the end of the word
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
