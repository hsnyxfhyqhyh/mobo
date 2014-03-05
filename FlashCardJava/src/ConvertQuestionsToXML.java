import java.io.File;
import java.util.ArrayList;


public class ConvertQuestionsToXML {
	public static void main (String args[]) {
		int lineCounter = 1;
		String fileName = "java_core.txt";
		
		String xmlFileName = "java_core.xml";
		
		String setName = "Java - Java core";
		
		StringBuffer sb = new StringBuffer();
		
		ArrayList lines = new ArrayList();
		FileUtil.getContents(lines, new File(fileName) );
		
		sb.append("<Sets>");
		sb.append(String.format("<Set name=\"%s\">", setName));
		for (int i=0; i< lines.size(); i++) {
			String line = lines.get(i).toString();
			
			if (line.startsWith(">>")) {
				lineCounter = 1;
				sb.append("<Question>");
				sb.append(String.format("<Title>%s</Title><Answer>", line.substring(2)));
			} else if (line.startsWith("####")) {
				sb.append("</Answer></Question>");
			} else if (line.trim().equals("")) {
				
			} else {
				sb.append(String.format("<Line number=\"%d\">%s</Line>", lineCounter, line));
				lineCounter++;
			}
		}
		
		sb.append("</Set>");
		sb.append("</Sets>");
		FileUtil.writeFile(sb.toString(), xmlFileName);
		System.out.println("DONE");
		
	}
}

/*
<Question>
<Title>What is consumer driven messaging?</Title>
<Answer>
	<Line number="1">JUNK</Line>
	<Line number="2">JUNK</Line>
	<Line number="3">JUNK</Line>
</Answer>
</Question>
*/