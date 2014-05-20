package il.ac.shenkar.MyTrainerIL.dao;

import android.content.Context;
import il.ac.shenkar.MyTrainerIL.entities.User;
import il.ac.shenkar.MyTrainerIL.helper.DatabaseHelper;

public class RegisterDao {

	private DatabaseHelper databaseHelper;
	
	/**
	 * @param context
	 */
	public RegisterDao(Context context){
		databaseHelper = new DatabaseHelper(context);
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 */
	public long createRegister(String firstName, String lastName, String email, String password){
		User user = new User(firstName, lastName, email, password);
		long user_id = 0;
		user_id = databaseHelper.createUser(user);
		return user_id;
	}
	
}
