package pc1.exergame.storage;

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
    * <<<<<< CHALLENGE BEGIN >>>>>>
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
        //dbRef.child("testDB").child("firstTest").child("attemptCount").setValue(0);
        dbRef.child("testDB").child("firstTest").child("isActive").setValue(1);


    }

    public void createChallengeEasy(int id, double lat, double lon, List<Integer>ex1){
        String idString = Integer.toString(id);
        dbRef.child("challenges").child(idString);
        dbRef.child("challenges").child(idString).child("lat").setValue(lat);
        dbRef.child("challenges").child(idString).child("lon").setValue(lon);
        dbRef.child("challenges").child(idString).child("ex1").setValue(ex1);
        dbRef.child("challenges").child(idString).child("attemptCount").setValue(0);
        dbRef.child("challenges").child(idString).child("isActive").setValue(1);

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
        dbRef.child("challenges").child(idString).child("isActive").setValue(1);

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
        dbRef.child("challenges").child(idString).child("isActive").setValue(1);

    }

    public void endChallenge(int id){
        String idString = Integer.toString(id);
        dbRef.child("chalenges").child(idString).child("isActive").setValue(0);

    }

    /*
    * <<<<<< CHALLENGE ATTEMPT BEGIN >>>>>>
    * */

    /*public void isNear(int challengeID, double currentLat, double currentLon){
        String idString = Integer.toString(challengeID);
        DatabaseReference latRef = db.getReference().child("chalenges").child(idString).child("lat");
        DatabaseReference lonRef = db.getReference().child("chalenges").child(idString).child("lat");
        if(currentLat - )
    }*/

    public void createAttempt(int attemptID, int userID, int challengeID, int time){ //int len docasne kym sa zhodneme na formate
        String idString = Integer.toString(attemptID);
        dbRef.child("attempts").child(idString).child("userID").setValue(userID);
        dbRef.child("attempts").child(idString).child("challengeID").setValue(challengeID);
        //insert time

        String challengeIdString = Integer.toString(challengeID);
        dbRef.child("challenges").child(challengeIdString).child("attemptCount").setValue(userID);
    }

    /*public void incrementAttemptCount(int id){
        final String idString = Integer.toString(id);
        DatabaseReference idRef = db.getReference();
        idRef = db.getReference().child("challenges").child(idString).child("attemptCount");
        idRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else if(((Long) currentData.getValue()) < 50 ) {
                    currentData.setValue((Long) currentData.getValue() + 1);
                } else if(((Long) currentData.getValue()) == 50 ) {
                    endChallenge(idString);
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
    }*/

    /*
    * <<<<<< ID INCREMENTOR BEGIN >>>>>>
    * */

    public void incrementUserID(int id){
        String idString = Integer.toString(id);
        DatabaseReference idRef = db.getReference();
        idRef = db.getReference().child("nextID").child("nextUser");
        idRef.runTransaction(new Transaction.Handler() {
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

    public void incrementChallengeID(int id){
        String idString = Integer.toString(id);
        DatabaseReference idRef = db.getReference();
        idRef = db.getReference().child("nextID").child("nextChallenge");
        idRef.runTransaction(new Transaction.Handler() {
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

    public void incrementAttemptID(int id){
        String idString = Integer.toString(id);
        DatabaseReference idRef = db.getReference();
        idRef = db.getReference().child("nextID").child("nextAttempt");
        idRef.runTransaction(new Transaction.Handler() {
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
    * <<<<<< GETTERS >>>>>>
    * */

    public void getChallenge(){

    }

    public void getAttempt(){

    }

}
