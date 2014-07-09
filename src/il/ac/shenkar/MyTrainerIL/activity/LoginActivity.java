package il.ac.shenkar.MyTrainerIL.activity;

import il.ac.shenkar.MyTrainerIL.dao.LoginDao;
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

public class LoginActivity extends Activity {

	protected LoginDao loginDao;
	private Context context;
		
	//Defining layout items
	private EditText inputEmail;
	private EditText inputPassword;
	
	private ImageButton btnLogin;
	private TextView register;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        context = this;
        loginDao = new LoginDao(context);
    	Intent intent = getIntent();
    	
        long userId = intent.getLongExtra("user", 0);
        if(userId == 0){
        	userId = loginDao.getLogin();
        	if(userId != 0){
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
            myIntent.putExtra("user",userId);
            startActivityForResult(myIntent, 0);
            finish();
        	}
        }
           	
    	
        setContentView(R.layout.login_activity);
        


        /**
         * Defining all layout items
         **/
        inputEmail = (EditText)findViewById(R.id.edit_text_email);
        inputPassword = (EditText)findViewById(R.id.edit_text_password);
        btnLogin = (ImageButton)findViewById(R.id.btn_login);
        register = (TextView)findViewById(R.id.text_view_link_to_register);
        
        /**
         * Button which Switches back to the login screen on clicked
         **/
        register.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
                Intent myIntent = new Intent(v.getContext(), RegisterActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
				return false;
			}
        });
        /**
         * Register Button click event.
         * A Toast is set to alert when the fields are empty.
         **/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (  ( !inputEmail.getText().toString().equals("")) && ( !inputPassword.getText().toString().equals("")) )
                {
                	long userId = loginDao.createLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
                	if(userId != 0){
                        Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                        myIntent.putExtra("user",userId);
                        startActivityForResult(myIntent, 0);
                        finish();
                	} else {
                		Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
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
    
    @Override
    public void onDestroy() {
    	super.onStop();
    }
}
