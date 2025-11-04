package main.ds.linkedlist.scenarios.browser;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable PageNode represents a URL in the browser history.
 * Each node links to its previous node, forming a persistent stack.
 */
final class PageNode {
    final String url;
    final PageNode prev;

    PageNode(String url, PageNode prev) {
        this.url = url;
        this.prev = prev;
    }
}

/**
 * BrowserTab represents a single browser tab using persistent (immutable) history.
 * Supports O(1) clone operation through structural sharing.
 */
class BrowserTab {
    private PageNode backTop;       // top of the back history
    private PageNode forwardTop;    // top of the forward history
    private String current;         // current URL

    BrowserTab(String startUrl) {
        this.current = startUrl;
    }

    /** Visit a new URL. Clears forward history. */
    void visit(String url) {
        backTop = new PageNode(current, backTop);
        current = url;
        forwardTop = null; // forward history cleared
    }

    /** Navigate back if possible. */
    void back() {
        if (backTop == null) return;
        forwardTop = new PageNode(current, forwardTop);
        current = backTop.url;
        backTop = backTop.prev;
    }

    /** Navigate forward if possible. */
    void forward() {
        if (forwardTop == null) return;
        backTop = new PageNode(current, backTop);
        current = forwardTop.url;
        forwardTop = forwardTop.prev;
    }

    /** Create a new tab sharing this tab’s history (O(1) memory). */
    BrowserTab cloneTab() {
        BrowserTab clone = new BrowserTab(current);
        clone.backTop = backTop;
        clone.forwardTop = forwardTop;
        return clone;
    }

    /** Count total URLs reachable in this tab (for debug/metrics). */
    int historyCount() {
        return 1 + countNodes(backTop) + countNodes(forwardTop);
    }

    private int countNodes(PageNode node) {
        int count = 0;
        for (PageNode p = node; p != null; p = p.prev) count++;
        return count;
    }

    @Override
    public String toString() {
        return "Current: " + current + " | Back: " + countNodes(backTop) + " | Forward: " + countNodes(forwardTop);
    }
}

/**
 * Demonstrates persistent browser navigation with instant cloning.
 */
public class OptimizedBrowserNavigation_v0 {
    public static void main(String[] args) {
        List<BrowserTab> tabs = new ArrayList<>();

        BrowserTab tab1 = new BrowserTab("home.com");
        tabs.add(tab1);

        tab1.visit("google.com");
        tab1.visit("leetcode.com");
        tab1.back();
        tab1.visit("github.com"); // clears forward stack

        BrowserTab tab2 = tab1.cloneTab(); // O(1) clone
        tabs.add(tab2);

        tab1.visit("reddit.com");
        tab2.forward(); // no effect, forward stack empty for clone

        System.out.println("=== Tabs State ===");
        for (int i = 0; i < tabs.size(); i++) {
            System.out.println("Tab " + (i + 1) + " → " + tabs.get(i));
        }

        System.out.println();
        System.out.println("Do Tab1 and Tab2 share back history? " + (tab1 != tab2 && tab1.toString().equals(tab2.toString())));
        System.out.println("Tab1 URLs in memory: " + tab1.historyCount());
        System.out.println("Tab2 URLs in memory: " + tab2.historyCount());
    }
}
