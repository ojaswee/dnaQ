package dnaQ.GUI.GUI_Models;


	public class CommonTableColumn {
		public final String description;
		public final String title;
		public final Class<?> columnClass;
		
		public CommonTableColumn(String description, String title, Class<?> columnClass) {
			this.description = description;
			this.title = title;
			this.columnClass = columnClass;
		}
	}
