package pc1.exergame.menu.activities;
/**
 * Class is handling the whole Browse Map Activity
 */

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import pc1.exergame.R;
import pc1.exergame.other.Haversine;
import pc1.exergame.popups.ChallengeQuery;
import pc1.exergame.storage.ChallengeMarkers;
import pc1.exergame.storage.DBController;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private final LatLng mDefaultLocation = new LatLng(48.2082, 16.3738);
    private static final int DEFAULT_ZOOM = 18;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private Location mCurrentLocation;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase db;
    private DatabaseReference dbref;
    private Haversine mhav;
    private DBController dbc;
    private HashMap<String, ChallengeMarkers> markers;
    private ChallengeMarkers mCM;

    /**
     * onCreate
     * Connect to GoogleAPIClient
     * if last position of map is known, show this position
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbc = new DBController();
        dbc.parseChallenges();

        if (savedInstanceState != null) {
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_maps);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    /**
     * on Resume
     * retrieve device location and update markers in given position
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            getDeviceLocation();
        }
        updateMarkers();
    }
/**
 * on Pause
 * Removes all location updates for the given pending intent.
 */
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }

    /**
     * Saves Camera position to
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mCurrentLocation);
            super.onSaveInstanceState(outState);
        }
    }

    /**
     * when connected, get deviceLocation and build the map fragment.
     * @param connectionHint
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        getDeviceLocation();
        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * onConnectionFailed method / logging
     * @param result
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.d(TAG, "Play services connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    /**
     * on ConnectionSuspended method / logging
     * @param cause
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "Play services connection suspended");
    }

    /**
     * If device location is changed / update Markers on the map
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateMarkers();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * provides parameters for displaying map content (Compass, Toolbar)
     * on Marker click check if user is close enough. Then make the Challenge available
     *
     * @param map
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker){
                if(marker.getSnippet() == null){
                    mMap.moveCamera(CameraUpdateFactory.zoomIn());
                    return true;
                }
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                ChallengeQuery query = new ChallengeQuery();
                FragmentManager fm = getSupportFragmentManager();
                LatLng markPos = marker.getPosition();
                Haversine mhav = new Haversine();
                Boolean inProx;

                if (mhav.IsClose(markPos.latitude, markPos.longitude, mCurrentLocation.getLatitude(),
                        mCurrentLocation.getLongitude()).equals("true")){
                    inProx = true;
                } else { inProx = false; }

                Bundle args = new Bundle();
                if (user != null){
                    String username = user.getEmail().replace("@user.pae","");
                    args.putString("username", username);
                }

                args.putBoolean("proximity", inProx);
                args.putString("description", marker.getSnippet());
                args.putString("type", marker.getTitle());
                args.putString("id", marker.getTag().toString());
                query.setArguments(args);
                query.show(fm, "LUL");

                return true;
            }
        });

        updateLocationUI();

        updateMarkers();

        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        } else if (mCurrentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            Log.d(TAG, "Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    /**
     * Builder to help construct the GoogleApiClient object.
     * Connecting to Google Maps API and creating of a new location request
     */
    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    /**
     * Method creates new location request (time interval, and accuracy/priority)
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * This method checks for granted permissions and gets the last known device location
     */
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
        }
    }

    /**
     * Callback for the result from requesting permissions.
     * This method is invoked for every call on requestPermissions
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Get all markers for showed Map from Firebase database based on latitude and longitude
     */
    private void updateMarkers() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            markers = dbc.getMarkers();

            if (markers.isEmpty()) {
                Log.i("markerz", "markers empty: ");
            } else {
                Log.i("markerz", "keyset: "+  markers.keySet());

                for (String key : markers.keySet()){
                    LatLng mLatLng = new LatLng(markers.get(key).getLatitude(), markers.get(key).getLongitude());
                    HashMap<String, HashMap> exer = markers.get(key).getExcercises();
                    String snippetMessage = "";

                    Log.i("exerMark", "keyset: "+  exer.keySet());
                    Log.i("exerMark", "values: "+  exer.values());

                    for (String exKey : exer.keySet()) {
                        snippetMessage = snippetMessage + exer.get(exKey).get("name") + ":  Sets: " +
                                exer.get(exKey).get("sets") + "  Reps: " + exer.get(exKey).get("reps") + "\n";
                    }

                    if (markers.get(key).isActive()){
                        mMap.addMarker(new MarkerOptions()
                                .position(mLatLng)
                                .title(markers.get(key).getType())
                                .snippet(snippetMessage)
                        ).setTag(key);
                    }

                }

            }

        } else {


        }
    }

    /**
     * Check for missing location permission
     */
    @SuppressWarnings("MissingPermission")
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mCurrentLocation = null;
        }
    }
}

