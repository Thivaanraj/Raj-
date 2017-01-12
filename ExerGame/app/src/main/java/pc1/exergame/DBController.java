package pc1.exergame;

/**
 * Created by Mylango on 12. 1. 2017.
 */

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBController {

    FirebaseDatabase db;
    DatabaseReference dbRef;


    public DBController(){
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference();
    }

    /*
    * onCreate(){
    *   switch(level){
    *       case 1:
    *           List
    *
*           case 2:
*               List
*               List
*               List
    *   }
    * }
    *
    *
    * */


    /*
    * CHALLENGE BEGIN
    * */

    public void testPush(){
        List<Integer>ex1 = new ArrayList<Integer>();
        ex1.add(1);
        ex1.add(2);
        ex1.add(5);
        dbRef.child("testDB").child("firstTest");
        dbRef.child("testDB").child("firstTest").child("lat").setValue("newshit");
        dbRef.child("testDB").child("firstTest").child("lon").setValue(69.69);
        dbRef.child("testDB").child("firstTest").child("ex1").setValue(ex1);
        dbRef.child("testDB").child("firstTest").child("attemptCount").setValue(0);
        dbRef.child("testDB").child("firstTest").child("isActive").setValue(1);

        getAttemptCount();

    }

    public void createChallengeEasy(int id, double lat, double lon, List<Integer>ex1){
        String idString = Integer.toString(id);
        dbRef.child("challenges").child(idString);
        dbRef.child("challenges").child(idString).child("lat").setValue(lat);
        dbRef.child("challenges").child(idString).child("lon").setValue(lon);
        dbRef.child("challenges").child(idString).child("ex1").setValue(ex1);
        dbRef.child("challenges").child(idString).child("attemptCount").setValue(0);

    }

    public void createChallengeMedium(int id, double lat, double lon, List<Integer>ex1, List<Integer>ex2, List<Integer>ex3){
        String idString = Integer.toString(id);
        dbRef.child("challenges").child(idString);
        dbRef.child("challenges").child(idString).child("lat").setValue(lat);
        dbRef.child("challenges").child(idString).child("lon").setValue(lon);
        dbRef.child("challenges").child(idString).child("ex1").setValue(ex1);
        dbRef.child("challenges").child(idString).child("ex2").setValue(ex2);
        dbRef.child("challenges").child(idString).child("ex3").setValue(ex3);
        dbRef.child("challenges").child(idString).child("attemptCount").setValue(0);
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
        dbRef.child("challenges").child(idString).child("attemptCount").setValue(0);
    }

    /*
    * CHALLENGE ATTEMPT BEGIN
    * */

    public void createAttempt(int attemptID, int userID, int challengeID, int time){ //int len docasne kym sa zhodneme na formate
        String idString = Integer.toString(attemptID);
        dbRef.child("attempts").child(idString).child("userID").setValue(userID);
        dbRef.child("attempts").child(idString).child("challengeID").setValue(challengeID);
        //insert time

        String challengeIdString = Integer.toString(challengeID);
        dbRef.child("challenges").child(challengeIdString).child("attemptCount").setValue(userID);
    }

    public void getAttemptCount(){
        dbRef = db.getReference().child("testDB").child("firstTest").child("attemptCount");
        dbRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(DatabaseError firebaseError, boolean committed, DataSnapshot currentData) {
                if (firebaseError != null) {
                    //Log.d("Firebase counter increment failed.");
                } else {
                    //Log.d("Firebase counter increment succeeded.");
                }
            }
        });
    }



    /*
    * ID INCREMENTOR BEGIN
    * */

    public void incrementUserID(int id){
        dbRef.child("nextID").child("nextUser").setValue(id);
    }

    public void incrementChallengeID(int id){dbRef.child("nextID").child("nextChallenge").setValue(id);}

    public void incrementAttemptID(int id){dbRef.child("nextID").child("nextAttempt").setValue(id);}


}
