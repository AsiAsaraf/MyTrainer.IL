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
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class UserPreferencesContActivity extends Activity{

	protected UserPreferencesDao userPreferencesDao;
	protected LoginDao loginDao;
	private UserPreferences userPreferences;
	private Context context;
	
	//Defining layout items
	private NumberPicker numberPickerLength;
	private Spinner spinnerReminder;
	private NumberPicker numberPickerWorkoutTime;
/*	private TextView length;
	private TextView reminder;
	private TextView schedule;
	private CheckBox checkBoxSun;
	private CheckBox checkBoxMon;
	private CheckBox checkBoxTue;
	private CheckBox checkBoxWed;
	private CheckBox checkBoxThu;
	private CheckBox checkBoxFri;
	private CheckBox checkBoxSat;
	private TextView workoutTime;*/

	private ImageButton btnMenu;
	private ImageButton btnCreate;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_preferences_cont_activity);
        Intent intent = getIntent();
        context = this;
        userPreferencesDao = new UserPreferencesDao(context);
        loginDao = new LoginDao(context);
        userPreferences = new UserPreferences();
        userPreferences.setUserId(intent.getLongExtra("user", 0));
        userPreferences.setGoal(intent.getIntExtra("goal", 0));
        userPreferences.setLevel(intent.getStringExtra("level"));
        userPreferences.setMuscleFocus(intent.getIntExtra("muscleFocus", 0));
        userPreferences.setPhase1(intent.getBooleanExtra("phase1", false));
        userPreferences.setPhase2(intent.getBooleanExtra("phase2", false));
        
        /**
         * Defining all layout items
         **/
        btnMenu = (ImageButton)findViewById(R.id.btn_menu);
        btnCreate = (ImageButton)findViewById(R.id.image_button_create);
        //length = (TextView)findViewById(R.id.text_view_length);
	    	numberPickerLength = (NumberPicker)findViewById(R.id.number_picker_length);
	        String[] lengthValues = new String[16];
	        int index = 3;
	        for(int i=0; i<lengthValues.length; i++){
	        	lengthValues[i] = Integer.toString(index*5);
	        	index++;
	        }
	        numberPickerLength.setMinValue(0);
	        numberPickerLength.setMaxValue(lengthValues.length-1);
	        numberPickerLength.setWrapSelectorWheel(false);
	        numberPickerLength.setDisplayedValues(lengthValues);  
        
        //reminder = (TextView)findViewById(R.id.text_view_reminder);
	    	spinnerReminder = (Spinner)findViewById(R.id.spinner_reminder);
	    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_reminder, android.R.layout.simple_spinner_item);
	    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	spinnerReminder.setAdapter(adapter);
/*	    schedule = (TextView)findViewById(R.id.text_view_schedule);
	    	checkBoxSun = (CheckBox)findViewById(R.id.checkbox_sunday);
	    	checkBoxMon = (CheckBox)findViewById(R.id.checkbox_monday);
	    	checkBoxTue = (CheckBox)findViewById(R.id.checkbox_tuesday);
	    	checkBoxWed = (CheckBox)findViewById(R.id.checkbox_wednesday);
	    	checkBoxThu = (CheckBox)findViewById(R.id.checkbox_thursday);
	    	checkBoxFri = (CheckBox)findViewById(R.id.checkbox_friday);
	    	checkBoxSat = (CheckBox)findViewById(R.id.checkbox_saturday);
	    workoutTime = (TextView)findViewById(R.id.text_view_workout_time);*/	
	    	numberPickerWorkoutTime = (NumberPicker)findViewById(R.id.number_picker_workout_time);
	        String[] workoutTimeValues = new String[24];
	        for(int i=0; i<workoutTimeValues.length; i++){
	        	if(i<10){
	        		workoutTimeValues[i] = "0" + Integer.toString(i) +":00";
	        	} else
	        		workoutTimeValues[i] = Integer.toString(i) +":00";
	        }
	        numberPickerWorkoutTime.setMinValue(0);
	        numberPickerWorkoutTime.setMaxValue(workoutTimeValues.length-1);
	        numberPickerWorkoutTime.setWrapSelectorWheel(false);
	        numberPickerWorkoutTime.setDisplayedValues(workoutTimeValues);  
	    
	    
	    /**
	     * Create Button click event.
	     **/
	    btnCreate.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View view) {
	        	AppUtils.scheduleBoolToString(userPreferences);
            	if(userPreferences.getSchedule().toString().equals("0000000")){
            		Toast.makeText(getBaseContext(), getResources().getString(R.string.verify), Toast.LENGTH_SHORT).show();
            	} else {
		        	long userPreferencesId = userPreferencesDao.createUserPrefrences(userPreferences);
		        	userPreferences.setId(userPreferencesId);
		        	userPreferencesDao.calcTrainingPlan(userPreferences);
		            Intent myIntent = new Intent(view.getContext(), TrainingPlanActivity.class);
		            startActivityForResult(myIntent, 0);
		            finish();
            	}
	        }
	    });
	    
        /**
         * Length Number Picker click event.
         **/
        numberPickerLength.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				userPreferences.setLength((3+newVal)*5);
			}
		});
	    
	    /**
	     * Reminder Spinner click event.
	     **/
	    spinnerReminder.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				parent.getSelectedItemPosition();
	               getResources().getStringArray(R.array.array_reminder);
	               spinnerReminder.setSelection(position);
	               userPreferences.setReminder(parent.getItemAtPosition(position).toString());		
			}
	
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}
	    });
	 
        /**
         * Workout Time Number Picker click event.
         **/
        numberPickerWorkoutTime.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				userPreferences.setWorkoutTime(newVal);
			}
		});
	
	    /**
         * Menu Button click event.
         **/
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	PopupMenu popup = new PopupMenu(UserPreferencesContActivity.this, btnMenu);
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
    
    public void onCheckboxClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		// Check which checkbox was clicked
	    switch(view.getId()) {
	        case R.id.checkbox_sunday:
	            if (checked){
	            	userPreferences.setSunday(true);
	            } else{
	            	userPreferences.setSunday(false);
	            } break;
	        case R.id.checkbox_monday:
	            if (checked){
	            	userPreferences.setMonday(true);
	            } else{
	            	userPreferences.setMonday(false);
	            } break;
	        case R.id.checkbox_tuesday:
	            if (checked){
	            	userPreferences.setTuesday(true);
	            } else{
	            	userPreferences.setTuesday(false);
	            } break;
	        case R.id.checkbox_wednesday:
	            if (checked){
	            	userPreferences.setWednesday(true);
	            } else{
	            	userPreferences.setWednesday(false);
	            } break;
	        case R.id.checkbox_thursday:
	            if (checked){
	            	userPreferences.setThursday(true);
	            } else{
	            	userPreferences.setThursday(false);
	            } break;
	        case R.id.checkbox_friday:
	            if (checked){
	            	userPreferences.setFriday(true);
	            } else{
	            	userPreferences.setFriday(false);
	            } break;
	        case R.id.checkbox_saturday:
	            if (checked){
	            	userPreferences.setSaturday(true);
	            } else{
	            	userPreferences.setSaturday(false);
	            } break;
	    }
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
