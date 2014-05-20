package il.ac.shenkar.MyTrainerIL.activity;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.UserPreferencesDao;
import il.ac.shenkar.MyTrainerIL.entities.UserPreferences;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserPreferencesContActivity extends Activity{

	protected UserPreferencesDao userPreferencesDao;
	private UserPreferences userPreferences;
	private Context context;
	
	//Defining layout items
	private TextView reminder;
	private Spinner spinnerReminder;
	private TextView schedule;
	private CheckBox checkBoxSun;
	private CheckBox checkBoxMon;
	private CheckBox checkBoxTue;
	private CheckBox checkBoxWed;
	private CheckBox checkBoxThu;
	private CheckBox checkBoxFri;
	private CheckBox checkBoxSat;
	private TextView workoutTime;
	private NumberPicker numberPickerWorkoutTime;

	
	private Button btnCreate;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_preferences_cont_activity);
        Intent intent = getIntent();
        context = this;
        userPreferencesDao = new UserPreferencesDao(context);
        userPreferences = new UserPreferences();
        userPreferences.setUserId(intent.getLongExtra("user", 0));
        userPreferences.setGoal(intent.getIntExtra("goal", 0));
        userPreferences.setLength(intent.getIntExtra("length", 0));
        userPreferences.setLevel(intent.getStringExtra("level"));
        userPreferences.setMuscleFocus(intent.getStringExtra("muscleFocus"));
        userPreferences.setPhase1(intent.getBooleanExtra("phase1", false));
        userPreferences.setPhase1(intent.getBooleanExtra("phase2", false));
        
        /**
         * Defining all layout items
         **/
        reminder = (TextView)findViewById(R.id.text_view_reminder);
	    	spinnerReminder = (Spinner)findViewById(R.id.spinner_reminder);
	    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_reminder, android.R.layout.simple_spinner_item);
	    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	spinnerReminder.setAdapter(adapter);
	    schedule = (TextView)findViewById(R.id.text_view_schedule);
	    	checkBoxSun = (CheckBox)findViewById(R.id.checkbox_sunday);
	    	checkBoxMon = (CheckBox)findViewById(R.id.checkbox_monday);
	    	checkBoxTue = (CheckBox)findViewById(R.id.checkbox_tuesday);
	    	checkBoxWed = (CheckBox)findViewById(R.id.checkbox_wednesday);
	    	checkBoxThu = (CheckBox)findViewById(R.id.checkbox_thursday);
	    	checkBoxFri = (CheckBox)findViewById(R.id.checkbox_friday);
	    	checkBoxSat = (CheckBox)findViewById(R.id.checkbox_saturday);
	    workoutTime = (TextView)findViewById(R.id.text_view_workout_time);	
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
	    
	    btnCreate = (Button)findViewById(R.id.button_create);
	    
	    /**
	     * Create Button click event.
	     **/
	    btnCreate.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View view) {
	        	userPreferencesDao.scheduleBoolToString(userPreferences);
	        	userPreferencesDao.createUserPrefrences(userPreferences);
	            Intent myIntent = new Intent(view.getContext(), TrainingPlanActivity.class);
	            startActivityForResult(myIntent, 0);
	            finish();
	        }
	    });
	    
	    /**
	     * Reminder Spinner click event.
	     **/
	    spinnerReminder.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int index = parent.getSelectedItemPosition();
	               // storing string resources into Array
	               String[] level = getResources().getStringArray(R.array.array_reminder);
	               spinnerReminder.setSelection(position);
	               userPreferences.setReminder(parent.getItemAtPosition(position).toString());
	                Toast.makeText(getBaseContext(), "You have selected : " + level[index], 
	                        Toast.LENGTH_SHORT).show();				
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
