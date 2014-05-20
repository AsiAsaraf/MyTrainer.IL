package il.ac.shenkar.MyTrainerIL.entities;

import java.util.List;

public class TrainingPlan {
	private static final long serialVersionUID = 1L;
	
	//private variables
		private long id;
		private User user;
		private String name;
		private List<Training> trainingList;
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
		 * @return the user
		 */
		public User getUser() {
			return user;
		}
		/**
		 * @param user the user to set
		 */
		public void setUser(User user) {
			this.user = user;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the trainingList
		 */
		public List<Training> getTrainingList() {
			return trainingList;
		}
		/**
		 * @param trainingList the trainingList to set
		 */
		public void setTrainingList(List<Training> trainingList) {
			this.trainingList = trainingList;
		}

}
