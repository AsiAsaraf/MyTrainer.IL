package il.ac.shenkar.MyTrainerIL.dao;

import java.util.ArrayList;
import java.util.Calendar;
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
		ArrayList<Training> trainingList = new ArrayList<Training>();
		ArrayList<ExerciseTraining> userExerciseTrainingList = databaseHelper.getExerciseTrainingArrayListByUserPref(userPreferences);
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
			ExerciseTraining exerciseTrainingPhase2 = null;
			ArrayList<ExerciseTraining> exerciseTrainingList = new ArrayList<ExerciseTraining>();
			long training_id = databaseHelper.createTraining(training);
			training.setId(training_id);
			if (userExerciseTrainingList != null){
				int trainingLength = 0;
				ArrayList<Integer> indexList = new ArrayList<Integer>();
				if((userPreferences.getPhase1()!= null) && (userPreferences.getPhase1())){
					ArrayList<ExerciseTraining> exerciseTrainingArrayListByPhase1 = databaseHelper.getExerciseTrainingArrayListByPhase1(userPreferences);
					Random randomGenerator = new Random();
					int index = randomGenerator.nextInt(exerciseTrainingArrayListByPhase1.size());
					ExerciseTraining exerciseTraining = exerciseTrainingArrayListByPhase1.get(index);
					trainingLength += exerciseTraining.getExercise().getLength();
					exerciseTraining.setTrainingId(training.getId());
					exerciseTrainingList.add(exerciseTraining);
					databaseHelper.createExerciseTraining(exerciseTraining);
				}
				if((userPreferences.getPhase2()!= null) && (userPreferences.getPhase2())){
					ArrayList<ExerciseTraining> exerciseTrainingArrayListByPhase2 = databaseHelper.getExerciseTrainingArrayListByPhase2(userPreferences);
					Random randomGenerator = new Random();
					int index = randomGenerator.nextInt(exerciseTrainingArrayListByPhase2.size());
					exerciseTrainingPhase2 = exerciseTrainingArrayListByPhase2.get(index);
					trainingLength += exerciseTrainingPhase2.getExercise().getLength();
					exerciseTrainingPhase2.setTrainingId(training.getId());
				}
				while (userPreferences.getLength() >= trainingLength){
					Random randomGenerator = new Random();
					int index = randomGenerator.nextInt(userExerciseTrainingList.size());
					if((indexList == null) || (indexList.isEmpty()) || (!indexList.contains(index))){
						ExerciseTraining exerciseTraining = userExerciseTrainingList.get(index);
						trainingLength += exerciseTraining.getExercise().getLength();
						exerciseTraining.setTrainingId(training.getId());
						indexList.add(index);
						exerciseTrainingList.add(exerciseTraining);
						databaseHelper.createExerciseTraining(exerciseTraining);
					}
				}
				if((userPreferences.getPhase2()!= null) && (userPreferences.getPhase2()) && exerciseTrainingPhase2 != null){
					exerciseTrainingList.add(exerciseTrainingPhase2);
					databaseHelper.createExerciseTraining(exerciseTrainingPhase2);
				}
				training.setExerciseTrainngList(exerciseTrainingList);
				//TODO - execute time + reminder
				training.setName(context.getResources().getString(R.string.training_num) + " " + training.getId());
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
	//TODO
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
}
