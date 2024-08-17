package main.ds.graphs.scenarios.socialnetwork.influencer;

import java.util.List;

public class InfluencerDetection {
    public static void main(String[] args) {
        SocialNetwork socialNetwork = new SocialNetwork();

        // Add individuals and their followers to the social network
        socialNetwork.addIndividual(1);
        socialNetwork.addIndividual(2);
        socialNetwork.addIndividual(3);
        socialNetwork.addIndividual(4);
        socialNetwork.addIndividual(5);

        socialNetwork.addFollower(1, 2, 10, 4);
        socialNetwork.addFollower(1, 3, 50, 2);
        socialNetwork.addFollower(3, 2, 100, 34);
        socialNetwork.addFollower(3, 4, 1, 0);
        socialNetwork.addFollower(4, 5, 10000, 2200);
        socialNetwork.addFollower(4, 1, 0, 5);

        // Detect the top 3 influencers
        List<Integer> influencers = socialNetwork.getTopInfluencers(3);
        System.out.println("Top Influencers:");
        for (int influencer : influencers) {
            System.out.println("ID: " + influencer);
        }

        System.out.println();

        // Detect the top 3 influencers along with their average likes
        List<InfluencerInfo> topInfluencers = socialNetwork.getTopInfluencersWithAvgLikes(3);
        System.out.println("Top Influencers:");
        for (InfluencerInfo influencerInfo : topInfluencers) {
            System.out.println("ID: " + influencerInfo.getInfluencerID() + ", Average Likes: " + influencerInfo.getAverageLikes());
        }
    }
}
