package il.ac.shenkar.MyTrainerIL.dao;

import il.ac.shenkar.MyTrainerIL.helper.DatabaseHelper;
import android.content.Context;

public class LoginDao {
	
	private DatabaseHelper databaseHelper;
	
	/**
	 * @param context
	 */
	public LoginDao(Context context){
		databaseHelper = new DatabaseHelper(context);
	}
	

	public long createLogin(String email, String password) {
		long user_id = databaseHelper.checkUser(email, password);
		if(user_id != 0 ) {
			databaseHelper.createLogin(user_id);
			return user_id;
		} else {
			return 0;
		}
		
	}
	
	public long getLogin() {
		long userId = databaseHelper.getLogin();
		if(userId != 0){
			return userId;
		}
		return 0;
	}
	
	public Boolean deleteLogin() {
		long user_id = databaseHelper.getLogin();
		if((user_id != 0) && (databaseHelper.deleteLogin(user_id))) {
			return true;
		} else {
			return false;
		}
	}

}
