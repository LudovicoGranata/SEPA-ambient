package it.unibo.arces.wot.sepa.apps.ambient;

import it.unibo.arces.wot.sepa.commons.response.QueryResponse;
import it.unibo.arces.wot.sepa.commons.response.Response;
import it.unibo.arces.wot.sepa.commons.sparql.Bindings;
import it.unibo.arces.wot.sepa.pattern.GenericClient;

public class AmbientMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//istanzio oggetto della classe di sostegno ConfigurationProvider		
		
		//local
		ConfigurationProvider conf=new ConfigurationProvider("ambientSEPA_local.jsap");
		
		//unibo
		//ConfigurationProvider conf=new ConfigurationProvider("ambientSEPA.jsap");
		
		//update delle observable properties
		conf.updateAirProperties();
		
		//Query che seleziona tutte le province 
		QueryResponse resp=conf.query("ALL_COORDINATES");
		if (resp==null) {
			//errore
			//da aggiungere nel log?
			System.exit(-1);			
		}
		
		//delete data
		conf.deleteData();
		
		//Per ogni risultato 
		for (Bindings valueBindings : resp.getBindingsResults().getBindings()) {
			
			//estraggo il nome della provincia, la latitudine e la longitudine per ogni risultato			
			String nomeProvincia=valueBindings.getValue("nome");
			String latitudine=valueBindings.getValue("latitudine");
			String longitudine=valueBindings.getValue("longitudine");
			
			//istanzio oggetto della classe AmbientREST per fare la chiamata REST
			AmbientREST ambientREST=new AmbientREST(latitudine, longitudine);
			
			
			//update data
			conf.updateData(nomeProvincia, ambientREST.getTime(), ambientREST.getAirData(), ambientREST.getDistanceError());	
			
			
			
		}
		
		
		

	}

}
