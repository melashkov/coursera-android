package com.melashkov.dailyselfie;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;



public class ViewImageActivity extends Activity{

	protected ImageView mImageView;
	
	public static final String EXTRA_PATH = "path";
	
	static final String TAG = "Selfie - ViewImage : ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		Log.d(TAG, "ViewImage created");
		super.onCreate(savedInstanceState);
		Log.d(TAG, "setting content view");
		setContentView(R.layout.activity_view_image);
		Log.d(TAG, "getting image view");
		mImageView = (ImageView) findViewById(R.id.full_screen_image);
		Log.d(TAG, "getting path");
		String path = getIntent().getStringExtra(EXTRA_PATH);
		Log.d(TAG, "path "+ path);
		new LoadBitmapTask(mImageView).execute(path);
	}
	
	
	class LoadBitmapTask extends AsyncTask<String, String, Bitmap> {
		
		public LoadBitmapTask(ImageView imageView) {
			mImageView = imageView;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			String path = params[0];
			return BitmapFactory.decodeFile(path);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			mImageView.setImageBitmap(result);
		}
		
	}
	
}
