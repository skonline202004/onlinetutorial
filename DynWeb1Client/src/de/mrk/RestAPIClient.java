package de.mrk;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


import com.google.gson.Gson;

@SuppressWarnings("deprecation")
public class RestAPIClient {

	public static void main(String[] args) {
		System.out.println("start Rest API Client");
		try {
			setAdressData(new Adresse("velmans","weg 10",12345,"bochum"));
			System.out.println(getAdressDaten("RestAPIServer/velmans"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getAdressDaten(String name) throws IOException{

		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/DynWeb1/" + name).openConnection();
		
		connection.setRequestMethod("GET");

		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String response = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				response += scanner.nextLine();
				response += "\n";
			}
			scanner.close();
			return response;
		}
		
		// error occured. Return empty json "{}"
		//
		return null;
	}
	

	public static void setAdressData(Adresse adresse) throws IOException{
		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/DynWeb1/RestAPIServer").openConnection();
		
		connection.setRequestMethod("POST");

		Gson gson = new Gson();
		String postData = gson.toJson(adresse);
		postData = "json=" + postData;
		
		connection.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
	    wr.write(postData);
	    wr.flush();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			System.out.println("POST was successful.");
		}
		else {
			System.out.println("error code " + responseCode + " occured");
		}
	}
}
