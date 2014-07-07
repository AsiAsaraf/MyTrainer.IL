package il.ac.shenkar.MyTrainerIL.reminder;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.activity.TrainingActivity;
import android.app.*;
import android.content.*;
import android.support.v4.app.NotificationCompat;

public class ReminderBroadcastReceiver extends BroadcastReceiver
{
	// on receive method
		public void onReceive(Context context, Intent intent)
		{
			Intent myIntent = new Intent(context, TrainingActivity.class);
			long training_id = intent.getLongExtra("training", 0);
			myIntent.putExtra("training", training_id);
			PendingIntent pendingIntent = null;
			pendingIntent = PendingIntent.getActivity(context, (int)training_id, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
			
			// build a notification
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new NotificationCompat.Builder(context).setContentTitle("MyTrainer.IL").setContentText(context.getResources().getString(R.string.training)).setSmallIcon(R.drawable.megaphone).setContentIntent(pendingIntent).build();
		    notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
			notificationManager.notify((int)training_id, notification);	
		}
}