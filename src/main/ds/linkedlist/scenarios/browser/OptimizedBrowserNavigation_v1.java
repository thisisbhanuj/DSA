package main.ds.linkedlist.scenarios.browser;

import java.util.*;

/**
 * Immutable PageNode with global memory tracking.
 */
final class PageNode_v1 {
    final String url;
    final PageNode_v1 prev;
    final int id; // unique node ID for tracking

    // The global memory counter tells us how many unique PageNode objects are in existence (global RAM footprint).
    private static int globalId = 0;
    private static final Set<Integer> activeNodes = new HashSet<>();

    PageNode_v1(String url, PageNode_v1 prev) {
        this.url = url;
        this.prev = prev;
        this.id = ++globalId;
        activeNodes.add(this.id);
    }

    static int activeNodeCount() {
        return activeNodes.size();
    }

    // optional removal hook for GC simulation
    static void unregister(PageNode_v1 node) {
        if (node != null) activeNodes.remove(node.id);
    }
}

/**
 * Browser tab with persistent history (O(1) clone) and global memory tracking.
 */
class BrowserTab_v1 {
    private PageNode_v1 backTop;
    private PageNode_v1 forwardTop;
    private String current;

    BrowserTab_v1(String startUrl) {
        this.current = startUrl;
    }

    void visit(String url) {
        backTop = new PageNode_v1(current, backTop);
        current = url;
        forwardTop = null;
    }

    void back() {
        if (backTop == null) return;
        forwardTop = new PageNode_v1(current, forwardTop);
        current = backTop.url;
        backTop = backTop.prev;
    }

    void forward() {
        if (forwardTop == null) return;
        backTop = new PageNode_v1(current, backTop);
        current = forwardTop.url;
        forwardTop = forwardTop.prev;
    }

    BrowserTab_v1 cloneTab() {
        BrowserTab_v1 clone = new BrowserTab_v1(current);
        clone.backTop = backTop;       // shared memory
        clone.forwardTop = forwardTop; // shared memory
        return clone;
    }

    private int count(PageNode_v1 node) {
        int c = 0;
        for (PageNode_v1 p = node; p != null; p = p.prev) c++;
        return c;
    }

    @Override
    public String toString() {
        return "Current: " + current +
                " | Back: " + count(backTop) +
                " | Forward: " + count(forwardTop);
    }
}

/**
 * Persistent browser navigation with memory tracking.
 */
public class OptimizedBrowserNavigation_v1 {
    public static void main(String[] args) {
        List<BrowserTab_v1> tabs = new ArrayList<>();
        int maxMemory = 0;

        BrowserTab_v1 tab1 = new BrowserTab_v1("home.com");
        tabs.add(tab1);
        maxMemory = Math.max(maxMemory, PageNode_v1.activeNodeCount());
        System.out.println("Maximum memory usage observed -0: " + maxMemory);

        tab1.visit("google.com");
        tab1.visit("leetcode.com");
        tab1.back();
        tab1.visit("github.com");
        maxMemory = Math.max(maxMemory, PageNode_v1.activeNodeCount());
        System.out.println("Maximum memory usage observed -1: " + maxMemory);

        BrowserTab_v1 tab2 = tab1.cloneTab(); // O(1)
        tabs.add(tab2);
        maxMemory = Math.max(maxMemory, PageNode_v1.activeNodeCount());
        System.out.println("Maximum memory usage observed -2: " + maxMemory);

        tab1.visit("reddit.com");
        tab2.forward(); // no effect, forward stack empty for clone
        maxMemory = Math.max(maxMemory, PageNode_v1.activeNodeCount());

        System.out.println("=== Tabs State ===");
        for (int i = 0; i < tabs.size(); i++)
            System.out.println("Tab " + (i + 1) + " → " + tabs.get(i));

        System.out.println();
        System.out.println("Total unique PageNodes in memory now: " + PageNode_v1.activeNodeCount());
        System.out.println("Maximum memory usage observed -3: " + maxMemory);
    }
}
