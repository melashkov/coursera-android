package com.melashkov.dailyselfie;

import java.io.File;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

	static final String TAG = "Selfie - MainActivity : ";


	static final int REQUEST_IMAGE_CAPTURE = 1;

	long mLastSelfieTime;

	String mCurrentPhotoPath;
	
	private ListViewAdapter mListViewAdapter;
	
	private StorageUtil mStorageUtil;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mStorageUtil = new StorageUtil();
		
		ListView listView = (ListView) findViewById(R.id.listView);
        if (listView != null) {
            mListViewAdapter = new ListViewAdapter(this);
            listView.setAdapter(mListViewAdapter);
        }
        mListViewAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		MenuItem takeSelfire = menu.findItem(R.id.action_take_selfire);
		takeSelfire.setIcon(R.drawable.ic_action_photo);
		takeSelfire.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_take_selfire) {
			dispatchTakePictureIntent();
			return true;
		}else if(id == R.id.action_delete_all_selfire)
		{
			mStorageUtil.deleteAllImages();
			mListViewAdapter.reloadContent();
		}
		return super.onOptionsItemSelected(item);
	}

	private void dispatchTakePictureIntent() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File photoFile = null;
		try {
			photoFile = mStorageUtil.createImageFile();
			mCurrentPhotoPath = photoFile.getAbsolutePath();
		} catch (IOException ex) {
			// Error occurred while creating the File
			Log.d(TAG, "");
		}
		// Continue only if the File was successfully created
		if (photoFile != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
			startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
			File f = new File( mCurrentPhotoPath );
			
			Log.d(TAG, mCurrentPhotoPath);
			
			mStorageUtil.saveThumbnail( f );
			Selfie selfie = new Selfie( f.lastModified(),  f.getAbsolutePath() );
			mListViewAdapter.add(selfie);
		}
	}
	
	public void showImage(String path )
	{
		Intent intent = new Intent(this, ViewImageActivity.class);
		intent.putExtra( ViewImageActivity.EXTRA_PATH,path);
		startActivity(intent);
		
        
	}

}
