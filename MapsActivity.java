package com.example.pvtruong.map_service;

import android.content.pm.PackageManager;
import android.location.Location;
import android.media.browse.MediaBrowser;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btn, btnvitri, btn_UpdateLocation;
    GoogleApiClient googleApiClient;// lấy ra vị trí hiện tại đang đừng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btn = (Button) findViewById(R.id.btn);
        btnvitri = (Button) findViewById(R.id.btn_vitri);
        btn_UpdateLocation = (Button) findViewById(R.id.btnUpdateLocation);
        btn_UpdateLocation.setOnClickListener(OnUpdateLocation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View bản đồ đến tọa độ hiện tại
                LatLng toado = new LatLng(21.040786, 105.774786);
                // Add cờ (marker) đánh dấu lên tọa độ đó
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(toado);
                markerOptions.title("Devpro");
                mMap.addMarker(markerOptions);

                //Zom bản đồ đến tọa độ đó
                // tỷ lệ zoom 16
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toado, 16));
            }
        });
        btnvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleApiClient.Builder builder = new GoogleApiClient.Builder(MapsActivity.this);
                builder.addApi(LocationServices.API);
                builder.addConnectionCallbacks(onGetMyLocation);
                googleApiClient = builder.build();
                // kết nối để lấy vị trí hiện tại
                googleApiClient.connect();


            }
        });

    }

    GoogleApiClient.ConnectionCallbacks onGetMyLocation = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {

            if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            // đây là nơi lấy tọa độ hiện tại
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            Toast.makeText(MapsActivity.this, "" + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();

            LatLng toado = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(toado).title("Vị tri hiện tại của tôi "));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toado, 16));
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };
    GoogleApiClient clientApi;
    View.OnClickListener OnUpdateLocation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clientApi = new GoogleApiClient.Builder(v.getContext()).
                    addApi(LocationServices.API).addConnectionCallbacks(OnKetNoiThanhCong).build();
            clientApi.connect();

        }
    };
    /**
     * khi map đã được tạo nó trẩ về ggmap
     */
    GoogleApiClient.ConnectionCallbacks OnKetNoiThanhCong = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            // Thiết lập xem các thông số yêu cầu
            LocationRequest request = new LocationRequest();
            request.setInterval(10000);// Thời gian chậm nhất yêu cầu lên server lấy vị trị 1 lần
            request.setFastestInterval(5000);//thời gian ngắn nhất
            // Lấy chính xác
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(clientApi, request, OnLocationChange);
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };
    LocationListener OnLocationChange=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng toado=new LatLng(location.getLatitude(),location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(toado).title("Điểm tiếp theo"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toado,16));
        }
    };
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
}
