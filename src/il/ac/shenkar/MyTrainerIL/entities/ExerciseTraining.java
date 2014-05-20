package il.ac.shenkar.MyTrainerIL.entities;

public class ExerciseTraining {
	//private variables
		private Exercise exercise;
		private Training training;
		
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
		public Training getTraining() {
			return training;
		}

		/**
		 * @param training the training to set
		 */
		public void setTraining(Training training) {
			this.training = training;
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
