import java.io.IOException;

public class GeoTopicUtil {
	
	public static void GeoTopicGenerator(String fileName){
		String cmd = "java -classpath /PATH/TO/tika-app-1.12.jar:$HOME/Documents/GeoTopic/location-ner-model:$HOME/Documents/GeoTopic/geotopic-mime org.apache.tika.cli.TikaCLI -m " + fileName + " > /TEMP/PATH/TO/GeoTopicout.txt";
		ProcessBuilder pb=new ProcessBuilder("bash","-c",cmd);
		
		try {
			Process GeoProcess = pb.start();
			GeoProcess.waitFor();
		} catch (IOException e1) {			
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		String fileName= "/PATH/TO/polar.geot";
		GeoTopicGenerator(fileName);
	}
	
}

