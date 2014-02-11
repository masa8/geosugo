package tokaido.sugoroku;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class PlaySugorokuDialog extends SherlockDialogFragment implements OnClickListener{
	 
	TextView mText;
	Button mBtn;
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.play_sugoroku, container);
	        mText = (TextView) view.findViewById(R.id.play_sugoroku_description);
	        mBtn = (Button) view.findViewById(R.id.play_sugoroku_button);
	        mBtn.setOnClickListener(this);
	        getDialog().setTitle("さいころをふる");
	       

	        return view;
	    }
	@Override
	public void onClick(View v) {
		int number = 1 + (int) (Math.random() * 6);
		Intent i = new Intent();
		i.putExtra("SAIKORO_RESULT", number);
		getTargetFragment().onActivityResult(getTargetRequestCode(), 0,i);
		
		Toast.makeText(getActivity(), 
				"進めるマスは" + Integer.valueOf(number).toString() + "マスです",
				Toast.LENGTH_LONG).show();
		
		
		this.dismiss();
	}
}
