package il.ac.shenkar.MyTrainerIL.dao;

import il.ac.shenkar.MyTrainerIL.entities.ExerciseTraining;
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

	public ExerciseTraining getExerciseTrainingById(long exerciseTraining_id) {
		ExerciseTraining exerciseTraining = databaseHelper.getExerciseTraining(exerciseTraining_id);
		return exerciseTraining;
	}
	
	public Boolean deleteExerciseTraining(long training_id){
		return(databaseHelper.deleteExerciseTraining(training_id));
	}
}
