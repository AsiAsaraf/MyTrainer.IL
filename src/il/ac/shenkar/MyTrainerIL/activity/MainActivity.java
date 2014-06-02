package il.ac.shenkar.MyTrainerIL.activity;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.LoginDao;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Context context;
	protected LoginDao loginDao;
	
	//Defining layout items
	private Button btnLogout;
	private Button btnSettings;
	private Button btnCreateTrainingPlan;
	private Button btnEnterTrainingPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent intent = getIntent();
        context = this;
        loginDao = new LoginDao(context);
        long tempUserId = intent.getLongExtra("user", 0);
        if(tempUserId == 0){
        	tempUserId = loginDao.getLogin();
        	if(tempUserId == 0){
                Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();        		
        	}
        }
        	
        final long userId = tempUserId;
        
        /**
         * Defining all layout items
         **/
        btnLogout = (Button)findViewById(R.id.btn_logout);
        btnSettings = (Button)findViewById(R.id.btn_settings);
        btnCreateTrainingPlan = (Button)findViewById(R.id.btn_create_trainig_plan);
        btnEnterTrainingPlan = (Button)findViewById(R.id.btn_enter_trainig_plan);
        
        /**
         * Logout Button click event.
         **/
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	loginDao.deleteLogin();
                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });

        /**
         * Settings Button click event.
         **/
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SettingsActivity.class);
                myIntent.putExtra("user",userId);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });  
        
        /**
         * Training plan Button click event.
         **/
        btnCreateTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), UserPreferencesActivity.class);
                myIntent.putExtra("user",userId);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });        
        
        /**
         * Training plan Enter click event.
         **/
        btnEnterTrainingPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), TrainingPlanActivity.class);
                myIntent.putExtra("user",userId);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
