package tokaido.sugoroku;

import org.json.JSONException;
import org.json.JSONObject;

class ZoneInfo {

		public enum Status {
			READY,
			PLAY,
			DONE;
			public static Status valueOf(int x){
				switch (x){
				case 0:
					return READY;
				case 1:
					return PLAY;
				case 2:
					return DONE;
				}
				return null;
			}
		};
		
		private static final String JSON_APP_STATUS = "app_status";
		private static final String JSON_CURRENT_ZONE = "current_zone";
		private static final String JSON_ACTIVITY_ZONE = "activity_zone";
		
		private Status mStatus; 
		private int mCurrentZoneIndex;
		private int mActivityZoneIndex;
		
		public ZoneInfo(JSONObject json) throws JSONException {
			mStatus = Status.valueOf(json.getInt(JSON_APP_STATUS));
			mCurrentZoneIndex = json.getInt(JSON_CURRENT_ZONE);
			mActivityZoneIndex = json.getInt(JSON_ACTIVITY_ZONE);
		}
		
		public ZoneInfo(){
			mStatus = Status.READY;
			mCurrentZoneIndex = -1;
			mActivityZoneIndex = -1;
		}
		
		public ZoneInfo(Status status,
						int current_zone_index,
						int activity_zone_index){
			mStatus = status;
			mCurrentZoneIndex = current_zone_index;
			mActivityZoneIndex = activity_zone_index;
		}
			
		public JSONObject toJSON() throws JSONException {
			JSONObject json = new JSONObject();
			json.put(JSON_APP_STATUS, mStatus);
			json.put(JSON_CURRENT_ZONE, mCurrentZoneIndex);
			json.put(JSON_ACTIVITY_ZONE, mActivityZoneIndex);				
			return json;
		}
		
		public void updateStatus(Status status){
			mStatus = status;
		}
		public Status getStatus(){
			return mStatus;
		}
		
		public int getCurrentZoneIndex(){
			return mCurrentZoneIndex;
		}

		public int getActivityZoneIndex(){
			return mActivityZoneIndex;
		}
		public void setCurrentZoneIndex(int index){
			mCurrentZoneIndex = index;
		}

		public void setActivityZoneIndex(int index){
			mActivityZoneIndex = index;
		}
}