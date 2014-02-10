package tokaido.sugoroku;

import android.support.v4.app.Fragment;

public class DescriptionActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new DescriptionFragment();
	}

}
