package il.ac.shenkar.MyTrainerIL.dao;

import il.ac.shenkar.MyTrainerIL.entities.Training;
import il.ac.shenkar.MyTrainerIL.helper.DatabaseHelper;
import android.content.Context;

public class TrainingDao {

	private DatabaseHelper databaseHelper;
	
	/**
	 * @param context
	 */
	public TrainingDao(Context context){
		databaseHelper = new DatabaseHelper(context);
	}

	public Training getTrainingById(long training_id) {
		Training training = databaseHelper.getTraining(training_id);
		if(training != null){
			return training;
		}
		return training;
	}
	
	public Boolean deleteTraining(long training_id){
		databaseHelper.deleteTraining(training_id);
		return true;
	}
}
