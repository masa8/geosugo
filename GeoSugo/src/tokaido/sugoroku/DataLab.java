package tokaido.sugoroku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;


import android.content.Context;
import android.widget.Toast;


public class DataLab {

	private static DataLab sDataLab;
	
	private ArrayList<Data> mTokaido;
	private ZoneInfo mZoneInfo;
	
	
	private Context mAppContext;
	private JSONSerializer mSerializer;
	
	public static DataLab get(Context c){
		
		if ( sDataLab == null ){
			sDataLab = new DataLab(c);
		}
		return  sDataLab;
	}
	
	private DataLab(Context c){
		mAppContext = c;
		mSerializer = new JSONSerializer(mAppContext,
				"data.json",
				"data_zinfo.json");

		try { 
			mTokaido = mSerializer.loadData();
			mZoneInfo = mSerializer.loadZoneInfo();
			Toast.makeText(mAppContext,"Data Loaded",Toast.LENGTH_SHORT).show();
			
		} catch (IOException e){
			mTokaido = createTokaido();
			mZoneInfo = createZoneInfo();
		}catch (Exception e){
			mTokaido = createTokaido();
			mZoneInfo = createZoneInfo();
		}
	}
	
	private ZoneInfo createZoneInfo(){
		return new ZoneInfo();
	}
	private ArrayList<Data> createTokaido(){
		
		Data[] data = {
				new Data("浮世絵（神奈川）",
						35.471068,139.626444,
						new StringBuilder()
						.append("東海道五拾三次之内 神奈川　歌川広重\n")
						.toString(),false,
						R.drawable.kanagawa),
				new Data("料亭田中家",
						35.470409,139.623758,
						new StringBuilder()
						.append("文久三年（1863年）創業。坂本龍馬の妻、お龍が仲居として働いていたこともある料亭。")
						.toString()
						,false,
						R.drawable.tanakaya),
						
				new Data("追分（東海道-八王子道）",
						35.459514,139.606479,
						new StringBuilder()
						.append("八王子道は、横浜開港後は「絹の道」と呼ばれました。").toString(),false,
						R.drawable.blank),
						
				new Data("浮世絵（程ケ谷新町入口）",
						35.453434,139.602968,
								new StringBuilder()
								.append("東海道五十三次之内　程ケ谷新町入口　歌川広重（初代）\n")
								.toString(),false,
								R.drawable.n252),
				new Data("浮世絵(境木立場）",35.436665,139.566755,
					new StringBuilder().append("東海道五十三次　六　程ケ谷　境木立場  歌川広重").toString(),
					false,R.drawable.n507),
				new Data("浮世絵（焼餅阪)",
						35.435516,139.566318,
						new StringBuilder()
					.append("東海道五拾三駅　六　丸屋鉄次郎\n")
					.toString(),false,
					R.drawable.n346),
					
				new Data("益田家のモチノキ",
						35.41487,139.543206,
						new StringBuilder().append("「相模モチ」の愛称で親しまれている二本のモチノキ。神奈川県指定天然記念物。").toString(),
						false,R.drawable.mochinoki),
						
				new Data("浮世絵（戸塚図）",
						35.405894,139.537203,
						new StringBuilder().append("東海道五十三次之内　戸塚図  香蝶楼国貞").toString(),
						false,R.drawable.n396),
						
				new Data("追分（金沢鎌倉道）",35.445472,139.59665,
						new StringBuilder().append("保土ヶ谷宿の金沢横町から金沢の六浦陣屋そして朝比奈切通しを経由して鎌倉へと続く道").toString(),
						false,R.drawable.blank),
	};
		
		return new ArrayList<Data>(Arrays.asList(data));
	}
	
	public ArrayList<Data> getDataArray(){
		return mTokaido;
	}
	public Data getData(UUID id){
		
		for ( Data k: mTokaido ){
			if ( k.mId.equals(id)){
				return k;
			}
		}
		
		return null;
	}
	
	public ZoneInfo getZoneInfo(){
		return mZoneInfo;
	}
	public boolean saveModel(){
		try {
			mSerializer.saveData(mTokaido);
			mSerializer.saveZoneInfo(mZoneInfo);
			return true;
		}catch (Exception e){
		
			return false;
		}
	}

}
