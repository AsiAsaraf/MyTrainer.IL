package il.ac.shenkar.MyTrainerIL.entities;

import java.util.ArrayList;
import java.util.Calendar;

public class Training {
	
	//private variables
		private long id;
		private long trainingPlanId;
		private String name;
		private String executeTime;
		private Calendar executeDate;
		private int length;
		private String reminder;
		private ArrayList<ExerciseTraining> exerciseTrainingList;
		
	//private flags
		private int	doneFlag;

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
		 * @return the executeTime
		 */
		public String getExecuteTime() {
			return executeTime;
		}

		/**
		 * @param executeTime the executeTime to set
		 */
		public void setExecuteTime(String executeTime) {
			this.executeTime = executeTime;
		}

		/**
		 * @return the executeDate
		 */
		public Calendar getExecuteDate() {
			return executeDate;
		}

		/**
		 * @param executeDate the executeDate to set
		 */
		public void setExecuteDate(long timeInMiliSec) {
			this.executeDate = Calendar.getInstance();
			this.executeDate.setTimeInMillis(timeInMiliSec);
		}

		/**
		 * @return the length
		 */
		public int getLength() {
			return length;
		}

		/**
		 * @param length the length to set
		 */
		public void setLength(int length) {
			this.length = length;
		}

		/**
		 * @return the reminder
		 */
		public String getReminder() {
			return reminder;
		}

		/**
		 * @param reminder the reminder to set
		 */
		public void setReminder(String reminder) {
			this.reminder = reminder;
		}

		/**
		 * @return the exerciseTrainnigList
		 */
		public ArrayList<ExerciseTraining> getExerciseTrainingList() {
			return exerciseTrainingList;
		}

		/**
		 * @param exerciseTrainingList the exerciseList to set
		 */
		public void setExerciseTrainngList(ArrayList<ExerciseTraining> exerciseTrainingList) {
			this.exerciseTrainingList = exerciseTrainingList;
		}

		/**
		 * @return the doneFlag
		 */
		public int getDoneFlag() {
			return doneFlag;
		}

		/**
		 * @param doneFlag the doneFlag to set
		 */
		public void setDoneFlag(int doneFlag) {
			this.doneFlag = doneFlag;
		}

		public long getTrainingPlanId() {
			return trainingPlanId;
		}

		public void setTrainingPlanId(long trainingPlanId) {
			this.trainingPlanId = trainingPlanId;
		}
	
}
