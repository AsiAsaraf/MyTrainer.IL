package il.ac.shenkar.MyTrainerIL.entities;

public class User {

	//private variables
		private long id;
		private String firstName;
		private String lastName;
		private String email;
		private String password;
		
		private UserPreferences userPreferences;
		
		public User() {
			super();
		}
		
		/**
		 * @param firstName
		 * @param lastName
		 * @param email
		 * @param password
		 */
		public User(String firstName, String lastName, String email, String password) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.password = password;
		}

		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(long id) {
			this.id = id;
		}

		/**
		 * @return the firstName
		 */
		public String getFirstName() {
			return firstName;
		}

		/**
		 * @param firstName the firstName to set
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		/**
		 * @return the lastName
		 */
		public String getLastName() {
			return lastName;
		}

		/**
		 * @param lastName the lastName to set
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @param email the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * @return the pass
		 */
		public String getPass() {
			return password;
		}

		/**
		 * @param pass the pass to set
		 */
		public void setPass(String pass) {
			this.password = pass;
		}

		/**
		 * @return the userPreferences
		 */
		public UserPreferences getUserPreferences() {
			return userPreferences;
		}

		/**
		 * @param userPreferences the userPreferences to set
		 */
		public void setUserPreferences(UserPreferences userPreferences) {
			this.userPreferences = userPreferences;
		}


}
