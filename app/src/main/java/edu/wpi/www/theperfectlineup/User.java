package edu.wpi.www.theperfectlineup;

/**
 * This class includes relevant user info
 */
public class User {
    private String sport;// TODO will have to be an array for coaches in multiple sports

    public User(String sport) {
        this.sport = sport;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
