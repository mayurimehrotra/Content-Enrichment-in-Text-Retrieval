import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.tika.parser.ner.regex.PDFRenameUtil;
import org.xml.sax.SAXException;

public class RunnerClass {
	
	public static String TTRRunner(String fileName){
		File file=new File("output.xhtml");
		FileWriter fileWriter=null;
		BufferedWriter bufferedWriter=null;
		try {
			fileWriter=new FileWriter(file);
			bufferedWriter=new BufferedWriter(fileWriter);
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		
		//create a new file with xhtml content of the input file
				String command = "java -jar /home/mayuri/Downloads/tika-app-1.12.jar -x " + fileName;
				Process process = null ;
				try
				{
				    process = Runtime.getRuntime().exec(command);
				    BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
			        String line;
			        while (true) {	        	
			            line = r.readLine();
			            if (line == null) { break; }	            
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
					} catch (IOException e) {
						e.printStackTrace();
					}			
				}
				
				TextToTagRatioUtil TTRObj = new TextToTagRatioUtil();
				TTRObj.TTR(file);
				TTRObj.smoothing();
				TTRObj.getStdDev();
				TTRObj.getRealContentArea();
				String TTRString = TTRObj.PrintContentAreas();							
				return TTRString;
								
	}//end of TTRRunner
	
	public static String LSTURunner(String dummyPath) throws Exception{
		LSTUUtil shortnerObj = new LSTUUtil();
		String urlString = shortnerObj.shorten(dummyPath);
		return urlString;
	}//end of LSTURunner
	
	public static void NERParserRunner(String text) throws Exception{
		HashMap<String,Set<String>> NEREntities = NERRegexUtil.extractNER(text);
		System.out.println("hi");
		//output NEREntities to JSON file code goes here
		
		
	}//end of NERParserRunner
	
	public static void GROBIDRunner(File path){
		try {
			
			try {
				GrobidParserUtil.grobidGenerator(path);
				
			} catch (SAXException | ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}//end of GROBIDRunner
	
	public static void GeoTopicRunner(String fileContent){
		FileOutputStream fop = null;
		File file;
		try {
			file = new File("temp.geot");
			fop = new FileOutputStream(file);

			if (!file.exists()) {
				file.createNewFile();
			}

			byte[] contentInBytes = fileContent.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		GeoTopicUtil.GeoTopicGenerator("temp.geot");
	}//end of GeoTopicRunner
	
	public static void FileIterator(File path)throws Exception{
		for (File fileEntry : path.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	FileIterator(fileEntry);
	        } 
	        else {
	            String fileHandle = fileEntry.getPath();
	            
	    		String TTRString = TTRRunner(fileHandle);
	    		System.out.println("TTRString :" +TTRString);
	    		String shortUrl = LSTURunner(fileHandle);
	    		System.out.println(shortUrl);
	    		NERParserRunner(TTRString);
	    		GeoTopicRunner(TTRString);
	            
	        }
	    }
	}

	public static void main(String[] args) throws Exception{
		File fileHandle = new File("/home/mayuri/demo");
		FileIterator(fileHandle);
		
		File pdfPathForGrobid = new File("/home/mayuri/demo/");
		PDFRenameUtil.RenamePDF(pdfPathForGrobid);
		GROBIDRunner(pdfPathForGrobid);
	}

}

