package model;

import java.util.Date;

public class User {
    int id;
    String firstName;
    String lastName;
    String email;
    String password;
    String profilePic;
    Date regDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public User(int id, String firstName, String lastName, String email, String password, String profilePic, Date regDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return id +
                ", " + firstName +
                "," + lastName +
                ", " + email +
                ", " + password +
                ", " + profilePic +
                ", " + regDate;
    }
}
