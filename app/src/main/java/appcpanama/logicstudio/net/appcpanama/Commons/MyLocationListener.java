package appcpanama.logicstudio.net.appcpanama.Commons;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by LogicStudio on 28/10/16.
 */
public class MyLocationListener implements LocationListener {

    LocationCallback locationCallback;

    public void onLocationChanged(Location loc) {
        locationCallback.locationChange(loc);
    }

    public void onProviderDisabled(String arg0) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    public interface LocationCallback{
        void locationChange(Location location);
    }

    public void setLocationCallback(LocationCallback locationCallback){
        this.locationCallback = locationCallback;
    }
}
