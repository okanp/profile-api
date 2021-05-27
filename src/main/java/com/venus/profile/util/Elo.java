package com.venus.profile.util;

public class Elo {

    private static int K = 30;

    // Function to calculate the Probability
    private static float Probability(float rating1, float rating2) {
        return 1.0f * 1.0f / (1 + 1.0f *
                (float) (Math.pow(10, 1.0f *
                        (rating1 - rating2) / 400)));
    }

    // Function to calculate Elo rating
    // K is a constant.
    // d determines whether Player A wins
    // or Player B.
    public static int[] game(float Ra, float Rb, boolean playerAWins) {

        // To calculate the Winning
        // Probability of Player B
        float Pb = Probability(Ra, Rb);

        // To calculate the Winning
        // Probability of Player A
        float Pa = Probability(Rb, Ra);

        // Case -1 When Player A wins
        // Updating the Elo Ratings
        if (playerAWins) {
            Ra = Ra + K * (1 - Pa);
            Rb = Rb + K * (0 - Pb);
        }

        // Case -2 When Player B wins
        // Updating the Elo Ratings
        else {
            Ra = Ra + K * (0 - Pa);
            Rb = Rb + K * (1 - Pb);
        }

        int playerAEloRanking = (int) (Math.round(Ra * 1000000.0) / 1000000.0);
        int playerBEloRanking = (int) (Math.round(Rb * 1000000.0) / 1000000.0);

        return new int[]{playerAEloRanking, playerBEloRanking};
    }
}