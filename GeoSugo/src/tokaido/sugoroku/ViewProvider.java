package tokaido.sugoroku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import tokaido.sugoroku.GeoHex.Loc;
import tokaido.sugoroku.GeoHex.Zone;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

class ViewProvider  {
	HashMap<String,Polygon> 			polygon_map = new HashMap<String,Polygon>();
	HashMap<String,ArrayList<Marker>> 	marker_map = new HashMap<String,ArrayList<Marker>>();
	HashMap<Marker,Data> 				marker_data_map = new HashMap<Marker,Data>();
	
	
	
	public void createPolygon(GoogleMap map,String key, Zone zone){
		Loc[] l = zone.getHexCoords();
		Polygon masu = map.addPolygon(new PolygonOptions()
							.add(	new LatLng(l[0].lat, l[0].lon), 
									new LatLng(l[1].lat, l[1].lon), 
									new LatLng(l[2].lat, l[2].lon), 
									new LatLng(l[3].lat, l[3].lon),
									new LatLng(l[4].lat, l[4].lon),
									new LatLng(l[5].lat, l[5].lon))
									.strokeColor(Color.RED)
									.strokeWidth(2)
									.fillColor  (0x0fff0000)
									.strokeColor(0xffff8888));
		
		polygon_map.put(key, masu);

	}
	
	public void createMarker(GoogleMap map, String key,ArrayList<Data> list){
		
		if ( list == null ){
			return ;
		}
		
		ArrayList<Marker> markers = new ArrayList<Marker>();
		for ( Data data: list){
			MarkerOptions mo = new MarkerOptions();
			mo.position(new LatLng(data.lat,data.lng));
    		mo.title(data.title);
    		
    		if ( data.visited ){
    			mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    		}else{
    			mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));		
    		}
			Marker marker = map.addMarker(mo);
			marker_data_map.put(marker, data);
			markers.add(marker);
			marker.setVisible(false);
		}
		marker_map.put(key, markers);
	}
	
	public Polygon getPolygon(String key){
		return polygon_map.get(key);
	}
	
	public ArrayList<Marker> getMarkers(String key){
		return marker_map.get(key);
	}
	
	public Data getData(Marker marker){
		return marker_data_map.get(marker);
	}
	
	public Set<Marker> getMarkers(){
		return marker_data_map.keySet();
	}
	

}