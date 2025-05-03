package org.example.gymbrobox;

public enum NutritionLimit {
    PROTEIN_LOW_LIMIT(20),
    KOHELENHYDRATE_LOW_LIMIT(40),
    FETT_LOW_LIMIT(20),
    KALORIEN_LOW_LIMIT(500),
    CO2_LOW_LIMIT(2);

    private final int value;

    NutritionLimit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
