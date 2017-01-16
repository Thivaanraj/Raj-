package pc1.exergame.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pc1.exergame.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengesFragment extends Fragment implements View.OnClickListener {


    Button fetchEasy, fetchMedium, fetchHard;

    public ChallengesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenges, container, false);
        fetchEasy = (Button) rootView.findViewById(R.id.fetchEasyFrag_btn);
        fetchMedium = (Button) rootView.findViewById(R.id.fetchMediumFrag_btn);
        fetchHard = (Button) rootView.findViewById(R.id.fetchHardFrag_btn);



        fetchEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Easy easyFrag = new Easy();
                ft.replace(R.id.content_main, easyFrag );
                ft.commit();
            }
        });

        fetchMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fetchHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges, container, false);
    }

    @Override
    public void onClick(View v) {

    }
}
