package il.ac.shenkar.MyTrainerIL.entities;

public class Exercise {
	private static final long serialVersionUID = 1L;
	
	//private variables
		private long id;
		private String name;
		private String description;
		private String difficulty;
		private String category;
		private int length;
		private String image;
		
	
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
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
	
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
	
		/**
		 * @return the difficulty
		 */
		public String getDifficulty() {
			return difficulty;
		}
	
		/**
		 * @param difficulty the difficulty to set
		 */
		public void setDifficulty(String difficulty) {
			this.difficulty = difficulty;
		}
	
		/**
		 * @return the category
		 */
		public String getCategory() {
			return category;
		}
	
		/**
		 * @param category the category to set
		 */
		public void setCategory(String category) {
			this.category = category;
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
		 * @return the image
		 */
		public String getImage() {
			return image;
		}
	
		/**
		 * @param image the image to set
		 */
		public void setImage(String image) {
			this.image = image;
		}
		
}
