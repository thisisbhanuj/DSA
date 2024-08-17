package main.ds.tree.trie;

/*
Non-compact trie, also known as a standard trie or radix trie, provides a flexible
and efficient solution for various trie-based operations, such as searching for words,
prefix matching, autocomplete suggestions, and more. It allows for efficient insertion,
deletion, and searching of strings. Non-compact tries are commonly used in text processing,
spell checkers, keyword matching and other applications where efficient string matching and retrieval are important.
*/
public class RadixTrieInterface {
    private int SIZE = 26;
    private RadixTrieInterface[] children;
    //private Map<Character, RadixTrieInterface> children;
    private boolean isEndOfWord;

    RadixTrieInterface() {
        children = new RadixTrieInterface[this.SIZE];
        isEndOfWord = false;
    }

    /*
    * The time & space complexity of inserting a word into the trie is O(L)
    */
    public void insert(String word){
        RadixTrieInterface current = this;
        for(char character : word.toCharArray()){
            int index = character - 'a';
            /*current.children.putIfAbsent(character, new RadixTrieInterface());
            current = current.children.get(character);*/
            if (current.children[index] == null) current.children[index] = new RadixTrieInterface();
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    /*
    * The time complexity of searching for a prefix in the trie is O(L).
    * Space complexity is O(1)
    */
    public boolean search(String word){
        RadixTrieInterface current = this;
        for(char character : word.toCharArray()) {
            int index = character - 'a';
            if (current.children[index] == null) { // !current.children.containsKey(character)
                return false;
            }
            current = current.children[index];
        }
        return current.isEndOfWord;
    }

    /*
     * The time complexity of searching for a word in the trie is O(P).
     * Space complexity is O(1)
     */
    public boolean startsWith(String prefix){
        RadixTrieInterface current = this;
        if(prefix != null) {
            for (char character : prefix.toCharArray()) {
                int index = character - 'a';
                if (current.children[index] == null){ // !current.children.containsKey(character)
                    return false;
                }
                current = current.children[index];
            }
            return true;
        }
        return false;
    }

    /*
    *  The time complexity of performing a depth-first search (DFS) in the trie is O(N)
    *  The space complexity is O(P), where P is the length of the prefix
    */
    public void depthFirstSearch(RadixTrieInterface node, String prefix) {
        for (char character : prefix.toCharArray()) {
            int index = character - 'a';
            if (node.children[index] == null) {
                return;
            }
            node = node.children[index];
        }
        // Start DFS from the node corresponding to the last character of the prefix
        depthFirstSearchHelper(node, prefix);
    }

    public void depthFirstSearchHelper(RadixTrieInterface node, String word) {
        if (node.isEndOfWord) {
            System.out.println(word);
            return;
        }

        /*for (char c = 'a'; c <= 'z'; c++) {
            int index = c - 'a';
            if (node.children[index] != null) {
                depthFirstSearchHelper(node.children[index], word + c);
            }
        }*/

        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null) {
                char c = (char) ('a' + i);
                depthFirstSearchHelper(node.children[i], word + c);
            }
        }
    }
}
