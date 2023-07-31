package ds.graphs.scenarios.socialnetwork.influencer;

import java.util.*;

class GraphNode {
    int ID;
    Map<GraphNode, Integer> followerLikes, followerLikesSorted;
    Map<GraphNode, Integer> postCounts, postCountsSorted;

    /*
    TreeMap modification allows you to access the followers with the highest likes and
    post counts more efficiently, which could be helpful when performing operations
    that involve influencers with the highest engagement from their followers.
    */
    public GraphNode(int id) {
        this.ID = id;
        // followerLikes map is sorted based on the total likes given by each follower
        this.followerLikes = new HashMap<>();
        this.followerLikesSorted = new TreeMap<>((a, b) -> {
            int comparison = Integer.compare(this.followerLikes.getOrDefault(b, 0), this.followerLikes.getOrDefault(a, 0));
            return comparison != 0 ? comparison : Integer.compare(this.postCounts.getOrDefault(b, 0), this.postCounts.getOrDefault(a, 0));
        });
        // postCounts map is sorted based on the total number of posts interacted with by each follower
        this.postCounts = new HashMap<>();
        this.postCountsSorted = new TreeMap<>((a, b) -> {
            int comparison = Integer.compare(this.postCounts.getOrDefault(b, 0), this.postCounts.getOrDefault(a, 0));
            return comparison != 0 ? comparison : Integer.compare(this.followerLikes.getOrDefault(b, 0), this.followerLikes.getOrDefault(a, 0));
        });
    }

    public void addFollower(GraphNode follower, int likes, int posts) {
        followerLikes.put(follower, followerLikes.getOrDefault(follower, 0) + likes);
        postCounts.put(follower, postCounts.getOrDefault(follower, 0) + posts);

        // Update the sorted maps with the new values
        followerLikesSorted.clear();
        followerLikesSorted.putAll(followerLikes);

        postCountsSorted.clear();
        postCountsSorted.putAll(postCounts);
    }

    /*
    * To calculate the average likes correctly, we need to consider both
    * the total likes and the total posts for each follower and then
    * divide by the number of times the follower interacted with the influencer.
    * */
    public double getAverageLikes() {
        double totalLikes = 0;
        int totalPosts = 0;
        for (GraphNode follower : followerLikes.keySet()) {
            int likes = followerLikes.get(follower);
            int posts = postCounts.get(follower);
            totalLikes += likes;
            totalPosts += posts;
        }

        int totalInteractions = followerLikes.size() + postCounts.size();
        return (totalInteractions != 0) ? (totalLikes + totalPosts) / (double) totalInteractions : 0;
    }

}

class InfluencerInfo {
    private int influencerID;
    private double averageLikes;

    public InfluencerInfo(int influencerID, double averageLikes) {
        this.influencerID = influencerID;
        this.averageLikes = averageLikes;
    }

    public int getInfluencerID() {
        return influencerID;
    }

    public double getAverageLikes() {
        return averageLikes;
    }
}

class SocialNetwork {
    private Map<Integer, GraphNode> nodes;

    public SocialNetwork() {
        this.nodes = new HashMap<>();
    }

    public void addIndividual(int id) {
        nodes.putIfAbsent(id, new GraphNode(id));
    }

    public void addFollower(int from, int to, int likes, int posts) {
        GraphNode fromNode = nodes.get(from);
        GraphNode toNode = nodes.get(to);
        if (fromNode != null && toNode != null) {
            fromNode.addFollower(toNode, likes, posts);
        }
    }

    // Perform BFS traversal to calculate influence scores
    public Map<Integer, Double> calculateInfluenceScores() {
        Map<Integer, Double> influenceScores = new HashMap<>();
        Queue<GraphNode> queue = new LinkedList<>();

        for (GraphNode node : nodes.values()) {
            Set<GraphNode> visited = new HashSet<>();
            queue.offer(node);
            visited.add(node);

            while (!queue.isEmpty()) {
                GraphNode current = queue.poll();
                double avgLikes = current.getAverageLikes();
                influenceScores.put(current.ID, influenceScores.getOrDefault(current.ID, 0.0) + avgLikes);

                for (GraphNode follower : current.followerLikes.keySet()) {
                    if (!visited.contains(follower)) {
                        queue.offer(follower);
                        visited.add(follower);
                    }
                }
            }
        }

        return influenceScores;
    }

    // Identify the top k influencers based on influence scores using Max Heap (PriorityQueue)
    public List<Integer> getTopInfluencers(int k) {
        Map<Integer, Double> influenceScores = calculateInfluenceScores();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(influenceScores.get(b), influenceScores.get(a)));

        for (int influencer : influenceScores.keySet()) {
            maxHeap.offer(influencer);
        }

        List<Integer> topInfluencers = new ArrayList<>();
        for (int i = 0; i < k && !maxHeap.isEmpty(); i++) {
            topInfluencers.add(maxHeap.poll());
        }

        return topInfluencers;
    }

    // Identify the top k influencers with likes, based on influence scores using Max Heap (PriorityQueue)
    public List<InfluencerInfo> getTopInfluencersWithAvgLikes(int k) {
        Map<Integer, Double> influenceScores = calculateInfluenceScores();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(influenceScores.get(b), influenceScores.get(a)));

        for (int influencer : influenceScores.keySet()) {
            maxHeap.offer(influencer);
        }

        List<InfluencerInfo> topInfluencersWithAvgLikes = new ArrayList<>();
        for (int i = 0; i < k && !maxHeap.isEmpty(); i++) {
            int influencerID = maxHeap.poll();
            double avgLikes = influenceScores.getOrDefault(influencerID, 0.0);
            topInfluencersWithAvgLikes.add(new InfluencerInfo(influencerID, avgLikes));
        }

        return topInfluencersWithAvgLikes;
    }

}
