import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        System.out.println("Public Webservice Client");
        System.out.println("Beispiel eines einfachen Webservice Clients, der den öffentlichen ");
        System.out.println("Webservice 'https://jsonplaceholder.typicode.com' nutzt und auf eine");
        System.out.println("ToDo-Liste zugreift. Details siehe Website" );
        System.out.println("");
        System.out.print("Geben Sie einen Index des zu lesenden ToDo-Eintrages ein (1-199): ");

        try {
            // create a BufferedReader using System.in
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            String strToDoIndex;
            strToDoIndex = obj.readLine();
            System.out.println("ToDo Eintrag mit index " + strToDoIndex + " wird geladen. Bitte warten ..." );
            String response = getToDoListItem(strToDoIndex);
            System.out.println("die Antwort im Json-Format ist: ");
            System.out.println(response);

            // Json Antwort umwandeln in ein ToDoItem
            //
            Gson gson = new Gson();
            TodoItem todoitem = gson.fromJson(response, TodoItem.class);

            // ... und ein wenig Ausgabe produzieren
            //
            System.out.println("todoitem.toString = " + todoitem.toString());
            System.out.println("title=" + todoitem.title);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getToDoListItem(String strItemNumber) throws IOException {

        String response = "";
        HttpURLConnection connection = (HttpURLConnection) new URL("https://jsonplaceholder.typicode.com/todos/"
                                        + strItemNumber).openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response += scanner.nextLine();
                response += "\n";
            }
            scanner.close();
        }
        else {
            response = "{}";
        }
        return response;
    }
}
