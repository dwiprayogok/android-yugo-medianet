package com.training.Medianet;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.training.Medianet.Utils.GPSTracker;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by root on 05/01/17.
 */
public class ShowMap extends AppCompatActivity implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    String TAG = "ShowMap";
    private GoogleMap map;
    Marker cloc;
    Marker dloc;
    Marker eloc;
    private GoogleApiClient googleApiClient;
    private LocationManager mLocationManager;
    Location currentLocation = new Location("current");
    GPSTracker gps;
    Double clongitude;
    Double clatitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_map);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        CheckGpsStatus();
    }

    public void CheckGpsStatus(){

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //GpsStatus = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(enabled)
        {
            if(status == ConnectionResult.SUCCESS) {
                //Success! Do what you want
                setCustomMarkerOnePosition();
                setCustomMarkerTwoPosition();
                currentlocation();
                connectGmapApiClient();



            }else{

                GooglePlayServicesUtil.getErrorDialog(status, this, status);


            }
        }else {

            new SweetAlertDialog(ShowMap.this,SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("GPS anda tidak aktif")
                    .setContentText("Aktipkan GPS Anda Terlebih Dahulu")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            Intent pindah = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(pindah);
                            finish();
                        }
                    }).show();

        }

    }



    private void connectGmapApiClient() {

        //Initializing googleapi client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    void currentlocation(){



        gps = new GPSTracker(ShowMap.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            currentLocation.setLatitude(latitude);
            currentLocation.setLongitude(longitude);
            clatitude = currentLocation.getLatitude();
            clongitude = currentLocation.getLongitude();
            // \n is for new line
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


        moveMap();


    }
    private void setCustomMarkerOnePosition() {
        LatLng latLng = new LatLng(-6.2400943, 106.7894802);
        //-6.2400943  106.7894802
        MarkerOptions mO = new MarkerOptions();
        mO.position(latLng) //setting position
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title("Ini Posisi saya yang pertama");
        dloc = map.addMarker(mO);
        dloc.showInfoWindow();

    }



    private  void setCustomMarkerTwoPosition() {
        LatLng latLng = new LatLng(-6.3015369,106.7818858 );
        MarkerOptions mO = new MarkerOptions();
        mO.position(latLng) //setting position
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .title("Ini Posisi saya yang Kedua");
        eloc = map.addMarker(mO);
        eloc.showInfoWindow();

    }

    private void moveMap() {

        //String to display current latitude and longitude

        String msg = clatitude + ", "+clongitude;

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(clatitude, clongitude);

        //Adding marker to map
        MarkerOptions mO = new MarkerOptions();

        mO.position(latLng) //setting position
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Ini Posisi saya Berada Sekarang");
        cloc = map.addMarker(mO);
        cloc.showInfoWindow();
        // map.addMarker(mO).showInfoWindow(); //Adding a title

        //Moving the camera
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        map.animateCamera(CameraUpdateFactory.zoomTo(12));

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }



    @Override
    public void onBackPressed() {
        finish();
    }
}
