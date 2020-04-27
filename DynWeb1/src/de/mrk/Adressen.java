package de.mrk;

import java.util.HashMap;
import java.util.Map;

public class Adressen {

	private Map<String, Adresse> adresseMap = new HashMap<>();
	
	private static Adressen instance = new Adressen();
	
	public static Adressen getInstance(){
		return instance;
	}

	private Adressen(){
	}

	public Adresse getAdresse(String name) {
		return adresseMap.get(name);
	}

	public void putAdresse(Adresse adresse) {
		adresseMap.put(adresse.name, adresse);
	}
}
