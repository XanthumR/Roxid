package msku.ceng.madlab.roxid.database;

public class Users {
    // Necessary
    private int userId;
    private String username;
    private String email;
    private String userPicture ;
    private String password;

    // Not necessary
    private String firstName;
    private String lastName;

    public Users(String email, String username, String password, String userPicture) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.userPicture = userPicture;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
}
