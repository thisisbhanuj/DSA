package main.ds.tree.intervals.segment.lazy.geospatial;

public class GeospatialPollutionTest {
    public static void main(String[] args) {
        int numberOfRegions = 10;
        GeospatialSegmentTree pollutionSegmentTree = new GeospatialSegmentTree(numberOfRegions);

        // Update pollution levels over different geographic ranges
        pollutionSegmentTree.updatePollutionLevel(2, 5, 10.5); // Increase pollution by 10.5 units in regions 2 to 5
        pollutionSegmentTree.updatePollutionLevel(4, 9, 5.0); // Increase pollution by 5.0 units in regions 4 to 9

        // Query the average pollution level over a specific region
        double averagePollution = pollutionSegmentTree.getAveragePollutionLevel(3, 7);
        System.out.println("Average Pollution Level from region 3 to 7: " + averagePollution);
    }
}
