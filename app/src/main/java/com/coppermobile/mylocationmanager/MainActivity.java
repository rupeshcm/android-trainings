package com.coppermobile.mylocationmanager;

import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.iv_fruit);
        tvLocation = findViewById(R.id.tv_location);

        MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        observeImages(myViewModel);
    }

    private void observeImages(MyViewModel myViewModel) {
        myViewModel.getFruits().observe(this, integer -> {
            if (integer != null) {
                image.setImageResource(integer);
            }
        });
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            tvLocation.setText(String.format("%s, %s", location.getLatitude(), location.getLongitude()));
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
}