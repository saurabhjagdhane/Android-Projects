package com.example.saurabh.project;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyService extends Service {
    final public static String MY_ACTION = "110394";
    double latitude = 0.0;
    double longitude = 0.0;
    private static String msg;

    public MyService() {
    }

    @Override
    public void onCreate() {
        MyListener listener = new MyListener();
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        } catch (SecurityException e) {
            Log.e("error", "msg...........security");
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent intent1 = new Intent();
        intent1.setAction(MY_ACTION);

        if (latitude != 0.0 && longitude != 0.0) {
            Toast.makeText(MyService.this, String.valueOf(latitude) + String.valueOf(longitude), Toast.LENGTH_SHORT).show();
            msg = getMyLocation(latitude, longitude);
        }

        if (msg == null) {
            intent1.putExtra("DATAPASSED1", "Tempe, AZ");
        } else {
            intent1.putExtra("DATAPASSED1", msg);
        }

        sendBroadcast(intent1);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MyListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (latitude == 0.0 && longitude == 0.0) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    public String getMyLocation(double lat, double lng) {
        Log.e("Inside", "Got lat n lng");
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0)
            System.out.println(addresses.get(0).getLocality());
        Toast.makeText(this, addresses.get(0).getLocality(), Toast.LENGTH_SHORT).show();
        return addresses.get(0).getLocality();


    }
}
