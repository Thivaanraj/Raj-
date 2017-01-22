package pc1.exergame.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import pc1.exergame.R;


public class RankingFragment extends Fragment {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbRef;

    ListView rankList;
    List<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    public RankingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
        dbRef = db.getReference().child("stats");

        rankList = (ListView) rootView.findViewById(R.id.rankings_list);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, list);

        FirebaseListAdapter<Long> fbListAdapter = new FirebaseListAdapter<Long>(
                (Activity) getContext(), Long.class, R.layout.support_simple_spinner_dropdown_item, dbRef.orderByValue()
        ) {
            @Override
            protected void populateView(View v, Long model, int position) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                DatabaseReference tempRef = getRef(position);
                int displayedPosition = position + 1;
                Long tempPoints = Math.abs(model);
                int displayedPoints = tempPoints.intValue();
                String itemKey = tempRef.getKey();
                textView.setText(displayedPosition + "        POINTS: " + Integer.toString(displayedPoints) + "        " + itemKey);

                DatabaseReference createRankRef = db.getReference().child("ranks");
                createRankRef.child(itemKey).setValue(displayedPosition);

            }
        };

        rankList.setAdapter(fbListAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

}
