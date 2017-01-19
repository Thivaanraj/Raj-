package pc1.exergame.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pc1.exergame.R;
import pc1.exergame.popups.ExerciseOne;
import pc1.exergame.storage.DBController;

/**
 * A simple {@link Fragment} subclass.
 */
public class Easy extends Fragment implements View.OnClickListener {

    private DBController dbc = new DBController();
    FirebaseDatabase db;
    DatabaseReference idRef;

    private String exSelection;
    private List<String> exercises;
    private List<Integer> sets;
    private List<Integer> reps;
    private int isActive, attemptCount;


    Spinner exSpin;
    EditText setsInput, repsInput;
    Button createBtn;


    public Easy() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_easy, container, false);

        db = FirebaseDatabase.getInstance();

        exercises = new ArrayList<String>();
        sets = new ArrayList<Integer>();
        reps = new ArrayList<Integer>();

        createBtn = (Button) rootView.findViewById(R.id.createEasy_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChallenge();
            }
        });


        exSpin = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.exercise_types, android.R.layout.simple_spinner_item);
        exSpin.setAdapter(adapter);
        exSpin.setSelection(0);
        exSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exSelection = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),exSelection,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setsInput = (EditText) rootView.findViewById(R.id.edit_sets);
        repsInput = (EditText) rootView.findViewById(R.id.edit_reps);


        // Inflate the layout for this fragment
        return rootView;

    }

    public void createChallenge(){
        exercises.add(exSelection);
        sets.add(Integer.valueOf(setsInput.getText().toString()));
        reps.add(Integer.valueOf(repsInput.getText().toString()));



        DatabaseReference idRef = db.getReference().child("nextID").child("nextChallenge");
        idRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type = "easy";
                double lat = 12.66;
                double lon = 34.69;
                String id = Long.toString(dataSnapshot.getValue(Long.class));
                Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
                dbc.createChallenge(id, type, lat, lon, exercises, sets, reps);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Toast.makeText(getContext(), "CHALLENGE CREATED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }



}
