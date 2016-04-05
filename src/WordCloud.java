import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//This utility is used to generate JSON file from The text for word cloud visualization
public class WordCloud {

	public static void main(String[] args) {
		WordCloud wc = new WordCloud();
		StringBuilder t = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader("TTRdata.txt"));
			String currentLine = null;
			
				while((currentLine = br.readLine())!=null){
					t.append(currentLine);
				}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
		}
		wc.createJSON(t.toString());
	}

	private void createJSON(String text) {
		// TODO Auto-generated method stub
		HashMap<String,String> stopwords = new HashMap<String, String>();
		List<String> input =new ArrayList<String>();
		  String line = null;
		  try{	   
			  FileReader fileReader = new FileReader("stopwords.txt");
	          BufferedReader bufferedReader =new BufferedReader(fileReader);
	          while((line = bufferedReader.readLine()) != null)
	            	stopwords.put(line,"");
	          bufferedReader.close();            
	      }
	      catch(FileNotFoundException ex){
	            ex.printStackTrace();	
		  }
	      catch(IOException ex){
	            ex.printStackTrace();
	      }
		  
		StringTokenizer list = new StringTokenizer(text);
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		while (list.hasMoreTokens()){
				String key=list.nextToken();
				if(!stopwords.containsKey(key)){
					  if(map.containsKey(key))
						  map.put(key, map.get(key)+1);
					  else
						  map.put(key, 1);

				}
		  }
		JSONArray jlist = new JSONArray();
		

		for(String key:map.keySet()){

			JSONObject obj = new JSONObject();
			obj.put("size", map.get(key));
			obj.put("text", key);
			jlist.add(obj);

		}
		try {

			FileWriter file = new FileWriter("WCJSON.json");
			file.write(jlist.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Hi");
	}
}	
