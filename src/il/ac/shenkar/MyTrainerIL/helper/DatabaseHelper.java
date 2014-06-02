package il.ac.shenkar.MyTrainerIL.helper;

import il.ac.shenkar.MyTrainerIL.entities.Exercise;
import il.ac.shenkar.MyTrainerIL.entities.ExerciseTraining;
import il.ac.shenkar.MyTrainerIL.entities.Training;
import il.ac.shenkar.MyTrainerIL.entities.TrainingPlan;
import il.ac.shenkar.MyTrainerIL.entities.User;
import il.ac.shenkar.MyTrainerIL.entities.UserPreferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private Context context;
	
	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "MyTrainerIL";

	// Table Names
	private static final String TABLE_EXERCISE = "exercise";
	private static final String TABLE_USER = "user";
	private static final String TABLE_TRAINING = "training";
	private static final String TABLE_TRAINING_PLAN = "training_plan";
	private static final String TABLE_EXERCISE_TRAINING = "exercise_training";
	private static final String TABLE_USER_PREFERENCES = "user_preferences";
	private static final String TABLE_LOGIN = "login";

	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_LENGTH = "length";
	private static final String KEY_DONE_FLAG = "done_flag";
	private static final String KEY_REMINDER = "reminder";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_UPDATED_AT = "updated_at";

	// USER Table - column names
	private static final String KEY_FIRST_NAME = "first_name";
	private static final String KEY_LAST_NAME = "last_name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASS = "pass";
	
	// EXERCISE Table - column names
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_DIFFICULTY = "difficulty";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_GOAL1 = "goal1";
	private static final String KEY_GOAL2 = "goal2";
	private static final String KEY_GOAL3 = "goal3";
 	private static final String KEY_IMAGE = "image";

	// TRAINING Table - column names
	private static final String KEY_TRAINING_PLAN_ID = "training_plan_id";
	private static final String KEY_EXECUTE_TIME = "execute_time";

	// TRAINING_PLAN Table - column names
	// AND
	// USER_PREFERENCES Table - column names
	private static final String KEY_USER_ID = "user_id";
	
	// USER_PREFERENCES Table - column names
	private static final String KEY_GOAL = "goal";
	private static final String KEY_LEVEL = "level";
	private static final String KEY_MUSCLE_FOCUS = "muscle_focus";
	private static final String KEY_PHASE1 = "phase1";
	private static final String KEY_PHASE2 = "phase2";
	private static final String KEY_SCHEDULE = "schedule";
	private static final String KEY_WORKOUT_TIME = "workout_time";
	
	// EXERCISE_TRAINING Table - column names
	private static final String KEY_EXERCISE_ID = "exercise_id";
	private static final String KEY_TRAINING_ID = "training_id";
			
	// Table Create Statements
	// Exercise table create statement
	private static final String CREATE_TABLE_EXERCISE = "CREATE TABLE "
			+ TABLE_EXERCISE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_NAME + " TEXT," 
			+ KEY_DESCRIPTION + " TEXT," 
			+ KEY_DIFFICULTY + " TEXT," 
			+ KEY_CATEGORY + " TEXT," 
			+ KEY_LENGTH + " TEXT,"
			+ KEY_GOAL1 + " INTEGER,"
			+ KEY_GOAL2 + " INTEGER,"
			+ KEY_GOAL3 + " INTEGER,"
			+ KEY_IMAGE + " TEXT" + ")";

	// Training table create statement
	private static final String CREATE_TABLE_TRAINING = "CREATE TABLE "
			+ TABLE_TRAINING + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_TRAINING_PLAN_ID + " INTEGER," 
			+ KEY_NAME + " TEXT," 
			+ KEY_EXECUTE_TIME + " DATETIME,"
			+ KEY_LENGTH + " INTEGER," 
			+ KEY_REMINDER + " TEXT,"
			+ KEY_DONE_FLAG + " INTEGER," 
			+ KEY_CREATED_AT + " DATETIME" + ")";

	// TrainingPlan table create statement
	private static final String CREATE_TABLE_TRAINING_PLAN = "CREATE TABLE "
			+ TABLE_TRAINING_PLAN + "(" + KEY_ID + " INTEGER PRIMARY KEY," 
			+ KEY_NAME + " TEXT,"
			+ KEY_USER_ID + " INTEGER," 
			+ KEY_CREATED_AT + " DATETIME" + ")";
	
	// ExerciseTraining table create statement 
	private static final String CREATE_TABLE_EXERCISE_TRAINING = "CREATE TABLE "
			+ TABLE_EXERCISE_TRAINING + "(" + KEY_ID + " INTEGER PRIMARY KEY," 
			+ KEY_EXERCISE_ID + " INTEGER,"
			+ KEY_TRAINING_ID + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	// User table create statement
	private static final String CREATE_TABLE_USER = "CREATE TABLE "
			+ TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_FIRST_NAME + " TEXT," 
			+ KEY_LAST_NAME + " TEXT," 
			+ KEY_EMAIL + " TEXT," 
			+ KEY_PASS + " TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";
	
	// UserPreferences table create statement
	private static final String CREATE_TABLE_USER_PREFERENCES = "CREATE TABLE "
			+ TABLE_USER_PREFERENCES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_USER_ID + " INTEGER," 
			+ KEY_GOAL + " INTEGER," 
			+ KEY_LENGTH + " INTEGER," 
			+ KEY_LEVEL + " TEXT," 
			+ KEY_MUSCLE_FOCUS + " TEXT,"
			+ KEY_PHASE1 + " INTEGER," 
			+ KEY_PHASE2 + " INTEGER," 			
			+ KEY_SCHEDULE + " TEXT,"
			+ KEY_WORKOUT_TIME + " TEXT," 
			+ KEY_REMINDER + " TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";
	
	// Login table create statement
	private static final String CREATE_TABLE_LOGIN = "CREATE TABLE "
			+ TABLE_LOGIN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_USER_ID + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME," 
			+ KEY_UPDATED_AT + " DATETIME" + ")";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	
	private Context getContext(){
		return context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		Context context = this.getContext();
		// creating required tables
		db.execSQL(CREATE_TABLE_EXERCISE);
		db.execSQL(CREATE_TABLE_EXERCISE_TRAINING);
		db.execSQL(CREATE_TABLE_TRAINING);
		db.execSQL(CREATE_TABLE_TRAINING_PLAN);
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_USER_PREFERENCES);
		db.execSQL(CREATE_TABLE_LOGIN);
		Log.i("DatabaseHelper", "Tables created seccesfully");
		
		// load data
        AssetManager assetManager = context.getAssets();
        try {
                InputStream inputStream = assetManager.open("exercise.csv");
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String line;
                ContentValues values;
                while ((line = bufferedReader.readLine()) != null) {
                	String[] rowData = line.split(",");
                	if (rowData.length == 10) {
                        values = new ContentValues();
                        //Record exercise found, now get values and insert record
                		values.put(KEY_NAME, rowData[1]);
                		values.put(KEY_DESCRIPTION, rowData[2]);
                		values.put(KEY_DIFFICULTY, rowData[3]);
                		values.put(KEY_CATEGORY, rowData[4]);
                		values.put(KEY_LENGTH, rowData[5]);
                		values.put(KEY_GOAL1, rowData[6]);
                		values.put(KEY_GOAL2, rowData[7]);
                		values.put(KEY_GOAL3, rowData[8]);
                		values.put(KEY_IMAGE, rowData[9]);
                        db.insert(TABLE_EXERCISE, null, values);   
                	}
                }
                Log.i("DatabaseHelper", "CSV file loaded seccesfully");

        } catch (IOException e) {
                Log.e("DatabaseHelper", "Failed to open data input file");
                e.printStackTrace();
        }
        
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE_TRAINING);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_PLAN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PREFERENCES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "Exercise" table methods ----------------//

	/**
	 * Create an exercise
	 */
	public long createExercise(Exercise exercise) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, exercise.getName());
		values.put(KEY_DESCRIPTION, exercise.getDescription());
		values.put(KEY_DIFFICULTY, exercise.getDifficulty());
		values.put(KEY_CATEGORY, exercise.getCategory());
		values.put(KEY_LENGTH, exercise.getLength());
		values.put(KEY_GOAL1, exercise.getGoal1());
		values.put(KEY_GOAL2, exercise.getGoal2());
		values.put(KEY_GOAL3, exercise.getGoal3());
		values.put(KEY_IMAGE, exercise.getImage());

		// insert row
		long exercise_id = db.insert(TABLE_EXERCISE, null, values);
		
		Log.i("DatabaseHelper", "Exercise: " + exercise_id + " created seccesfully");

		return exercise_id;
	}

	/**
	 * Get an exercise
	 */
	public Exercise getExercise(long exercise_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_EXERCISE + " WHERE "
				+ KEY_ID + " = " + exercise_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Exercise exercise = new Exercise();
		exercise.setId(c.getLong(c.getColumnIndex(KEY_ID)));
		exercise.setName(c.getString(c.getColumnIndex(KEY_NAME)));
		exercise.setDifficulty(c.getString(c.getColumnIndex(KEY_DIFFICULTY)));
		exercise.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
		exercise.setLength(c.getInt(c.getColumnIndex(KEY_LENGTH)));
		exercise.setGoal1(c.getInt(c.getColumnIndex(KEY_GOAL1))>0);
		exercise.setGoal2(c.getInt(c.getColumnIndex(KEY_GOAL2))>0);
		exercise.setGoal3(c.getInt(c.getColumnIndex(KEY_GOAL3))>0);
		exercise.setImage(c.getString(c.getColumnIndex(KEY_IMAGE)));
		
		Log.i("DatabaseHelper", "Exercise: " + exercise_id + " found seccesfully");

		return exercise;
	}
	
	/**
	 * Update an exercise
	 */
	public int updateExercise(Exercise exercise) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, exercise.getName());
		values.put(KEY_DESCRIPTION, exercise.getDescription());
		values.put(KEY_DIFFICULTY, exercise.getDifficulty());
		values.put(KEY_CATEGORY, exercise.getCategory());
		values.put(KEY_LENGTH, exercise.getLength());
		values.put(KEY_GOAL1, exercise.getGoal1());
		values.put(KEY_GOAL2, exercise.getGoal2());
		values.put(KEY_GOAL3, exercise.getGoal3());				
		values.put(KEY_IMAGE, exercise.getImage());
		
		Log.i("DatabaseHelper", "Exercise: " + exercise.getId() + " updated seccesfully");

		// updating row
		return db.update(TABLE_EXERCISE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(exercise.getId()) });
	}
	
	/**
	 * Delete an exercise
	 */
	public void deleteExercise(long exercise_id) {
		 SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(TABLE_EXERCISE, KEY_ID + " = ?",
		            new String[] { String.valueOf(exercise_id) });
		    Log.i("DatabaseHelper", "Exercise: " + exercise_id + " deleted seccesfully");
	}
	
	/**
	 * Get all exercises
	 */
	public List<Exercise> getAllExercises() {
		List<Exercise> exercises = new ArrayList<Exercise>();
	    String selectQuery = "SELECT * FROM " + TABLE_EXERCISE;
	 
	    Log.e(LOG, selectQuery);
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	            Exercise exercise = new Exercise();
	    		exercise.setId(c.getLong(c.getColumnIndex(KEY_ID)));
	    		exercise.setName(c.getString(c.getColumnIndex(KEY_NAME)));
	    		exercise.setDifficulty(c.getString(c.getColumnIndex(KEY_DIFFICULTY)));
	    		exercise.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY)));
	    		exercise.setLength(c.getInt(c.getColumnIndex(KEY_LENGTH)));
	    		exercise.setImage(c.getString(c.getColumnIndex(KEY_IMAGE)));
	            // adding to exercise list
	            exercises.add(exercise);
	        } while (c.moveToNext());
	    }
	 
	    return exercises;
	}

	// ------------------------ "Training" table methods ----------------//

	/**
	 * Create a training
	 */
	public long createTraining(Training training) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TRAINING_PLAN_ID, training.getTrainingPlanId());
		values.put(KEY_NAME, training.getName());
		values.put(KEY_EXECUTE_TIME, training.getExecuteTime());
		values.put(KEY_LENGTH, training.getLength());
		values.put(KEY_REMINDER, training.getReminder());
		values.put(KEY_DONE_FLAG, training.getDoneFlag());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long training_id = db.insert(TABLE_TRAINING, null, values);

		return training_id;
	}
	
	/**
	 * Get a training
	 */
	public Training getTraining(long training_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_TRAINING + " WHERE "
				+ KEY_ID + " = " + training_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Training training = new Training();
		training.setId(c.getLong(c.getColumnIndex(KEY_ID)));
		training.setTrainingPlanId(c.getLong(c.getColumnIndex(KEY_TRAINING_PLAN_ID)));
		training.setName(c.getString(c.getColumnIndex(KEY_NAME)));
		training.setExecuteTime(c.getString(c.getColumnIndex(KEY_EXECUTE_TIME)));
		training.setLength(c.getInt(c.getColumnIndex(KEY_LENGTH)));
		training.setReminder(c.getString(c.getColumnIndex(KEY_REMINDER)));
		training.setDoneFlag(c.getInt(c.getColumnIndex(KEY_DONE_FLAG)));
		
		return training;
	}

	/**
	 * Update a training
	 */
	public int updateTraining(Training training) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TRAINING_PLAN_ID, training.getTrainingPlanId());
		values.put(KEY_NAME, training.getName());
		values.put(KEY_EXECUTE_TIME, training.getExecuteTime());
		values.put(KEY_LENGTH, training.getLength());
		values.put(KEY_REMINDER, training.getReminder());
		values.put(KEY_DONE_FLAG, training.getDoneFlag());

		// updating row
		return db.update(TABLE_TRAINING, values, KEY_ID + " = ?",
				new String[] { String.valueOf(training.getId()) });
	}

	/**
	 * Delete a training
	 */
	//TODO
	public void deleteTraining(long training_id) {
/*		SQLiteDatabase db = this.getWritableDatabase();

		// before deleting training
		// check if exercises under this training should also be deleted
		if (should_delete_all_tag_todos) {
			// get all todos under this tag
			List<Todo> allTagToDos = getAllToDosByTag(tag.getTagName());

			// delete all todos
			for (Todo todo : allTagToDos) {
				// delete todo
				deleteToDo(todo.getId());
			}
		}

		// now delete the training
		    db.delete(TABLE_TRAINING, KEY_ID + " = ?",
		            new String[] { String.valueOf(training_id) });*/
	}
	
	/**
	 * Get all trainings from training plan
	 */
	public List<Training> getAllTrainingsInPlan(long trainingPlan_id) {
		List<Training> trainings = new ArrayList<Training>();
	    String selectQuery = "SELECT * FROM " + TABLE_TRAINING + " WHERE " + KEY_TRAINING_PLAN_ID + " = " + trainingPlan_id;
	 
	    Log.e(LOG, selectQuery);
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (c.moveToFirst()) {
	        do {
	    		Training training = new Training();
	    		training.setId(c.getLong(c.getColumnIndex(KEY_ID)));
	    		training.setTrainingPlanId(c.getLong(c.getColumnIndex(KEY_TRAINING_PLAN_ID)));
	    		training.setName(c.getString(c.getColumnIndex(KEY_NAME)));
	    		training.setExecuteTime(c.getString(c.getColumnIndex(KEY_EXECUTE_TIME)));
	    		training.setLength(c.getInt(c.getColumnIndex(KEY_LENGTH)));
	    		training.setReminder(c.getString(c.getColumnIndex(KEY_REMINDER)));
	    		training.setDoneFlag(c.getInt(c.getColumnIndex(KEY_DONE_FLAG)));
	    		//training.setExerciseList();
	            // adding to training list
	            trainings.add(training);
	        } while (c.moveToNext());
	    }
	 
	    return trainings;
	}

	// ------------------------ "TrainingPlan" table methods ----------------//

	/**
	 * Calculate a training plan
	 */
	public List<ExerciseTraining> getExerciseTrainingListByUserPref(UserPreferences userPreferences) {
		SQLiteDatabase db = this.getReadableDatabase();
		String KEY_TEMP_GOAL = "";
		if(userPreferences.getGoal()==1)
			KEY_TEMP_GOAL = "goal1";
		else if(userPreferences.getGoal()==2)
			KEY_TEMP_GOAL = "goal2";
		else if(userPreferences.getGoal()==3)
			KEY_TEMP_GOAL = "goal3";
		String selectQuery = "SELECT * FROM " + TABLE_EXERCISE + " WHERE "
				+ KEY_DIFFICULTY + " = " + "'" + userPreferences.getLevel() + "'" 
				+ " AND " + KEY_CATEGORY + " = '" + userPreferences.getMuscleFocus() + "'"
				+  " AND " + KEY_TEMP_GOAL + " = " + 1;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);
			
	    // looping through all rows and adding to list
			if (c != null){
				List<ExerciseTraining> exerciseTrainingList = new ArrayList<ExerciseTraining>();
				c.moveToFirst();
		        do {
		    		ExerciseTraining exerciseTraining = new ExerciseTraining();
		    		Exercise exercise = this.getExercise(c.getLong(c.getColumnIndex(KEY_ID)));
		    		
		    		exerciseTraining.setExercise(exercise);
	
		            // adding to training list
		    		exerciseTrainingList.add(exerciseTraining);
		        } while (c.moveToNext());
		        return exerciseTrainingList;
			}
			return null;
	}

	
	
	/**
	 * Create a training plan
	 */
	public long createTrainingPlan(TrainingPlan trainingPlan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, trainingPlan.getUser().getId());
		values.put(KEY_NAME, trainingPlan.getName());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long trainingPlan_id = db.insert(TABLE_TRAINING_PLAN, null, values);

		return trainingPlan_id;
	}
	
	/**
	 * Get a training plan by training plan id
	 */
	public TrainingPlan getTrainingPlan(long trainingPlan_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_TRAINING_PLAN + " WHERE "
				+ KEY_ID + " = " + trainingPlan_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		TrainingPlan trainingPlan = new TrainingPlan();
		trainingPlan.setId(c.getLong(c.getColumnIndex(KEY_ID)));
		trainingPlan.setName(c.getString(c.getColumnIndex(KEY_NAME)));
		trainingPlan.setUser(getUser(c.getLong(c.getColumnIndex(KEY_USER_ID))));
		
		return trainingPlan;
	}
	
	/**
	 * Get a training plan by user id
	 */
	public TrainingPlan getTrainingPlanByUser(long user_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_TRAINING_PLAN + " WHERE "
				+ KEY_USER_ID + " = " + user_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		TrainingPlan trainingPlan = new TrainingPlan();
		trainingPlan.setId(c.getLong(c.getColumnIndex(KEY_ID)));
		trainingPlan.setName(c.getString(c.getColumnIndex(KEY_NAME)));
		trainingPlan.setUser(getUser(c.getLong(c.getColumnIndex(KEY_USER_ID))));
		trainingPlan.setTrainingList(getAllTrainingsInPlan(c.getLong(c.getColumnIndex(KEY_ID))));
		
		return trainingPlan;
	}

	/**
	 * Update a training plan
	 */
	public int updateTrainingPlan(TrainingPlan trainingPlan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, trainingPlan.getUser().getId());
		values.put(KEY_NAME, trainingPlan.getName());

		// updating row
		return db.update(TABLE_TRAINING_PLAN, values, KEY_ID + " = ?",
				new String[] { String.valueOf(trainingPlan.getId()) });
	}

	/**	
	 * Delete a training plan
	 */
	//TODO
	public void deleteTrainingPlan(long trainingPlan_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TRAINING_PLAN, KEY_ID + " = ?",
				new String[] { String.valueOf(trainingPlan_id) });
	}
	
	// ------------------------ "ExerciseTraining" table methods ----------------//
	
	/**
	 * Create a exerciseTraining
	 */
	public long createExerciseTraining(ExerciseTraining exerciseTraining) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EXERCISE_ID, exerciseTraining.getExercise().getId());
		values.put(KEY_TRAINING_ID, exerciseTraining.getTraining().getId());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long exerciseTraining_id = db.insert(TABLE_EXERCISE_TRAINING, null, values);

		return exerciseTraining_id;
	}
	
	/**
	 * Get a exerciseTraining
	 */
	public ExerciseTraining getExerciseTraining(long exerciseTraining_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_EXERCISE_TRAINING + " WHERE "
				+ KEY_ID + " = " + exerciseTraining_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		ExerciseTraining exerciseTraining = new ExerciseTraining();
		exerciseTraining.setExercise(getExercise(c.getLong(c.getColumnIndex(KEY_EXERCISE_ID))));
		exerciseTraining.setTraining(getTraining(c.getLong(c.getColumnIndex(KEY_TRAINING_ID))));
		
		return exerciseTraining;
	}

	/**
	 * Update a exerciseTraining
	 */
	//TODO - exerciseTraining have no unique id
	public int updateExerciseTraining(ExerciseTraining exerciseTraining) {
/*		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EXERCISE_ID, exerciseTraining.getExercise().getId());
		values.put(KEY_TRAINING_ID, exerciseTraining.getTraining().getId());

		// updating row
		return db.update(TABLE_EXERCISE_TRAINING, values, KEY_ID + " = ?",
				new String[] { String.valueOf(exerciseTraining.getId()) });
		*/	return 0;	
	}

	/**
	 * Delete a exerciseTraining
	 */
	//TODO
	public void deleteExerciseTraining(long exerciseTraining_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TRAINING_PLAN, KEY_ID + " = ?",
				new String[] { String.valueOf(exerciseTraining_id) });
	}
	
	// ------------------------ "User" table methods ----------------//
	
	/**
	 * Create a user
	 */
	public long createUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FIRST_NAME, user.getFirstName());
		values.put(KEY_LAST_NAME, user.getLastName());
		values.put(KEY_EMAIL, user.getEmail());
		values.put(KEY_PASS, user.getPass());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long user_id = db.insert(TABLE_USER, null, values);

		return user_id;
	}

	/**
	 * Get a user
	 */
	public User getUser(long user_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
				+ KEY_ID + " = " + user_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		User user = new User();
		user.setId(c.getLong(c.getColumnIndex(KEY_ID)));
		user.setFirstName(c.getString(c.getColumnIndex(KEY_FIRST_NAME)));
		user.setLastName(c.getString(c.getColumnIndex(KEY_LAST_NAME)));
		user.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
		user.setPass(c.getString(c.getColumnIndex(KEY_PASS)));

		return user;
	}
	
	/**
	 * Update an user
	 */
	public int updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FIRST_NAME, user.getFirstName());
		values.put(KEY_LAST_NAME, user.getLastName());
		values.put(KEY_EMAIL, user.getEmail());
		values.put(KEY_PASS, user.getPass());

		// updating row
		return db.update(TABLE_USER, values, KEY_ID + " = ?",
				new String[] { String.valueOf(user.getId()) });
	}
	
	/**
	 * Delete an user
	 */
	public void deleteUser(long user_id) {
		 SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(TABLE_USER, KEY_ID + " = ?",
		            new String[] { String.valueOf(user_id) });
	}
	
	/**
	 * Check user
	 */
	public long checkUser(String email, String password) {
		 SQLiteDatabase db = this.getReadableDatabase();
		 String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE "
					+ KEY_EMAIL + " = '" + email + "' AND " + KEY_PASS + " = '" + password + "'";

		 Log.e(LOG, selectQuery);

		 Cursor c = db.rawQuery(selectQuery, null);
		 long user_id = 0;
		 if (c.moveToFirst()){
			 user_id = c.getLong(c.getColumnIndex(KEY_ID));
		 }
		 return user_id;
	}
	
	// ------------------------ "UserPreferences" table methods ----------------//
	
	/**
	 * Create an user preferences
	 */
	public long createUserPreferences(UserPreferences userPreferences) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, userPreferences.getUserId());
		values.put(KEY_GOAL, userPreferences.getGoal());
		values.put(KEY_LENGTH, userPreferences.getLength());
		values.put(KEY_LEVEL, userPreferences.getLevel());
		values.put(KEY_MUSCLE_FOCUS, userPreferences.getMuscleFocus());
		values.put(KEY_PHASE1, userPreferences.getPhase1());
		values.put(KEY_PHASE2, userPreferences.getPhase2());
		values.put(KEY_SCHEDULE, userPreferences.getSchedule());
		values.put(KEY_WORKOUT_TIME, userPreferences.getWorkoutTime());
		values.put(KEY_REMINDER, userPreferences.getReminder());
		values.put(KEY_CREATED_AT, getDateTime());

		// insert row
		long userPreferences_id = db.insert(TABLE_USER_PREFERENCES, null, values);

		return userPreferences_id;
	}

	/**
	 * Get an user preferences
	 */
	public UserPreferences getUserPreferences(long userPreferences_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_USER_PREFERENCES + " WHERE "
				+ KEY_ID + " = " + userPreferences_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		UserPreferences userPreferences = new UserPreferences();
		userPreferences.setId(c.getLong(c.getColumnIndex(KEY_ID)));
		userPreferences.setUserId(c.getLong(c.getColumnIndex(KEY_USER_ID)));
		userPreferences.setGoal(c.getInt(c.getColumnIndex(KEY_GOAL)));
		userPreferences.setLength(c.getInt(c.getColumnIndex(KEY_LENGTH)));
		userPreferences.setLevel(c.getString(c.getColumnIndex(KEY_LEVEL)));
		userPreferences.setMuscleFocus(c.getString(c.getColumnIndex(KEY_MUSCLE_FOCUS)));
		userPreferences.setPhase1(c.getInt(c.getColumnIndex(KEY_PHASE1))>0);
		userPreferences.setPhase2(c.getInt(c.getColumnIndex(KEY_PHASE2))>0);
		userPreferences.setSchedule(c.getString(c.getColumnIndex(KEY_SCHEDULE)));
		userPreferences.setWorkoutTime(c.getInt(c.getColumnIndex(KEY_WORKOUT_TIME)));
		userPreferences.setReminder(c.getString(c.getColumnIndex(KEY_REMINDER)));
		
		return userPreferences;
	}
	
	/**
	 * Update an user preferences
	 */
	public int updateUserPreferences(UserPreferences userPreferences) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, userPreferences.getUserId());
		values.put(KEY_GOAL, userPreferences.getGoal());
		values.put(KEY_LENGTH, userPreferences.getLength());
		values.put(KEY_LEVEL, userPreferences.getLevel());
		values.put(KEY_MUSCLE_FOCUS, userPreferences.getMuscleFocus());
		values.put(KEY_PHASE1, userPreferences.getPhase1());
		values.put(KEY_PHASE2, userPreferences.getPhase2());
		values.put(KEY_SCHEDULE, userPreferences.getSchedule());
		values.put(KEY_WORKOUT_TIME, userPreferences.getWorkoutTime());
		values.put(KEY_REMINDER, userPreferences.getReminder());

		// updating row
		return db.update(TABLE_USER_PREFERENCES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(userPreferences.getId()) });
	}
	
	/**
	 * Delete an user preferences
	 */
	public void deleteUserPreferences(long UserPreferences_id) {
		 SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(TABLE_USER_PREFERENCES, KEY_ID + " = ?",
		            new String[] { String.valueOf(UserPreferences_id) });
	}
	
	// ------------------------ "Login" table methods ----------------//
	
	/**
	 * Create a login record
	 */
	public long createLogin(long user_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, user_id);
		values.put(KEY_CREATED_AT, getDateTime());
		values.put(KEY_UPDATED_AT, getDateTime());

		// insert row
		long login_id = db.insert(TABLE_LOGIN, null, values);

		return login_id;
	}

	/**
	 * Get a login user id
	 */
	public long getLogin() {
		SQLiteDatabase db = this.getReadableDatabase();
		long login_user_id = 0;
		String selectQuery = "SELECT * FROM " + TABLE_LOGIN;
		Log.e(LOG, selectQuery);
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.getCount() > 0){
			c.moveToFirst();
			login_user_id = c.getColumnIndex(KEY_USER_ID);
		}
		return login_user_id;
	}
		
	/**
	 * Delete a login
	 */
	public void deleteLogin(long user_id) {
		 SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(TABLE_LOGIN, KEY_USER_ID + " = ?",
		            new String[] { String.valueOf(user_id) });
	}
	
	////
	
	
	/**
	 * Get DateTime
	 */
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
	
}
