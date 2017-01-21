package pc1.exergame.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import pc1.exergame.storage.DBController;

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Medium extends Fragment implements View.OnClickListener {

    FirebaseDatabase db;

    private DBController dbc = new DBController();
    private String id, type, exSelection1, exSelection2, exSelection3;
    private double lat, lon;
    private List<String> exercises;
    private List<Integer> sets;
    private List<Integer> reps;
    private int isActive, attemptCount;


    Spinner exSpin1, exSpin2, exSpin3;
    EditText setsInput1, repsInput1, setsInput2, repsInput2, setsInput3, repsInput3;
    Button createBtn;


    public Medium() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_medium, container, false);
        Bundle location = getArguments();
        lat = location.getDouble("lat");
        lon = location.getDouble("long");

        db = FirebaseDatabase.getInstance();

        exercises = new ArrayList<String>();
        sets = new ArrayList<Integer>();
        reps = new ArrayList<Integer>();

        createBtn = (Button) rootView.findViewById(R.id.createMedium_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChallenge();
            }
        });


        exSpin1 = (Spinner) rootView.findViewById(R.id.spinnerEx1_medium);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.exercise_types, android.R.layout.simple_spinner_item);
        exSpin1.setAdapter(adapter);
        exSpin1.setSelection(0);
        exSpin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exSelection1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),exSelection1,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exSpin2 = (Spinner) rootView.findViewById(R.id.spinnerEx2_medium);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this.getContext(), R.array.exercise_types, android.R.layout.simple_spinner_item);
        exSpin2.setAdapter(adapter2);
        exSpin2.setSelection(0);
        exSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exSelection2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),exSelection1,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exSpin3 = (Spinner) rootView.findViewById(R.id.spinnerEx3_medium);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this.getContext(), R.array.exercise_types, android.R.layout.simple_spinner_item);
        exSpin3.setAdapter(adapter3);
        exSpin3.setSelection(0);
        exSpin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exSelection3 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),exSelection1,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setsInput1 = (EditText) rootView.findViewById(R.id.edit_sets1_medium);
        repsInput1 = (EditText) rootView.findViewById(R.id.edit_reps1_medium);
        setsInput2 = (EditText) rootView.findViewById(R.id.edit_sets2_medium);
        repsInput2 = (EditText) rootView.findViewById(R.id.edit_reps2_medium);
        setsInput3 = (EditText) rootView.findViewById(R.id.edit_sets3_medium);
        repsInput3 = (EditText) rootView.findViewById(R.id.edit_reps3_medium);


        // Inflate the layout for this fragment
        return rootView;

    }

    public void createChallenge(){
        exercises.add(exSelection1);
        exercises.add(exSelection2);
        exercises.add(exSelection3);

        sets.add(Integer.parseInt(setsInput1.getText().toString()));
        reps.add(Integer.parseInt(repsInput1.getText().toString()));
        sets.add(Integer.parseInt(setsInput2.getText().toString()));
        reps.add(Integer.parseInt(repsInput2.getText().toString()));
        sets.add(Integer.parseInt(setsInput3.getText().toString()));
        reps.add(Integer.parseInt(repsInput3.getText().toString()));

        DatabaseReference idRef = db.getReference().child("nextID").child("nextChallenge");
        idRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type = "medium";
                String id = Long.toString(dataSnapshot.getValue(Long.class));
                Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
                dbc.createChallenge(id, type, lat, lon, exercises, sets, reps);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }


}
