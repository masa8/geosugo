package tokaido.sugoroku;

import java.util.ArrayList;

import tokaido.sugoroku.GeoHex.Zone;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;

class SugorokuMaster implements OnMapClickListener{
		
		/**
	 	* 
	 	*/
		private final MapFragment mMapFragment;
		FragmentActivity mActivity;
		GoogleMap mMap;
		ModelProvider mMp;
		ViewProvider mVp;
		
		
		
		public SugorokuMaster(MapFragment mapFragment, 
							Fragment fragment,
							ModelProvider mp,
							ViewProvider vp){
	        
			mMapFragment = mapFragment;
			mActivity = fragment.getActivity();
			
			initializeMap();
			setModelProvider(mp);
			setViewProvider(vp);
			
			
		}

		private void initializeMap() {
			
			mMap = mMapFragment.getMap();
			mMap.setMyLocationEnabled(true);
	    	mMap.moveCamera( 
					CameraUpdateFactory.newLatLngZoom(
							new LatLng(35.46311677452055, 139.60979461669922),  13.0f ));
			
		}
		
		private void setModelProvider(ModelProvider mp){
			mMp = mp;
			mMp.createZone();
			mMp.createData();
		}
		
		private void setViewProvider(ViewProvider vp){
			mVp = vp;
			for(String key :  mMp.getZoneKeySet()){
				Zone zone = mMp.getZone(key);
				mVp.createPolygon(mMap, key, zone);
				mVp.createMarker(mMap,key,mMp.getDataIn(key));
			}
		}			
		
		public void setListener(){
	    	
			mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){

    			@Override
    			public void onInfoWindowClick(Marker arg0) {
    				Data k = mVp.getData(arg0);
    				Intent i = new Intent(SugorokuMaster.this.mMapFragment.getActivity(),DescriptionActivity.class);
    				i.putExtra(DescriptionFragment.ID, k.mId);
    				SugorokuMaster.this.mMapFragment.startActivity(i);
				
    			}
			
    		});

	    	mMap.setOnMapClickListener(this);
		}
		

		public void moved(LatLng current){
			
			
			String anyZoneKey = GeoHex.encode(current.latitude, 
												current.longitude, 
												ModelProvider.MAP_LEVEL);
				
			mMp.moveAction(anyZoneKey);
			
			ZoneInfo info = DataLab.get(mActivity).getZoneInfo();
			if (info.getStatus() == ZoneInfo.Status.PLAY && 
					mMp.isLastZone()){
					showGoalMessage();
					info.updateStatus(ZoneInfo.Status.DONE);
				
			}else if ( info.getStatus() == ZoneInfo.Status.PLAY && 
					mMp.isLastActivity()){
					showSugorokuMessage();
			}else if (info.getStatus() == ZoneInfo.Status.READY && 
					mMp.isLastActivity()){
					showSugorokuMessage();
			}
			
					
		}
		

		public void showGoalMessage(){
			FragmentManager fm = mActivity.getSupportFragmentManager();
	        GoalSugorokuDialog saikoro = new GoalSugorokuDialog();
	        saikoro.setTargetFragment(mMapFragment,0);
	        saikoro.show(fm, "goal_sugoroku");
			
		}
		
		public void showSugorokuMessage(){
			FragmentManager fm = mActivity.getSupportFragmentManager();
	        PlaySugorokuDialog saikoro = new PlaySugorokuDialog();
	        saikoro.setTargetFragment(mMapFragment,0);
	        saikoro.show(fm, "play_sugoroku");
		}

		public void onSugorokuAction(int increment){
			mMp.saikoroAction(increment);
		}
		
		public void updateView(){
			
			/** Update Marker Status */
			for ( Marker m: mVp.getMarkers()){
				Data k = mVp.getData(m);
				if (k.visited){
					m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
					
				}else{
					m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					
				}
			}
			
			if ( DataLab.get(mActivity).getZoneInfo().getStatus() == 
					ZoneInfo.Status.DONE){
				for(String key: mMp.getZoneKeySet()){
					Polygon target = mVp.getPolygon(key);
					if ( target != null ){
						target.setFillColor  (0x1f00ff00);
						target.setStrokeColor(0xff008800);
					}
				}
				
				for ( Marker marker : mVp.getMarkers()){
					if ( marker != null ){
						marker.setVisible(true);
					}
				}

				return;
			}
			
			ZoneInfo info = DataLab.get(mMapFragment.getActivity()).getZoneInfo();
			
			for(String key: mMp.getZoneKeySet()){
				int index = mMp.getZoneIndex(key);
				
				if ( info.getStatus() == ZoneInfo.Status.READY ){
					 // Do nothing, because of you are not on the SUGOROKU.
					return;
				}else if ( info.getCurrentZoneIndex() == index ){
					/** Draw Current Zone */
						Polygon target = mVp.getPolygon(mMp.getZoneKey(index));
						if ( target != null ){
							target.setFillColor  (0x1fffff00);
							target.setStrokeColor(0xff888800);
						}
						ArrayList<Marker> mlist = mVp.getMarkers(key);
						if ( mlist != null ){
							for ( Marker m: mlist ){
							m.setVisible(true);
							}
						}
				}else if ( index < info.getCurrentZoneIndex() ){
					/** Draw back Zones */
					Polygon back = mVp.getPolygon(key);
					if ( back != null ){
						back.setFillColor  (0x1f00ff00);
						back.setStrokeColor(0xff008800);
					}
					ArrayList<Marker> mlist = mVp.getMarkers(key);
					if ( mlist != null ){
						for ( Marker m: mlist ){
						m.setVisible(true);
						}
					}
			
				}else if ( info.getCurrentZoneIndex() < index &&
						   index <= info.getActivityZoneIndex()){
					/** Draw Activity Zones */
					Polygon back = mVp.getPolygon(key);
					if ( back != null ){
						back.setFillColor  (0x0fff0000);
						back.setStrokeColor(0xffff0000);
					}
					ArrayList<Marker> mlist = mVp.getMarkers(key);
					if ( mlist != null ){
						for ( Marker m: mlist ){
							m.setVisible(true);
						}
					}
				}else {
					/** Draw forward zones */
					Polygon back = mVp.getPolygon(key);
					if ( back != null ){
						back.setFillColor(0x1f1A4472);
						back.setStrokeColor(0xff1A4472);
					}
				
				}
			}
			

		}
		
		@Override
		public void onMapClick(LatLng clicked) {
			moved(clicked);
			updateView();
		}	
		
		
	}