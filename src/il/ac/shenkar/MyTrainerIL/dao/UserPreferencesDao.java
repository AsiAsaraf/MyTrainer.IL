package il.ac.shenkar.MyTrainerIL.dao;

import il.ac.shenkar.MyTrainerIL.entities.UserPreferences;
import il.ac.shenkar.MyTrainerIL.helper.DatabaseHelper;
import android.content.Context;

public class UserPreferencesDao {

	private DatabaseHelper databaseHelper;
	
	/**
	 * @param context
	 */
	public UserPreferencesDao(Context context){
		databaseHelper = new DatabaseHelper(context);
	}
	
	public Boolean createUserPrefrences(UserPreferences userPreferences) {
		long user_id = databaseHelper.createUserPreferences(userPreferences);
		if(user_id != 0 ) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public void scheduleBoolToString(UserPreferences userPreferences){
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
}
