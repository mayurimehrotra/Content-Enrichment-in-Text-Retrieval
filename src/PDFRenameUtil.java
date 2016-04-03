

import java.io.File;

public class PDFRenameUtil {

	public static void RenamePDF(File path)throws Exception{
		for (File fileEntry : path.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	RenamePDF(fileEntry);
	        } 
	        else {
	            String fileHandle = fileEntry.getName();
	            if(!fileHandle.endsWith(".pdf")){
	            	String renamedFile = fileEntry.toString()+".pdf";
	 	            File outFileName =new File(renamedFile);
	 	            fileEntry.renameTo(outFileName);
	            }      
	        }
	    }
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String pathString="/home/mayuri/demo/";
		File PDFpath =new File(pathString);
		RenamePDF(PDFpath);
		
		
		
	}

}
