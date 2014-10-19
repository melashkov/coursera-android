package course.labs.activitylab;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityTwo extends Activity {

	// Use these as keys when you're saving state between reconfigurations
	private static final String RESTART_KEY = "restart";
	private static final String RESUME_KEY = "resume";
	private static final String START_KEY = "start";
	private static final String CREATE_KEY = "create";

	// String for LogCat documentation
	private final static String TAG = "Lab-ActivityTwo";

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
		setContentView(R.layout.activity_two);

		// Assign the appropriate TextViews to the TextView variables
		mTvCreate  = (TextView) findViewById(R.id.create);
		mTvRestart = (TextView) findViewById(R.id.start);
		mTvStart   = (TextView) findViewById(R.id.resume);
		mTvResume  = (TextView) findViewById(R.id.restart);

		Button closeButton = (Button) findViewById(R.id.bClose); 
		closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// This function closes Activity Two
				ActivityTwo.this.finish();
			}
		});

		// Has previous state been saved?
		if (savedInstanceState != null) {
			// Restore value of counters from saved state
			mStart   = savedInstanceState.getInt("mStart"  ,0);
			mCreate  = savedInstanceState.getInt("mCreate" ,0);
			mResume  = savedInstanceState.getInt("mResume" ,0);
			mRestart = savedInstanceState.getInt("mRestart",0);
		}

		// Emit LogCat message
		Log.i(TAG, "Entered the onCreate() method");

		mCreate++;
		displayCounts();
	}

	// Lifecycle callback methods overrides

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

		// Emit LogCat message
		Log.i(TAG, "Entered the onDestroy() method");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save counter state information with a collection of key-value pairs
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
