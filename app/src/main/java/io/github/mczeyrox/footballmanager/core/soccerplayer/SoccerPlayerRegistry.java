package io.github.mczeyrox.footballmanager.core.soccerplayer;

import java.util.HashMap;
import java.util.Map;

public class SoccerPlayerRegistry {

    private String version;
    private Map<String, SoccerPlayer> soccerPlayerMap;

    public SoccerPlayerRegistry() {
        soccerPlayerMap = new HashMap<>();
    }

    public void AddSoccerPlayers(SoccerPlayer... soccerPlayers) {
        if (soccerPlayers != null) {
            for (SoccerPlayer soccerPlayer : soccerPlayers) {
                soccerPlayerMap.put(soccerPlayer.getFirstName() + ":" + soccerPlayer.getSecondName(), soccerPlayer);
            }
        }
    }

    public Map<String, SoccerPlayer> getSoccerPlayerMap() {
        return soccerPlayerMap;
    }

    public void setSoccerPlayerMap(Map<String, SoccerPlayer> soccerPlayerMap) {
        this.soccerPlayerMap = soccerPlayerMap;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
