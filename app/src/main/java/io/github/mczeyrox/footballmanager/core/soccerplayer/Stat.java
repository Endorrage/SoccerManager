package io.github.mczeyrox.footballmanager.core.soccerplayer;

import io.github.mczeyrox.footballmanager.core.utility.Utility;

public class Stat {

    private String name;
    private float value;

    public Stat() {
        this.name = "";
        this.value = Utility.getRandomNumber(0, 100);
    }

    public Stat(String name) {
        this.name = name;
        this.value = Utility.getRandomNumber(0, 100);
    }

    public Stat(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
