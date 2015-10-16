package com.jocusinteractive.firstproject;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FloatingActionButton backButton;
    private Double LATITUDE = -43.473739;
    private Double LONGITUDE = 172.609034;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //catch list of location
        if (getIntent().getExtras() != null) {
            //listOfLocations = this.getIntent().getExtras().getParcelable("listOfLocations");
        }


        backButton = (FloatingActionButton) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;


        ArrayList<Marker> listOfMarkers = new ArrayList<Marker>();

        if(mainPage.listOfLocations.size() != 0){
            for(Location location: mainPage.listOfLocations){ //add each point from list as marker on map

                LatLng loc = new LatLng((LATITUDE + location.getLatLng().first),(LONGITUDE + location.getLatLng().second));
                String color = location.getColor();
                Marker marker;

                BitmapDescriptorFactory bdf;
                switch (color) {
                    case "#3333FF":
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .title(location.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        break;
                    case "#33CC00":
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .title(location.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        break;
                    case "#FF9900":
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .title(location.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        break;
                    case "#FF0033":
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .title(location.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        break;
                    case "#CCCCCC":
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .title(location.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        break;
                    default:
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .title(location.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                        break;
                }



                listOfMarkers.add(marker);
            }
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder(); //define bounds for the map
        for (Marker marker : listOfMarkers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 150; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cu); //move camera to these bounds




    }
}
