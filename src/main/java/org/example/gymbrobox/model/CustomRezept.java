package org.example.gymbrobox.model;

import java.util.List;

public class CustomRezept {
    private String name;
    private int portionen;
    private List<Zutat> zutaten;

    public CustomRezept() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPortionen() {
        return portionen;
    }

    public void setPortionen(int portionen) {
        this.portionen = portionen;
    }

    public List<Zutat> getZutaten() {
        return zutaten;
    }

    public void setZutaten(List<Zutat> zutaten) {
        this.zutaten = zutaten;
    }
}
