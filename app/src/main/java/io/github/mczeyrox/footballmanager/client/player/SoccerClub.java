package io.github.mczeyrox.footballmanager.client.player;

import io.github.mczeyrox.footballmanager.core.soccerplayer.SoccerPlayer;

public class SoccerClub {

    public static SoccerClub localClub;

    private String clubName;
    private SoccerPlayer[] clubPlayers;

    public SoccerClub() {

    }

    public SoccerClub(String clubName, SoccerPlayer... clubPlayers) {
        this.clubName = clubName;
        this.clubPlayers = clubPlayers;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public SoccerPlayer[] getClubPlayers() {
        return clubPlayers;
    }

    public void setClubPlayers(SoccerPlayer[] clubPlayers) {
        this.clubPlayers = clubPlayers;
    }
}
