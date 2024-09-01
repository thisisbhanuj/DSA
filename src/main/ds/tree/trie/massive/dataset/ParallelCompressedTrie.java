package main.ds.tree.trie.massive.dataset;

import java.util.List;
import java.util.concurrent.*;

/**
 * For very large datasets, parallelize the insertion of strings into the Trie to speed up the process.
 * -
 * Parallel insertion in a CompressedTrie can be achieved by using a divide-and-conquer approach.
 * Partition the Dataset:
 *      Divide the list of strings into smaller subsets that can be processed independently.
 * Concurrent Processing:
 *      Use Java's ForkJoinPool or ExecutorService to parallelize the insertion process for each subset.
 * Merge Tries:
 *      After parallel insertion, each subset will have its own CompressedTrie.
 *      These tries can be merged by iteratively inserting one trie into another.
 */
public class ParallelCompressedTrie extends CompressedTrie {

    private static final int THRESHOLD = 100;  // Define the threshold for splitting tasks

    public void parallelInsert(List<String> words) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new InsertTask(words, this));
    }

    private class InsertTask extends RecursiveAction {
        private final List<String> words;
        private final CompressedTrie trie;

        InsertTask(List<String> words, CompressedTrie trie) {
            this.words = words;
            this.trie = trie;
        }

        @Override
        protected void compute() {
            if (words.size() <= THRESHOLD) {
                // Insert words sequentially if below threshold
                for (String word : words) {
                    trie.insert(word);
                }
            } else {
                // Split the task into smaller tasks
                int mid = words.size() / 2;
                List<String> leftWords = words.subList(0, mid);
                List<String> rightWords = words.subList(mid, words.size());

                // Create two subtasks
                InsertTask leftTask = new InsertTask(leftWords, new CompressedTrie());
                InsertTask rightTask = new InsertTask(rightWords, new CompressedTrie());

                // Fork subtasks
                invokeAll(leftTask, rightTask);

                // Merge the results
                mergeTries(leftTask.trie, rightTask.trie);
            }
        }

        // Merge two tries by inserting one into the other
        private void mergeTries(CompressedTrie trie1, CompressedTrie trie2) {
            for (TrieNode child : trie2.root.children.values()) {
                trie1.insert(child.part);
            }
        }
    }
}
