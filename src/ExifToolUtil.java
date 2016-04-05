import java.io.IOException;

public class ExifToolUtil {
	
	//Change path for config.xml and *-app.jar
	public static void ExifToolGenerator(String fileName){
				
		String cmd = "java -Dtika.config=/home/mayuri/ExifTool/exif-tika-config.xml -classpath /home/mayuri/Downloads/tika-app-1.12.jar org.apache.tika.cli.TikaCLI -m " + fileName+ " > ExifOut.txt";

		ProcessBuilder pb2=new ProcessBuilder("bash","-c",cmd);
		try {
			pb2.start();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
}
		
	public static void main(String[] args) {
		
		String fileName= "/home/mayuri/ExifTool/vid1.mp4";
		ExifToolGenerator(fileName);
	}
}
