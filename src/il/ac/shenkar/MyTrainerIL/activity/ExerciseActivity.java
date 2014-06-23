package il.ac.shenkar.MyTrainerIL.activity;

import java.io.FileNotFoundException;
import java.io.InputStream;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.ExerciseDao;
import il.ac.shenkar.MyTrainerIL.entities.ExerciseTraining;
import android.R.drawable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ExerciseActivity extends Activity{

	protected ExerciseDao exerciseDao;
	private ExerciseTraining exerciseTraining;
	private Context context;
	
	//Defining layout items
	private TextView name;
	private TextView description;
	private ImageView image;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity);
        Intent intent = getIntent();
        context = this;
        exerciseDao = new ExerciseDao(context);
        exerciseTraining = exerciseDao.getExerciseTrainingById(intent.getLongExtra("exerciseTraining", 0));
        
        /**
         * Defining all layout items
         **/
        name = (TextView)findViewById(R.id.text_view_name);
        name.setText(exerciseTraining.getExercise().getName());
        description = (TextView)findViewById(R.id.text_view_description);
        description.setText(exerciseTraining.getExercise().getDescription());
        image = (ImageView)findViewById(R.id.image_view_exercise);
/*        Uri uri = Uri.parse("android.resource://" + "il.ac.shenkar.MyTrainerIL" + exerciseTraining.getExercise().getImage());
        InputStream stream;
        Drawable res;        
		try {
			stream = getContentResolver().openInputStream(uri);
			res = Drawable.createFromStream(stream, uri.toString());
			image.setImageDrawable(res);
		} catch (FileNotFoundException e) {
			res = getResources().getDrawable(R.drawable.default_exercise_image);
			e.printStackTrace();
		}*/
        
        Uri uri = Uri.parse("android.resource://" + "il.ac.shenkar.MyTrainerIL" + "/drawable/exercise_ball");
        InputStream stream;
        Drawable res;        
		try {
			stream = getContentResolver().openInputStream(uri);
			res = Drawable.createFromStream(stream, uri.toString());
			image.setImageDrawable(res);
		} catch (FileNotFoundException e) {
			res = getResources().getDrawable(R.drawable.exercise_img3);
			e.printStackTrace();
		}
        
    }
}
