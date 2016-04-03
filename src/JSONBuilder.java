import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONBuilder{
	
	public static void JSONBuilderUtil(HashMap<String, String> fileNameMap){
		
		for(HashMap.Entry<String,String> entry : fileNameMap.entrySet()){
			System.out.println(entry.getKey()+" :: "+entry.getValue());
			
			String fileName = entry.getValue();
			File TextFileHandle = new File("/Users/PavanLupane/599-2/outfiles/"+fileName+".txt");
			File JSONFileHandle = new File("/Users/PavanLupane/599-2/outfiles/"+fileName+".json");
			
			JSONParser parser = new JSONParser();
			
			try {
				Object JsonObj = parser.parse(new FileReader("/Users/PavanLupane/599-2/outfiles/"+fileName+".json"));
				
				JSONObject jsonObject = (JSONObject) JsonObj;
				System.out.println(jsonObject);
				
				StringBuilder textFileString = new StringBuilder();
				BufferedReader br = new BufferedReader(new FileReader("/Users/PavanLupane/599-2/outfiles/"+fileName+".txt"));
				String currentLine;
				JSONObject tempObj = new JSONObject();
				while((currentLine = br.readLine())!=null){
					
					textFileString.append("|");
				
					System.out.println(currentLine);
					currentLine = currentLine.replace("\"", "");
					textFileString.append(currentLine.trim());
				}
				br.close();
				//System.out.println(textFileString.toString());
				jsonObject.put("GROBID", textFileString.toString());
				
				//System.out.println(jsonObject);
				//System.out.println(jsonObject.get("GROBID").toString());
				
				FileWriter file = new FileWriter("/Users/PavanLupane/599-2/outfiles/"+fileName+".json");
				file.write(jsonObject.toJSONString());
				file.flush();
				file.close();
				
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void JSONBuilderUtil(String fileName,HashMap<String,Set<String>> NEREntities) throws IOException{
		JSONObject mainObj = new JSONObject();
		mainObj.put("FileName", fileName);
		
		//Printing NEW and SWEET
		
		for(HashMap.Entry<String,Set<String>> entry : NEREntities.entrySet()){
			if(entry.getValue().size()!=0){
				System.out.println(entry.getKey()+" ::"+entry.getValue());
				mainObj.put(entry.getKey(), entry.getValue().toString());
			}
		}
		
		//Printing GeoTopic
		
		StringBuilder fileString = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader("/Users/PavanLupane/599-2/outfiles/GeoTopicout.txt"));
		String currentLine;
		while((currentLine = br.readLine())!=null){
			fileString.append("|");
			fileString.append(currentLine);
		}
		br.close();
		mainObj.put("GeoTopic", fileString.toString());
		
		//System.out.println(mainObj);
		
		//Write mainObject to file 
		
		FileWriter file = new FileWriter("/Users/PavanLupane/599-2/outfiles/"+fileName+".json");
		file.write(mainObj.toJSONString());
		file.flush();
		file.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
