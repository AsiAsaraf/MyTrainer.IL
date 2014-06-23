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

public class UserPreferencesActivity extends Activity{

	protected UserPreferencesDao userPreferencesDao;
	private UserPreferences userPreferences;
	private Context context;
	
	//Defining layout items
	private TextView goal;
	private RadioGroup radioGroupGoal;
	private RadioButton radioButtonGoal;
	private TextView length;
	private NumberPicker numberPickerLength;
	private TextView level;
	private Spinner spinnerLevel;
	private TextView muscleFocus;
	private Spinner spinnerMuscleFocus;
	private TextView phases;
	private CheckBox checkBoxPhases1;
	private CheckBox checkBoxPhases2;
	
	private Button btnNext;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_preferences_activity);
        Intent intent = getIntent();
        context = this;
        final long userId = intent.getLongExtra("user", 0);
        userPreferencesDao = new UserPreferencesDao(context);
        userPreferences = new UserPreferences();
        userPreferences.setUserId(userId);
        
        /**
         * Defining all layout items
         **/
        goal = (TextView)findViewById(R.id.text_view_goal);
        	radioGroupGoal = (RadioGroup)findViewById(R.id.radio_group_goal);
        length = (TextView)findViewById(R.id.text_view_length);
	    	numberPickerLength = (NumberPicker)findViewById(R.id.number_picker_length);
	        String[] lengthValues = new String[16];
	        for(int i=3; i<lengthValues.length+3; i++){
	        	if(i<4)
	        	   lengthValues[i-3] = Integer.toString(i*5);
	        	lengthValues[i-3] = Integer.toString(i*5);
	        }
	        numberPickerLength.setMinValue(0);
	        numberPickerLength.setMaxValue(lengthValues.length+1);
	        numberPickerLength.setWrapSelectorWheel(false);
	        numberPickerLength.setDisplayedValues(lengthValues);  
        level = (TextView)findViewById(R.id.text_view_level);
        	spinnerLevel = (Spinner)findViewById(R.id.spinner_level);
        	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_level, android.R.layout.simple_spinner_item);
        	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinnerLevel.setAdapter(adapter);
        muscleFocus = (TextView)findViewById(R.id.text_view_muscle_focus);
        	spinnerMuscleFocus = (Spinner)findViewById(R.id.spinner_muscle_focus);
        	ArrayAdapter<CharSequence> muscleAdarpter = ArrayAdapter.createFromResource(this, R.array.array_muscle_focus, android.R.layout.simple_spinner_item);
        	muscleAdarpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinnerMuscleFocus.setAdapter(muscleAdarpter);
        phases = (TextView)findViewById(R.id.text_view_phases);
        	checkBoxPhases1 = (CheckBox)findViewById(R.id.checkbox_phases1);
        	checkBoxPhases2 = (CheckBox)findViewById(R.id.checkbox_phases2);
        
        btnNext = (Button)findViewById(R.id.button_next);
        
        /**
         * Next Button click event.
         **/
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {            	
                Intent myIntent = new Intent(view.getContext(), UserPreferencesContActivity.class);
                myIntent.putExtra("user", userPreferences.getUserId());
                myIntent.putExtra("goal",userPreferences.getGoal());
                myIntent.putExtra("length", userPreferences.getLength());
                myIntent.putExtra("level",userPreferences.getLevel());
                myIntent.putExtra("muscleFocus",userPreferences.getMuscleFocus());
                myIntent.putExtra("phase1", userPreferences.getPhase1());
                myIntent.putExtra("phase2", userPreferences.getPhase2());
                startActivityForResult(myIntent, 0);
                finish();
            }
        });
        
        /**
         * Level Spinner click event.
         **/
        spinnerLevel.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int index = parent.getSelectedItemPosition();
	               // storing string resources into Array
	               String[] level = getResources().getStringArray(R.array.array_level);
	               spinnerLevel.setSelection(position);
	               userPreferences.setLevel(parent.getItemAtPosition(position).toString());
	                Toast.makeText(getBaseContext(), "You have selected : " + level[index], 
	                        Toast.LENGTH_SHORT).show();				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
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
         * Muscle Focus Spinner click event.
         **/
        spinnerMuscleFocus.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int index = parent.getSelectedItemPosition();
	               // storing string resources into Array
	               String[] muscleFocus = getResources().getStringArray(R.array.array_muscle_focus);
	               //userPreferences.setMuscleFocus(parent.getItemAtPosition(position).toString());
	               userPreferences.setMuscleFocus(position); 
	               Toast.makeText(getBaseContext(), "You have selected : " + muscleFocus[index], 
	                        Toast.LENGTH_SHORT).show();				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}
        });
       
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
    
    /*private void verifyAll(){
    	if((this.userPreferences.getGoal() != 0) && (this.userPreferences.getLength() != 0) && (this.userPreferences.getLevel() != null) && (this.userPreferences.getMuscleFocus() != null)){
    		
    	} else {
    		Toast.makeText(getBaseContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
    	}
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
