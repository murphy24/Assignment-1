package com.phonegap;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class NetworkListener implements LocationListener {

	private Context mCtx;
	private Location cLoc;
	private LocationManager mLocMan;
	private static final String LOG_TAG = "PhoneGap";
	GeoListener owner;

	public NetworkListener(Context ctx, int interval, GeoListener m)
	{
		owner = m;
		mCtx = ctx;
		this.start(interval);
	}

	public Location getLocation()
	{
		cLoc = mLocMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		return cLoc;
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "The provider " + provider + " is disabled");
	}


	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "The provider "+ provider + " is enabled");
	}


	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		Log.d(LOG_TAG, "The status of the provider " + provider + " has changed");
		if(status == 0)
		{
			Log.d(LOG_TAG, provider + " is OUT OF SERVICE");
		}
		else if(status == 1)
		{
			Log.d(LOG_TAG, provider + " is TEMPORARILY_UNAVAILABLE");
		}
		else
		{
			Log.d(LOG_TAG, provider + " is Available");
		}
	}


	/*
	 * The GPS is the primary form of Geolocation in PhoneGap.  Only fire the success variables if the GPS is down
	 * for some reason
	 */
	public void onLocationChanged(Location location) {
		Log.d(LOG_TAG, "The location has been updated!");
		if (!owner.mGps.hasLocation())
		{
			owner.success(location);
		}
		cLoc = location;
	}

	public void start(int interval)
	{
		mLocMan = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);
		mLocMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, interval, 0, this);
		cLoc = mLocMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	}

	public void stop()
	{
		mLocMan.removeUpdates(this);
	}

}
