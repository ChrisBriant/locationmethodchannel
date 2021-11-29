package com.example.locationmethodchannel2;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

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
                switch (call.method) {
                    case "getLongitude":
                        try{
                            double lng = getLongitude();
                            result.success(lng);
                        } catch(Exception e) {
                            result.error("1",e.getMessage(),null);
                        }
                        break;
                    case "getLatitude":
                        try{
                            double lat = getLatitude();
                            result.success(lat);
                        } catch(Exception e) {
                            result.error("1",e.getMessage(),null);
                        }
                        break;
                    case "initLocation":
                        try{
                            initLocation(ctx,this);
                            result.success("Successfully Initialised");
                        } catch(Exception e) {
                            result.error("1",e.getMessage(),null);
                        }
                        break;
                    default:
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
        if (locationTrack.canGetLocation()) {
            return locationTrack.getLongitude();
        } else {
            throw new Exception("Failed to get longitude");
        }
    }

    private double getLatitude() throws Exception {
        if (locationTrack.canGetLocation()) {
            return locationTrack.getLatitude();
        } else {
            throw new Exception("Failed to get latitude");
        }
    }
}
