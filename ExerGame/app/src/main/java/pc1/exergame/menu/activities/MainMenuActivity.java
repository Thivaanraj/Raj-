package pc1.exergame.menu.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;

import pc1.exergame.R;
import pc1.exergame.fragments.ChallengesFragment;
import pc1.exergame.fragments.DashboardFragment;
import pc1.exergame.fragments.Easy;
import pc1.exergame.fragments.Hard;
import pc1.exergame.fragments.Medium;
import pc1.exergame.fragments.RankingFragment;


public class MainMenuActivity extends AppCompatActivity implements
        ChallengesFragment.Communicator,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final String FRAGMENT_TAG = "fragment_tag";

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public boolean checkFocus(String focus) {
        return this.focus.equals(focus);
    }

    private String focus = "";

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private LocationRequest mLocationRequest;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buildGoogleApiClient();
        mGoogleApiClient.connect();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        createDefaultFragment();

    }

    private void createDefaultFragment() {
        if (!checkFocus("dashboard")) {
            setFocus("dashboard");
            DashboardFragment fragment = new DashboardFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, FRAGMENT_TAG)
                    .commit();
        }
    }

    public void ChallengesClick(View view) {
        if (!checkFocus("challenges")) {
            setFocus("challenges");
            ChallengesFragment fragment = new ChallengesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                    .commit();
        }
    }

    public void MapClick(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void RankingClick(View view) {
        if (!checkFocus("ranking")) {
            setFocus("ranking");
            RankingFragment fragment = new RankingFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                    .commit();
        }
    }

    public void DashboardClick(View view) {
        if (!checkFocus("dashboard")) {
            setFocus("dashboard");
            DashboardFragment fragment = new DashboardFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
                    .commit();
        }
    }

    public void globalSignOut(View view) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        finish();
    }


    @Override
    public void callFrag(String challengeType) {
        Bundle loc = new Bundle();
        loc.putDouble("lat", mCurrentLocation.getLatitude());
        loc.putDouble("long", mCurrentLocation.getLongitude());
        switch (challengeType) {
            case "ez":
                Easy ez = new Easy();
                ez.setArguments(loc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, ez, FRAGMENT_TAG)
                        .commit();
                break;

            case "med":
                Medium med = new Medium();
                med.setArguments(loc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, med, FRAGMENT_TAG)
                        .commit();
                break;

            case "hard":
                Hard hard = new Hard();
                hard.setArguments(loc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, hard, FRAGMENT_TAG)
                        .commit();
                break;
            case "dash":
                DashboardFragment dash = new DashboardFragment();
                //dash.setArguments(loc);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, dash, FRAGMENT_TAG)
                        .commit();
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        getDeviceLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }


    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
            LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            getDeviceLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
    }
}
