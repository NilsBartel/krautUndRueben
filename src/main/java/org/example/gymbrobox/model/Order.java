package org.example.gymbrobox.model;

import java.util.List;

public class Order {

    List<Box> boxen;

    public Order() {
    }

    public List<Box> getBoxen() {
        return boxen;
    }

    public void setBoxen(List<Box> boxen) {
        this.boxen = boxen;
    }
}
