package io.github.tobero.soccerPlayer;

public class SoccerPlayer {

    public enum Positions { STRIKER, LEFT_MID, RIGHT_MID, CENTER_MID, ATTACKING_MID, DEFENSIVE_MID, LEFT_DEFENDER, RIGHT_DEFENDER, DEFENDER, GOALKEEPER }

    private String firstName;
    private String secondName;

    private int price;
    private Positions position;

    private Stat pace;
    private Stat shooting;
    private Stat passing;
    private Stat dribbling;
    private Stat defending;
    private Stat goalFending;
    private Stat physical;

    public SoccerPlayer() {
    }

    public SoccerPlayer(String firstName, String secondName, Positions position, Stat pace, Stat shooting, Stat passing, Stat dribbling, Stat defending, Stat goalFending, Stat physical) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.position = position;
        this.pace = pace;
        this.shooting = shooting;
        this.passing = passing;
        this.dribbling = dribbling;
        this.defending = defending;
        this.goalFending = goalFending;
        this.physical = physical;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Positions getPosition() {
        return position;
    }

    public void setPosition(Positions position) {
        this.position = position;
    }

    public Stat getPace() {
        return pace;
    }

    public void setPace(Stat pace) {
        this.pace = pace;
    }

    public Stat getShooting() {
        return shooting;
    }

    public void setShooting(Stat shooting) {
        this.shooting = shooting;
    }

    public Stat getPassing() {
        return passing;
    }

    public void setPassing(Stat passing) {
        this.passing = passing;
    }

    public Stat getDribbling() {
        return dribbling;
    }

    public void setDribbling(Stat dribbling) {
        this.dribbling = dribbling;
    }

    public Stat getDefending() {
        return defending;
    }

    public void setDefending(Stat defending) {
        this.defending = defending;
    }

    public Stat getGoalFending() {
        return goalFending;
    }

    public void setGoalFending(Stat goalFending) {
        this.goalFending = goalFending;
    }

    public Stat getPhysical() {
        return physical;
    }

    public void setPhysical(Stat physical) {
        this.physical = physical;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return firstName + " " + secondName;
    }
}

