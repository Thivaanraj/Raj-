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

import java.util.ArrayList;
import java.util.List;

import pc1.exergame.R;
import pc1.exergame.popups.ExerciseOneDialog;
import pc1.exergame.storage.DBController;

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hard extends Fragment implements View.OnClickListener {

    private DBController dbc = new DBController();
    private String id, type, exSelection1, exSelection2, exSelection3, exSelection4, exSelection5;
    private double lat, lon;
    private List<String> exercises;
    private List<Integer> sets;
    private List<Integer> reps;
    private int isActive, attemptCount;


    Spinner exSpin1, exSpin2, exSpin3, exSpin4, exSpin5;
    EditText setsInput1, repsInput1, setsInput2, repsInput2, setsInput3, repsInput3, setsInput4, repsInput4, setsInput5, repsInput5;
    Button createBtn;


    public Hard() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hard, container, false);

        exercises = new ArrayList<String>();
        sets = new ArrayList<Integer>();
        reps = new ArrayList<Integer>();

        createBtn = (Button) rootView.findViewById(R.id.createHard_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChallenge();
            }
        });


        exSpin1 = (Spinner) rootView.findViewById(R.id.spinnerEx1_hard);
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

        exSpin2 = (Spinner) rootView.findViewById(R.id.spinnerEx2_hard);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this.getContext(), R.array.exercise_types, android.R.layout.simple_spinner_item);
        exSpin2.setAdapter(adapter2);
        exSpin2.setSelection(0);
        exSpin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exSelection2 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),exSelection2,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exSpin3 = (Spinner) rootView.findViewById(R.id.spinnerEx3_hard);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this.getContext(), R.array.exercise_types, android.R.layout.simple_spinner_item);
        exSpin3.setAdapter(adapter3);
        exSpin3.setSelection(0);
        exSpin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exSelection3 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),exSelection3,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exSpin4 = (Spinner) rootView.findViewById(R.id.spinnerEx4_hard);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this.getContext(), R.array.exercise_types, android.R.layout.simple_spinner_item);
        exSpin4.setAdapter(adapter4);
        exSpin4.setSelection(0);
        exSpin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exSelection4 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),exSelection4,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exSpin5 = (Spinner) rootView.findViewById(R.id.spinnerEx5_hard);
        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this.getContext(), R.array.exercise_types, android.R.layout.simple_spinner_item);
        exSpin5.setAdapter(adapter5);
        exSpin5.setSelection(0);
        exSpin5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exSelection5 = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),exSelection3,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setsInput1 = (EditText) rootView.findViewById(R.id.edit_sets1_hard);
        repsInput1 = (EditText) rootView.findViewById(R.id.edit_reps1_hard);
        setsInput2 = (EditText) rootView.findViewById(R.id.edit_sets2_hard);
        repsInput2 = (EditText) rootView.findViewById(R.id.edit_reps2_hard);
        setsInput3 = (EditText) rootView.findViewById(R.id.edit_sets3_hard);
        repsInput3 = (EditText) rootView.findViewById(R.id.edit_reps3_hard);
        setsInput4 = (EditText) rootView.findViewById(R.id.edit_sets4_hard);
        repsInput4 = (EditText) rootView.findViewById(R.id.edit_reps4_hard);
        setsInput5 = (EditText) rootView.findViewById(R.id.edit_sets5_hard);
        repsInput5 = (EditText) rootView.findViewById(R.id.edit_reps5_hard);


        // Inflate the layout for this fragment
        return rootView;

    }

    public void createChallenge(){
        exercises.add(exSelection1);
        exercises.add(exSelection2);
        exercises.add(exSelection3);
        exercises.add(exSelection4);
        exercises.add(exSelection5);

        sets.add(Integer.parseInt(setsInput1.getText().toString()));
        reps.add(Integer.parseInt(repsInput1.getText().toString()));
        sets.add(Integer.parseInt(setsInput2.getText().toString()));
        reps.add(Integer.parseInt(repsInput2.getText().toString()));
        sets.add(Integer.parseInt(setsInput3.getText().toString()));
        reps.add(Integer.parseInt(repsInput3.getText().toString()));
        sets.add(Integer.parseInt(setsInput4.getText().toString()));
        reps.add(Integer.parseInt(repsInput4.getText().toString()));
        sets.add(Integer.parseInt(setsInput5.getText().toString()));
        reps.add(Integer.parseInt(repsInput5.getText().toString()));

        type = "hard";
        lat = 66.66;
        lon = 69.69;
        id = "HardChallenge";
        dbc.createChallenge(id, type, lat, lon, exercises, sets, reps);
        Toast.makeText(getContext(), "CHALLENGE CREATED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }


    public void showChoice(String message){

    }

    public void showDialog(View v){
        FragmentManager manager = getFragmentManager();
        ExerciseOneDialog exerciseOneDialog = new ExerciseOneDialog();
        exerciseOneDialog.setTargetFragment(this, 1);
        exerciseOneDialog.show(manager, "exDialog");
    }

}
