package com.tushar.Model;

public class Match {
    private String id;
    private String season;
    private String tossWinner;
    private String winner;
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
