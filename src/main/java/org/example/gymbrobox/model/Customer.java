package org.example.gymbrobox.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Customer {

    private int KUNDENNR;
    private String NACHNAME;
    private String VORNAME;

    public int getKUNDENNR() {
        return KUNDENNR;
    }

    public void setKUNDENNR(int KUNDENNR) {
        this.KUNDENNR = KUNDENNR;
    }

    public String getNACHNAME() {
        return NACHNAME;
    }

    public void setNACHNAME(String NACHNAME) {
        this.NACHNAME = NACHNAME;
    }

    public String getVORNAME() {
        return VORNAME;
    }

    public void setVORNAME(String VORNAME) {
        this.VORNAME = VORNAME;
    }
}
