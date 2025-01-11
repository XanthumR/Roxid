package msku.ceng.madlab.roxid;

public final class Constants {

    private static Constants instance;

    public static final String usernameField="username";

    private String ClubName;

    private Constants(){

    }

    public static synchronized Constants getInstance(){
        if (instance == null){

            instance = new Constants();
        }
        return instance;
    }

    public String getClubName() {
        return ClubName;
    }

    public void setClubName(String clubName) {
        ClubName = clubName;
    }
}
