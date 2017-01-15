package pc1.exergame.popups;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pc1.exergame.R;


public class ExerciseOneDialog extends DialogFragment implements View.OnClickListener {

    Button pullupBtn, pushupBtn, squatBtn, lungeBtn, dipBtn, oapullBtn, oaPushBtn, hspuBtn, muBtn, pistolBtn ;
    Communicator communicator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        communicator = (Communicator) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_exercise, null);
        pullupBtn = (Button) view.findViewById(R.id.select_pullups);
        pushupBtn = (Button) view.findViewById(R.id.select_pushups);
        squatBtn = (Button) view.findViewById(R.id.select_squats);
        lungeBtn = (Button) view.findViewById(R.id.select_lunges);
        dipBtn = (Button) view.findViewById(R.id.select_dips);
        oapullBtn = (Button) view.findViewById(R.id.select_oapull);
        oaPushBtn = (Button) view.findViewById(R.id.select_oapush);
        hspuBtn = (Button) view.findViewById(R.id.select_hspu);
        muBtn = (Button) view.findViewById(R.id.select_mu);
        pistolBtn = (Button) view.findViewById(R.id.select_pistol);

        pullupBtn.setOnClickListener(this);
        pushupBtn.setOnClickListener(this);
        squatBtn.setOnClickListener(this);
        lungeBtn.setOnClickListener(this);
        dipBtn.setOnClickListener(this);
        oapullBtn.setOnClickListener(this);
        oaPushBtn.setOnClickListener(this);
        hspuBtn.setOnClickListener(this);
        muBtn.setOnClickListener(this);
        pistolBtn.setOnClickListener(this);
        setCancelable(false);

        return view;
    }

    @Override
    public void onClick(View v) {
        int selected = v.getId();
        switch (selected) {
            case R.id.select_pullups:
                this.communicator.onDialogMessage("pullups");
                dismiss();
                break;
            case R.id.select_pushups:
                this.communicator.onDialogMessage("pushups");
                dismiss();
                break;
            case R.id.select_squats:
                this.communicator.onDialogMessage("squats");
                dismiss();
                break;
            case R.id.select_lunges:
                this.communicator.onDialogMessage("lunges");
                dismiss();
                break;
            case R.id.select_dips:
                this.communicator.onDialogMessage("dips");
                dismiss();
                break;
            case R.id.select_oapull:
                this.communicator.onDialogMessage("oapull");
                dismiss();
                break;
            case R.id.select_oapush:
                this.communicator.onDialogMessage("oapush");
                dismiss();
                break;
            case R.id.select_hspu:
                this.communicator.onDialogMessage("hspu");
                dismiss();
                break;
            case R.id.select_mu:
                this.communicator.onDialogMessage("mu");
                dismiss();
                break;
            case R.id.select_pistol:
                this.communicator.onDialogMessage("pistol");
                dismiss();
                break;


        }

    }



    public interface Communicator {
        public abstract void onDialogMessage(String message);
    }
}
