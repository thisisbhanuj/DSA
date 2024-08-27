package main.ds.tree.intervals.segment.lazy.geospatial;

// When dealing with large geospatial datasets, such as satellite imagery or environmental monitoring,
// a lazy segment tree can handle updates over a region (updating pollution levels over a geographic range)
// and answer queries like "What is the average pollution level over a specific area?" efficiently.
public class GeospatialSegmentTree{
    private final double[] segmentTreeArray; // Stores aggregate data (e.g., pollution levels)
    private final double[] lazyUpdatesArray; // Stores pending updates for each segment
    private final int regionSize; // Represents the number of geographic regions

    public GeospatialSegmentTree(int regionSize) {
        this.regionSize = regionSize;
        this.segmentTreeArray = new double[4 * regionSize]; // Allocating enough space for segment tree
        this.lazyUpdatesArray = new double[4 * regionSize]; // For handling lazy updates
    }

    private void propagatePendingUpdate(int segmentStart, int segmentEnd, int node) {
        if (lazyUpdatesArray[node] != 0) { // Check if there is a pending update
            segmentTreeArray[node] += lazyUpdatesArray[node] * (segmentEnd - segmentStart + 1); // Apply the update
            if (segmentStart != segmentEnd) { // If not a leaf node, propagate update to child nodes
                lazyUpdatesArray[2 * node + 1] += lazyUpdatesArray[node];
                lazyUpdatesArray[2 * node + 2] += lazyUpdatesArray[node];
            }
            lazyUpdatesArray[node] = 0; // Clear the pending update
        }
    }

    private void updateRegion(int segmentStart, int segmentEnd, double value, int regionStart, int regionEnd, int node) {
        propagatePendingUpdate(segmentStart, segmentEnd, node);

        if (segmentStart > regionEnd || segmentEnd < regionStart) {
            return; // No overlap
        }

        if (segmentStart >= regionStart && segmentEnd <= regionEnd) { // Total overlap
            segmentTreeArray[node] += value * (segmentEnd - segmentStart + 1);
            if (segmentStart != segmentEnd) {
                lazyUpdatesArray[2 * node + 1] += value;
                lazyUpdatesArray[2 * node + 2] += value;
            }
            return;
        }

        // Partial overlap
        int mid = (segmentStart + segmentEnd) / 2;
        updateRegion(segmentStart, mid, value, regionStart, regionEnd, 2 * node + 1);
        updateRegion(mid + 1, segmentEnd, value, regionStart, regionEnd, 2 * node + 2);
        segmentTreeArray[node] = segmentTreeArray[2 * node + 1] + segmentTreeArray[2 * node + 2];
    }

    private double queryRegion(int segmentStart, int segmentEnd, int regionStart, int regionEnd, int node) {
        propagatePendingUpdate(segmentStart, segmentEnd, node);

        if (segmentStart > regionEnd || segmentEnd < regionStart) {
            return 0; // No overlap
        }

        if (segmentStart >= regionStart && segmentEnd <= regionEnd) { // Total overlap
            return segmentTreeArray[node];
        }

        // Partial overlap
        int mid = (segmentStart + segmentEnd) / 2;
        double leftSum = queryRegion(segmentStart, mid, regionStart, regionEnd, 2 * node + 1);
        double rightSum = queryRegion(mid + 1, segmentEnd, regionStart, regionEnd, 2 * node + 2);
        return leftSum + rightSum;
    }

    public void updatePollutionLevel(int regionStart, int regionEnd, double pollutionValue) {
        updateRegion(0, regionSize - 1, pollutionValue, regionStart, regionEnd, 0);
    }

    public double getTotalPollutionLevel(int regionStart, int regionEnd) {
        return queryRegion(0, regionSize - 1, regionStart, regionEnd, 0);
    }

    public double getAveragePollutionLevel(int regionStart, int regionEnd) {
        double totalPollution = getTotalPollutionLevel(regionStart, regionEnd);
        return totalPollution / (regionEnd - regionStart + 1);
    }
}
