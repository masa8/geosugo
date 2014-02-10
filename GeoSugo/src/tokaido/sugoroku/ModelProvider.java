package tokaido.sugoroku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import tokaido.sugoroku.GeoHex.Zone;

class ModelProvider {
	
	/**
	 * 
	 */
	private final MapFragment mMapFragment;
	static final int MAP_LEVEL = 7;
	HashMap<String,Zone> 			zone_map = new HashMap<String,Zone>();
	HashMap<String,Integer>	 		zone_index_map = new HashMap<String,Integer>();
	ArrayList<String>				index_zonekey_array = new ArrayList<String>();
	HashMap<String,ArrayList<Data>> zone_data = new HashMap<String,ArrayList<Data>>();
	
		

	/**
	 * @param mapFragment
	 */
	ModelProvider(MapFragment mapFragment) {
		mMapFragment = mapFragment;
	}

	
	public void createZone(){
		
		Zone[] masu = {
					/*0*/GeoHex.getZoneByLocation(35.47045679700973, 139.6249008178711, MAP_LEVEL),
					/*1*/GeoHex.getZoneByLocation(35.46598295825904, 139.6142578125, MAP_LEVEL),
					/*2*/GeoHex.getZoneByLocation(35.46311677452055, 139.60979461669922, MAP_LEVEL),
					/*3*/GeoHex.getZoneByLocation(35.45857261537831, 139.6087646484375, MAP_LEVEL),
					/*4*/GeoHex.getZoneByLocation(35.44661675543854, 139.59988117218018, MAP_LEVEL),
					/*5*/GeoHex.getZoneByLocation(35.4432604052822, 139.5977783203125, MAP_LEVEL),
					/*6*/GeoHex.getZoneByLocation(35.44672163912571, 139.58795070648193, MAP_LEVEL),
					/*7*/GeoHex.getZoneByLocation(35.44322544256488, 139.57683563232422, MAP_LEVEL),
					/*8*/GeoHex.getZoneByLocation(35.43738645574568, 139.56677198410034, MAP_LEVEL),
					/*9*/GeoHex.getZoneByLocation(35.43200162550634, 139.56499099731445, MAP_LEVEL),
					/*10*/GeoHex.getZoneByLocation(35.42780540426507, 139.56443309783936, MAP_LEVEL),
					/*11*/GeoHex.getZoneByLocation(35.422035243064826, 139.5549488067627, MAP_LEVEL),
					/*12*/GeoHex.getZoneByLocation(35.41512799148973, 139.54681634902954, MAP_LEVEL),
					/*13*/GeoHex.getZoneByLocation(35.41206762745147, 139.54181671142578, MAP_LEVEL),
					/*14*/GeoHex.getZoneByLocation(35.404757259005535, 139.53761100769043, MAP_LEVEL),
					};
	
		for (int i = 0; i < masu.length; ++i){
			String key = GeoHex.encode(masu[i].lat, masu[i].lon, MAP_LEVEL);
			zone_map.put(key,masu[i]);
			zone_index_map.put(key, i);
			index_zonekey_array.add(i,key);
		}
		
		
	}
	

	public void createData(){
        
		ArrayList<Data> data = DataLab.get(mMapFragment.getActivity()).getDataArray();
	
        mapDataToZone(data);
    }

	private void mapDataToZone(ArrayList<Data> data) {
		for ( Data i: data){
        	String candidate = GeoHex.encode(i.lat, i.lng, MAP_LEVEL);
        	for (String  key : zone_map.keySet()){
        		if ( key.equals(candidate)){
        			if ( zone_data.get(key) == null ){
        				ArrayList<Data> list = new ArrayList<Data>();
        				list.add(i);
        				zone_data.put(key, list);
        			}else{
        				zone_data.get(key).add(i);
        			}
        		}
        	}
			
        }
	}
	
	public Zone getZone(String key){
		return zone_map.get(key);
	}
	
	public Integer getZoneIndex(String key){
		return zone_index_map.get(key);
	}

	public Set<String> getZoneKeySet(){
		return zone_map.keySet();
	}
	
	public ArrayList<Data> getDataIn(String key){
		return zone_data.get(key);
	}
	
	public String getZoneKey(int i){
		return index_zonekey_array.get(i);
	}
	
	public boolean isLastZone(){
		ZoneInfo info = DataLab.get(mMapFragment.getActivity()).getZoneInfo();
		if ( info.getStatus() == ZoneInfo.Status.PLAY && 
				info.getCurrentZoneIndex() == getZoneKeySet().size() - 1){
					return true;
		}
					return false; 
	}
	
	
	public boolean isLastActivity(){
		ZoneInfo info = DataLab.get(mMapFragment.getActivity()).getZoneInfo();
		if ( info.getStatus() == ZoneInfo.Status.PLAY && 
				info.getCurrentZoneIndex() == info.getActivityZoneIndex()){
					return true;
		}
					return false; 
	}

	
	public void moveAction(String newPositionKey){
		
		
		if ( getZoneIndex(newPositionKey) == null ){ 
			/** Do nothing because of out of zone there. */
			return;
		}
		
		ZoneInfo info = DataLab
				.get(mMapFragment.getActivity())
				.getZoneInfo();
		
		if (info.getStatus() == ZoneInfo.Status.READY ){
			
			info.setCurrentZoneIndex(getZoneIndex(newPositionKey));
			info.setActivityZoneIndex(getZoneIndex(newPositionKey));
			info.updateStatus(ZoneInfo.Status.PLAY);
			return;
			
		}else if ( 	info.getStatus() == ZoneInfo.Status.PLAY ){
			
			if ( getZoneIndex(newPositionKey).intValue() == 
					getZoneKeySet().size() - 1 ){
				
						info.setCurrentZoneIndex(getZoneIndex(newPositionKey));
						return;
						
			}else if (info.getActivityZoneIndex() <
					   getZoneIndex(newPositionKey).intValue()){	
						
						/** Do nothing because of you still have some activity zone you must go.*/
						return;
				
			}else if (getZoneIndex(newPositionKey) == 
						info.getActivityZoneIndex()){
				
						/** Got to the last activity zone.*/
						info.setCurrentZoneIndex(getZoneIndex(newPositionKey));
						return;	
			}else{
						/** Moving forward within activity zones*/
						info.setCurrentZoneIndex(getZoneIndex(newPositionKey));
			}
			
		}else if ( 	info.getStatus() == ZoneInfo.Status.DONE ){
			//Do nothing.
		}
		
	}

	public void saikoroAction(int increment){
		
		ZoneInfo info = DataLab.get(mMapFragment.getActivity()).getZoneInfo();
		if ( info.getStatus() == ZoneInfo.Status.READY){
			return;
		}
		
		int current_zone_index = info.getCurrentZoneIndex();

		int activity_zone_index;
		if ( (current_zone_index + increment) < getZoneKeySet().size()){
			
			/** Set the activity zone to the zone got*/
			activity_zone_index = current_zone_index + increment;
		}else{
			/** Set the activity zone to the last*/
			activity_zone_index = getZoneKeySet().size() - 1;
		}
		
		info.setActivityZoneIndex(activity_zone_index);
}




}