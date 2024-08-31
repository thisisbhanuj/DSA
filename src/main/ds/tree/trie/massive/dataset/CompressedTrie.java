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

        // Traverse through the word to insert it into the Trie
        while (i < word.length()) {
            char currentChar = word.charAt(i);

            // If the current character matches a child node
            if (node.children.containsKey(currentChar)) {
                TrieNode child = node.children.get(currentChar);

                // Find the length of the common prefix between the word and the current node's part
                int commonPrefixLength = findCommonPrefixLength(word, i, child.part);

                // If the entire part of the child matches, move deeper in the Trie
                if (commonPrefixLength == child.part.length()) {
                    node = child;
                    i += commonPrefixLength;  // Move forward in the word
                    continue;  // Continue processing the remaining part of the word
                }

                // If there's a partial match, split the node and insert the remaining word part
                splitNode(node, child, commonPrefixLength, word.substring(i + commonPrefixLength));
                return;  // Insertion is complete
            } else {
                // No matching child node, add the remaining part of the word as a new node
                addNewNode(node, word.substring(i));
                return;  // Insertion is complete
            }
        }
        // Mark the node as the end of the word
        node.isEndOfWord = true;
    }

    /**
     * Finds the length of the common prefix between the word being inserted
     * and the part stored in the child node.
     */
    private int findCommonPrefixLength(String word, int startIndex, String childPart) {
        int j = 0;
        // Compare characters until they differ or one string ends
        while (j < childPart.length() && startIndex + j < word.length() && word.charAt(startIndex + j) == childPart.charAt(j)) {
            j++;
        }
        return j;
    }

    /**
     * Splits the child node into two parts based on the common prefix length.
     * Inserts the remaining part of the word as a new node if necessary.
     */
    private void splitNode(TrieNode parent, TrieNode child, int commonPrefixLength, String remainingWordPart) {
        // Extract the common prefix and the unique parts
        String commonPrefix = child.part.substring(0, commonPrefixLength);
        String remainingChildPart = child.part.substring(commonPrefixLength);

        // Create a new node for the remaining part of the child's string
        TrieNode newChild = new TrieNode(remainingChildPart);
        newChild.children.putAll(child.children);  // Transfer children to the new node
        newChild.isEndOfWord = child.isEndOfWord;  // Copy the end-of-word flag

        // Update the child node to only contain the common prefix
        child.part = commonPrefix;
        child.children.clear();  // Clear existing children
        child.children.put(remainingChildPart.charAt(0), newChild);  // Add the new node as a child
        child.isEndOfWord = false;  // This node is no longer the end of a word

        // If there's any remaining part in the word, add it as a new child node
        if (!remainingWordPart.isEmpty()) {
            TrieNode newWordNode = new TrieNode(remainingWordPart);
            newWordNode.isEndOfWord = true;
            child.children.put(remainingWordPart.charAt(0), newWordNode);
        } else {
            // If the entire word matched the common prefix, mark this node as the end of the word
            child.isEndOfWord = true;
        }
    }

    /**
     * Adds a new node for the remaining part of the word when no matching child exists.
     */
    private void addNewNode(TrieNode node, String remainingWordPart) {
        TrieNode newNode = new TrieNode(remainingWordPart);
        newNode.isEndOfWord = true;  // Mark the new node as the end of the word
        node.children.put(remainingWordPart.charAt(0), newNode);  // Add the new node as a child of the current node
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
        trie.insert("flowering");
        trie.insert("flow");
        trie.insert("flight");

        String lcp = trie.findLongestCommonPrefix();
        System.out.println("Longest Common Prefix: " + lcp);  // Output: "fl"
    }
}
