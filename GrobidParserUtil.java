import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GrobidParserUtil {
	public static void grobidGenerator(File path) throws IOException,InterruptedException,ParserConfigurationException, SAXException{
	
	System.out.println("Processing Files....");
//	ProcessBuilder pb = new ProcessBuilder("java","-Xmx1024m","-jar","/Users/PavanLupane/599-2/grobidWork/src/grobid/grobid-core/target/grobid-core-0.4.1-SNAPSHOT.one-jar.jar","-gH","/Users/PavanLupane/599-2/grobidWork/src/grobid/grobid-home/","-gP","/Users/PavanLupane/599-2/grobidWork/src/grobid/grobid-home/config/grobid.properties","-dIn","/Users/PavanLupane/599-2/grobidWork/src/grobid/papers/","-dOut","/Users/PavanLupane/599-2/grobidWork/src/grobid/out","-exe","processFullText"); 
//	Process grobidProcess = pb.start();
//	grobidProcess.waitFor();
		
		for (File fileEntry : path.listFiles()) {
			if(fileEntry.toString().contains(".pdf")){
				System.out.println("Processing File ::"+ fileEntry);
				
				int index =	fileEntry.toString().lastIndexOf("/");
				String fileName = fileEntry.toString().substring(index+1);
//				System.out.println("FIlename is ::"+fileName);
				int dotIndex = fileName.indexOf('.');
				String fileNamePart = fileName.substring(0, dotIndex);
//				System.out.println("name is ::"+fileNamePart);
				
				String xmlPathString = "/Users/PavanLupane/599-2/grobidWork/src/grobid/out/"+fileNamePart+".tei.xml";
				File xmlFile = new File(xmlPathString);
				
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
				Document doc = dbBuilder.parse(xmlFile);
				doc.getDocumentElement().normalize();
				
				NodeList authorsFirstNameNode = doc.getElementsByTagName("forename");
				Node foreNameNode = authorsFirstNameNode.item(0);
				String firstName = foreNameNode.getTextContent();
				
				NodeList authorsSurnameNode = doc.getElementsByTagName("surname");
				Node surnameNode = authorsSurnameNode.item(0);
				String surname = surnameNode.getTextContent();
				
				String authorName = firstName+" "+surname;
				System.out.println("First Author is :: "+authorName);
				
				
				ProcessBuilder pb2 = new ProcessBuilder("python","/Users/PavanLupane/599-2/scholarPyWork/scholar.py/scholar.py","-c","20","--author",authorName,">>","/Users/PavanLupane/599-2/scholarPyWork/scholar.py/temp.txt"); 
				Process scholarPyProcess = pb2.start();
				scholarPyProcess.waitFor();
					
			}	
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String pathString="/Users/PavanLupane/599-2/grobidWork/src/grobid/papers";
		 File PDFpath =new File(pathString);
		 try {
			grobidGenerator(PDFpath);
			
			
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
