import java.util.ArrayList;


public class QuestionDTO {
	private String title; 
	
	private ArrayList<String> lines = new ArrayList<String>() ;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addLine(String line) {
		lines.add(line);
	}
	
	public String getAnswer() {
		String s = "";
		for (int i=0; i< lines.size(); i++) {
			s = s +lines.get(i) + "\n\n";
		}
		
		return s;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("\nQUESTION TITLE: " + title);
		sb.append("\nAnswer");
		for (int i=0; i<lines.size(); i++){
			String line = lines.get(i);
			sb.append("\n\t" + line); 
		}
		
		return sb.toString();
	}
}
