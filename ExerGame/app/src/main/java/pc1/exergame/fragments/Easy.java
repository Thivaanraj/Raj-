package pc1.exergame.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import pc1.exergame.R;
import pc1.exergame.popups.ExerciseOne;
import pc1.exergame.storage.DBController;

/**
 * A simple {@link Fragment} subclass.
 */
public class Easy extends Fragment implements View.OnClickListener {

    private DBController dbc = new DBController();
    private String id, type;
    private double lat, lon;
    private String[] exSpin = {"Pullups", "Pushups", "Squats", "Lunges"};
    private List<Integer> exercises;
    private List<Integer> sets;
    private List<Integer> reps;
    private int isActive, attemptCount;

    Spinner setEx, setSet, setRep;
    ArrayAdapter adapter;


    public Easy() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_easy, container, false);

    }

    public void createChallenge(){
        //implement to get id
        //dbc.createChallenge(id, type, lat, lon, exercises, sets, reps);
    }

    @Override
    public void onClick(View v) {
        selectExercise(v);
    }

    public void selectExercise(View v){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_exercise, null);
        RadioGroup radioGroup = (RadioGroup) alertLayout.findViewById(R.id.radioGroup);
        RadioButton pullups, pushups, squats, lunges, dips, oaPull, oaPush, hspu, muscleup;
        //RadioButton cbShowPassword = (RadioButton) alertLayout.findViewById(R.id.selectPullups);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });


        pullups = (RadioButton) alertLayout.findViewById(R.id.selectPullups);
        pushups = (RadioButton) alertLayout.findViewById(R.id.selectPushups);
        squats = (RadioButton) alertLayout.findViewById(R.id.selectSquats);
        lunges = (RadioButton) alertLayout.findViewById(R.id.selectLunges);
        dips = (RadioButton) alertLayout.findViewById(R.id.selectDips);
        oaPull = (RadioButton) alertLayout.findViewById(R.id.selectOAPullups);
        oaPush = (RadioButton) alertLayout.findViewById(R.id.selectOAPushups);
        hspu = (RadioButton) alertLayout.findViewById(R.id.selectHSPU);
        muscleup = (RadioButton) alertLayout.findViewById(R.id.selectMuscleups);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Select Exercise");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        int selection = radioGroup.getCheckedRadioButtonId();
        switch (selection){
            case (R.id.selectPullups):
                selection = 1;
                break;
            case (R.id.selectPushups):
                selection = 2;
                break;
            case (R.id.selectSquats):
                selection = 3;
                break;
            case (R.id.selectLunges):
                selection = 4;
                break;
            case (R.id.selectDips):
                selection = 5;
                break;
            case (R.id.selectOAPullups):
                selection = 6;
                break;
            case (R.id.selectOAPushups):
                selection = 7;
                break;
            case (R.id.selectHSPU):
                selection = 8;
                break;
            case (R.id.selectMuscleups):
                selection = 9;
                break;
            case -1:
                selection = 0;
                break;
        }

        AlertDialog dialog = alert.create();
        dialog.show();

    }
}
