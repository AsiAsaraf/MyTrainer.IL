package il.ac.shenkar.MyTrainerIL.reminder;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;

public class ReminderHandler
{
	// define extra information
	public final static String EXTRA_NAME = "il.ac.shenkar.MyTrainerIL.NAME";	
		public final static String EXTRA_ID = "il.ac.shenkar.MyTrainerIL.ID";
		public final static String EXTRA_ENTERING = "il.ac.shenkar.MyTrainerIL.ENTERING";
		
	// define the interval time between requestLocationUpdates()
		public final static long MIN_TIME = 1000 * 360 * 60;	
	// define the minimum distance for requestLocationUpdates()
		public final static float MIN_DISTANCE = 300;		
		
		// intent for a reminder by time
		public final static String ALARM_INTENT = "il.ac.shenkar.MyTrainerIL.reminderByTime_broadcast";
		
/*	// create time reminder
		public static void setReminder(Context context, TaskItem taskItem)
		{
			Intent intent = new Intent(ALARM_INTENT);
			intent.putExtra(EXTRA_NAME, taskItem.getName());
			intent.putExtra(EXTRA_ID, (int) taskItem.getID());
	
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) taskItem.getID(), intent, 0);
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, taskItem.getReminderDate().getTimeInMillis(), pendingIntent);
		}
	
	// cancel time reminder
		public static void cancelReminder(Context context, long id)
		{
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) id, new Intent(ALARM_INTENT), 0);
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarmManager.cancel(pendingIntent);		
		}*/
	
}