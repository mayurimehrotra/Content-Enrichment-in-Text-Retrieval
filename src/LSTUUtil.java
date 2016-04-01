import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LSTUUtil {

	public static void main(String[] args) {
		
		try{
			//shorten("abcd");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public String shorten(String realName) throws Exception{
		// TODO Auto-generated method stub
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://localhost:8080/a");

		// Request parameters and other properties.
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("lsturl", "http://www.google.com/"+realName));
		parameters.add(new BasicNameValuePair("format", "json"));
		httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
		//StringBuilder responseStr = new StringBuilder();
		String output,output2="";
		output=in.readLine();
		JSONObject myObject =(JSONObject) new JSONParser().parse(output);
		in.close();
		output=(String)myObject.get("short");
		output=output.replace("http://localhost:8080", "polar.usc.edu");
		//System.out.println(output);
		return output;
	}
	
}
