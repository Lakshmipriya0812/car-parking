package com.appblocky.smartparking;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private Marker myMarker;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        googleMap.setOnMarkerClickListener(this);
        user=getIntent().getStringExtra("user");

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(this);
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng p1 = new LatLng(11.628233,78.1407022);
        mMap.addMarker(new MarkerOptions().position(p1).title("p1"));

        LatLng p2 = new LatLng(11.626999,78.1428222);
        mMap.addMarker(new MarkerOptions().position(p2).title("p2"));

        LatLng p3 = new LatLng(11.623833,78.1408722);
        mMap.addMarker(new MarkerOptions().position(p3).title("p3"));

        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p1,zoomLevel));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("mymarker: ",marker.getTitle());
//        Log.i("markerclick: ",marker.getPosition().toString());
        String loc=marker.getTitle();
        Intent i= new Intent(this,ParkingArea1.class);
        i.putExtra("loc",loc);
        i.putExtra("user",user);
        startActivity(i);
        finish();
        return false;
    }
}