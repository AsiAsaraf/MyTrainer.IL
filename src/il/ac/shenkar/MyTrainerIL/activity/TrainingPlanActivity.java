package il.ac.shenkar.MyTrainerIL.activity;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.TrainingPlanDao;
import il.ac.shenkar.MyTrainerIL.entities.Training;
import il.ac.shenkar.MyTrainerIL.entities.TrainingPlan;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class TrainingPlanActivity extends Activity{

	protected TrainingPlanDao trainingPlanDao;
	private TrainingPlan trainingPlan;
	private Context context;
	
	//Defining layout items
	private TableLayout tableLayoutTrainingPlan;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_plan_activity);
        context = this;
        trainingPlanDao = new TrainingPlanDao(context);
        trainingPlan = new TrainingPlan();
        
        /**
         * Defining all layout items
         **/
        tableLayoutTrainingPlan = (TableLayout)findViewById(R.id.table_layout_training_plan);
        
        trainingPlan = trainingPlanDao.getTrainingPlanByUser();
        
/*        for (int i = 1; i <= trainingPlan.getTrainingList().size(); i++) {
        	Training training = trainingPlan.getTrainingList().get(i);
        	
        	TableRow row = new TableRow(this);
        	row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

    	    TextView textViewNumber = new TextView(this);
    	    textViewNumber.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    	    textViewNumber.setBackgroundResource(R.drawable.cell_shape);
    	    textViewNumber.setPadding(5, 5, 5, 5);
    	    textViewNumber.setText("R");
    	    row.addView(textViewNumber);
	    	
    	    tableLayoutTrainingPlan.addView(row);

	    }*/  
    }
}