package il.ac.shenkar.MyTrainerIL.activity;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.LoginDao;
import il.ac.shenkar.MyTrainerIL.dao.UserPreferencesDao;
import il.ac.shenkar.MyTrainerIL.entities.UserPreferences;
import il.ac.shenkar.MyTrainerIL.utils.AppUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class UserPreferencesActivity extends Activity{

	protected LoginDao loginDao;
	protected UserPreferencesDao userPreferencesDao;
	private UserPreferences userPreferences;
	private Context context;
	
	//Defining layout items
/*	private TextView goal;
	private RadioGroup radioGroupGoal;
	private TextView level;
	private TextView muscleFocus;
	private TextView phases;
	private CheckBox checkBoxPhases1;
	private CheckBox checkBoxPhases2;*/
	private Spinner spinnerMuscleFocus;
	private Spinner spinnerLevel;
	
	private ImageButton btnNext;
	private ImageButton btnMenu;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_preferences_activity);
        Intent intent = getIntent();
        context = this;
        loginDao = new LoginDao(context);
        if(loginDao.getLogin() == 0){
            Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
            startActivityForResult(myIntent, 0);
            finish();
        }
        final long userId = intent.getLongExtra("user", 0);
    	if(userId == 0){
            Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
            startActivityForResult(myIntent, 0);
            finish();        		
    	}
        userPreferencesDao = new UserPreferencesDao(context);
        userPreferences = new UserPreferences();
        userPreferences.setUserId(userId);
        
        /**
         * Defining all layout items
         **/
        btnMenu = (ImageButton)findViewById(R.id.btn_menu);
/*        goal = (TextView)findViewById(R.id.text_view_goal);
        	radioGroupGoal = (RadioGroup)findViewById(R.id.radio_group_goal);
        level = (TextView)findViewById(R.id.text_view_level);*/
        	spinnerLevel = (Spinner)findViewById(R.id.spinner_level);
        	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_level, android.R.layout.simple_spinner_item);
        	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinnerLevel.setAdapter(adapter);
        //muscleFocus = (TextView)findViewById(R.id.text_view_muscle_focus);
        	spinnerMuscleFocus = (Spinner)findViewById(R.id.spinner_muscle_focus);
        	ArrayAdapter<CharSequence> muscleAdarpter = ArrayAdapter.createFromResource(this, R.array.array_muscle_focus, android.R.layout.simple_spinner_item);
        	muscleAdarpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinnerMuscleFocus.setAdapter(muscleAdarpter);
        /*phases = (TextView)findViewById(R.id.text_view_phases);
        	checkBoxPhases1 = (CheckBox)findViewById(R.id.checkbox_phases1);
        	checkBoxPhases2 = (CheckBox)findViewById(R.id.checkbox_phases2);*/
        
        btnNext = (ImageButton)findViewById(R.id.image_button_next);
        
        /**
         * Next Button click event.
         **/
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {            
            	if((userPreferences.getGoal() == 0)){
            		Toast.makeText(getBaseContext(), getResources().getString(R.string.verify), Toast.LENGTH_SHORT).show();
            	} else {
	                Intent myIntent = new Intent(view.getContext(), UserPreferencesContActivity.class);
	                myIntent.putExtra("user", userPreferences.getUserId());
	                myIntent.putExtra("goal",userPreferences.getGoal());
	                myIntent.putExtra("level",userPreferences.getLevel());
	                myIntent.putExtra("muscleFocus",userPreferences.getMuscleFocus());
	                myIntent.putExtra("phase1", userPreferences.getPhase1());
	                myIntent.putExtra("phase2", userPreferences.getPhase2());
	                startActivityForResult(myIntent, 0);
	                finish();
            	}
            }
        });
        
        /**
         * Level Spinner click event.
         **/
        spinnerLevel.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				parent.getSelectedItemPosition();
	               getResources().getStringArray(R.array.array_level);
	               spinnerLevel.setSelection(position);
	               userPreferences.setLevel(parent.getItemAtPosition(position).toString());		
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}
        });
        
        /**
         * Muscle Focus Spinner click event.
         **/
        spinnerMuscleFocus.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				parent.getSelectedItemPosition();
	               getResources().getStringArray(R.array.array_muscle_focus);
	               //userPreferences.setMuscleFocus(parent.getItemAtPosition(position).toString());
	               userPreferences.setMuscleFocus(position); 		
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}
        });
        
	    /**
         * Menu Button click event.
         **/
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	PopupMenu popup = new PopupMenu(UserPreferencesActivity.this, btnMenu);
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
    
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_button_goal1:
                if (checked){
        			userPreferences.setGoal(1);
                } break;
            case R.id.radio_button_goal2:
                if (checked){
        			userPreferences.setGoal(2);
                } break;
            case R.id.radio_button_goal3:
                if (checked){
        			userPreferences.setGoal(3);
                } break;
        }
    }
    
    public void onCheckboxClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		// Check which checkbox was clicked
	    switch(view.getId()) {
	        case R.id.checkbox_phases1:
	            if (checked){
	            	userPreferences.setPhase1(true);
	            } else{
	            	userPreferences.setPhase1(false);
	            } break;
	        case R.id.checkbox_phases2:
	            if (checked){
	            	userPreferences.setPhase2(true);
	            } else{
	            	userPreferences.setPhase2(false);
	            } break;
	    }
    }
    
    @Override
    public void onBackPressed() {
    	AppUtils.onButtonBackPressed(this);
    }
}
