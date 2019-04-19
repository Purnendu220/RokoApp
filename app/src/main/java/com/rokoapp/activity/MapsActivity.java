package com.rokoapp.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rokoapp.R;
import com.rokoapp.fragment.SourceToDestination;
import com.rokoapp.manageApi.ApiManager;
import com.rokoapp.manageApi.FailureCodes;
import com.rokoapp.manageApi.ResponseProgressListener;
import com.rokoapp.model.response.RouteFinderModel;
import com.rokoapp.utils.SaveCache;
import com.rokoapp.utils.TrackerService;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.rokoapp.utils.ParamUtils.ANDROID_TOKEN;
import static com.rokoapp.utils.ParamUtils.ID_AUTH;

@SuppressWarnings("deprecation")
public class MapsActivity extends FragmentActivity implements  /*OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,*/ View.OnClickListener, ResponseProgressListener {

    private boolean frameIsVisible = false;
    int PLACE_AUTOCOMPLETE_REQUEST_HOME_CODE = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_OFFICE_CODE = 2;

//    private GoogleMap mMap;

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView textHomeLocation, textOfficeLocation;
    private Button findRouteButton;
    private ImageView closeLocationLayout;

    private String sourceName, destName;
    private double homeLatitude = 0, homeLongitude = 0, officeLatitude = 0, officeLongitude = 0;

    /*private GoogleApiClient client, mClient;

    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;*/
    private AutocompleteFilter typeFilter;


    /*private Double myLatitude = -33.865143;
    private Double myLongitude = 151.209900;*/

//    public static final int REQUEST_LOCATION_CODE=99;

    private RelativeLayout headerUser;
    private LinearLayout searchBar;
    private LinearLayout frame_contain;
    private TextView textViewUserName, welcomeText;
    private ImageView userImage;

    private Boolean exit = false;
    private String androidToken = null;
    private String fireBaseId = null;

    ArrayList<RouteFinderModel> routeFinderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initViews();
        initListeners();

        /*if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setTitle(R.string.app_name);
        }*/


        ID_AUTH = SaveCache.getString(MapsActivity.this,"Authorization");
        String token = SaveCache.getString(MapsActivity.this, ANDROID_TOKEN);
        if (token != null && !token.isEmpty()){
            androidToken = token;
        }else {
            androidToken = FirebaseInstanceId.getInstance().getToken();
        }

        ApiManager.getDeviceDetail(MapsActivity.this, androidToken, fireBaseId, MapsActivity.this);

