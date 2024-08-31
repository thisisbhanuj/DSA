package main.ds.tree.trie;

/*
    Non-compact trie, also known as a standard trie or radix trie, provides a flexible
    and efficient solution for various trie-based operations, such as searching for words,
    prefix matching, autocomplete suggestions, and more. It allows for efficient insertion,
    deletion, and searching of strings. Non-compact tries are commonly used in text processing,
    spell checkers, keyword matching and other applications where efficient string matching and retrieval are important.
*/
public class RadixTrieInterface {
    private static final int SIZE = 26;
    private RadixTrieInterface[] children;
    private boolean isEndOfWord;

    public RadixTrieInterface() {
        children = new RadixTrieInterface[SIZE];
        isEndOfWord = false;
    }

    /*
     * The time & space complexity of inserting a word into the trie is O(L)
     */
    public void insert(String word){
        RadixTrieInterface current = this;
        for(char character : word.toCharArray()){
            int index = character - 'a';
            if (current.children[index] == null) {
                current.children[index] = new RadixTrieInterface();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    /**
     * Searches word
     * @Time-Complexity The time complexity of searching for a word in the trie is O(L).
     * @Space-Complexity Space complexity is O(1)
     */
    public boolean search(String word){
        RadixTrieInterface current = this;
        for(char character : word.toCharArray()) {
            int index = character - 'a';
            if (current.children[index] == null) {
                return false;
            }
            current = current.children[index];
        }
        return current.isEndOfWord;
    }

    /**
     * Checks if any word in the trie starts with the given prefix.
     *
     * @Time-Complexity The time complexity of searching for a prefix in the trie is O(P).
     * @Space-Complexity Space complexity is O(1)
     */
    public boolean startsWith(String prefix){
        RadixTrieInterface current = this;
        for (char character : prefix.toCharArray()) {
            int index = character - 'a';
            if (current.children[index] == null) {
                return false;
            }
            current = current.children[index];
        }
        return true;
    }

    /**
     *  This method is used to explore all words in the trie that start with a given prefix.
     *  It prints out or processes all words that have the given prefix, typically
     *  using Depth-First Search (DFS) to explore the subtree of the prefix node.
     *
     *  @Time-Complexity In the worst case, DFS will visit all nodes in the subtree,
     *  making the time complexity O(N), where N is the total number of nodes in the subtree.
     *
     *  @Space-Complexity The space complexity of DFS is determined by the recursion stack. In the worst case,
     *  if the trie is skewed, the stack could be as deep as the length of the word (or prefix),
     *  so the space complexity is O(P + D), where D is the maximum depth of the trie.
     */
    public void depthFirstSearch(String prefix) {
        RadixTrieInterface node = this;
        for (char character : prefix.toCharArray()) {
            int index = character - 'a';
            if (node.children[index] == null) {
                return; // No words found with this prefix
            }
            node = node.children[index];
        }
        depthFirstSearchHelper(node, prefix);
    }

    private void depthFirstSearchHelper(RadixTrieInterface node, String word) {
        if (node.isEndOfWord) {
            System.out.println(word);
        }

        for (int i = 0; i < SIZE; i++) {
            if (node.children[i] != null) {
                char nextChar = (char) ('a' + i);
                depthFirstSearchHelper(node.children[i], word + nextChar);
            }
        }
    }

    /**
        @Time-Complexity
        1. countChildren(current):
           This method iterates over all possible children of a node.
           Its time complexity is O(SIZE), where SIZE is 26.
           Since SIZE is constant, this is effectively O(1).

        2. while Loop:
           The loop continues as long as the current node is not an end of a word and has exactly one child.
           The number of iterations is determined by the length of the longest common prefix.

        3. for Loop:
           The `for` loop iterates through the child nodes of the current node.
           Since SIZE is constant, this is effectively O(1).

        Combined Complexity
        - The `while` loop iterates up to the length of the longest common prefix.
        - If the longest common prefix has a length `L`, then the loop will run O(L) times.
        - Inside the `while` loop, the `for` loop runs in constant time, O(1), since SIZE is constant.

        So, the overall time complexity of the `longestCommonPrefix` method is O(L)

        @Space-Complexity
        - The space complexity is primarily determined by the space used by the `StringBuilder` `lcp`,
          which grows with the length of the longest common prefix. Hence, the space complexity is O(L)

        In summary:
        -Time Complexity: O(L)
        -Space Complexity: O(L)
    */
    public void longestCommonPrefix() {
        StringBuilder lcp = new StringBuilder();
        RadixTrieInterface current = this;

        while (!current.isEndOfWord && countChildren(current) == 1) {
            // Find the single child node
            for (int i = 0; i < SIZE; i++) {
                if (current.children[i] != null) {
                    current = current.children[i];  // Move to this child
                    lcp.append((char) ('a' + i));  // Append the corresponding character
                    break;  // Since there's only one child, we break the loop
                }
            }
        }

        System.out.println("Longest Common Prefix: " + lcp);
    }

    private int countChildren(RadixTrieInterface node) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (node.children[i] != null) {
                count++;
            }
        }
        return count;
    }
}
