package pc1.exergame.popups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pc1.exergame.R;

/**
 * Created by mp on 16-Jan-17.
 */

public class ChallengeDetailDialog extends DialogFragment {

    FirebaseDatabase db;
    DatabaseReference dbRef;
    TextView type, headEx, headSet, headRep;
    TextView ex1, ex2, ex3, ex4, ex5;
    TextView set1, set2, set3, set4, set5;
    TextView rep1, rep2, rep3, rep4, rep5;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_exercise, null);

        //final String challengeID = getArguments().getString("selection");

        db = FirebaseDatabase.getInstance();
        //dbRef = db.getReference().child("challenges").child(getArguments().getString("selection"));


        //type = (TextView) rootView.findViewById(R.id.challenge_detail_type);
        headEx = (TextView) rootView.findViewById(R.id.challenge_detail_head1);
        headSet = (TextView) rootView.findViewById(R.id.challenge_detail_head2);
        headRep = (TextView) rootView.findViewById(R.id.challenge_detail_head3);

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

        //type.setText(getArguments().getString("type"));

        /*switch (getArguments().getString("type")) {
            case "easy":
                ex1.setText(getArguments().getString("ex1"));
                set1.setText(String.valueOf(getArguments().getString("set1")));
                rep1.setText(String.valueOf(getArguments().getString("rep1")));
            case "medium":
                ex1.setText(getArguments().getString("ex1"));
                ex2.setText(getArguments().getString("ex2"));
                ex3.setText(getArguments().getString("ex3"));

                set1.setText(String.valueOf(getArguments().getString("set1")));
                set2.setText(String.valueOf(getArguments().getString("set2")));
                set3.setText(String.valueOf(getArguments().getString("set3")));

                rep1.setText(String.valueOf(getArguments().getString("rep1")));
                rep2.setText(String.valueOf(getArguments().getString("rep2")));
                rep3.setText(String.valueOf(getArguments().getString("rep3")));
            case "hard":*/
                ex1.setText(getArguments().getString("ex1"));
                ex2.setText(getArguments().getString("ex2"));
                ex3.setText(getArguments().getString("ex3"));
                ex4.setText(getArguments().getString("ex4"));
                ex5.setText(getArguments().getString("ex5"));

                set1.setText(String.valueOf(getArguments().getLong("set1")));
                set2.setText(String.valueOf(getArguments().getLong("set2")));
                set3.setText(String.valueOf(getArguments().getLong("set3")));
                set4.setText(String.valueOf(getArguments().getLong("set4")));
                set5.setText(String.valueOf(getArguments().getLong("set5")));

                rep1.setText(String.valueOf(getArguments().getLong("rep1")));
                rep2.setText(String.valueOf(getArguments().getLong("rep2")));
                rep3.setText(String.valueOf(getArguments().getLong("rep3")));
                rep4.setText(String.valueOf(getArguments().getLong("rep4")));
                rep5.setText(String.valueOf(getArguments().getLong("rep5")));

        /*}*/

        return rootView;
    }
}
