package tokaido.sugoroku;



import java.util.UUID;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class DescriptionFragment extends Fragment {
	public static final String ID = "ID";
	
	private Data data;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		UUID id = (UUID)getActivity().getIntent()
				.getSerializableExtra(ID);
		

		data = DataLab.get(getActivity()).getData(id);
			
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.infowindow, container,false);
		

		getActivity().setTitle(data.title);
		ImageView iv = (ImageView)v.findViewById(R.id.photo);
		iv.setImageResource(data.image);
		
		TextView dt = (TextView)v.findViewById(R.id.info);
		dt.setText(data.detail);
		
		CheckBox cb = (CheckBox)v.findViewById(R.id.visited);
		cb.setChecked(data.visited);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				data.visited = isChecked;
			}
			
		});
		

		return v;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
