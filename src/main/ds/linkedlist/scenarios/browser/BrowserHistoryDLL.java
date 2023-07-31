package ds.linkedlist.scenarios.browser;

import java.util.LinkedList;

public class BrowserHistoryDLL {
    private LinkedList<String> history;
    private Integer currentIndex;
    public BrowserHistoryDLL() {
        this.history = new LinkedList<String>();
        this.currentIndex = -1;
    }

    public void visit (String location) {
        while (currentIndex + 1 < history.size()) {
            history.removeLast();
            currentIndex++;
        }
        history.add(location);
        currentIndex++;
    }

    public String forward () {
        if (currentIndex < history.size() - 1) {
            currentIndex++;
        }

        return history.get(currentIndex);
    }

    public String back () {
        if (currentIndex > 0) {
            currentIndex--;
        }

        return history.get(currentIndex);
    }

    public static void main(String[] args) {
        BrowserHistoryDLL browserHistoryDLL = new BrowserHistoryDLL();
        browserHistoryDLL.visit("Google");
    }
}
