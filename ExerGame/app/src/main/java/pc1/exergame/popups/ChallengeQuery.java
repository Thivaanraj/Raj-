package pc1.exergame.popups;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import pc1.exergame.storage.DBController;

public class ChallengeQuery extends DialogFragment {

    DBController db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String msg = getArguments().getString("description");
        final String title = getArguments().getString("type");
        final String chalId = getArguments().getString("id");
        final String username = getArguments().getString("username");
        Boolean inProximity = getArguments().getBoolean("proximity");

        builder.setMessage(msg)
                .setTitle(title);

        if (inProximity) {
            builder.setMessage("Are you up for this challenge? \n\n"+ msg);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    db = new DBController();
                    switch (title) {
                        case "easy":
                            db.createAttemptEasy(username, chalId);
                            break;

                        case "medium":
                            db.createAttemptMedium(username, chalId);
                            break;

                        case "hard":
                            db.createAttemptHard(username, chalId);
                            break;
                    }
                }
            });

        } else { Toast.makeText(getActivity(), "Too far away", Toast.LENGTH_SHORT).show(); }
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
