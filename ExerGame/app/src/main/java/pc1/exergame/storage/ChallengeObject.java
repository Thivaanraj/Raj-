package pc1.exergame.storage;

import java.util.List;

/**
 * Created by mp on 16-Jan-17.
 */

public class ChallengeObject {

    private String id, type;
    private double lat, lon;
    private List<String> exercises;
    private List<Integer> sets;
    private List<Integer> reps;
    private int isActive, attemptCount;

    public ChallengeObject(){

    }

}
