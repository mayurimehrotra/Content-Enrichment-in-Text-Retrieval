import java.io.IOException;

public class GeoTopicUtil {
	
	public static void GeoTopicGenerator(String fileName){
		String cmd = "java -classpath /home/mayuri/Documents/GeoTopic/tika-app-1.12.jar:$HOME/Documents/GeoTopic/location-ner-model:$HOME/Documents/GeoTopic/geotopic-mime org.apache.tika.cli.TikaCLI -m " + fileName + " > out.txt";
		ProcessBuilder pb=new ProcessBuilder("bash","-c",cmd);
		try {
			pb.start();
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		String fileName= "/home/mayuri/Documents/GeoTopic/geotopic-mime/org/apache/tika/mime/polar.geot";
		GeoTopicGenerator(fileName);
	}
	
}

