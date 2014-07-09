package il.ac.shenkar.MyTrainerIL.activity;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.LoginDao;
import il.ac.shenkar.MyTrainerIL.dao.TrainingPlanDao;
import il.ac.shenkar.MyTrainerIL.dao.UserPreferencesDao;
import il.ac.shenkar.MyTrainerIL.entities.TrainingPlan;
import il.ac.shenkar.MyTrainerIL.utils.AppUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Context context;
	protected LoginDao loginDao;
	protected TrainingPlanDao trainingPlanDao;
	protected UserPreferencesDao userPreferencesDao;
	
	//Defining layout items
	private ImageButton btnMenu;
	private ImageButton btnUpdateTrainingPlan;
	private ImageButton btnCreateTrainingPlan;
	private ImageButton btnEnterTrainingPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent intent = getIntent();
        context = this;
        loginDao = new LoginDao(context);
        trainingPlanDao = new TrainingPlanDao(context);
        userPreferencesDao = new UserPreferencesDao(context);
        long tempUserId = intent.getLongExtra("user", 0);
        if(tempUserId == 0){
        	tempUserId = loginDao.getLogin();
        	if(tempUserId == 0){
                Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();        		
        	}
        }
        invalidateOptionsMenu();
        	
        final long userId = tempUserId;
        TrainingPlan trainingPlan = trainingPlanDao.getTrainingPlanByUser(userId);
        
        /**
         * Defining all layout items
         **/
        btnMenu = (ImageButton)findViewById(R.id.btn_menu);
        btnCreateTrainingPlan = (ImageButton)findViewById(R.id.btn_create_trainig_plan);
        btnUpdateTrainingPlan = (ImageButton)findViewById(R.id.btn_update_training_plan);
        btnEnterTrainingPlan = (ImageButton)findViewById(R.id.btn_enter_trainig_plan);
        if(trainingPlan != null){
        	btnCreateTrainingPlan.setImageResource(R.drawable.create_training_plan_btn_grayed);
        	btnCreateTrainingPlan.setClickable(false);
        	btnCreateTrainingPlan.setEnabled(false);
        } else {
        	btnUpdateTrainingPlan.setImageResource(R.drawable.enter_training_plan_btn_grayed);
        	btnUpdateTrainingPlan.setClickable(false);
        	btnUpdateTrainingPlan.setEnabled(false);
        	btnEnterTrainingPlan.setImageResource(R.drawable.update_training_plan_btn_grayed);
        	btnEnterTrainingPlan.setClickable(false);
        	btnEnterTrainingPlan.setEnabled(false);
        }
        
        /**
         * Update Training Plan Button click event.
         **/
        btnUpdateTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if (btnUpdateTrainingPlan.isClickable()) {
                    userPreferencesDao.deleteUserPrefrences(userId);
                    Intent myIntent = new Intent(view.getContext(), UserPreferencesActivity.class);
                    myIntent.putExtra("user",userId);
                    startActivityForResult(myIntent, 0);
                    finish();
                }
            }
        });  
        
        /**
         * Create Training Plan Button click event.
         **/
        btnCreateTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if(btnCreateTrainingPlan.isClickable()){
	                Intent myIntent = new Intent(view.getContext(), UserPreferencesActivity.class);
	                myIntent.putExtra("user",userId);
	                startActivityForResult(myIntent, 0);
	                finish();
            	}
            }
        });        
        
        /**
         * Enter Training Plan Button click event.
         **/
        btnEnterTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if(btnEnterTrainingPlan.isClickable()){
	                Intent myIntent = new Intent(view.getContext(), TrainingPlanActivity.class);
	                myIntent.putExtra("user",userId);
	                startActivityForResult(myIntent, 0);
	                finish();
            	}
            }
        });        
        
        /**
         * Menu Button click event.
         **/
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	PopupMenu popup = new PopupMenu(MainActivity.this, btnMenu);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popup.getMenu());
                popup.getMenu().getItem(0).setVisible(false);
                popup.getMenu().getItem(1).setVisible(false);
                popup.getMenu().getItem(2).setVisible(false);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
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
    	if (loginDao.deleteLogin()){
	        Intent myIntent = new Intent(this.getBaseContext(), LoginActivity.class);
	        startActivityForResult(myIntent, 0);
	        finish();
    	} else 
    		Toast.makeText(getBaseContext(), "some error occored while logout", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onBackPressed() {
    	AppUtils.onButtonBackPressed(this);
    }
    
    @Override
    public void onDestroy() {
    	super.onStop();
    }
}
