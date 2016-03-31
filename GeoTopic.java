import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GeoTopic {

	public static void main(String[] args) throws InterruptedException {
		
		
		File file=new File("output.txt");
		FileWriter fileWriter=null;
		BufferedWriter bufferedWriter=null;
		try {
			fileWriter=new FileWriter(file);
			bufferedWriter=new BufferedWriter(fileWriter);
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		
		//String fileName="/home/mayuri/Documents/GeoTopic/test.geot";
		String command = "java -classpath /home/mayuri/Documents/GeoTopic/tika-app-1.12.jar:$HOME/Documents/GeoTopic/location-ner-model:$HOME/Documents/GeoTopic/geotopic-mime org.apache.tika.cli.TikaCLI -m /home/mayuri/Documents/GeoTopic/geotopic-mime/org/apache/tika/mime/polar.geot";
 		Process process = null ;
		try
		{			
			process = Runtime.getRuntime().exec(command);
		    BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String line;
	        while (true) {	        	
	            line = r.readLine();
	            if (line == null) { break; }	
	            System.out.println( line + " ");
	            bufferedWriter.append(line);
	            bufferedWriter.newLine();
	        }
		} catch (IOException e)
		{
		    e.printStackTrace();
		}finally{
			try {
				bufferedWriter.close();
				fileWriter.close();
				System.out.println("Done");
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
	
	}
	
}
