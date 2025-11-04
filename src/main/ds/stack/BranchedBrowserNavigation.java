package main.ds.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

class BrowserTab {
    Deque<String> backStack = new ArrayDeque<>();
    Deque<String> forwardStack = new ArrayDeque<>();
    String current;

    BrowserTab(String startUrl) {
        current = startUrl;
    }

    void visit(String url) {
        if (current != null) backStack.push(current);
        current = url;
        forwardStack.clear();
    }

    void back() {
        if (!backStack.isEmpty()) {
            forwardStack.push(current);
            current = backStack.pop();
        }
    }

    void forward() {
        if (!forwardStack.isEmpty()) {
            backStack.push(current);
            current = forwardStack.pop();
        }
    }

    // Cloning is a deep copy → every clone physically duplicates URLs
    BrowserTab cloneUpTo(int n) {
        // n = number of steps from oldest to clone
        BrowserTab newTab = new BrowserTab(current);

        List<String> temp = new ArrayList<>(backStack);
        // keep oldest first
        for (int i = temp.size() - 1; i >= temp.size() - n && i >= 0; i--) {
            newTab.backStack.push(temp.get(i));
        }

        newTab.forwardStack = new ArrayDeque<>(forwardStack); // copy forward stack
        return newTab;
    }
}

public class BranchedBrowserNavigation {
    static void getTotalUrlsInMemory(List<BrowserTab> tabs) {
        int total = 0;
        for (BrowserTab tab : tabs)
            total += tab.backStack.size() + tab.forwardStack.size() + 1; // current
        System.out.println("Total URLS: " + total);
    }

    public static void main(String[] args) {
        List<BrowserTab> tabs = new ArrayList<>();
        BrowserTab tab1 = new BrowserTab("home.com");
        tabs.add(tab1);

        tab1.visit("google.com");
        tab1.visit("leetcode.com");
        tab1.back();
        tab1.visit("github.com"); // clears forward stack

        // Clone tab1 up to 2 steps of backStack
        BrowserTab tab2 = tab1.cloneUpTo(2);
        tabs.add(tab2);

        tab1.back();
        tab2.forward(); // testing independent stacks

        getTotalUrlsInMemory(tabs);
    }
}
