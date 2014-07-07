package il.ac.shenkar.MyTrainerIL.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.entities.ExerciseTraining;
import il.ac.shenkar.MyTrainerIL.entities.Training;
import il.ac.shenkar.MyTrainerIL.entities.TrainingPlan;
import il.ac.shenkar.MyTrainerIL.entities.UserPreferences;
import il.ac.shenkar.MyTrainerIL.helper.DatabaseHelper;
import il.ac.shenkar.MyTrainerIL.reminder.ReminderHandler;
import il.ac.shenkar.MyTrainerIL.utils.AppUtils;
import android.content.Context;
import android.util.Log;

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
		int sum = AppUtils.scheduleCount(userPreferences.getSchedule());
		int trainingsNum = 4*sum;
		int training_num = 0;
		List<Integer> scheduleList = AppUtils.scheduleArray(userPreferences.getSchedule(), sum);
		for(int i=0; i<trainingsNum;i++){
			Training training = new Training();
			int days = scheduleList.get(i);
			ExerciseTraining exerciseTrainingPhase2 = null;
			ArrayList<ExerciseTraining> exerciseTrainingList = new ArrayList<ExerciseTraining>();
			long exerciseTrainingId = 0;
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
					exerciseTrainingId = databaseHelper.createExerciseTraining(exerciseTraining);
					exerciseTraining.setId(exerciseTrainingId);
					exerciseTrainingList.add(exerciseTraining);
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
						exerciseTrainingId = databaseHelper.createExerciseTraining(exerciseTraining);
						exerciseTraining.setId(exerciseTrainingId);
						exerciseTrainingList.add(exerciseTraining);
					}
				}
				if((userPreferences.getPhase2()!= null) && (userPreferences.getPhase2()) && exerciseTrainingPhase2 != null){
					exerciseTrainingId = databaseHelper.createExerciseTraining(exerciseTrainingPhase2);
					exerciseTrainingPhase2.setId(exerciseTrainingId);
					exerciseTrainingList.add(exerciseTrainingPhase2);
				}
				training.setExerciseTrainngList(exerciseTrainingList);
				training_num++;
				
				//addReminder(days, training);
				//test reminder
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MINUTE, (int)training_id);
				training.setExecuteDate(calendar.getTimeInMillis());
				training.setExecuteTime(AppUtils.fromCalendarToString(calendar));
				ReminderHandler.setReminder(context, training);
				//end test
				
				
				training.setName(context.getResources().getString(R.string.training_num) + " " + training_num);
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
	
	public void addReminder(int days, Training training){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		training.setExecuteDate(calendar.getTimeInMillis());
		training.setExecuteTime(AppUtils.fromCalendarToString(calendar));
		ReminderHandler.setReminder(context, training);
	}
	
/*	//TODO
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
	}*/
	
	public Boolean deleteUserPrefrences(long user_id){
		if(user_id == 0){
			user_id = databaseHelper.getLogin();
		}
		TrainingPlan trainingPlan = databaseHelper.getTrainingPlanByUser(user_id);
		ArrayList<Training> trainings = trainingPlan.getTrainingList();
		for(Iterator<Training> iterator = trainings.iterator(); iterator.hasNext();){
			Training training = iterator.next();
			databaseHelper.deleteExerciseTraining(training.getId());
			databaseHelper.deleteTraining(training.getId());
			iterator.remove();
		}
		Log.i("delete", "trainings deleted seccesfully");
		databaseHelper.deleteTrainingPlanByUser(user_id);
		return(databaseHelper.deleteUserPreferences(user_id));
	}
}
