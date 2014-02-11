package tokaido.sugoroku;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends SherlockMapFragment implements  android.location.LocationListener {
	
	SugorokuMaster sm;
	private LocationManager mLocationManager;
	
	public static MapFragment newInstance(){
		return new MapFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container,
			Bundle savedInstanceState) {
		
		View view =  super.onCreateView(inflater, 
					container ,
					savedInstanceState);
		
		sm = new SugorokuMaster(this, 
				this,
				new ModelProvider(this),
				new ViewProvider());
		sm.setListener();

		
		
		return view;
	
	}

	@Override
	public void onResume() {
		super.onResume();
		sm.updateView();

		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getApplicationContext());
		if (result == ConnectionResult.SUCCESS){
			mLocationManager =
			        (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
			if ( mLocationManager != null ){
				mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
						10000,          // 10-second interval.
						10,             // 10 meters.
						this);
			}

			
		}else{
			Toast.makeText(getActivity(), R.string.e_message_googleplay, Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onPause() {
		/** Save all the model data */
		DataLab.get(getActivity()).saveModel();
		
		if ( mLocationManager != null ){
			mLocationManager.removeUpdates(this);
		}
		super.onPause();
	}


	@Override
	public void onLocationChanged(Location location) {
	    Toast.makeText(getActivity(), Double.valueOf(location.getLatitude()).toString()
	    		+ ":" + Double.valueOf(location.getLongitude()).toString(),
	    		Toast.LENGTH_SHORT).show();
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        sm.moved(latLng);
        sm.updateView();
        getMap().moveCamera(CameraUpdateFactory.newLatLng(latLng));

	}
	
	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == 0){
			int number = arg2.getIntExtra("SAIKORO_RESULT",-1);
			if ( number != -1){
				sm.onSugorokuAction(number);
			}
		}
		
		sm.updateView();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
