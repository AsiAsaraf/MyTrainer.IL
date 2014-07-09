package il.ac.shenkar.MyTrainerIL.activity;

import java.util.List;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.LoginDao;
import il.ac.shenkar.MyTrainerIL.dao.TrainingPlanDao;
import il.ac.shenkar.MyTrainerIL.entities.Training;
import il.ac.shenkar.MyTrainerIL.entities.TrainingPlan;
import il.ac.shenkar.MyTrainerIL.utils.AppUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class TrainingPlanActivity extends Activity{

	protected TrainingPlanDao trainingPlanDao;

	protected LoginDao loginDao;
	private TrainingPlan trainingPlan;
	private List<Training> trainingList;
	private Context context;
	
	//Defining layout items
	private TableLayout tableLayoutTrainingPlan;
	private ImageButton btnMenu;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_plan_activity);
        context = this;
        trainingPlanDao = new TrainingPlanDao(context);
        loginDao = new LoginDao(context);
        if(loginDao.getLogin() == 0){
            Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
            startActivityForResult(myIntent, 0);
            finish();
        }
        trainingPlan = trainingPlanDao.getTrainingPlanByUser(0);
        trainingList = trainingPlan.getTrainingList();
        if(trainingPlan == null){
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
            myIntent.putExtra("user",loginDao.getLogin());
            startActivityForResult(myIntent, 0);
            finish();
        }
        
        /**
         * Defining all layout items
         **/
        btnMenu = (ImageButton)findViewById(R.id.btn_menu);
        tableLayoutTrainingPlan = (TableLayout)findViewById(R.id.table_layout_training_plan);
        
        TableLayout tableHeaderTrainingPlan = (TableLayout)findViewById(R.id.table_header_training_plan);
        tableHeaderTrainingPlan = addRowToTable(tableHeaderTrainingPlan, 0, getString(R.string.col_training_name), getString(R.string.col_training_length), getString(R.string.col_training_date));

	    int j = 0;
	    while (trainingList.size() > j) {
	    	tableLayoutTrainingPlan = addRowToTable(tableLayoutTrainingPlan, j+1, trainingList.get(j).getName(), Integer.toString(trainingList.get(j).getLength()), trainingList.get(j).getExecuteTime());
	    	j++;
	    }
	    
	    /**
         * Menu Button click event.
         **/
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	PopupMenu popup = new PopupMenu(TrainingPlanActivity.this, btnMenu);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popup.getMenu());
                popup.getMenu().getItem(1).setVisible(false);
                popup.getMenu().getItem(2).setVisible(false);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.main:
                            	main();  
                                return true;
                            case R.id.logout:
                        		logout();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            popup.show();
            }
        });
	    
    }
    
    public void logout(){
    	loginDao.deleteLogin();
        Intent myIntent = new Intent(this.getBaseContext(), LoginActivity.class);
        myIntent.putExtra("user", 0);
        startActivityForResult(myIntent, 0);
        finish();
    }
    
    public void main(){
        Intent myIntent = new Intent(this.getBaseContext(), MainActivity.class);
        myIntent.putExtra("user",loginDao.getLogin());
        startActivityForResult(myIntent, 0);
        finish();
    }        
    
    private TableLayout addRowToTable(TableLayout table, int id, String contentCol3, String contentCol2, String contentCol1) {
	    Context context = getApplicationContext();
	    TableRow row = new TableRow(context);
	
	    TableRow.LayoutParams rowParams = new TableRow.LayoutParams();
	    // Wrap-up the content of the row
	    rowParams.height = LayoutParams.WRAP_CONTENT;
	    rowParams.width = LayoutParams.WRAP_CONTENT;
	    // The simplified version of the table of the picture above will have two columns
	
	    // FIRST COLUMN
	    int h = 50;
	    TableRow.LayoutParams col1Params = new TableRow.LayoutParams(80, h);
	    col1Params.gravity = Gravity.CENTER;
	    TextView col1 = new TextView(context);
	    col1.setTextColor(getResources().getColor(R.color.black));	    
	    col1.setTextSize(16);
	    col1.setText(contentCol1);
	    if((!contentCol1.isEmpty()) && (contentCol1.equals(getString(R.string.col_training_date)))){
	    	col1Params.gravity = Gravity.CENTER;
	    	col1.setTypeface(null, Typeface.BOLD);	    	
	    } else {
	    	col1Params.gravity = Gravity.RIGHT;
	    }
	    row.addView(col1, col1Params);
	
	    // SECOND COLUMN
	    TableRow.LayoutParams col2Params = new TableRow.LayoutParams(8, h);
	    col2Params.gravity = Gravity.CENTER;
	    TextView col2 = new TextView(context);
	    col2.setTextColor(getResources().getColor(R.color.black));
	    col2.setTextSize(16);
	    col2.setText(contentCol2);
	    if(contentCol2.equals(getString(R.string.col_training_length))){
	    	col2Params.gravity = Gravity.CENTER;
	    	col2.setTypeface(null, Typeface.BOLD);	    	
	    } else {
	    	col2Params.gravity = Gravity.CENTER;
	    }
	    row.addView(col2, col2Params);
	    
	    // THIRD COLUMN
	    TableRow.LayoutParams col3Params = new TableRow.LayoutParams(32, h);
	    TextView col3 = new TextView(context);
	    col3.setTextColor(getResources().getColor(R.color.black));	
	    col3.setTextSize(16);
	    col3.setText(contentCol3);
	    if(contentCol3.equals(getString(R.string.col_training_name))){
	    	col3Params.gravity = Gravity.CENTER;
	    	col3.setTypeface(null, Typeface.BOLD);	    	
	    } else {
	    	col3Params.gravity = Gravity.RIGHT;
	    }
	    row.addView(col3, col3Params);
	    
	    row.setClickable(true);
	    row.setId(id);
	    row.setOnClickListener(tablerowOnClickListener);
	    table.addView(row, rowParams);
	    return table;
    }
    
    private OnClickListener tablerowOnClickListener = new OnClickListener() {
        public void onClick(View v) {
        	int position = ((TableRow)v).getId();
            Intent myIntent = new Intent(getBaseContext(), TrainingActivity.class);
            myIntent.putExtra("user",loginDao.getLogin());
            myIntent.putExtra("training",trainingList.get(position-1).getId());
            startActivityForResult(myIntent, 0);
            finish();
        }
    };  

    @Override
    public void onBackPressed() {
    	AppUtils.onButtonBackPressed(this);
    }
    
    @Override
    public void onDestroy() {
    	super.onStop();
    }
}