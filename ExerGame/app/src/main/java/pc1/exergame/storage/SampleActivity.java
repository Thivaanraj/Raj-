package pc1.exergame.storage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pc1.exergame.R;
import pc1.exergame.storage.DBController;

public class SampleActivity extends AppCompatActivity{
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        DBController dbc = new DBController();
        dbc.testPush();
    }

}
