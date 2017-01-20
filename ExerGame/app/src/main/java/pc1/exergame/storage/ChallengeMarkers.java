package pc1.exergame.storage;

import java.util.HashMap;

public class ChallengeMarkers {

    private String id;
    private long attempts;
    private boolean isActive;
    private double latitude;
    private double longitude;
    private int reps;
    private int sets;
    private String type;
    private HashMap exercises;

    public ChallengeMarkers(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAttempts() {
        return attempts;
    }

    public void setAttempts(long attempts) {
        this.attempts = attempts;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap getExcercises() {
        return exercises;
    }

    public void setExcercises(HashMap excercises) {
        this.exercises = excercises;
    }
}
