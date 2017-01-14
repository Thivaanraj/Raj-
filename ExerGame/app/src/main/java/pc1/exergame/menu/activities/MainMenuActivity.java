package pc1.exergame.menu.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import pc1.exergame.R;
import pc1.exergame.fragments.ChallengesFragment;
import pc1.exergame.fragments.DashboardFragment;
import pc1.exergame.fragments.MapViewFragment;
import pc1.exergame.fragments.RankingFragment;

public class MainMenuActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG = "fragment_tag";

    public void setFocus(String focus) {
        this.focus = focus;
    }

    private String focus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        createDefaultFragment();

    }
    
    private void createDefaultFragment(){
        setFocus("challenges");
        ChallengesFragment fragment = new ChallengesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    public void ChallengesClick(View view) {
        setFocus("challenges");
        ChallengesFragment fragment = new ChallengesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    public void MapClick(View view) {
        if (focus.equals("map")) return;
        setFocus("map");
        MapViewFragment fragment = new MapViewFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();

        //Intent intent = new Intent(this, MapsActivity.class);
        //startActivity(intent);
    }

    public void RankingClick(View view) {
        setFocus("ranking");
        RankingFragment fragment = new RankingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    public void DashboardClick(View view) {
        setFocus("dashboard");
        DashboardFragment fragment = new DashboardFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    public void globalSignOut(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        finish();
    }
}
