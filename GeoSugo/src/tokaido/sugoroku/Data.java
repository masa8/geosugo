package tokaido.sugoroku;


import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Model
 * @author kevin
 *
 */
public class Data {
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_LAT = "lat";
	private static final String JSON_LNG = "lng";
	private static final String JSON_DETAIL= "detail";
	private static final String JSON_VISITED = "visited";
	private static final String JSON_IMAGE = "image";
	

	
	public final UUID mId;
	public String title;
	public double lat;
	public double lng;
	public String detail;
	public boolean visited;
	public int image;
	
	
	public Data(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		title = json.getString(JSON_TITLE);
		lat = json.getDouble(JSON_LAT);
		lng = json.getDouble(JSON_LNG);
		detail = json.getString(JSON_DETAIL);
		visited = json.getBoolean(JSON_VISITED);
		image = json.getInt(JSON_IMAGE);
		
		
	}
	public Data(String _title, 
					double _lat,
					double _lng, 
					String _detail,
					boolean _visited,
					int _image){
		
		mId = UUID.randomUUID();
		title = _title;
		lat = _lat;
		lng = _lng;
		detail = _detail;
		visited = _visited;
		image = _image;
		
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, title);
		json.put(JSON_LAT, lat);
		json.put(JSON_LNG, lng);
		json.put(JSON_DETAIL, detail);
		json.put(JSON_VISITED, visited);
		json.put(JSON_IMAGE,image);
		
		return json;
	}
}