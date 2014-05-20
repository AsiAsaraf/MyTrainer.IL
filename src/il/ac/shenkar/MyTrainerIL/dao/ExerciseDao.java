package il.ac.shenkar.MyTrainerIL.dao;

import il.ac.shenkar.MyTrainerIL.helper.DatabaseHelper;
import android.content.Context;

public class ExerciseDao {

	private DatabaseHelper databaseHelper;
	
	/**
	 * @param context
	 */
	public ExerciseDao(Context context){
		databaseHelper = new DatabaseHelper(context);
	}
}
