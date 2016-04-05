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
	
	//This utility picks up the GROBID input and append to the JSON file containing NER, SWEET and Geotopic information
	public static void JSONBuilderUtil(File path){
		
		for (File fileEntry : path.listFiles()) {
	        	
	        	String f = fileEntry.getName();
	        	
	        	f = f.replace(".txt", "");
	        	
	        	if(RunnerClass.LSTUMap.containsKey(f+".pdf")){
	        		long score = 0L;
	        		String fName = RunnerClass.LSTUMap.get(f+".pdf");
	        		
	        		JSONParser parser = new JSONParser();
	    			
	    			try {
	    				Object JsonObj = parser.parse(new FileReader("/home/mayuri/JSONOuts/"+fName+".json"));
	    				
	    				JSONObject jsonObject = (JSONObject) JsonObj;
	    				//System.out.println(jsonObject);
	    				
	    				StringBuilder textFileString = new StringBuilder();
	    				BufferedReader br = new BufferedReader(new FileReader("/home/mayuri/scholarPyOut/"+f+".txt"));
	    				String currentLine;
	    				JSONObject tempObj = new JSONObject();
	    				while((currentLine = br.readLine())!=null){
	    					
	    					textFileString.append("|");
	    				
	    					//currentLine = currentLine.replace("\"", "");
	    					score++;
	    					textFileString.append(currentLine.trim());
	    				}
	    				br.close();
	    				//System.out.println(textFileString.toString());
	    				jsonObject.put("GROBID", textFileString.toString());
	    				long prevScore = (long)jsonObject.get("METADATASCORE");
	    				score += prevScore;
	    				
	    				jsonObject.put("METADATASCORE", score);
	    				
	    				//System.out.println(jsonObject);
	    				//System.out.println(jsonObject.get("GROBID").toString());
	    				
	    				FileWriter file = new FileWriter("/home/mayuri/JSONOuts/"+fName+".json");
	    				file.write(jsonObject.toJSONString());
	    				file.flush();
	    				file.close();
	    				
	    			} catch (IOException | ParseException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	        	}
	     
	       }//end of for
	}
	
	//This utility generates JSON file containing Metadata 
	public static void JSONBuilderUtil(String fileName,HashMap<String,Set<String>> NEREntities) throws IOException{
		JSONObject mainObj = new JSONObject();
		mainObj.put("FileName","polar.usc.edu/"+fileName);
		int score = 0;
		//Printing NER and SWEET
		
		for(String key: NEREntities.keySet() ){
			if(NEREntities.get(key).size()!=0){
				System.out.println(key +" ::"+NEREntities.get(key));
				mainObj.put(key, NEREntities.get(key).toString());
				score+= NEREntities.get(key).size();
			}
		}
		
		//Printing GeoTopic
		
		StringBuilder fileString = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader("/home/mayuri/GeoOut/GeoTopicout.txt"));
		String currentLine;
		while((currentLine = br.readLine())!=null){
			fileString.append("|");
			fileString.append(currentLine);
			score++;
		}
		br.close();
		mainObj.put("GeoTopic", fileString.toString());
		mainObj.put("METADATASCORE", score);
		
		//System.out.println(mainObj);
		
		//Write mainObject to file 
		
		FileWriter file = new FileWriter("/home/mayuri/JSONOuts/"+fileName+".json");
		file.write(mainObj.toJSONString());
		file.flush();
		file.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
