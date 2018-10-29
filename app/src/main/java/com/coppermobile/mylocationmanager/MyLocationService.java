package com.coppermobile.mylocationmanager;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class MyLocationService extends Service {

    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private final IBinder mBinder = new MyBinder();
    private Location location;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        MyLocationService getService() {
            return MyLocationService.this;
        }
    }

    public void getMyLocation() {
        LocationRequest mLocationRequest = getFusedLocationRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> location = MyLocationService.this.getUserLastLocation(task));

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult == null)
                    return;

                location = locationResult.getLastLocation();
                if (location != null) {
                    Toast.makeText(MyLocationService.this, "Location: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    @NonNull
    private LocationRequest getFusedLocationRequest() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);
        return mLocationRequest;
    }

    private Location getUserLastLocation(Task<Location> task) {
        Location loc = task.getResult();
        if (loc != null) {
            return loc;
        } else {
            Toast.makeText(this, "Location is not Available", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    public void onDestroy() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
        super.onDestroy();
    }
}