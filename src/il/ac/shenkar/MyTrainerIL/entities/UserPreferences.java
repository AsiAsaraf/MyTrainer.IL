package il.ac.shenkar.MyTrainerIL.entities;

public class UserPreferences {

	//private variables
		private long id;
		private long userId;
		private int goal;
		private int length;
		private String level;
		private String muscleFocus;
		private Boolean phase1;
		private Boolean phase2;
		private String schedule;
		private Boolean sunday;
		private Boolean monday;
		private Boolean tuesday;
		private Boolean wednesday;
		private Boolean thursday;
		private Boolean friday;
		private Boolean saturday;
		private int workoutTime;
		private String reminder;
		
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
		 * @return the userId
		 */
		public long getUserId() {
			return userId;
		}
		/**
		 * @param userId the userId to set
		 */
		public void setUserId(long userId) {
			this.userId = userId;
		}
		/**
		 * @return the goal
		 */
		public int getGoal() {
			return goal;
		}
		/**
		 * @param goal the goal to set
		 */
		public void setGoal(int goal) {
			this.goal = goal;
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
		 * @return the level
		 */
		public String getLevel() {
			return level;
		}
		/**
		 * @param level the level to set
		 */
		public void setLevel(String level) {
			this.level = level;
		}
		/**
		 * @return the muscleFocus
		 */
		public String getMuscleFocus() {
			return muscleFocus;
		}
		/**
		 * @param muscleFocus the muscleFocus to set
		 */
		public void setMuscleFocus(String muscleFocus) {
			this.muscleFocus = muscleFocus;
		}
		/**
		 * @return the phase1
		 */
		public Boolean getPhase1() {
			return phase1;
		}
		/**
		 * @param phase1 the phases to set
		 */
		public void setPhase1(Boolean phase1) {
			this.phase1 = phase1;
		}
		/**
		 * @return the phase1
		 */
		public Boolean getPhase2() {
			return phase2;
		}
		/**
		 * @param phase1 the phases to set
		 */
		public void setPhase2(Boolean phase2) {
			this.phase2 = phase2;
		}
		/**
		 * @return the schedule
		 */
		public String getSchedule() {
			return schedule;
		}
		/**
		 * @param schedule the schedule to set
		 */
		public void setSchedule(String schedule) {
			this.schedule = schedule;
		}
		public Boolean getSunday() {
			return sunday;
		}
		public void setSunday(Boolean sunday) {
			this.sunday = sunday;
		}
		public Boolean getMonday() {
			return monday;
		}
		public void setMonday(Boolean monday) {
			this.monday = monday;
		}
		public Boolean getTuesday() {
			return tuesday;
		}
		public void setTuesday(Boolean tuesday) {
			this.tuesday = tuesday;
		}
		public Boolean getWednesday() {
			return wednesday;
		}
		public void setWednesday(Boolean wednesday) {
			this.wednesday = wednesday;
		}
		public Boolean getThursday() {
			return thursday;
		}
		public void setThursday(Boolean thursday) {
			this.thursday = thursday;
		}
		public Boolean getFriday() {
			return friday;
		}
		public void setFriday(Boolean friday) {
			this.friday = friday;
		}
		public Boolean getSaturday() {
			return saturday;
		}
		public void setSaturday(Boolean saturday) {
			this.saturday = saturday;
		}
		/**
		 * @return the workoutTime
		 */
		public int getWorkoutTime() {
			return workoutTime;
		}
		/**
		 * @param workoutTime the workoutTime to set
		 */
		public void setWorkoutTime(int workoutTime) {
			this.workoutTime = workoutTime;
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
	
}
