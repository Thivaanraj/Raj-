package pc1.exergame.popups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

/**
 * Created by mp on 14-Jan-17.
 */

public class ExerciseOne extends DialogFragment {

    final CharSequence[] items = {"Pullups", "Pushups", "Squats", "Lunges"};
    int selection;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Choose Exercise").setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                switch(arg1){
                    case 0:
                        selection = 0;
                        break;
                    case 1:
                        selection = 1;
                        break;
                    case 2:
                        selection = 2;
                        break;
                    case 3:
                        selection = 3;
                        break;
                }
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), Integer.toString(selection), Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }


}
