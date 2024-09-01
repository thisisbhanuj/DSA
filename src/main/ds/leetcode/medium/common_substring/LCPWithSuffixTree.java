package main.ds.leetcode.medium.common_substring;

import java.util.*;

/*
    ### Time Complexity:
    1. Suffix Tree Construction:  
       - Building a suffix tree generally takes O(n^2) time in this implementation.
         Each suffix of the string is inserted into the tree, and since each suffix can be up to `n` characters long,
         and insertion might take up to `n` time, the overall complexity becomes O(n^2).
       - Note: Optimized suffix tree constructions like Ukkonen's algorithm can reduce the time complexity to O(n),
         but the given implementation doesn't follow that approach.
    
    2. Longest Common Prefix (LCP) Calculation:
       - Finding the LCP involves traversing the suffix tree, and comparing the characters of `str1` and `str2`.
         This process could take up to O(min(len(str1), len(str2))) in the worst case, because it may need to traverse through the entire length of the shorter string.
    
    ### Space Complexity:
    1. Suffix Tree Storage:  
       - The space complexity is O(n^2) in the worst case. Each node in the suffix tree stores a substring and a list of suffix indexes.
         In the worst case, where every suffix leads to a unique path in the tree, the number of nodes could be proportional to the square of the length of the string (`n * (n+1) / 2`), leading to O(n^2) space.
    
    2. LCP Calculation:  
       - The space required for the LCP calculation is O(1), aside from the suffix tree,
         since it only involves a constant amount of extra space for the LCP array.
    
    ### Summary:
    - Time Complexity: 
      - Suffix Tree Construction: O(n^2)
      - LCP Calculation: O(min(len(str1), len(str2)))

    - Space Complexity: O(n^2)
 */

/**
 * Suffix Tree for Single String.
 * Stores all suffixes of a single string.
 * Helps in operations like substring search, longest common prefix, and longest repeated substring for that one string.
 */
public class LCPWithSuffixTree {
    static class Node {
        // Use a map or hash table to store child nodes and their corresponding edge labels.
        Map<Character, Node> children = new HashMap<>();
        int start, end;
        List<Integer> suffixIndexes = new ArrayList<>();

        Node(int start, int end) {
            this.start = start;
            this.end = end;
        }

        int edgeLength(String text) {
            return Math.min(end, text.length()) - start;
        }
    }

    private final String text;
    private Node root;

    public LCPWithSuffixTree(String text) {
        this.text = text;
        buildSuffixTree();
    }

    private void buildSuffixTree() {
        root = new Node(-1, -1); //  Begin from the root node.

        for (int i = 0; i < text.length(); i++) {
            String suffix = text.substring(i);
            addSuffix(suffix, i); // For each suffix of the string
        }
    }

    private void addSuffix(String suffix, int startIndex) {
        Node currentNode = root;
        int suffixLength = suffix.length();
        int i = 0;

        // For each character in the suffix, traverse the existing edges to match the characters.
        // If the characters match, continue along the edge.
        while (i < suffixLength) {
            char ch = suffix.charAt(i);
            // If no matching edge is found, create a new node for the remaining characters of the suffix.
            if (!currentNode.children.containsKey(ch)) {
                Node newNode = new Node(startIndex + i, text.length());
                currentNode.children.put(ch, newNode);
                return;
            }

            Node nextNode = currentNode.children.get(ch);
            int edgeLength = Math.min(nextNode.edgeLength(text), suffixLength - i);

            int j = 0;
            // If you encounter a partial match and need to insert a suffix, split the edge and create a new node.
            while (j < edgeLength && i + j < suffixLength) {
                if (text.charAt(nextNode.start + j) != suffix.charAt(i + j)) {
                    break;
                }
                j++;
            }
            if (j < edgeLength) {
                Node splitNode = new Node(nextNode.start, nextNode.start + j);
                currentNode.children.put(ch, splitNode);
                splitNode.children.put(text.charAt(nextNode.start + j), nextNode);
                nextNode.start += j;
                splitNode.children.put(suffix.charAt(i + j), new Node(startIndex + i + j, text.length()));
            } else {
                // Once you reach the end of the suffix, mark it at the leaf node with the starting index of the suffix in the original string.
                nextNode.suffixIndexes.add(startIndex);
            }

            i += j;
            currentNode = nextNode;
        }
    }

    public String longestCommonPrefix(String str1, String str2) {
        int[] lcp = new int[1];
        findLCP(root, str1, str2, 0, 0, lcp);
        return str1.substring(0, lcp[0]);
    }

    private void findLCP(Node node, String str1, String str2, int index1, int index2, int[] lcp) {
        if (node == null) return;

        // Traverse the tree as long as the characters in str1 and str2 match
        while (index1 < str1.length() && index2 < str2.length()) {
            if (!node.children.containsKey(str1.charAt(index1))) {
                break;
            }

            Node nextNode = node.children.get(str1.charAt(index1));
            int edgeLength = Math.min(nextNode.edgeLength(text), str1.length() - index1);

            // Edge Length Handling: Properly calculates the edge length to avoid overstepping the bounds of the strings.
            int i = 0;
            while (i < edgeLength && index1 + i < str1.length() && index2 + i < str2.length()) {
                if (str1.charAt(index1 + i) != str2.charAt(index2 + i)) {
                    lcp[0] += i; // Update lcp length before returning
                    return;
                }
                i++;
            }

            lcp[0] += i; // Update lcp length after successful comparison
            node = nextNode;
            index1 += i;
            index2 += i;
        }
    }

    public static void main(String[] args) {
        String text = "banana";
        LCPWithSuffixTree suffixTree = new LCPWithSuffixTree(text);

        String str1 = "banana";
        String str2 = "banpsoft";
        System.out.println("Longest common prefix between \"" + str1 + "\" and \"" + str2 + "\" is: " + suffixTree.longestCommonPrefix(str1, str2));
    }
}