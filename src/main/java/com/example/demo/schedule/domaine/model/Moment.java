package com.example.demo.schedule.domaine.model;

public enum Moment {
    MORNING,
    AFTERNOON;

    public static Moment fromString(String moment) {
        return switch (moment) {
            case "MORNING" -> Moment.MORNING;
            case "AFTERNOON" -> Moment.AFTERNOON;
            default -> throw new IllegalArgumentException("INVALID MOMENT " + moment);
        };
    }
}
