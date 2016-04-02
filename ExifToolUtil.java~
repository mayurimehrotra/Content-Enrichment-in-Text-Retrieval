import java.io.IOException;

public class ExifToolUtil {
	
	
	public static void ExifToolGenerator(String fileName){
				
		String cmd = "java -Dtika.config=/home/mayuri/ExifTool/exif-tika-config.xml -classpath /home/mayuri/Downloads/tika-app-1.12.jar org.apache.tika.cli.TikaCLI -m " + fileName+ " > ExifOut.txt";

		ProcessBuilder pb2=new ProcessBuilder("bash","-c",cmd);
		try {
			pb2.start();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
		
		
		/*Process process=null;
		BufferedReader r =null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			r = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
	        while (true) {	        	
	            line = r.readLine();
	            if (line == null) { break; }	            
	            System.out.println(line);
	        }
		} catch (IOException e) {
		
			e.printStackTrace();
		} */
		
}
		
	public static void main(String[] args) {
		
		String fileName= "/home/mayuri/ExifTool/vid1.mp4";
		ExifToolGenerator(fileName);
		//System.out.println("Done");
	}
}
