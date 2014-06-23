package il.ac.shenkar.MyTrainerIL.entities;

public class ExerciseTraining {
	//private variables
		private Exercise exercise;
		private long trainingId;
		
	//private flags
		private int	doneFlag;

		/**
		 * @return the exercise
		 */
		public Exercise getExercise() {
			return exercise;
		}

		/**
		 * @param exercise the exercise to set
		 */
		public void setExercise(Exercise exercise) {
			this.exercise = exercise;
		}

		/**
		 * @return the training
		 */
		public long getTrainingId() {
			return trainingId;
		}

		/**
		 * @param training the training to set
		 */
		public void setTrainingId(long trainingId) {
			this.trainingId = trainingId;
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

}