        showData();
        startTrackerService();
    }

   /* private void askForResult() {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }else {
            showData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        askForResult();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, Please enable it.")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                    finalWarningCloseApp();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void finalWarningCloseApp(){
        new AlertDialog.Builder(MapsActivity.this)
                .setMessage("You have to Allow Location Services, get the better results.")
                .setPositiveButton("Yes, enable it.", (dialog, which) -> {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                })
                .setNegativeButton("No, Exit App", (dialog, id) -> {
                    dialog.dismiss();
                    MapsActivity.this.finish();
                })
                .create()
                .show();
    }*/


    private void showData() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/

        int width = getResources().getDisplayMetrics().widthPixels*2 / 3;
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mNavigationView.getLayoutParams();
        params.width = width;
        mNavigationView.setLayoutParams(params);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.my_trips) {
                    startActivity(new Intent(MapsActivity.this, TripHistory.class));
                    MapsActivity.this.finish();
                }
                if (menuItem.getItemId() == R.id.pass_list) {
//                    startActivity(new Intent(Home.this, MyPass.class));
                    startActivity(new Intent(MapsActivity.this, TrackMyBusOne.class));
                    MapsActivity.this.finish();
                }
                /*if (menuItem.getItemId() == R.id.my_pass) {
                    startActivity(new Intent(MapsActivity.this, MyPass.class));
                    MapsActivity.this.finish();
                }*/
                if (menuItem.getItemId() == R.id.drawer_sos) {
                    startActivity(new Intent(MapsActivity.this, MySos.class));
                    MapsActivity.this.finish();
                }
                if (menuItem.getItemId() == R.id.drawer_share) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out Roko App at: https://play.google.com/store/apps/details?id="+getPackageName());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                /*if (menuItem.getItemId() == R.id.drawer_exit) {

                }
                if (menuItem.getItemId() == R.id.drawer_costumer_service) {
                    startActivity(new Intent(MainActivity.this, CostumerService.class));
                    MainActivity.this.finish();
                }
                if (menuItem.getItemId() == R.id.drawer_faq) {
                    startActivity(new Intent(MainActivity.this, Faq.class));
                    MainActivity.this.finish();
                }*/
                return false;
            }
        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        setupDrawerHeader();
    }

    private void findLocation(int requestCode){
        if (ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)!=  PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            finish();
            System.exit(0);
            return;
        }
            /*lastLocation = LocationServices.FusedLocationApi.getLastLocation(client);
        if (lastLocation != null){
            LatLng center = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

            LatLng northSide = SphericalUtil.computeOffset(center, 10000,0);
            LatLng southSide = SphericalUtil.computeOffset(center, 10000, 180);

            LatLngBounds bounds = LatLngBounds.builder()
                    .include(northSide)
                    .include(southSide)
                    .build();*/

            try {
                typeFilter = new AutocompleteFilter.Builder().setCountry("IN").build();
//                typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).setTypeFilter(3).build();
//                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setBoundsBias(bounds).setFilter(typeFilter).build(this);
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter).build(this);
                startActivityForResult(intent, requestCode);
            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                Log.d("", "findLocation: "+e.toString());
            }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_HOME_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng latLng = place.getLatLng();
                homeLatitude = latLng.latitude;
                homeLongitude = latLng.longitude;
                textHomeLocation.setText(place.getAddress());
                sourceName = (String) place.getName()+", "+place.getAddress();
//                Toast.makeText(MapsActivity.this, place.getLatLng().toString(), Toast.LENGTH_LONG).show();
                Log.d("", "Place: ================================================== home: " + place.toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.d("", "onActivityResult: RESULT_CANCELED");
            }
        }
        else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_OFFICE_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
