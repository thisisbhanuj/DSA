package main.ds.linkedlist.scenarios.browser;

/**
 * Immutable PageNode with reference counting.
 * This simulates memory-safe browser history nodes.
 * Key idea:
 *  - Each node keeps track of how many "owners" (tabs or other nodes) reference it.
 *  - When a tab moves its pointer or closes, it decrements references.
 *  - Nodes are freed only when no references remain (refCount == 0).
 */
final class PageNodeRC {
    final String url;
    final PageNodeRC prev;   // pointer to previous page
    private int refCount;    // number of references to this node

    // Constructor: every new node is initially referenced by creator
    PageNodeRC(String url, PageNodeRC prev) {
        this.url = url;
        this.prev = prev;
        this.refCount = 1; // initial reference

        // Increment previous node's refCount because this node points to it
        if (prev != null) prev.incrementRef();
    }

    /**
     * Increment reference count when a new pointer points to this node.
     */
    void incrementRef() {
        refCount++;
    }

    /**
     * Release a reference to this node.
     * If no references remain, recursively release previous nodes.
     */
    void release() {
        refCount--;
        if (refCount == 0) {
            if (prev != null) prev.release();
            // Node is now free; in real system, we could nullify fields for GC
        }
    }

    int getRefCount() {
        return refCount;
    }

    @Override
    public String toString() {
        return url + "(" + refCount + ")";
    }
}

/**
 * BrowserTabRC models a browser tab with back/forward history.
 * Features:
 *  - Persistent history with reference-counted nodes.
 *  - O(1) clone by sharing nodes.
 *  - Memory-safe: nodes are freed when no tab references them.
 */
class BrowserTabRC {
    private PageNodeRC backTop;      // top of back stack
    private PageNodeRC forwardTop;   // top of forward stack
    private String current;           // current page

    BrowserTabRC(String startUrl) {
        this.current = startUrl;
    }

    /**
     * Visit a new URL:
     *  - push current page to back stack
     *  - clear forward stack
     *  - release forward nodes safely
     */
    void visit(String url) {
        if (forwardTop != null) {
            forwardTop.release();
            forwardTop = null;
        }

        backTop = new PageNodeRC(current, backTop);
        current = url;
    }

    /**
     * Go back:
     *  - move current to forward stack
     *  - pop backTop and update current
     *  - release old backTop node safely
     */
    void back() {
        if (backTop == null) return;

        forwardTop = new PageNodeRC(current, forwardTop);
        current = backTop.url;

        PageNodeRC oldBackTop = backTop;
        backTop = backTop.prev;

        oldBackTop.release();
    }

    /**
     * Go forward:
     *  - move current to back stack
     *  - pop forwardTop and update current
     *  - release old forwardTop node safely
     */
    void forward() {
        if (forwardTop == null) return;

        backTop = new PageNodeRC(current, backTop);
        current = forwardTop.url;

        PageNodeRC oldForwardTop = forwardTop;
        forwardTop = forwardTop.prev;

        oldForwardTop.release();
    }

    /**
     * Clone this tab:
     *  - O(1) time and memory
     *  - Shared nodes are safe because we increment refCounts
     */
    BrowserTabRC cloneTab() {
        BrowserTabRC clone = new BrowserTabRC(current);
        clone.backTop = backTop;
        clone.forwardTop = forwardTop;

        if (backTop != null) backTop.incrementRef();
        if (forwardTop != null) forwardTop.incrementRef();

        return clone;
    }

    /**
     * Close this tab:
     *  - Release all nodes in back and forward stacks
     *  - Ensures memory is freed safely
     */
    void close() {
        if (backTop != null) backTop.release();
        if (forwardTop != null) forwardTop.release();
    }

    @Override
    public String toString() {
        return "Current: " + current +
                " | BackTop: " + (backTop != null ? backTop : "null") +
                " | ForwardTop: " + (forwardTop != null ? forwardTop : "null");
    }
}

/**
 * Persistent browser navigation with reference-counted nodes.
 * Shows safe memory management with clones and navigation.
 */
public class BrowserRCMain {
    public static void main(String[] args) {
        System.out.println("=== Browser Tab Demo with Reference Counting ===");

        BrowserTabRC tab1 = new BrowserTabRC("home.com");

        tab1.visit("google.com");       // visit new page
        tab1.visit("leetcode.com");     // visit new page

        System.out.println("\nAfter visiting pages on Tab1:");
        System.out.println("Tab1: " + tab1);

        // Clone Tab1 → Tab2 shares history
        BrowserTabRC tab2 = tab1.cloneTab();
        System.out.println("\nAfter cloning Tab1 to Tab2:");
        System.out.println("Tab1: " + tab1);
        System.out.println("Tab2: " + tab2);

        // Tab1 visits new page → creates new node D
        tab1.visit("github.com");
        System.out.println("\nAfter Tab1 visits github.com:");
        System.out.println("Tab1: " + tab1);
        System.out.println("Tab2 (unchanged): " + tab2);

        // Tab1 goes back → moves github.com to forward stack
        tab1.back();
        System.out.println("\nAfter Tab1 goes back:");
        System.out.println("Tab1: " + tab1);

        // Tab2 goes forward (no effect because forward is empty)
        tab2.forward();
        System.out.println("\nAfter Tab2 tries forward (no effect):");
        System.out.println("Tab2: " + tab2);

        // Close Tab2 → releases nodes safely
        tab2.close();
        System.out.println("\nAfter closing Tab2 (memory freed if no references):");
        System.out.println("Tab1: " + tab1);

        // Close Tab1 → all remaining nodes released
        tab1.close();
        System.out.println("\nAfter closing Tab1 (all nodes freed):");
    }
}
