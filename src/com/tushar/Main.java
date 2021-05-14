package com.tushar;

import com.tushar.Model.Delivery;
import com.tushar.Model.Match;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    public static final int MATCH_CITY = 2;

    public static void main(String[] args) {
        List<Match> matches = getMatchData();
        List<Delivery> deliveries = getDeliveryData();

        findNumberOfMatchesPlayedPerSeason(matches);
        findNumberOfMatchesWonByTeam(matches);
        findExtraRunsConcededPerTeamIn2016(matches, deliveries,"2016");
        findMostEcoNomicalBlowerIn2015(matches,deliveries,"2015");
        findMatchesPlayedInEachCity(matches);
    }

    private static void findMatchesPlayedInEachCity(List<Match> matches) {
        Map<String,Integer> CityVsFrequency = new TreeMap<>();

        for (Match match:matches) {
            if (CityVsFrequency.get(match.getMatchCity())==null && !(match.getMatchCity().isBlank())) {
                CityVsFrequency.put(match.getMatchCity(), 1);
            }
            else {
                if (!(match.getMatchCity().isBlank())) {
                    CityVsFrequency.put(match.getMatchCity(), CityVsFrequency.get(match.getMatchCity()) + 1);
                }
            }
        }
        System.out.println("\n Number of matches in each city \n" +CityVsFrequency);
    }

    private static void findMostEcoNomicalBlowerIn2015(List<Match> matches, List<Delivery> deliveries, String year) {
        System.out.println("\n findMostEcoNomicalBlowerIn2015");

        int index;
        List <String> blower;
        Set<String> blowerNameIn2015;
        List <Double> economy;

        List<Delivery> deliveriesDataIn2015 = new LinkedList<>();
        Map<String, Integer> blowerVsOvers = new TreeMap<>();
        Map<String, Integer> blowerVsTotalruns = new TreeMap<>();
        Map<String,Double> blowerVsEconomy = new TreeMap<>();
        Set<String> matchIdIn2015 = new HashSet<>();

        for (Match match:matches) {
            if (match.getMatchSeason().equals(year)) {
                matchIdIn2015.add(match.getMatchId());
            }
        }
        for (Delivery delivery:deliveries) {
            if(matchIdIn2015.contains(delivery.getDeliveryMatchId())) {
                deliveriesDataIn2015.add(delivery);
            }
        }
        for (Delivery delivery:deliveriesDataIn2015) {
            if(blowerVsOvers.get(delivery.getDeliveryBlower())==null) {
                int overs=(Integer.parseInt(delivery.getDeliveryBall())+ 6*(Integer.parseInt(delivery.getDeliveryOver())))/6;
                blowerVsOvers.put(delivery.getDeliveryBlower(),overs);
                blowerVsTotalruns.put(delivery.getDeliveryBlower(),Integer.parseInt(delivery.getDeliveryTotalRuns()));
            }
            else {
                int overs=blowerVsOvers.get(delivery.getDeliveryBlower())+Integer.parseInt(delivery.getDeliveryBall())+ 6*(Integer.parseInt(delivery.getDeliveryOver()))/6;
                blowerVsOvers.put(delivery.getDeliveryBlower(),overs);
                blowerVsTotalruns.put(delivery.getDeliveryBlower(),blowerVsTotalruns.get(delivery.getDeliveryBlower())+Integer.parseInt(delivery.getDeliveryTotalRuns()));
            }
        }
        blowerNameIn2015=blowerVsOvers.keySet();
        blowerNameIn2015=blowerVsTotalruns.keySet();

        for (String blowerName:blowerNameIn2015) {
            blowerVsEconomy.put(blowerName, (double) (blowerVsTotalruns.get(blowerName) / (blowerVsOvers.get(blowerName) / 6)));
        }
        blower = blowerVsEconomy.keySet().stream().collect(Collectors.toList());
        economy = blowerVsEconomy.values().stream().collect(Collectors.toList());
        index=economy.indexOf(Collections.max(economy));
        System.out.println(blower.get(index)+">"+economy.get(index));
    }

    private static void findExtraRunsConcededPerTeamIn2016(List<Match> matches, List<Delivery> deliveries, String year) {
        System.out.println("\n findExtraRunsConcededPerTeamIn2016");

        List<Delivery> deliveriesDataIn2016 = new LinkedList<Delivery>();
        Set<String> matchIdIn2016 = new LinkedHashSet<String>();
        Map <String,Integer> extraRunsConcended = new HashMap<>();


        for (Match match : matches) {
            if(match.getMatchSeason().equals(year)) {
                matchIdIn2016.add(match.getMatchId());
            }
        }
        for (Delivery delivery : deliveries) {
            if(matchIdIn2016.contains(delivery.getDeliveryMatchId())) {
                deliveriesDataIn2016.add(delivery);
            }
        }
        for (Delivery delivery : deliveriesDataIn2016) {
            if (extraRunsConcended.get(delivery.getDeliveryBlowingTeam()) == null) {
                extraRunsConcended.put(delivery.getDeliveryBlowingTeam(), Integer.parseInt(delivery.getDeliveryExtraRuns()));
            } else {
                extraRunsConcended.put(delivery.getDeliveryBlowingTeam(), extraRunsConcended.get(delivery.getDeliveryBlowingTeam()) + Integer.parseInt(delivery.getDeliveryExtraRuns()));
            }
        }
        System.out.println(extraRunsConcended);
    }

    private static void findNumberOfMatchesWonByTeam(List<Match> matches) {
        System.out.println("\nNo of matches won by team ");
        Set<String> uniqueWinners = new LinkedHashSet<String>();
        List<String> winners = new LinkedList<String>();
        for (int i = 0; i < matches.size(); i++) {
            uniqueWinners.add(matches.get(i).getMatchwinner());
            winners.add(matches.get(i).getMatchwinner());
        }
        Iterator iterator = uniqueWinners.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            String oneWinner = (String) iterator.next();
            int frequency = Collections.frequency(winners, oneWinner);
            if (oneWinner.length() == 0) {
                continue;
            } else {
                System.out.println(oneWinner + "=" + frequency);
            }
        }
    }

    private static void findNumberOfMatchesPlayedPerSeason(List<Match> matches) {
        System.out.println("\n NumberOfMatchesPlayedPerSeason");
        Set<String> uniqueSeason = new LinkedHashSet<String>();
        List<String> season = new LinkedList<String>();
        for (int i = 0; i < matches.size(); i++) {
            season.add(matches.get(i).getMatchSeason());
            uniqueSeason.add(matches.get(i).getMatchSeason());
        }
        Iterator iterator = uniqueSeason.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            String oneSeason = (String) iterator.next();
            int frequency = Collections.frequency(season, oneSeason);
            System.out.println(oneSeason + "=" + frequency);
        }
    }

    private static List<Delivery> getDeliveryData() {
        List<Delivery> deliveries = new LinkedList<Delivery>();
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("E:\\mountblue\\Ipl project\\archive\\deliveries.csv"));
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    private static List<Match> getMatchData() {
        List<Match> matches = new LinkedList<Match>();
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("E:\\mountblue\\Ipl project\\archive\\matches.csv"));
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
