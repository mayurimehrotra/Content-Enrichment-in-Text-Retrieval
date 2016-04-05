import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GrobidParserUtil {		//This function runs GROBID utility on input PDF files
	
	public static void grobidGenerator(File path) throws IOException,InterruptedException,SAXException,ParserConfigurationException{
		System.out.println("Processing Files....");
		
		String inputPapersPath = path.toString();
		ProcessBuilder pb = new ProcessBuilder("java","-Xmx1024m","-jar","/home/mayuri/grobid/src/grobid/grobid-core/target/grobid-core-0.4.1-SNAPSHOT.one-jar.jar","-gH","/home/mayuri/grobid/src/grobid/grobid-home/","-gP","/home/mayuri/grobid/src/grobid/grobid-home/config/grobid.properties","-dIn",inputPapersPath,"-dOut","/home/mayuri/grobidOut/","-exe","processFullText","-r"); 
		Process grobidProcess = pb.start();
		grobidProcess.waitFor();
		
		System.out.println("Calling Student.py....");
		String spyString = "/home/mayuri/grobidOut/";
		File spyPath = new File(spyString);
		scholarPyRunner(spyPath);
		
	}
	
	// This function runs scholar.py utility on tei.xml files generated in GROBID utility
	public static void scholarPyRunner(File path) throws IOException,InterruptedException,ParserConfigurationException, SAXException{
	
		for (File fileEntry : path.listFiles()) {
			if(fileEntry.isDirectory()){
				scholarPyRunner(fileEntry);
			}else{
			
				if(fileEntry.toString().contains(".tei.xml")){
					System.out.println("Processing File ::"+ fileEntry);
					
					int index =	fileEntry.toString().lastIndexOf("/");
					String fileName = fileEntry.toString().substring(index+1);
	//				System.out.println("FIlename is ::"+fileName);
					int dotIndex = fileName.indexOf('.');
					String fileNamePart = fileName.substring(0, dotIndex);
	//				System.out.println("name is ::"+fileNamePart);
					
					String xmlPathString = fileEntry.toString();
					File xmlFile = new File(xmlPathString);
					
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
					Document doc = dbBuilder.parse(xmlFile);
					doc.getDocumentElement().normalize();
					
					NodeList authorsFirstNameNode = doc.getElementsByTagName("forename");
					if(authorsFirstNameNode.item(0) != null){
					Node foreNameNode = authorsFirstNameNode.item(0);
					NodeList authorsSurnameNode = doc.getElementsByTagName("surname");
					Node surnameNode = authorsSurnameNode.item(0);
					//if(!foreNameNode.getTextContent().isEmpty()){
						String firstName = foreNameNode.getTextContent();
						String surname = surnameNode.getTextContent();
						
						String authorName = firstName+" "+surname;
						//System.out.println("First Author is :: "+authorName);
						
						String t = "python /home/mayuri/Scholar/src/scholar.py/scholar.py -c 20 --author "+ authorName + "- csv >> /home/mayuri/scholarPyOut/"+fileNamePart+".txt";
						ProcessBuilder pb2 = new ProcessBuilder("bash","-c",t); 
						//ProcessBuilder pb2 = new ProcessBuilder(t);
						Process scholarPyProcess = pb2.start();
						scholarPyProcess.waitFor();
					}

				}	
			}	
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Input PDF paper directory path
		String pathString="/Users/PavanLupane/599-2/grobidWork/src/grobid/papers";
		 File PDFpath =new File(pathString);
		 try {
			grobidGenerator(PDFpath);
			scholarPyRunner(PDFpath);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO: handle exception
		}
	}

}
