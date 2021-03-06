package com.tushar;

import com.tushar.Model.Delivery;
import com.tushar.Model.Match;

import java.sql.*;
import java.util.*;

class ComparatorClass implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
    }
}

public class Main {
    public static final int DELIVERY_BLOWING_TEAM = 4;
    public static final int DELIVERY_TOTAL_RUN = 18;
    public static final int DELIVERY_EXTRA_RUN = 17;
    public static final int DELIVERY_BLOWER = 9;
    public static final int DELIVERY_BALL = 6;
    public static final int DELIVERY_OVER = 5;
    public static final int DELIVERY_MATCH_ID = 1;
    public static final int MATCH_WINNER = 11;
    public static final int MATCH_TOSS_WINNER = 7;
    public static final int SEASON = 2;
    public static final int MATCH_ID = 1;
    public static final int MATCH_CITY = 3;

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();

        findNumberOfMatchesPlayedPerSeason(matches);
        findNumberOfMatchesWonByTeam(matches);
        findExtraRunsConcededPerTeamIn2016(matches, deliveries);
        findMostEconomicalBlowerIn2015(matches, deliveries);
        findMatchesPlayedInEachCity(matches);
    }

    private static void findMatchesPlayedInEachCity(List<Match> matches) {
        Map<String, Integer> numberOfMatchesPlayedInCity = new HashMap<>();

        for (Match match : matches) {
            if (numberOfMatchesPlayedInCity.get(match.getCity()) == null && !(match.getCity().isBlank())) {
                numberOfMatchesPlayedInCity.put(match.getCity(), 1);
            } else if (!(match.getCity().isBlank())) {
                numberOfMatchesPlayedInCity.put(match.getCity(), numberOfMatchesPlayedInCity.get(match.getCity()) + 1);
            }
        }
        System.out.println("\n Number Of Matches Played In Each City \n" + numberOfMatchesPlayedInCity);
    }

    private static void findMostEconomicalBlowerIn2015(List<Match> matches, List<Delivery> deliveries) {
        List<Delivery> deliveriesDataIn2015 = new ArrayList<>();
        Set<String> matchIdIn2015 = new HashSet<>();
        Map<String, Integer> oversBolledByBlower = new HashMap<>();
        Map<String, Integer> totalRunsGaveByBlower = new HashMap<>();
        Map<String, Double> economyRateOfBlower = new HashMap<>();

        for (Match match : matches) {
            if (match.getSeason().equals("2015")) {
                matchIdIn2015.add(match.getId());
            }
        }
        Set<String> blowerNameIn2015 = new HashSet<>();
        for (Delivery delivery : deliveries) {
            if (matchIdIn2015.contains(delivery.getMatchId())) {
                deliveriesDataIn2015.add(delivery);
                blowerNameIn2015.add(delivery.getBlower());
            }
        }
        for (Delivery delivery : deliveriesDataIn2015) {
            if (oversBolledByBlower.get(delivery.getBlower()) == null) {
                int overs = (Integer.parseInt(delivery.getOver()) * 6 + Integer.parseInt(delivery.getBall())) / 6;
                oversBolledByBlower.put(delivery.getBlower(), overs);
            } else {
                int overs = (6 * (Integer.parseInt(delivery.getOver()) + oversBolledByBlower.get(delivery.getBlower()))) / 6;
                oversBolledByBlower.put(delivery.getBlower(), overs);
            }
        }
        for (Delivery delivery : deliveriesDataIn2015) {
            if (totalRunsGaveByBlower.get(delivery.getBlower()) == null) {
                totalRunsGaveByBlower.put(delivery.getBlower(), Integer.parseInt(delivery.getTotalRuns()));
            } else {
                totalRunsGaveByBlower.put(delivery.getBlower(), totalRunsGaveByBlower.get(delivery.getBlower()) + Integer.parseInt(delivery.getTotalRuns()));
            }
            for (String blowerName : blowerNameIn2015) {
                if (totalRunsGaveByBlower.get(blowerName) == null || oversBolledByBlower.get(blowerName) == null) {
                    continue;
                } else {
                    double economy = ((double) totalRunsGaveByBlower.get(blowerName) / oversBolledByBlower.get(blowerName));
                    economyRateOfBlower.put(blowerName, economy);
                }
            }
        }
        List<Map.Entry> list = new ArrayList<>(economyRateOfBlower.entrySet());
        list.sort(new ComparatorClass());
        System.out.println("\n Most Economical Blower In 2015 is:");
        System.out.println(list.get(list.size() - 1).getKey() + "=" + list.get(list.size() - 1).getValue());
    }

    private static void findExtraRunsConcededPerTeamIn2016(List<Match> matches, List<Delivery> deliveries) {
        System.out.println("\n Extra Runs Conceded Per Team In 2016:");

        List<Delivery> deliveriesDataIn2016 = new ArrayList<>();
        List<String> matchIdsIn2016 = new ArrayList<>();
        Map<String, Integer> extraRunsConceded = new HashMap<>();

        for (Match match : matches) {
            if (match.getSeason().equals("2016")) {
                matchIdsIn2016.add(match.getId());
            }
        }
        for (Delivery delivery : deliveries) {
            if (matchIdsIn2016.contains(delivery.getMatchId())) {
                deliveriesDataIn2016.add(delivery);
            }
        }
        for (Delivery delivery : deliveriesDataIn2016) {
            if (extraRunsConceded.get(delivery.getBlowingTeam()) == null) {
                extraRunsConceded.put(delivery.getBlowingTeam(), Integer.parseInt(delivery.getExtraRuns()));
            } else {
                extraRunsConceded.put(delivery.getBlowingTeam(), extraRunsConceded.get(delivery.getBlowingTeam()) + Integer.parseInt(delivery.getExtraRuns()));
            }
        }
        System.out.println(extraRunsConceded);
    }

    private static void findNumberOfMatchesWonByTeam(List<Match> matches) {
        System.out.println("\nNo of matches won by team ");
        Map<String, Integer> numberOfMatchesWonByTeam = new HashMap<>();
        matches.remove(0);
        for (Match match : matches) {
            if (numberOfMatchesWonByTeam.get(match.getWinner()) == null) {
                numberOfMatchesWonByTeam.put(match.getWinner(), 1);
            } else {
                numberOfMatchesWonByTeam.put(match.getWinner(), numberOfMatchesWonByTeam.get(match.getWinner()) + 1);
            }
        }
        numberOfMatchesWonByTeam.remove("");
        System.out.println(numberOfMatchesWonByTeam);
    }

    private static void findNumberOfMatchesPlayedPerSeason(List<Match> matches) {
        System.out.println("\n Number Of Matches Played Per Season:");
        Map<String, Integer> numberOfMatchesPerSeason = new HashMap<>();
        matches.remove(0);
        for (Match match : matches) {
            numberOfMatchesPerSeason.merge(match.getSeason(), 1, Integer::sum);
        }
        System.out.println(numberOfMatchesPerSeason);
    }

    private static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveries = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ipl_project", "root", "Tushar@420");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from deliveries");
            while (resultSet.next()) {
                Delivery delivery = new Delivery();
                delivery.setMatchId(resultSet.getString(DELIVERY_MATCH_ID));
                delivery.setOver(resultSet.getString(DELIVERY_OVER));
                delivery.setBall(resultSet.getString(DELIVERY_BALL));
                delivery.setBlower(resultSet.getString(DELIVERY_BLOWER));
                delivery.setExtraRuns(resultSet.getString(DELIVERY_EXTRA_RUN));
                delivery.setTotalRuns(resultSet.getString(DELIVERY_TOTAL_RUN));
                delivery.setBlowingTeam(resultSet.getString(DELIVERY_BLOWING_TEAM));

                deliveries.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries;
    }


    private static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ipl_project", "root", "Tushar@420");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from matches");
            while (resultSet.next()) {
                Match match = new Match();
                match.setId(resultSet.getString(MATCH_ID));
                match.setSeason(resultSet.getString(SEASON));
                match.setTossWinner(resultSet.getString(MATCH_TOSS_WINNER));
                match.setWinner(resultSet.getString(MATCH_WINNER));
                match.setCity(resultSet.getString(MATCH_CITY));

                matches.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }
}