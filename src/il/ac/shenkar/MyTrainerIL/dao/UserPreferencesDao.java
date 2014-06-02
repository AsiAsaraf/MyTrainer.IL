package il.ac.shenkar.MyTrainerIL.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.entities.ExerciseTraining;
import il.ac.shenkar.MyTrainerIL.entities.Training;
import il.ac.shenkar.MyTrainerIL.entities.TrainingPlan;
import il.ac.shenkar.MyTrainerIL.entities.UserPreferences;
import il.ac.shenkar.MyTrainerIL.helper.DatabaseHelper;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.widget.Toast;

public class UserPreferencesDao {

	private DatabaseHelper databaseHelper;
	private Context context;
	
	/**
	 * @param context
	 */
	public UserPreferencesDao(Context context){
		databaseHelper = new DatabaseHelper(context);
		this.context = context;
	}
	
	public long createUserPrefrences(UserPreferences userPreferences) {
		long user_pref_id = databaseHelper.createUserPreferences(userPreferences);
		if(user_pref_id != 0 ) {
			return user_pref_id;
		} else {
			return 0;
		}
		
	}
	
	public TrainingPlan calcTrainingPlan(UserPreferences userPreferences){
		TrainingPlan trainingPlan = new TrainingPlan();
		trainingPlan.setName(context.getResources().getString(R.string.training_plan_name));
		trainingPlan.setUser(databaseHelper.getUser(userPreferences.getUserId()));
		long trainingPlan_id = databaseHelper.createTrainingPlan(trainingPlan);
		trainingPlan.setId(trainingPlan_id);
		List<Training> trainingList = new ArrayList<Training>();
		List<ExerciseTraining> userExerciseTrainingList = databaseHelper.getExerciseTrainingListByUserPref(userPreferences);
		String schedule = userPreferences.getSchedule();
		String[] s1 = schedule.split("");
		int sum = 0;
		for(int i=1;i<schedule.length();i++){
			if(s1[i] != null || !s1[i].isEmpty()){
			sum+=Integer.parseInt(s1[i]);
			}
		}
		int trainingsNum = 4*sum;
		for(int i=0; i<=trainingsNum;i++){
			Training training = new Training();
			List<ExerciseTraining> exerciseTrainingList = new ArrayList<ExerciseTraining>();
			training.setName(context.getResources().getString(R.string.training_num));
			long training_id = databaseHelper.createTraining(training);
			training.setId(training_id);
			if (userExerciseTrainingList != null){
				int trainingLength = 0;
				while (userPreferences.getLength() >= trainingLength){
					Random randomGenerator = new Random();
					int index = randomGenerator.nextInt(userExerciseTrainingList.size());
					ExerciseTraining exerciseTraining = userExerciseTrainingList.get(index);
					trainingLength += exerciseTraining.getExercise().getLength();
					exerciseTraining.setTraining(training);
					exerciseTrainingList.add(exerciseTraining);
					databaseHelper.createExerciseTraining(exerciseTraining);
				}
			training.setExerciseTrainngList(exerciseTrainingList);
			training.setLength(trainingLength);
			training.setTrainingPlanId(trainingPlan.getId());
			databaseHelper.updateTraining(training);
			trainingList.add(training);
		}
			
		}
		trainingPlan.setTrainingList(trainingList);
		databaseHelper.updateTrainingPlan(trainingPlan);
		return trainingPlan;
	}
	
	public void addCalendarEvent(Context context, UserPreferences userPreferences) {
		Calendar beginTime = Calendar.getInstance();
	    beginTime.set(2013, 12, 20, 7, 30);
	    Calendar endTime = Calendar.getInstance();
	    endTime.set(2013, 12, 20, 8, 30);

	    ContentValues calEvent = new ContentValues();
	    calEvent.put(CalendarContract.Events.CALENDAR_ID, 1);
	    calEvent.put(CalendarContract.Events.TITLE, "title is game time");
	    calEvent.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
	    calEvent.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
	    calEvent.put(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=20;BYDAY=MO,TU,WE,TH,FR;WKST=MO");

	    calEvent.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
	    Uri uri = context.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, calEvent);

	    // The returned Uri contains the content-retriever URI for 
	    // the newly-inserted event, including its id
	    int id = Integer.parseInt(uri.getLastPathSegment());
	    Toast.makeText(context, "Created Calendar Event " + id, Toast.LENGTH_SHORT).show();
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
