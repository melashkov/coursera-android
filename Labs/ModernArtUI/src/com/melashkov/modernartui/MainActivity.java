package com.melashkov.modernartui;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private SeekBar mSeekBar = null;
	
	private HashMap<String, View> viewMap = null;
	
	private static final String TAG = "Modern-UI";
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewMap=new HashMap<String, View>();
		viewMap.put("view1", findViewById(R.id.view1) );
		viewMap.put("view2", findViewById(R.id.view2) );
		viewMap.put("view3", findViewById(R.id.view3) );
		//viewMap.put("view4", findViewById(R.id.view4) );
		viewMap.put("view5", findViewById(R.id.view5) );
		
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		
		mSeekBar.setOnSeekBarChangeListener( new OnSeekBarChangeListener() {
			int progressChanged = 0;

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				progressChanged = progress;
				mutateColor(10);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}
	
	protected void mutateColor(int value)
	{
		ColorDrawable viewBackground = null;
		for (Object key : viewMap.keySet()) {
			viewBackground = (ColorDrawable) viewMap.get(key).getBackground();
			Log.i(TAG, "viewBackground.getColor() "+ key + viewBackground.getColor());
			viewMap.get(key).setBackgroundColor(viewBackground.getColor() + value);
		}
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_more_information) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.dialog_text);
            // Add the buttons
            builder.setPositiveButton(R.string.visit_moma, new DialogInterface.OnClickListener() {
                @Override
				public void onClick(DialogInterface dialog, int id) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moma.org"));
                    startActivity(browserIntent);
                }
            });
            builder.setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                @Override
				public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
	
}
