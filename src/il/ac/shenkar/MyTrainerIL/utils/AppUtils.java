package il.ac.shenkar.MyTrainerIL.utils;

import il.ac.shenkar.MyTrainerIL.entities.UserPreferences;

import java.text.DateFormat;
import java.util.Calendar;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AppUtils 
{

	// Convert string to boolean
		public static boolean stringToBoolean(String toConvert)
		{
			return toConvert.equals("true");
		}
		
	// Convert Calendar to informative string
		public static String fromCalendarToString(Calendar calendar)
		{
			DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
			String s = df.format(calendar.getTime());
			df = DateFormat.getTimeInstance(DateFormat.SHORT);
			s += " " + df.format(calendar.getTime());
			return s;
		}
		
		public static void scheduleBoolToString(UserPreferences userPreferences){
			String  schedule  = new String();
			
			if(userPreferences.getSunday()!=null){
				schedule = "1"; 
			} else
				schedule = "0";
			if(userPreferences.getMonday()!=null){
				schedule = schedule + "1"; 
			} else
				schedule = schedule + "0";
			if(userPreferences.getTuesday()!=null){
				schedule = schedule + "1"; 
			} else
				schedule = schedule + "0";
			if(userPreferences.getWednesday()!=null){
				schedule = schedule + "1"; 
			} else
				schedule = schedule + "0";
			if(userPreferences.getThursday()!=null){
				schedule = schedule + "1"; 
			} else
				schedule = schedule + "0";
			if(userPreferences.getFriday()!=null){
				schedule = schedule + "1"; 
			} else
				schedule = schedule + "0";
			if(userPreferences.getSaturday()!=null){
				schedule = schedule + "1"; 
			} else
				schedule = schedule + "0";
			
			userPreferences.setSchedule(schedule);
		}
		
		public void scheduleStringToBool(UserPreferences userPreferences){
			if(userPreferences.getSchedule()!= null){
				String schedule = userPreferences.getSchedule();
				String[] parts = schedule.split("");
				if(parts[0]=="1"){
					userPreferences.setSunday(true);
				} else 
					userPreferences.setSunday(false);
				if(parts[1]=="1"){
					userPreferences.setMonday(true);
				} else 
					userPreferences.setMonday(false);
				if(parts[2]=="1"){
					userPreferences.setTuesday(true);
				} else 
					userPreferences.setTuesday(false);
				if(parts[3]=="1"){
					userPreferences.setWednesday(true);
				} else 
					userPreferences.setWednesday(false);
				if(parts[4]=="1"){
					userPreferences.setTuesday(true);
				} else 
					userPreferences.setThursday(false);
				if(parts[5]=="1"){
					userPreferences.setFriday(true);
				} else 
					userPreferences.setFriday(false);
				if(parts[6]=="1"){
					userPreferences.setSaturday(true);
				} else 
					userPreferences.setSaturday(false);
			}
			
		}
		
	// Set app font
		public static final void setAppFont(ViewGroup mContainer, Typeface mFont)
		{
		    if (mContainer == null || mFont == null) return;

		    final int mCount = mContainer.getChildCount();

		    // Loop through all of the children.
		    for (int i = 0; i < mCount; ++i)
		    {
		        final View mChild = mContainer.getChildAt(i);
		        if (mChild instanceof TextView)
		        {
		            // Set the font if it is a TextView.
		            ((TextView) mChild).setTypeface(mFont);
		        }
		        else if (mChild instanceof ViewGroup)
		        {
		            // Recursively attempt another ViewGroup.
		            setAppFont((ViewGroup) mChild, mFont);
		        }
		    }
		}
	
}