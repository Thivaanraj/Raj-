package pc1.exergame.menu.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import pc1.exergame.fragments.Hard;
import pc1.exergame.fragments.Medium;
import pc1.exergame.popups.ExerciseOne;

import com.google.firebase.auth.FirebaseAuth;

import pc1.exergame.R;
import pc1.exergame.fragments.ChallengesFragment;
import pc1.exergame.fragments.DashboardFragment;
import pc1.exergame.fragments.Easy;
import pc1.exergame.fragments.MapViewFragment;
import pc1.exergame.fragments.RankingFragment;


public class MainMenuActivity extends AppCompatActivity implements ChallengesFragment.Communicator{

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
        //setSupportActionBar(toolbar);

        createDefaultFragment();

    }
    
    private void createDefaultFragment(){
        setFocus("dashboard");
        DashboardFragment fragment = new DashboardFragment();
        // ChallengesFragment fragment = new ChallengesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    public void ChallengesClick(View view) {
        setFocus("challenges");
        //Hard fragment = new Hard();
        //Medium fragment = new Medium();
        ChallengesFragment fragment = new ChallengesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
    }

    public void MapClick(View view) {
        /*if (focus.equals("map")) return;
        setFocus("map");
        MapViewFragment fragment = new MapViewFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                .commit();
        */
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
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


    @Override
    public void callFrag(String challengeType) {
        switch (challengeType){
            case "ez":
                FragmentManager manager = getSupportFragmentManager();
                manager.findFragmentByTag("ez_frag");

                Easy ez = new Easy();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, ez, FRAGMENT_TAG)
                        .commit();
                break;
            case "med":


                Medium med = new Medium();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, med, FRAGMENT_TAG)
                        .commit();
                break;
            case "hard":


                Hard hard = new Hard();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, hard, FRAGMENT_TAG)
                        .commit();
        }

    }
}
