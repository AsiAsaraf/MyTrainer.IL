package il.ac.shenkar.MyTrainerIL.activity;

import java.util.List;
import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.LoginDao;
import il.ac.shenkar.MyTrainerIL.dao.TrainingPlanDao;
import il.ac.shenkar.MyTrainerIL.entities.Training;
import il.ac.shenkar.MyTrainerIL.entities.TrainingPlan;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class TrainingPlanActivity extends Activity{

	protected TrainingPlanDao trainingPlanDao;
	protected LoginDao loginDao;
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
        loginDao = new LoginDao(context);
        trainingPlan = trainingPlanDao.getTrainingPlanByUser();
        if(trainingPlan == null){
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
            myIntent.putExtra("user",loginDao.getLogin());
            startActivityForResult(myIntent, 0);
            finish();
        }
        
        /**
         * Defining all layout items
         **/
        tableLayoutTrainingPlan = (TableLayout)findViewById(R.id.table_layout_training_plan);
        
        TableLayout tableHeaderTrainingPlan = (TableLayout)findViewById(R.id.table_header_training_plan);
        tableHeaderTrainingPlan = addRowToTable(tableHeaderTrainingPlan, getString(R.string.col_training_num), getString(R.string.col_training_name), getString(R.string.col_training_length), getString(R.string.col_training_date));

	    String longestRow = "";
	    List<Training> trainingList = trainingPlan.getTrainingList();
	    int lengthRow = 0;
	    int j = 0;
	    while (trainingList.size() > j) {
	    	tableLayoutTrainingPlan = addRowToTable(tableLayoutTrainingPlan, Integer.toString(j+1), trainingList.get(j).getName() + (j+1), Integer.toString(trainingList.get(j).getLength()), trainingList.get(j).getExecuteTime());
/*	    	lengthRow = Integer.toString(j).length() + trainingList.get(j).getName().length() + Integer.toString(trainingList.get(j).getLength()).length() + trainingList.get(j).getExecuteTime().length();
	    	if (longestRow.isEmpty() || lengthRow > (longestRow.length()-3)) //Include -3 for subtracting the space occupied by "-"
    			longestRow = Integer.toString(j) + "-" + trainingList.get(j).getName() + "-" + Integer.toString(trainingList.get(j).getLength()) + "-" + trainingList.get(j).getExecuteTime();*/
	    	j++;
	    }
	    
	  //  tableHeaderTrainingPlan = addRowToTable(tableHeaderTrainingPlan, longestRow.split("-")[0], longestRow.split("-")[1], longestRow.split("-")[3], longestRow.split("-")[4]);

    }
    
    private TableLayout addRowToTable(TableLayout table, String contentCol4, String contentCol3, String contentCol2, String contentCol1) {
	    Context context = getApplicationContext();
	    TableRow row = new TableRow(context);
	
	    TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
	    // Wrap-up the content of the row
	    rowParams.height = LayoutParams.WRAP_CONTENT;
	    rowParams.width = LayoutParams.WRAP_CONTENT;
	    // The simplified version of the table of the picture above will have two columns
	
	    // FIRST COLUMN
	    TableRow.LayoutParams col1Params = new TableRow.LayoutParams();
	    // Wrap-up the content of the row
	    col1Params.height = LayoutParams.WRAP_CONTENT;
	    col1Params.width = LayoutParams.WRAP_CONTENT;
	    // Set the gravity to center the gravity of the column
	    col1Params.gravity = Gravity.RIGHT;
	    TextView col1 = new TextView(context);
	    col1.setText(contentCol1);
	    row.addView(col1, col1Params);
	
	    // SECOND COLUMN
	    TableRow.LayoutParams col2Params = new TableRow.LayoutParams();
	    // Wrap-up the content of the row
	    col2Params.height = LayoutParams.WRAP_CONTENT;
	    col2Params.width = LayoutParams.WRAP_CONTENT;
	    // Set the gravity to center the gravity of the column
	    col2Params.gravity = Gravity.RIGHT;
	    TextView col2 = new TextView(context);
	    col2.setText(contentCol2);
	    row.addView(col2, col2Params);
	    
	    // THIRD COLUMN
	    TableRow.LayoutParams col3Params = new TableRow.LayoutParams();
	    // Wrap-up the content of the row
	    col3Params.height = LayoutParams.WRAP_CONTENT;
	    col3Params.width = LayoutParams.WRAP_CONTENT;
	    // Set the gravity to center the gravity of the column
	    col3Params.gravity = Gravity.RIGHT;
	    TextView col3 = new TextView(context);
	    col3.setText(contentCol3);
	    row.addView(col3, col3Params);
	    
	    // ROURTH COLUMN
	    TableRow.LayoutParams col4Params = new TableRow.LayoutParams();
	    // Wrap-up the content of the row
	    col4Params.height = LayoutParams.WRAP_CONTENT;
	    col4Params.width = LayoutParams.WRAP_CONTENT;
	    // Set the gravity to center the gravity of the column
	    col4Params.gravity = Gravity.RIGHT;
	    TextView col4 = new TextView(context);
	    col4.setText(contentCol4);
	    row.addView(col4, col4Params);
	    
	    table.addView(row, rowParams);
	    return table;
    }
    
}