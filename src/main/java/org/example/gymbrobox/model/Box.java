package org.example.gymbrobox.model;

import java.util.List;

public class Box {

    String typ;
    List<String> rezepte;


    public Box() {
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public List<String> getRezepte() {
        return rezepte;
    }

    public void setRezepte(List<String> rezepte) {
        this.rezepte = rezepte;
    }
}
