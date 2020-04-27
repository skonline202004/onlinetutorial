package de.mrk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class RestAPIServer
 */
@WebServlet("/RestAPIServer/*")
public class RestAPIServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestAPIServer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");		
		String requestUrl = request.getRequestURI();
		String name = requestUrl.substring("/DynWeb1/RestAPIServer".length());
		// Beispiel: http://localhost:8080/DynWeb1/RestAPIServer/velmans
		if (name.length()>0)
			name = name.substring(1); // delete /-char
		// hier gilt: name = velmans aus obigem Beispiel
		
		Adresse adresse = Adressen.getInstance().getAdresse(name);
		// wandle adresse um in einen Json-String
		//
		String jsonString = "{}";
		if (adresse != null)
		{
			// zum Namen wurde ein Adressdatensatz gefunden ...
			Gson gson = new Gson();
      		jsonString = gson.toJson(adresse);
      		// jsString enthält nun die Adresse im Json-Format
      		//
		}
		// Ergebnis zurück an den Aufrufer senden
		//
		response.getWriter().append(jsonString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		String jsonString = request.getParameter("json");
		Gson gson = new Gson();
		Adresse adresse = gson.fromJson(jsonString, Adresse.class);
		Adressen.getInstance().putAdresse(adresse);
	}

}
