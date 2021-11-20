package io.github.mczeyrox.footballmanager.core.utility;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import io.github.mczeyrox.footballmanager.core.soccerplayer.SoccerPlayerRegistry;
import io.github.mczeyrox.footballmanager.core.utility.text.JsonUtility;

public class DataContainer {

    public static SoccerPlayerRegistry SOCCER_PLAYER_REGISTRY;

    public static void init() {
        try {
            Log.d("ggg", "Started grabbing player data");
            SOCCER_PLAYER_REGISTRY = (SoccerPlayerRegistry) JsonUtility.ReadObjectFromURL(
                    new URL("https://raw.githubusercontent.com/ToberoCat/FootballManager/master/GAME_DATA/soccer_players.json"),
                    SoccerPlayerRegistry.class);
            Log.d("ggg", "Finished grabbing player data");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
