package com.application.roxid.friends;

public class Friend {
     String Username;
     String profileImage;


    public Friend(String profileImage, String username) {
        this.profileImage = profileImage;
        Username = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getUsername() {
        return Username;
    }
}
