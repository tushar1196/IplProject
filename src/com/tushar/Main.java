package com.tushar;

import com.tushar.Model.Delivery;
import com.tushar.Model.Match;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static final int DELIVERY_BLOWING_TEAM = 3;
    public static final int DELIVERY_TOTAL_RUN = 17;
    public static final int DELIVERY_EXTRA_RUN = 16;
    public static final int DELIVERY_BLOWER = 8;
    public static final int DELIVERY_BALL = 5;
    public static final int DELIVERY_OVER = 4;
    public static final int DELIVERY_MATCH_ID = 0;
    public static final int MATCH_WINNER = 10;
    public static final int MATCH_TOSS_WINNER = 6;
    public static final int SEASON = 1;
    public static final int MATCH_ID = 0;
    private static final int MATCH_CITY = 2;

    public static void main(String[] args) {
        List<Match> matches = getMatchData();
        List<Delivery> deliveries = getDeliveryData();

        
    }

    private static List<Delivery> getDeliveryData() {
        System.out.println("Delivery Data read");
        List<Delivery> deliveries = new LinkedList<Delivery>();
        String line = "";
        try {
            final BufferedReader br = new BufferedReader(new FileReader("E:\\mountblue\\Ipl project\\archive\\deliveries.csv"));
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                Delivery delivery = new Delivery();
                delivery.setDeliveryMatchId(fields[DELIVERY_MATCH_ID]);
                delivery.setDeliveryOver(fields[DELIVERY_OVER]);
                delivery.setDeliveryBall(fields[DELIVERY_BALL]);
                delivery.setDeliveryBlower(fields[DELIVERY_BLOWER]);
                delivery.setDeliveryExtraRuns(fields[DELIVERY_EXTRA_RUN]);
                delivery.setDeliveryTotalRuns(fields[DELIVERY_TOTAL_RUN]);
                delivery.setDeliveryBlowingTeam(fields[DELIVERY_BLOWING_TEAM]);

                deliveries.add(delivery);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return deliveries;

    }

    private static List<Match> getMatchData() {
        System.out.println("Match Data read");
        final List<Match> matches = new LinkedList<Match>();
        String line = "";
        try {
            final BufferedReader br = new BufferedReader(new FileReader("E:\\mountblue\\Ipl project\\archive\\matches.csv"));
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                Match match = new Match();
                match.setMatchId(fields[MATCH_ID]);
                match.setMatchSeason(fields[SEASON]);
                match.setMatchTossWinner(fields[MATCH_TOSS_WINNER]);
                match.setMatchwinner(fields[MATCH_WINNER]);
                match.setMatchCity(fields[MATCH_CITY]);

                matches.add(match);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }
}
