package il.ac.shenkar.MyTrainerIL.activity;

import java.io.FileNotFoundException;
import java.io.InputStream;

import il.ac.shenkar.MyTrainerIL.R;
import il.ac.shenkar.MyTrainerIL.dao.ExerciseDao;
import il.ac.shenkar.MyTrainerIL.dao.LoginDao;
import il.ac.shenkar.MyTrainerIL.dao.TrainingDao;
import il.ac.shenkar.MyTrainerIL.entities.ExerciseTraining;
import il.ac.shenkar.MyTrainerIL.utils.AppUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ExerciseActivity extends Activity{

	protected ExerciseDao exerciseDao;
	protected LoginDao loginDao;
	
	protected TrainingDao trainingDao;
	private ExerciseTraining exerciseTraining;
	private Context context;
	
	//Defining layout items
	private ImageButton btnMenu;
	private TextView name;
	private TextView description;
	private ImageView image;
	private ImageButton btnExerciseBack;
	private ImageButton btnExerciseNext;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity);
        Intent intent = getIntent();
        context = this;
        exerciseDao = new ExerciseDao(context);
        loginDao = new LoginDao(context);
        if(loginDao.getLogin() == 0){
            Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
            startActivityForResult(myIntent, 0);
            finish();
        }
        
        trainingDao = new TrainingDao(context);
        //Training training = trainingDao.getTrainingById(intent.getLongExtra("training", 0));
        exerciseTraining = exerciseDao.getExerciseTrainingById(intent.getLongExtra("exerciseTraining", 0));
        long trainingId = intent.getLongExtra("training", 0);
        if(exerciseTraining == null){
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
            myIntent.putExtra("user",loginDao.getLogin());
            startActivityForResult(myIntent, 0);
            finish();
        }
        
        /**
         * Defining all layout items
         **/
        btnMenu = (ImageButton)findViewById(R.id.btn_menu);
        btnExerciseBack = (ImageButton)findViewById(R.id.btn_exercise_back);
        if((exerciseDao.getExerciseTrainingById(exerciseTraining.getId()-1) == null) || (exerciseDao.getExerciseTrainingById(exerciseTraining.getId()-1).getTrainingId() != trainingId)){
        	btnExerciseBack.setVisibility(View.INVISIBLE);
        }
        btnExerciseNext = (ImageButton)findViewById(R.id.btn_exercise_next);
        if((exerciseDao.getExerciseTrainingById(exerciseTraining.getId()+1) == null) || (exerciseDao.getExerciseTrainingById(exerciseTraining.getId()+1).getTrainingId() != trainingId)){
        	btnExerciseNext.setVisibility(View.INVISIBLE);
        }
        name = (TextView)findViewById(R.id.text_view_name);
        name.setText(exerciseTraining.getExercise().getName());
        description = (TextView)findViewById(R.id.text_view_description);
        description.setText(exerciseTraining.getExercise().getDescription());
        image = (ImageView)findViewById(R.id.image_view_exercise);
        Uri uri = Uri.parse("android.resource://" + "il.ac.shenkar.MyTrainerIL" + exerciseTraining.getExercise().getImage());
        InputStream stream;
        Drawable res;        
		try {
			stream = getContentResolver().openInputStream(uri);
			res = Drawable.createFromStream(stream, uri.toString());
			image.setImageDrawable(res);
		} catch (FileNotFoundException e) {
			//res = getResources().getDrawable(R.drawable.default_exercise_image);
			e.printStackTrace();
		}
        
/*        Uri uri = Uri.parse("android.resource://" + "il.ac.shenkar.MyTrainerIL" + "/drawable/pic1");
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
		
	    /**
         * Menu Button click event.
         **/
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	PopupMenu popup = new PopupMenu(ExerciseActivity.this, btnMenu);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.main:
                            	main();  
                                return true;
                            case R.id.training_plan:
                            	trainingPlan();  
                                return true;
                            case R.id.training:
                            	training();  
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
        
	    /**
         * Back Exercise Button click event.
         **/
        btnExerciseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(), ExerciseActivity.class);
                myIntent.putExtra("training", exerciseTraining.getTrainingId());
                myIntent.putExtra("exerciseTraining", exerciseTraining.getId()-1);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });
        
	    /**
         * Next Exercise Button click event.
         **/
        btnExerciseNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(), ExerciseActivity.class);
                myIntent.putExtra("training", exerciseTraining.getTrainingId());
                myIntent.putExtra("exerciseTraining", exerciseTraining.getId()+1);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });
        
    }
    
    public void logout(){
    	loginDao.deleteLogin();
        Intent myIntent = new Intent(this.getBaseContext(), LoginActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
    }
    
    public void main(){
        Intent myIntent = new Intent(this.getBaseContext(), MainActivity.class);
        myIntent.putExtra("user",loginDao.getLogin());
        startActivityForResult(myIntent, 0);
        finish();
    }    
    
    public void trainingPlan(){
        Intent myIntent = new Intent(this.getBaseContext(), TrainingPlanActivity.class);
        myIntent.putExtra("user",loginDao.getLogin());
        startActivityForResult(myIntent, 0);
        finish();
    }
    
    public void training(){
        Intent myIntent = new Intent(this.getBaseContext(), TrainingActivity.class);
        myIntent.putExtra("user",loginDao.getLogin());
        myIntent.putExtra("training",(long)exerciseTraining.getTrainingId());
        startActivityForResult(myIntent, 0);
        finish();
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
