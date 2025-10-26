package main.ds.leetcode.easy;

public class LongestCommonPrefix {
    // Time Complexity: Compare each character of the prefix across all n strings.
    // Let m = length of the prefix → O(n * m).
    // Space Complexity: Only constant extra space for variables → O(1).
    public String linearScan(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }

    // A Trie (prefix tree) stores strings in such a way that common prefixes are shared among the strings.
    // Each node represents a character, and the path from the root to any node forms a prefix of one or more strings.
    static class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;

        TrieNode() {
            children = new TrieNode[26]; // Assuming only lowercase English letters
            isEndOfWord = false;
        }
    }
    static class Trie {
        private final TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
            node.isEndOfWord = true;
        }

        String longestCommonPrefix() {
            StringBuilder lcp = new StringBuilder();
            TrieNode node = root;

            while (countChildren(node) == 1 && !node.isEndOfWord) {
                for (int i = 0; i < 26; i++) {
                    if (node.children[i] != null) {
                        lcp.append((char) ('a' + i));
                        node = node.children[i];
                        break;
                    }
                }
            }

            return lcp.toString();
        }

        private int countChildren(TrieNode node) {
            int count = 0;
            for (TrieNode child : node.children) {
                if (child != null) {
                    count++;
                }
            }
            return count;
        }
    }

    // Time Complexity:
    //      O(M * N), where M is the number of strings and N is the average length of the strings.
    //      Inserting each string takes O(N) and there are M strings.
    //      Traversing the trie to find the longest common prefix takes O(N) in the worst case.
    // Space Complexity:
    //      O(M * N) due to storage of all strings in the trie.
    public String lcpByTrie(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        Trie trie = new Trie();

        // Insert all words into the trie
        for (String str : strs) {
            trie.insert(str);
        }

        // Find the longest common prefix
        return trie.longestCommonPrefix();
    }

    public static void main(String[] args) {

    }
}

