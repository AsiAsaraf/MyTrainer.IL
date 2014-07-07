package il.ac.shenkar.MyTrainerIL.dao;

import il.ac.shenkar.MyTrainerIL.entities.TrainingPlan;
import il.ac.shenkar.MyTrainerIL.helper.DatabaseHelper;
import android.content.Context;

public class TrainingPlanDao {

	private DatabaseHelper databaseHelper;
	
	/**
	 * @param context
	 */
	public TrainingPlanDao(Context context){
		databaseHelper = new DatabaseHelper(context);
	}

	public TrainingPlan getTrainingPlanByUser(long user_id){
		if(user_id == 0){
			user_id = databaseHelper.getLogin();
		}
		TrainingPlan trainingPlan = databaseHelper.getTrainingPlanByUser(user_id);
		if(trainingPlan != null){
			return trainingPlan;
		} else {
			return null;
		}
	}
	
	public Boolean deleteTrainingPlanByUser(long user_id){
		if(user_id == 0){
			user_id = databaseHelper.getLogin();
		}
		return(databaseHelper.deleteTrainingPlanByUser(user_id));
	}

}
