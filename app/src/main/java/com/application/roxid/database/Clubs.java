package com.application.roxid.database;

public class Clubs {
    // Necesary
    private int clubId;
    private String clubName;
    private String clubPicture;

    public Clubs(String clubName, String clubPicture) {
        this.clubName = clubName;
        this.clubPicture = clubPicture;
    }

    public int getClubId() {
        return clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubPicture() {
        return clubPicture;
    }

    public void setClubPicture(String clubPicture) {
        this.clubPicture = clubPicture;
    }
}
