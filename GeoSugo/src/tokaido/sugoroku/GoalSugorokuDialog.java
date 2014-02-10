package tokaido.sugoroku;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GoalSugorokuDialog extends DialogFragment implements OnClickListener{
	 
	TextView mText;
	Button mBtn;
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.goal_sugoroku, container);
	        mText = (TextView) view.findViewById(R.id.goal_sugoroku_description);
	        mBtn = (Button) view.findViewById(R.id.goal_sugoroku_button);
	        mBtn.setOnClickListener(this);
	        getDialog().setTitle(R.string.goal_title);
	       

	        return view;
	    }
	@Override
	public void onClick(View v) {
		
		this.dismiss();
	}
}
