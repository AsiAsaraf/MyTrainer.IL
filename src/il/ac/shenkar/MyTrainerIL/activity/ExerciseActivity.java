package il.ac.shenkar.MyTrainerIL.activity;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.ExerciseDao;
import il.ac.shenkar.MyTrainerIL.entities.Exercise;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class ExerciseActivity extends Activity{

	protected ExerciseDao exerciseDao;
	private Exercise exercise;
	private Context context;
	
	//Defining layout items
	private TextView name;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity);
        context = this;
        exerciseDao = new ExerciseDao(context);
        exercise = new Exercise();
        
        /**
         * Defining all layout items
         **/
        name = (TextView)findViewById(R.id.text_view_email);
    }
}
