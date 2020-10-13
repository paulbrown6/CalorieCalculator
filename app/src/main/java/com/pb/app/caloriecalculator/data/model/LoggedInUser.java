package com.pb.app.caloriecalculator.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private LoggedInUser user;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }



    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
