import java.io.File;

public class LoadData {
	public static void main(String[] args) {
		/* Call the following function only when initialize the database */
		
//		DBHelper.initializeDB();
		
		
//		addSet("java_amqp.xml", "电脑");
		addSet("java_core.xml", "电脑");
				
		System.out.println("ALL DONE");
	}

	private static void addSet(String fileName, String categoryName) {
		
		int categoryID = DBHelper.getCategoryID(categoryName);
		
		if (categoryID == 0 ) {
			DBHelper.addCategory(categoryName);
			categoryID = DBHelper.getCategoryID(categoryName);
		}
		
		
		addSet(fileName, categoryID);
		

	}
	
	private static void addSet(String fileName, int categoryFK) {

		SetDTO setDTO = XmlParserUtil.getSetContent(fileName);
		
		boolean createSuccessful = false;

		DBHelper.addSet(setDTO, categoryFK);

	}

}
