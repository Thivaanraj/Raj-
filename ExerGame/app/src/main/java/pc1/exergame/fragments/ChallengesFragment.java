package pc1.exergame.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pc1.exergame.R;


public class ChallengesFragment extends Fragment {


    Button fetchEasy, fetchMedium, fetchHard;

    public Communicator comm;

    public ChallengesFragment() {
        // Required empty public constructor
    }

    public interface Communicator {
        public void callFrag(String challengeType);
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a = (Activity) context;

        try {
            comm = (Communicator) a;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString() + "CLASS CAST EXCEPTION");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenges, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        comm = (Communicator) getActivity();

        fetchEasy = (Button) getActivity().findViewById(R.id.fetchEasyFrag_btn);
        fetchMedium = (Button) getActivity().findViewById(R.id.fetchMediumFrag_btn);
        fetchHard = (Button) getActivity().findViewById(R.id.fetchHardFrag_btn);


        fetchEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.callFrag("ez");
            }
        });

        fetchMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.callFrag("med");
            }
        });

        fetchHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.callFrag("hard");
            }
        });
    }


}
