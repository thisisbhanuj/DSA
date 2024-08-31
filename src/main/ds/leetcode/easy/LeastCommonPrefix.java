package main.ds.leetcode.easy;

class BadSolution {
    // Time Complexity: O(M * P^2), where M is the number of strings and P is the length of the shortest string
    // (because indexOf and substring operations can take time proportional to the length of the current prefix).
    // Space Complexity: O(P), where P is the length of the longest prefix.
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        // The initial assumption is that the prefix is the first string,
        // which takes O(N) time, where N is the length of the first string.
        String prefix = strs[0];

        // The for loop iterates through each string in the array.
        // In the worst case, this will be O(M), where M is the number of strings.
        for (int i = 1; i < strs.length; i++) {
            // Reduce the prefix until it matches the start of the current string
            // Inside the while loop, strs[i].indexOf(prefix) is used to check if the current prefix is a prefix of strs[i].
            // This operation takes O(P) time, where P is the length of the current prefix.
            while (strs[i].indexOf(prefix) != 0) {
                // Trim the last character from the prefix
                // The substring operation also takes O(P) time
                prefix = prefix.substring(0, prefix.length() - 1);

                // If the prefix becomes empty, return ""
                if (prefix.isEmpty()) return "";
            }
        }

        return prefix;
    }
}

// Time Complexity:
//      O(M * N), where M is the number of strings and N is the average length of the strings.
//      Inserting each string takes O(N) and there are M strings.
//      Traversing the trie to find the longest common prefix takes O(N) in the worst case.
// Space Complexity:
//      O(M * N) due to storage of all strings in the trie.
public class LeastCommonPrefix {
    class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;

        TrieNode() {
            children = new TrieNode[26]; // Assuming only lowercase English letters
            isEndOfWord = false;
        }
    }

    // A Trie (prefix tree) stores strings in such a way that common prefixes are shared among the strings.
    // Each node represents a character, and the path from the root to any node forms a prefix of one or more strings.
    class Trie {
        private TrieNode root;

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

    class Solution {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) return "";

            Trie trie = new Trie();

            // Insert all words into the trie
            for (String str : strs) {
                trie.insert(str);
            }

            // Find the longest common prefix
            return trie.longestCommonPrefix();
        }
    }

}
