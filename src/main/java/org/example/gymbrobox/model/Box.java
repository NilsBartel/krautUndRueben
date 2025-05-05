package org.example.gymbrobox.model;

import java.util.List;

public class Box {

    String typ;
    List<Rezept> rezepte;


    public Box() {
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public List<Rezept> getRezepte() {
        return rezepte;
    }

    public void setRezepte(List<Rezept> rezepte) {
        this.rezepte = rezepte;
    }
}
