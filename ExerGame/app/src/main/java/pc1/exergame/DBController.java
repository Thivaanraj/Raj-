package pc1.exergame;

/**
 * Created by Mylango on 12. 1. 2017.
 */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DBController {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = db.getReference();

    /*
    * CHALLENGE BEGIN
    * */

    public void createChallengeEasy(int id, double lat, double lon, List<Integer>ex1){
        String idString = Integer.toString(id);
        dbRef.child("challenges").child(idString);
        dbRef.child("challenges").child(idString).child("lat").setValue(lat);
        dbRef.child("challenges").child(idString).child("lon").setValue(lon);
        dbRef.child("challenges").child(idString).child("ex1").setValue(ex1);
    }

    public void createChallengeMedium(int id, double lat, double lon, List<Integer>ex1, List<Integer>ex2, List<Integer>ex3){
        String idString = Integer.toString(id);
        dbRef.child("challenges").child(idString);
        dbRef.child("challenges").child(idString).child("lat").setValue(lat);
        dbRef.child("challenges").child(idString).child("lon").setValue(lon);
        dbRef.child("challenges").child(idString).child("ex1").setValue(ex1);
        dbRef.child("challenges").child(idString).child("ex2").setValue(ex2);
        dbRef.child("challenges").child(idString).child("ex3").setValue(ex3);
    }

    public void createChallengeHard(int id, double lat, double lon, List<Integer>ex1, List<Integer>ex2, List<Integer>ex3, List<Integer>ex4, List<Integer>ex5){
        String idString = Integer.toString(id);
        dbRef.child("challenges").child(idString);
        dbRef.child("challenges").child(idString).child("lat").setValue(lat);
        dbRef.child("challenges").child(idString).child("lon").setValue(lon);
        dbRef.child("challenges").child(idString).child("ex1").setValue(ex1);
        dbRef.child("challenges").child(idString).child("ex2").setValue(ex2);
        dbRef.child("challenges").child(idString).child("ex3").setValue(ex3);
        dbRef.child("challenges").child(idString).child("ex4").setValue(ex4);
        dbRef.child("challenges").child(idString).child("ex5").setValue(ex5);
    }

    /*
    * CHALLENGE ATTEMPT BEGIN
    * */

    public void createAttempt(int attemptID, int userID, int challengeID, int time){ //int len docasne kym sa zhodneme na formate
        String idString = Integer.toString(attemptID);
        dbRef.child("attempts").child(idString).child("userID").setValue(userID);
        dbRef.child("attempts").child(idString).child("challengeID").setValue(challengeID);
        //insert time
    }

    /*
    * ID INCREMENTOR BEGIN
    * */

    public void incrementUserID(int id){
        dbRef.child("nextID").child("nextUser").setValue(id);
    }

    public void incrementChallengeID(int id){
        dbRef.child("nextID").child("nextChallenge").setValue(id);
    }

    public void incrementAttemptID(int id){
        dbRef.child("nextID").child("nextAttempt").setValue(id);
    }


}
