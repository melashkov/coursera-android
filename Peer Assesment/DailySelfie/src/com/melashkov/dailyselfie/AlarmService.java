package com.melashkov.dailyselfie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmService {

	private Context mContext;

	private static final long ALARM_INTERVAL = 2 * 60 * 1000;

	private PendingIntent mPendingIntent;

	private AlarmManager mAlarmManager;

	public AlarmService(Context context) {
		mContext = context;
        mPendingIntent = PendingIntent.getBroadcast(mContext, 0, new Intent(mContext, AlarmReceiver.class), 0);
	}

	public void setRepeatingAlarm() {
		// Get the AlarmManager Service
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(
        		AlarmManager.ELAPSED_REALTIME_WAKEUP, 
        		ALARM_INTERVAL, 
        		ALARM_INTERVAL, 
        		mPendingIntent);
		
		// Show Toast message
		Toast.makeText(
				mContext, 
				"Repeating Alarm Set", 
				Toast.LENGTH_LONG
				).show();
	}

	public void cancelRepeatingAlarm() {
		// Cancel all alarms using mNotificationReceiverPendingIntent
		mAlarmManager.cancel(mPendingIntent);

		// Show Toast message
		Toast.makeText(mContext,"Repeating Alarms Cancelled", Toast.LENGTH_LONG).show();
	}

}
