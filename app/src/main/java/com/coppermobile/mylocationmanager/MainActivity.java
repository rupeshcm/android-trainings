package com.coppermobile.mylocationmanager;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;

    private ImageView image;
    private TextView tvLocation;

    private LocationListener mGpsListener = new MyLocationListener();
    private MyLocationService myLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.iv_fruit);
        tvLocation = findViewById(R.id.tv_location);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE);
        } else {
            bindLocationListener();
        }

        MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        observeImages(myViewModel);

        Intent i = new Intent(this, MyLocationService.class);
        startService(i);
    }

    private void observeImages(MyViewModel myViewModel) {
        myViewModel.getFruits().observe(this, integer -> {
            if (integer != null) {
                image.setImageResource(integer);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            bindLocationListener();
        } else {
            Toast.makeText(this, "This sample requires Location access", Toast.LENGTH_LONG).show();
        }
    }

    private void bindLocationListener() {
        BoundLocationManager.bindLocationListenerIn(this, mGpsListener, getApplicationContext());
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            tvLocation.setText(String.format("%s, %s", String.valueOf(String.format("Location: %s", location.getLatitude())), location.getLongitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MainActivity.this, "Provider enabled: " + provider, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        MyLocationService.MyBinder b = (MyLocationService.MyBinder) binder;
        myLocationService = b.getService();
        myLocationService.getMyLocation();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        myLocationService = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MyLocationService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }
}