package il.ac.shenkar.MyTrainerIL.reminder;


import il.ac.shenkar.MyTrainerIL.entities.Training;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class ReminderHandler
{
	
	// intent for a reminder by time
			public final static String ALARM_INTENT = "il.ac.shenkar.MyTrainerIL.reminder.reminderByTime_broadcast";
			
	// create time reminder
		public static void setReminder(Context context, Training training)
		{
			
			
			Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
			intent.putExtra("training", training.getId());
	
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) training.getId(), intent, 0);
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, training.getExecuteDate().getTimeInMillis(), pendingIntent);
		}
}