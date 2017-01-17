package pc1.exergame.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pc1.exergame.R;
import pc1.exergame.popups.ChallengeDetailDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase db;
    DatabaseReference dbRef;
    ListView listview;
    List<String> challengeList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String selection, type;
    TextView rank, username, points;
    TextView ex1, ex2, ex3, ex4, ex5;
    TextView set1, set2, set3, set4, set5, rep1, rep2, rep3, rep4, rep5;
    Button showEasy, showMedium, showHard;
    TextView t1, t2, t3;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        db = FirebaseDatabase.getInstance();

        username = (TextView) rootView.findViewById(R.id.dash_nickname);
        rank = (TextView) rootView.findViewById(R.id.dash_rank_value);
        points = (TextView) rootView.findViewById(R.id.dash_point_value);

        checkUser();


        ex1 = (TextView) rootView.findViewById(R.id.challenge_detail_ex1);
        ex2 = (TextView) rootView.findViewById(R.id.challenge_detail_ex2);
        ex3 = (TextView) rootView.findViewById(R.id.challenge_detail_ex3);
        ex4 = (TextView) rootView.findViewById(R.id.challenge_detail_ex4);
        ex5 = (TextView) rootView.findViewById(R.id.challenge_detail_ex5);

        set1 = (TextView) rootView.findViewById(R.id.challenge_detail_set1);
        set2 = (TextView) rootView.findViewById(R.id.challenge_detail_set2);
        set3 = (TextView) rootView.findViewById(R.id.challenge_detail_set3);
        set4 = (TextView) rootView.findViewById(R.id.challenge_detail_set4);
        set5 = (TextView) rootView.findViewById(R.id.challenge_detail_set5);

        rep1 = (TextView) rootView.findViewById(R.id.challenge_detail_rep1);
        rep2 = (TextView) rootView.findViewById(R.id.challenge_detail_rep2);
        rep3 = (TextView) rootView.findViewById(R.id.challenge_detail_rep3);
        rep4 = (TextView) rootView.findViewById(R.id.challenge_detail_rep4);
        rep5 = (TextView) rootView.findViewById(R.id.challenge_detail_rep5);


        showEasy = (Button) rootView.findViewById(R.id.dash_showEasy_btn);
        showMedium = (Button) rootView.findViewById(R.id.dash_showMedium_btn);
        showHard = (Button) rootView.findViewById(R.id.dash_showHard_btn);




        showEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference refDialog = db.getReference().child("challenges").child("ezTEst");
                refDialog.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ex1.setText(dataSnapshot.child("exercises").child("0").getValue(String.class));
                        ex2.setText("");
                        ex3.setText("");
                        ex4.setText("");
                        ex5.setText("");

                        set1.setText(String.valueOf(dataSnapshot.child("sets").child("0").getValue(Long.class)));
                        set2.setText("");
                        set3.setText("");
                        set4.setText("");
                        set5.setText("");

                        rep1.setText(String.valueOf(dataSnapshot.child("reps").child("0").getValue(Long.class)));
                        rep2.setText("");
                        rep3.setText("");
                        rep4.setText("");
                        rep5.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        showMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference refDialog = db.getReference().child("challenges").child("MediumChallenge");
                refDialog.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ex1.setText((dataSnapshot.child("exercises").child("0").getValue(String.class)));
                        ex2.setText((dataSnapshot.child("exercises").child("1").getValue(String.class)));
                        ex3.setText((dataSnapshot.child("exercises").child("2").getValue(String.class)));
                        ex4.setText("");
                        ex5.setText("");

                        set1.setText(String.valueOf(dataSnapshot.child("sets").child("0").getValue(Long.class)));
                        set2.setText(String.valueOf(dataSnapshot.child("sets").child("1").getValue(Long.class)));
                        set3.setText(String.valueOf(dataSnapshot.child("sets").child("2").getValue(Long.class)));
                        set4.setText("");
                        set5.setText("");

                        rep1.setText(String.valueOf(dataSnapshot.child("reps").child("0").getValue(Long.class)));
                        rep2.setText(String.valueOf(dataSnapshot.child("sets").child("1").getValue(Long.class)));
                        rep3.setText(String.valueOf(dataSnapshot.child("sets").child("2").getValue(Long.class)));
                        rep4.setText("");
                        rep5.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        showHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference refDialog = db.getReference().child("challenges").child("HardChallenge");
                refDialog.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ex1.setText((dataSnapshot.child("exercises").child("0").getValue(String.class)));
                        ex2.setText((dataSnapshot.child("exercises").child("1").getValue(String.class)));
                        ex3.setText((dataSnapshot.child("exercises").child("2").getValue(String.class)));
                        ex4.setText((dataSnapshot.child("exercises").child("3").getValue(String.class)));
                        ex5.setText((dataSnapshot.child("exercises").child("4").getValue(String.class)));

                        set1.setText(String.valueOf(dataSnapshot.child("sets").child("0").getValue(Long.class)));
                        set2.setText(String.valueOf(dataSnapshot.child("sets").child("1").getValue(Long.class)));
                        set3.setText(String.valueOf(dataSnapshot.child("sets").child("2").getValue(Long.class)));
                        set4.setText(String.valueOf(dataSnapshot.child("sets").child("3").getValue(Long.class)));
                        set5.setText(String.valueOf(dataSnapshot.child("sets").child("4").getValue(Long.class)));

                        rep1.setText(String.valueOf(dataSnapshot.child("reps").child("0").getValue(Long.class)));
                        rep2.setText(String.valueOf(dataSnapshot.child("sets").child("1").getValue(Long.class)));
                        rep3.setText(String.valueOf(dataSnapshot.child("sets").child("2").getValue(Long.class)));
                        rep4.setText(String.valueOf(dataSnapshot.child("sets").child("3").getValue(Long.class)));
                        rep5.setText(String.valueOf(dataSnapshot.child("sets").child("4").getValue(Long.class)));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }

    private void checkUser(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            //Toast.makeText(getContext(), user.getEmail() + " signed in", Toast.LENGTH_SHORT).show();
            String displayedNick = user.getEmail().replace("@user.pae","");
            username.setText(displayedNick);

            DatabaseReference statRef = db.getReference().child("stats").child(displayedNick);
            statRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Long tempPoints = dataSnapshot.getValue(Long.class);
                    tempPoints = Math.abs(tempPoints);
                    int displayedPoints = tempPoints.intValue();
                    points.setText(Integer.toString(displayedPoints));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            DatabaseReference rankRef = db.getReference().child("ranks").child(displayedNick);
            rankRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    rank.setText(Integer.toString(dataSnapshot.getValue(Integer.class)));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }



}
