import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
/*import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
*/

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
//import org.jsoup.Jsoup;

public class Main {
	
	private static ArrayList<Float> TTRArray = new ArrayList<>();
	private static ArrayList<Float> SmoothedTTRArray = new ArrayList<>();
	private static ArrayList<Integer> ContentArrayLines = new ArrayList<>();
	private static float stdDeviation , mean;
	private static StringBuilder outputString=new StringBuilder();
	
	public static void main(String[] args) {
						
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
		String fileName="/home/mayuri/Downloads/test.pdf";
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
		TTR(file);
		
		smoothing();
		
		getStdDev();
		
		getRealContentArea();
		
		PrintContentAreas();
		
		//java 8 feature to get a count of number of lines in a file 
		/*final Path path = Paths.get("./big_file.txt");
		try {
			final long lineCount = Files.lines(path).count();
		} catch (IOException e) {			
			e.printStackTrace();
		}*/		
	}

		
	private static void PrintContentAreas() {
		
		File fileOut=new File("content.txt");
		File fileIn=new File("output.xhtml");
		FileWriter fileWriter=null; FileReader fileReader=null;
		BufferedWriter bufferedWriter=null; BufferedReader bufferedReader=null;
		
		int i=0, j=0, lineNumber=ContentArrayLines.get(i);
		
		try {
			fileWriter=new FileWriter(fileOut);
			bufferedWriter=new BufferedWriter(fileWriter);
			fileReader=new FileReader(fileIn);
			bufferedReader = new BufferedReader(fileReader);
	        String line;
	        while (true) {	        	
	            line = bufferedReader.readLine().replaceAll("\\<.*?\\>", "");
	            if (line == null) { break; }
	            if(j==lineNumber){
		            bufferedWriter.append(line);		            
		            bufferedWriter.newLine();
		            outputString.append(line);
		            outputString.append("\n");
		            i++;
		            if(i<ContentArrayLines.size())
		            	lineNumber=ContentArrayLines.get(i);
		            else 
		            	break;
	            }
	            j++;
	        }
	        System.out.println(outputString.toString());
		} catch (IOException e1) {			
			e1.printStackTrace();
		} finally{
			try {
				bufferedReader.close();
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}


	private static void getRealContentArea() {
		
		System.out.println("\nReal Content Areas : ");
		int SmoothedTTRArraySize=SmoothedTTRArray.size();
		
		System.out.println("Smoothed Array :" +SmoothedTTRArraySize + " " + SmoothedTTRArray.toString());
				
		for(int i=0;i<SmoothedTTRArraySize;i++){
			if(SmoothedTTRArray.get(i) >= stdDeviation ){
				System.out.print(i+ " ");
				ContentArrayLines.add(i);
			}			
		}
		System.out.println("\n\nSize: "+ContentArrayLines.size());
	}


	private static void getStdDev() {
		
		DescriptiveStatistics stats = new DescriptiveStatistics();
		int SmoothedTTRArraySize= SmoothedTTRArray.size();
		
	    for (int i = 0; i <SmoothedTTRArraySize; i++)
	    	stats.addValue(SmoothedTTRArray.get(i));                	    

	    stdDeviation = (float) stats.getStandardDeviation();
	    mean=(float)stats.getMean();	    
	    stdDeviation +=mean;
	    
	    System.out.println("Std Dev: "+stdDeviation);
	}


	private static void smoothing() {
		
		int radius=2,i,sum,TTRArraySize= TTRArray.size();	
		int denominatorValue= 2*radius + 1;
		float e;				
		
		for(int index=0;index<TTRArraySize;index++){
			sum=0;
			for(i=index-radius;i<=index+radius;i++){
				if( ( i > 0) && ( i < TTRArraySize) && (i!=index) )
					sum += TTRArray.get(i);
			}
			SmoothedTTRArray.add((float) (sum/denominatorValue));					
		}				
	}

	private static void TTR(File file) {
	
		String line;
		int x,y,i=0;		
		BufferedReader bufferedReader=null;
		FileReader fileReader=null;
		try {
			fileReader = new FileReader(file);
			bufferedReader=new BufferedReader(fileReader);
			while( (line = bufferedReader.readLine()) !=null )
			{
				line= line.trim();				
				
				if( line.contains("xml") || line.isEmpty() || line.contains("head") || line.contains("script") ){
					TTRArray.add((float) 0);
					//System.out.println(i++ + "\t" + line + "\t0 special case" );
				}else{
					x = getNonTagCount(line);
					y = getTagCount(line);
					
					if(y==0)
						TTRArray.add((float) x);
					else
						TTRArray.add((float) x/y);						
					
					//System.out.println(i++ + "\t" + line + "\tWord Count " + x+ "\tTag Count " + y);	
				}			
			}
			System.out.println("TTRArray " + TTRArray.size()+ " " +  TTRArray.toString());
		} catch (IOException e1) {			
			e1.printStackTrace();
		}finally{
			try {
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}					
		}
	}

	
	//text= Jsoup.parse(line).text();
	private static int getNonTagCount(String line) {
				
		String text;
		if(line.contains("title"))
			text=line.replace("title", "").replaceAll("[</>]", "");					
		else
			text = line.replaceAll("\\<.*?\\>", "");			  					
				
		return text.length();						
	}

	private static int getTagCount(String line) {
		
		int tagCount = line.replaceAll("[^<>]", "").length();		
		return tagCount/2;
	}
	
}
