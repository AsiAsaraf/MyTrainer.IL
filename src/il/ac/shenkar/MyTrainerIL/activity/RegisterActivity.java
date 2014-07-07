package il.ac.shenkar.MyTrainerIL.activity;

import il.ac.shenkar.MyTrainerIL.dao.RegisterDao;
import il.ac.shenkar.MyTrainerIL.utils.AppUtils;
import il.ac.shenkar.MyTrainerIL.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	protected RegisterDao registerDao;
	private Context context;
	
	//Defining layout items
	private EditText inputLastName;
	private EditText inputFirstName;
	private EditText inputEmail;
	private EditText inputPassword;
	
	private ImageButton btnRegister;
	private TextView login;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        context = this;
        registerDao = new RegisterDao(context);
        
        /**
         * Defining all layout items
         **/
        inputLastName = (EditText)findViewById(R.id.edit_text_last_name);
        inputFirstName = (EditText)findViewById(R.id.edit_text_first_name);
        inputEmail = (EditText)findViewById(R.id.edit_text_email);
        inputPassword = (EditText)findViewById(R.id.edit_text_password);
        btnRegister = (ImageButton)findViewById(R.id.image_button_register);
        login = (TextView)findViewById(R.id.text_view_link_to_login);
        
        /**
         * Button which Switches back to the login screen on clicked
         **/
        login.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
				return false;
			}
        });
    	/**
         * Register Button click event.
         * A Toast is set to alert when the fields are empty.
         **/
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (  ( !inputEmail.getText().toString().equals("")) && ( inputEmail.getText().toString().contains("@")) && ( !inputPassword.getText().toString().equals("")) && ( !inputFirstName.getText().toString().equals("")) && ( !inputLastName.getText().toString().equals("")) )
                {
                	long user_id = registerDao.createRegister(inputFirstName.getText().toString(), inputLastName.getText().toString(), inputEmail.getText().toString(), inputPassword.getText().toString());
                	if(user_id != 0){
                        Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                        startActivityForResult(myIntent, 0);
                        finish();
                	} else {
                		Toast.makeText(getApplicationContext(), getResources().getString(R.string.register_failed), Toast.LENGTH_SHORT).show();
                	}
                }
                else
                {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.verify), Toast.LENGTH_SHORT).show();
                }
            }
        });
       
    }
    
    @Override
    public void onBackPressed() {
    	AppUtils.onButtonBackPressed(this);
    }
}
