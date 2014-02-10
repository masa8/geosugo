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
				new Data("�����G�i�_�ސ�j",
						35.471068,139.626444,
						new StringBuilder()
						.append("���C���܏E�O���V�� �_�ސ�@�̐�L�d\n")
						.toString(),false,
						R.drawable.kanagawa),
				new Data("�����c����",
						35.470409,139.623758,
						new StringBuilder()
						.append("���v�O�N�i1863�N�j�n�ƁB��{���n�̍ȁA�����������Ƃ��ē����Ă������Ƃ����闿���B")
						.toString()
						,false,
						R.drawable.tanakaya),
						
				new Data("�Ǖ��i���C��-�����q���j",
						35.459514,139.606479,
						new StringBuilder()
						.append("�����q���́A���l�J�`��́u���̓��v�ƌĂ΂�܂����B").toString(),false,
						R.drawable.blank),
						
				new Data("�����G�i���P�J�V�������j",
						35.453434,139.602968,
								new StringBuilder()
								.append("���C���܏\�O���V���@���P�J�V�������@�̐�L�d�i����j\n")
								.toString(),false,
								R.drawable.n252),
				new Data("�����G(���ؗ���j",35.436665,139.566755,
					new StringBuilder().append("���C���܏\�O���@�Z�@���P�J�@���ؗ���  �̐�L�d").toString(),
					false,R.drawable.n507),
				new Data("�����G�i�Ėݍ�)",
						35.435516,139.566318,
						new StringBuilder()
					.append("���C���܏E�O�w�@�Z�@�ۉ��S���Y\n")
					.toString(),false,
					R.drawable.n346),
					
				new Data("�v�c�Ƃ̃��`�m�L",
						35.41487,139.543206,
						new StringBuilder().append("�u���̓��`�v�̈��̂Őe���܂�Ă����{�̃��`�m�L�B�_�ސ쌧�w��V�R�L�O���B").toString(),
						false,R.drawable.mochinoki),
						
				new Data("�����G�i�˒ː}�j",
						35.405894,139.537203,
						new StringBuilder().append("���C���܏\�O���V���@�˒ː}  �����O����").toString(),
						false,R.drawable.n396),
						
				new Data("�Ǖ��i���򊙑q���j",35.445472,139.59665,
						new StringBuilder().append("�ۓy���J�h�̋��򉡒��������̘Z�Y�w�������Ē���ސؒʂ����o�R���Ċ��q�ւƑ�����").toString(),
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
