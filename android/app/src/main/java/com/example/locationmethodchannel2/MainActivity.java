package com.example.locationmethodchannel2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.util.ArrayList;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "chrisbriant.uk.dev/location";

    private LocationTrack locationTrack;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
    super.configureFlutterEngine(flutterEngine);
      new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
          .setMethodCallHandler(
            (call, result) -> {
              Context ctx = this.getApplicationContext();
              //locationTrack = LocationTrack.getInstance();
              //locationTrack.initLocation(ctx,this);
              // Note: this method is invoked on the main thread.
              // TODO
              if (call.method.equals("getLocation")) {
                  try{
                      double lng = getLongitude();
                      double lat = getLatitude();
                      result.success(lng);
                  } catch(Exception e) {
                      result.error("1",e.getMessage(),null);
                  }

              } else if(call.method.equals("initLocation")) {
                  try{
                      initLocation(ctx,this);
                      result.success("Successfully Initialised");
                  } catch(Exception e) {
                      result.error("1",e.getMessage(),null);
                  }
              } else {
                result.notImplemented();
              }
            }
          );

    }

    private void initLocation(Context ctx, Activity act) {
        locationTrack = LocationTrack.getInstance();
        locationTrack.initLocation(ctx,act);
    }

    private double getLongitude() throws Exception {
        //locationTrack = LocationTrack.getInstance();

        if (locationTrack.canGetLocation()) {
            return locationTrack.getLongitude();
        } else {
            throw new Exception("Failed to get longitude");
        }
    }

    private double getLatitude() throws Exception {
        //locationTrack = LocationTrack.getInstance();

        if (locationTrack.canGetLocation()) {
            return locationTrack.getLatitude();
        } else {
            throw new Exception("Failed to get latitude");
        }
    }
}
