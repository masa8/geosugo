package tokaido.sugoroku;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class LicenseDialog extends SherlockDialogFragment {

	TextView mText;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Html.ImageGetter imggetter = new Html.ImageGetter(){

			@Override
			public Drawable getDrawable(String source) {
				
				try {
	                InputStream is = new URL(source).openStream();
	                Bitmap bitmap = BitmapFactory.decodeStream(is);
	                BitmapDrawable d = new BitmapDrawable(bitmap);
	                d.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
	                d.setLevel(1);
	                return d;
	               } catch (FileNotFoundException e) {
	                e.printStackTrace();
	                Drawable d = getResources().getDrawable(R.drawable.ccbysa88x31);
	                return d;
				} catch (MalformedURLException e) {
	                e.printStackTrace();
	                Drawable d = getResources().getDrawable(R.drawable.ccbysa88x31);
	                return d;
				} catch (IOException e) {
	                e.printStackTrace();
	                Drawable d = getResources().getDrawable(R.drawable.ccbysa88x31);
	                return d;
				}
				
			}
			
			
		};
			
			View view = inflater.inflate(R.layout.license_dialog, container);
	        mText = (TextView) view.findViewById(R.id.license_dialog_view);
	        mText.setMovementMethod(LinkMovementMethod.getInstance());
	        getDialog().setTitle(getResources().getString(R.string.license_title));
	        mText.setText(Html.fromHtml(
	        					getResources().getString(R.string.license_tag_description),
	        					imggetter,
	        					null)
	        					);
	        
		return view;
	}

}