//                Toast.makeText(MapsActivity.this,  place.getLatLng().toString(), Toast.LENGTH_LONG).show();
                textOfficeLocation.setText(place.getAddress());
                destName = (String) place.getName()+", "+place.getAddress();
                LatLng latLng = place.getLatLng();
                officeLatitude = latLng.latitude;
                officeLongitude = latLng.longitude;
                Log.d("", "Place: ================================================= office: " + place.toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.d("", "onActivityResult: RESULT_CANCELED");
            }
        }
    }

    private void setupDrawerHeader() {
        String userName = SaveCache.getString(MapsActivity.this, "userName");

        welcomeText.setText("Welcome");
        textViewUserName.setText(userName);

        if (!userName.equals("") && !userName.isEmpty()) {
            String[] old = userName.split(" ");
            StringBuilder add = new StringBuilder();
            for (String s : old)
                add.append(s.charAt(0));

            String text = add.toString();
            text = text.length() < 2 ? text : text.substring(0, 2);

            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder().buildRound(text, color); // radius in px
            userImage.setImageDrawable(drawable);
        }

        headerUser.setOnClickListener( view -> {
            Intent i = new Intent(MapsActivity.this, EditProfile.class);
            startActivity(i);
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.main_drawer);
        searchBar = findViewById(R.id.searchBar);
        frame_contain = findViewById(R.id.frame_contain);
        textHomeLocation = findViewById(R.id.textHomeLocation);
        textOfficeLocation = findViewById(R.id.textOfficeLocation);
        findRouteButton = findViewById(R.id.findRouteButton);
        closeLocationLayout = findViewById(R.id.closeLocationLayout);

        View header = mNavigationView.inflateHeaderView(R.layout.drawer_header);
        headerUser = header.findViewById(R.id.headerUser);
        textViewUserName = header.findViewById(R.id.userName);
        welcomeText = header.findViewById(R.id.welcomeText);
        userImage = header.findViewById(R.id.userImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (exit) {
            System.exit(0);
            finish();
        } else {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(() -> exit = false, 3 * 1000);

        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //permission is granted
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        if(client == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                        Toast.makeText(this,"Permision Current Location Enabled",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this,"Permission is denied",Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        buildGoogleApiClient();
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
//            Toast.makeText(this,"Current Location Enabled",Toast.LENGTH_SHORT).show();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
//        Toast.makeText(this,"welcome",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLocationChanged(Location location) {
        *//*myLatitude=location.getLatitude();
        myLongitude=location.getLongitude();

        Log.d("myLatitude",String.valueOf(myLatitude));
        Log.d("myLongitude",String.valueOf(myLongitude));
        Toast.makeText(this,"onLocationChangedCalled",Toast.LENGTH_SHORT).show();*//*

        lastLocation=location;
        if(currentLocationMarker !=null){
            currentLocationMarker.remove();
        }

        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();

        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        currentLocationMarker=mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));

        if(client!=null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
//            Toast.makeText(this,"Location Listner called",Toast.LENGTH_SHORT).show();
        }

    }





    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/

    private void initListeners() {
        searchBar.setOnClickListener(this);
        textHomeLocation.setOnClickListener(this);
        textOfficeLocation.setOnClickListener(this);
        findRouteButton.setOnClickListener(this);
        closeLocationLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchBar:
                if(!frameIsVisible){
                    frameIsVisible = true;
                    searchBar.setVisibility(View.GONE);
                    frame_contain.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.textHomeLocation:
                findLocation(PLACE_AUTOCOMPLETE_REQUEST_HOME_CODE);
                break;
            case R.id.textOfficeLocation:
                findLocation(PLACE_AUTOCOMPLETE_REQUEST_OFFICE_CODE);
                break;
            case R.id.findRouteButton:
                    findTheRoute();
                break;
            case R.id.closeLocationLayout:
                if (frameIsVisible){
                   frameIsVisible = false;
                   searchBar.setVisibility(View.VISIBLE);
                   frame_contain.setVisibility(View.GONE);
                   textHomeLocation.setText(R.string.select_home_location);
                   textOfficeLocation.setText(R.string.select_office_location);
                    homeLatitude = 0;
                    homeLongitude = 0;
                    officeLongitude = 0;
                    officeLatitude = 0;
                }
                break;
        }
    }

    private void findTheRoute() {
        if (homeLatitude==0 && homeLongitude ==0){
            textHomeLocation.requestFocus();
            textHomeLocation.setError("Select home location first.");
            return;
        }if (officeLatitude==0 && officeLongitude==0){
            textOfficeLocation.requestFocus();
            textOfficeLocation.setError("Select office location");
            return;
        }
        if (homeLatitude !=0 && officeLatitude !=0){
//            ApiManager.getRouteForUser(MapsActivity.this, 28.63220811970849, 77.3095102197085, 28.5072885197085, 77.3778969, MapsActivity.this, routeFinderList);
            routeFinderList.clear();
            ApiManager.getRouteForUser(MapsActivity.this, homeLatitude, homeLongitude, officeLatitude, officeLongitude, MapsActivity.this, routeFinderList);
        }
    }

    @Override
    public void onResponseInProgress() {

    }

    @Override
    public void onResponseCompleted(int i) {
        switch (i){
            case 1:
                Intent m = new Intent(MapsActivity.this, SelectShortestRoute.class);
                m.putExtra("source", sourceName);
                m.putExtra("dest", destName);
                m.putExtra("routeList", routeFinderList);
                this.startActivity(m);
                MapsActivity.this.finish();
                /*SourceToDestination sourceToDestination = new SourceToDestination();
                Bundle bundle = new Bundle();
                bundle.putSerializable("routeList", routeFinderList);
                sourceToDestination.setArguments(bundle);
                sourceToDestination.show(getSupportFragmentManager(), "add_dialog_fragment");*/
    //            Toast.makeText(MapsActivity.this,"Response Received successfully.", Toast.LENGTH_LONG).show();
            break;
            case 2:
                Log.d("", "onResponseCompleted: Device details are saved.");
            break;
        }
    }

    @Override
    public void onResponseCompletedWithData(int i, Object data) {

    }

    @Override
    public void onResponseFailed(FailureCodes code) {

    }

    private void startTrackerService() {
        startService(new Intent(this, TrackerService.class));
        //finish();
    }

}
