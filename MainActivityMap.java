package com.example.pvtruong.map_service;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by PVTruong on 18/03/2017.
 */

public class MainActivityMap extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng toado = new LatLng(21.03582, 105.7971);
        mMap.addMarker(new MarkerOptions().position(toado).
                title("This is where"));// lấy icon .icon(BitmapDescriptorFactory.from..
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toado, 18));
       // mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);// bản đồ địa hình

        LatLng toado2 = new LatLng( 21.02127,105.8483147);
        mMap.addPolyline(new PolylineOptions().
                add(    toado,
                        new LatLng(21.0368368,105.7963181),
                        new LatLng(21.0321089,105.822584),
                        toado2).width(10).color(Color.BLUE)
                );
    }
}
