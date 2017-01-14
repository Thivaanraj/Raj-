package pc1.exergame.fragments;


import android.os.Bundle;
import android.app.DialogFragment;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
        ExerciseOne my_dialog = new ExerciseOne();
        my_dialog.show(getFragmentManager(), "my_dialog");
    }
}
