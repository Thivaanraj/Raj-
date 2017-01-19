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


    public void createChallenge(String id, String type, double lat, double lon, List<String> exercises,
                                    List<Integer> sets, List<Integer> reps){
        dbRef.child("challenges").child(id);
        dbRef.child("challenges").child(id).child("type").setValue(type);
        dbRef.child("challenges").child(id).child("lat").setValue(lat);
        dbRef.child("challenges").child(id).child("lon").setValue(lon);
        dbRef.child("challenges").child(id).child("exercises").setValue(exercises);
        dbRef.child("challenges").child(id).child("sets").setValue(sets);
        dbRef.child("challenges").child(id).child("reps").setValue(reps);
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


    public void createAttemptEasy(String username, String challengeID){
        dbRef.child("attempts").child(username).child("easy").setValue(challengeID);

        incrementAttemptCount(challengeID);
    }

    public void createAttemptMedium(String username, String challengeID){
        dbRef.child("attempts").child(username).child("medium").setValue(challengeID);

        incrementAttemptCount(challengeID);
    }

    public void createAttemptHard(String username, String challengeID){
        dbRef.child("attempts").child(username).child("hard").setValue(challengeID);

        incrementAttemptCount(challengeID);
    }


    public void incrementAttemptCount(final String id){
        DatabaseReference idRef = db.getReference().child("challenges").child(id).child("attemptCount");
        idRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if(((Long) currentData.getValue()) < 50 ) {
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


    /*
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ID INCREMENTOR & HANDLING BEGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    * */



    public void incrementChallengeID(){
        DatabaseReference idRef = db.getReference().child("nextID").child("nextChallenge");
        idRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {

                    currentData.setValue((Long) currentData.getValue() + 1);

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



}
