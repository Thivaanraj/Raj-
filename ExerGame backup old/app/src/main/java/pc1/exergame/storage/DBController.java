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

import static java.lang.Math.sqrt;

public class DBController {

    FirebaseDatabase db;
    DatabaseReference dbRef;


    public DBController(){
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference();
    }


    /*
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< CHALLENGE BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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

    public void createChallengeEasy(String id, String type, double lat, double lon, List<Integer>ex1){
        dbRef.child("challenges").child(id);
        dbRef.child("challenges").child(id).child("type").setValue(type);
        dbRef.child("challenges").child(id).child("lat").setValue(lat);
        dbRef.child("challenges").child(id).child("lon").setValue(lon);
        dbRef.child("challenges").child(id).child("ex1").setValue(ex1);
        dbRef.child("challenges").child(id).child("attemptCount").setValue(0);
        dbRef.child("challenges").child(id).child("isActive").setValue(1);

        incrementChallengeID();

    }

    public void createChallengeMedium(String id, String type, double lat, double lon, List<Integer>ex1, List<Integer>ex2, List<Integer>ex3){
        dbRef.child("challenges").child(id);
        dbRef.child("challenges").child(id).child("type").setValue(type);
        dbRef.child("challenges").child(id).child("lat").setValue(lat);
        dbRef.child("challenges").child(id).child("lon").setValue(lon);
        dbRef.child("challenges").child(id).child("ex1").setValue(ex1);
        dbRef.child("challenges").child(id).child("ex2").setValue(ex2);
        dbRef.child("challenges").child(id).child("ex3").setValue(ex3);
        dbRef.child("challenges").child(id).child("attemptCount").setValue(0);
        dbRef.child("challenges").child(id).child("isActive").setValue(1);

        incrementChallengeID();

    }

    public void createChallengeHard(String id, String type, double lat, double lon, List<Integer>ex1, List<Integer>ex2, List<Integer>ex3, List<Integer>ex4, List<Integer>ex5){
        dbRef.child("challenges").child(id);
        dbRef.child("challenges").child(id).child("type").setValue(type);
        dbRef.child("challenges").child(id).child("lat").setValue(lat);
        dbRef.child("challenges").child(id).child("lon").setValue(lon);
        dbRef.child("challenges").child(id).child("ex1").setValue(ex1);
        dbRef.child("challenges").child(id).child("ex2").setValue(ex2);
        dbRef.child("challenges").child(id).child("ex3").setValue(ex3);
        dbRef.child("challenges").child(id).child("ex4").setValue(ex4);
        dbRef.child("challenges").child(id).child("ex5").setValue(ex5);
        dbRef.child("challenges").child(id).child("attemptCount").setValue(0);
        dbRef.child("challenges").child(id).child("isActive").setValue(1);

        incrementChallengeID();

    }

    public void endChallenge(String id){
        dbRef.child("challenges").child(id).child("isActive").setValue(0);

    }

    /*
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< CHALLENGE ATTEMP BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * */

   /* public void isNear(String challengeID, double currentLat, double currentLon){;
        DatabaseReference latRef = db.getReference().child("chalenges").child(challengeID).child("lat");
        DatabaseReference lonRef = db.getReference().child("chalenges").child(challengeID).child("lat");

        double chLat, chLon;
        double latDiff = (currentLat-chLat) * (currentLat-chLat);
        double lonDiff = (currentLon-chLon) * (currentLon-chLon);
        latDiff=sqrt(latDiff);
        lonDiff=sqrt(lonDiff);
        if(latDiff < 0.01 && lonDiff < 0.01){

        }
        else{
            //give alert that challenge is too far
        }
    }*/

    public void createAttempt(String attemptID, String userID, String challengeID, int time){ //int len docasne kym sa zhodneme na formate
        dbRef.child("attempts").child(attemptID).child("userID").setValue(userID);
        dbRef.child("attempts").child(attemptID).child("challengeID").setValue(challengeID);
        //insert time

        dbRef.child("challenges").child(challengeID).child("attemptCount").setValue(userID);
        addPoints(challengeID, userID);
    }

    public void incrementAttemptCount(final String id){
        DatabaseReference idRef = db.getReference().child("challenges").child(id).child("attemptCount");
        idRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else if(((Long) currentData.getValue()) < 50 ) {
                    currentData.setValue((Long) currentData.getValue() + 1);
                } else if(((Long) currentData.getValue()) == 50 ) {
                    endChallenge(id);
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

    public void addPoints(String challengeID, String userID){

    }

    /*
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ID INCREMENTOR & HANDLING BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * */

    public void incrementUserID(){
        DatabaseReference idRef = db.getReference().child("nextID").child("nextUser");
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

    public void incrementChallengeID(){
        DatabaseReference idRef = db.getReference().child("nextID").child("nextChallenge");
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

    public void incrementAttemptID(){
        DatabaseReference idRef = db.getReference().child("nextID").child("nextAttempt");
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
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< GETTERS BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * */

    public String getData(String id, DataSnapshot ds){
        return (String) ds.child("challenges").child(id).child("type").getValue();
    }


    public void getChallenge(String id){
        DatabaseReference challengeRef = db.getReference().child("challenges").child(id);
        /*challengeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String email = dataSnapshot.getValue(String.class);
                //do what you want with the email
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    public void getAttempt(){

    }

    //NEXT GETTERS

    /*public int getNextUser(){
        int next = (Integer) dbRef.child("nextID").child("nextUser").getValue();
        return next;
    }

    public int getNextAttempt(){
        int next = ((Integer) dbRef.child("nextID").child("nextAttempt").getValue());
        return next;
    }

    public int getNextChallenge(){
        int next = ((Integer) dbRef.child("nextID").child("nextChallenge").getValue());
        return next;
    }*/

}
