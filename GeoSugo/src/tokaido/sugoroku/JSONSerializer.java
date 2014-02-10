package tokaido.sugoroku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;

class JSONSerializer {
	private Context mContext;
	private String mFilename;
	private String mInfoFilename;
	public JSONSerializer(Context c, String f,String fz){
		mContext = c;
		mFilename = f;
		mInfoFilename = fz;
	}
	
	public ArrayList<Data> loadData() throws IOException,
	JSONException {
		
		ArrayList<Data> data = new ArrayList<Data>();
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null ){
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			for ( int i = 0; i < array.length(); i++ ){
				data.add(new Data(array.getJSONObject(i)));
			}
		}catch (FileNotFoundException e){
			throw e;
		}finally {
			if ( reader != null ){
				reader.close();
			}
		}
		
		return data;
	}
	
	public ZoneInfo loadZoneInfo() throws IOException,
	JSONException {
		
		ZoneInfo zone_info = null;
		BufferedReader reader = null;
		
		try {
			InputStream in = mContext.openFileInput(mInfoFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null ){
				jsonString.append(line);
			}
			
			JSONObject object = new JSONObject().getJSONObject(jsonString.toString());
				zone_info = new ZoneInfo(object);
				
		}catch (FileNotFoundException e){
			throw e;
		
		}finally {
			if ( reader != null ){
				reader.close();
			}
		}
		
		return zone_info;
	}
	
	
	public void saveData(ArrayList<Data> data)
	throws JSONException, IOException {
		JSONArray array = new JSONArray();
		for (Data c: data){
			array.put(c.toJSON());
		}
		
		Writer writer = null;
		try{
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		}finally{
			if (writer != null){
				writer.close();
			}
		}
	}
	public void saveZoneInfo(ZoneInfo data)
	throws JSONException, IOException {
		JSONObject object = data.toJSON();
		
		Writer writer = null;
		try{
			OutputStream out = mContext.openFileOutput(mInfoFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(object.toString());
		}finally{
			if (writer != null){
				writer.close();
			}
		}
	}
}