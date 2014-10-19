package course.labs.activitylab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityOne extends Activity {

	// Use these as keys when you're saving state between reconfigurations
	private static final String RESTART_KEY = "restart";
	private static final String RESUME_KEY = "resume";
	private static final String START_KEY = "start";
	private static final String CREATE_KEY = "create";

	// String for LogCat documentation
	private final static String TAG = "Lab-ActivityOne";

	// Lifecycle counters
	private int mCreate  = 0;
	private int mRestart = 0;
	private int mStart   = 0;
	private int mResume  = 0;
	
	// Lifecycle counters views
	private TextView mTvCreate;
	private TextView mTvRestart;
	private TextView mTvStart;
	private TextView mTvResume;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one);

		//assigning views to variables 
		mTvCreate  = (TextView) findViewById(R.id.create);
		mTvRestart = (TextView) findViewById(R.id.start);
		mTvStart   = (TextView) findViewById(R.id.resume);
		mTvResume  = (TextView) findViewById(R.id.restart);

		Button launchActivityTwoButton = (Button) findViewById(R.id.bLaunchActivityTwo);
		launchActivityTwoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Launch Activity Two
				// Hint: use Context's startActivity() method
				// Create an intent stating which Activity you would like to
				// start
				try
				{
					Intent intent = new Intent(ActivityOne.this, ActivityTwo.class);
					startActivity(intent);
				} catch (Exception e){
					Log.e(TAG, e.toString());
				}
			}
		});

		// Has previous state been saved?
		if (savedInstanceState != null) {
			mStart   = savedInstanceState.getInt("mStart"  ,0);
			mCreate  = savedInstanceState.getInt("mCreate" ,0);
			mResume  = savedInstanceState.getInt("mResume" ,0);
			mRestart = savedInstanceState.getInt("mRestart",0);
		}

		Log.i(TAG, "Entered the onCreate() method");
		mCreate++;
		displayCounts();
	}

	// Lifecycle callback overrides

	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "Entered the onStart() method");
		mStart++;
		displayCounts();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "Entered the onResume() method");
		mResume++;
		displayCounts();
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "Entered the onPause() method");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(TAG, "Entered the onStop() method");
	}

	@Override
	public void onRestart() {
		super.onRestart();
		Log.i(TAG, "Entered the onRestart() method");
		mRestart++;
		displayCounts();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Entered the onDestroy() method");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt("mStart", mStart);
		savedInstanceState.putInt("mCreate", mCreate);
		savedInstanceState.putInt("mResume", mResume);
		savedInstanceState.putInt("mRestart", mRestart);
	}

	// Updates the displayed counters
	// This method expects that the counters and TextView variables use the
	// names
	// specified above
	public void displayCounts() {
		mTvCreate.setText("onCreate() calls: " + mCreate);
		mTvStart.setText("onStart() calls: " + mStart);
		mTvResume.setText("onResume() calls: " + mResume);
		mTvRestart.setText("onRestart() calls: " + mRestart);

	}
}
