import java.io.IOException;

public class SolrUtility {

	//This utility ingests metadata jsons into the solr core
	public static void main(String[] args) {
	
		
		String cmd1="/home/mayuri/solr-4.10.0/bin/solr start";
		String fileName= "books.json";
		String cmd2= "curl 'http://localhost:8983/solr/final/update?commit=true' --data-binary @/media/mayuri/Data/" +fileName+ " -H 'Content-type:application/json' > SolrOut.txt ";
		String cmd3="/home/mayuri/solr-4.10.0/bin/solr restart";
		
		ProcessBuilder pb1=new ProcessBuilder("bash","-c",cmd1);
		ProcessBuilder pb2=new ProcessBuilder("bash","-c",cmd2);
		ProcessBuilder pb3=new ProcessBuilder("bash","-c",cmd3);
		
		try {
			//pb1.start();
			pb3.start();
			pb2.start();
			System.out.println("Done");		
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}	
}
