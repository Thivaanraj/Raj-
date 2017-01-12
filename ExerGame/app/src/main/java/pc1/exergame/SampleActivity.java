package pc1.exergame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SampleActivity extends AppCompatActivity{

    DBController dbc = new DBController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
    }


    /*public void clickButton(View v) {
        Button button=(Button) v;
        dbc.testPush();
    }*/
}
