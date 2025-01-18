package com.application.roxid.database;

public class Chats {
    // Necessary
    private String chatName;
    private int clubId;

    public Chats(String chatName, int clubId) {
        this.chatName = chatName;
        this.clubId = clubId;
    }

    public int getClubId() {
        return clubId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
