package il.ac.shenkar.MyTrainerIL.activity;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.TrainingDao;
import il.ac.shenkar.MyTrainerIL.dao.TrainingPlanDao;
import il.ac.shenkar.MyTrainerIL.entities.Exercise;
import il.ac.shenkar.MyTrainerIL.entities.Training;
import il.ac.shenkar.MyTrainerIL.entities.TrainingPlan;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class TrainingActivity extends Activity{

	protected TrainingDao trainingDao;
	private Training training;
	private Context context;
	
	//Defining layout items
	private TableLayout tableLayoutTraining;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_activity);
        context = this;
        trainingDao = new TrainingDao(context);
        training = new Training();
        
        /**
         * Defining all layout items
         **/
        tableLayoutTraining = (TableLayout)findViewById(R.id.table_layout_training);
        
/*        training = trainingDao.getTrainingByUser();
        
        for (int i = 1; i <= training.getExerciseList().size(); i++) {
        	Exercise exercise = training.getExerciseList().get(i);
        	
        	TableRow row = new TableRow(this);
        	row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

    	    TextView textViewNumber = new TextView(this);
    	    textViewNumber.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    	    textViewNumber.setBackgroundResource(R.drawable.cell_shape);
    	    textViewNumber.setPadding(5, 5, 5, 5);
    	    textViewNumber.setText("R");
    	    row.addView(textViewNumber);
	    	
    	    tableLayoutTraining.addView(row);

	    }*/  
    }
}
