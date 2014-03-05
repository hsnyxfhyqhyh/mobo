import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FileUtil {
	/*
	 * Write something to a file
	 */
	public static void writeFile(String content, String filename) {
		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(filename));

			out.print(content);
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
		}
	}
	
	/*
	 * Get each line from the text file and add it to the arraylist passed in. 
	 */
	public static ArrayList getContents(ArrayList lines, File aFile) {
		StringBuilder contents = new StringBuilder();
		if (lines == null) {
			lines = new ArrayList();
		}

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null; // not declared within while loop

				while ((line = input.readLine()) != null) {
					lines.add(line);
				}
			} finally {
				input.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return lines;
	}
}
