package it.unibo.arces.wot.sepa.apps.ambient;

import java.io.IOException;
import java.util.HashMap;

import it.unibo.arces.wot.sepa.commons.exceptions.SEPABindingsException;
import it.unibo.arces.wot.sepa.commons.exceptions.SEPAPropertiesException;
import it.unibo.arces.wot.sepa.commons.exceptions.SEPAProtocolException;
import it.unibo.arces.wot.sepa.commons.exceptions.SEPASecurityException;
import it.unibo.arces.wot.sepa.commons.response.QueryResponse;
import it.unibo.arces.wot.sepa.commons.response.Response;
import it.unibo.arces.wot.sepa.commons.sparql.Bindings;
import it.unibo.arces.wot.sepa.commons.sparql.RDFTermLiteral;
import it.unibo.arces.wot.sepa.commons.sparql.RDFTermURI;
import it.unibo.arces.wot.sepa.pattern.GenericClient;
import it.unibo.arces.wot.sepa.pattern.JSAP;

public class ConfigurationProvider {
	
	private String jsapFileName;
	private GenericClient client;
	private String [] supportedPropertyArray= {"aqi","o3","no2","so2","co"};
	
	
	public ConfigurationProvider(String jsapFileName) {
		super();
		
		this.jsapFileName = jsapFileName;
		JSAP appProfile=null;
		//String jsapFileFullPath=this.getClass().getResource(jsapFileName).getPath();
		try {
			appProfile= new JSAP(jsapFileName);
		} catch (SEPAPropertiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPASecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			client= new GenericClient(appProfile, null, null);
		} catch (SEPAProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public String getJsapFileName() {
		return jsapFileName;
	}
	public void setJsapFileName(String jsapFileName) {
		this.jsapFileName = jsapFileName;
	}
	public GenericClient getClient() {
		return client;
	}
	
	public QueryResponse query (String queryName) {
		QueryResponse result=null;
		Response res=null;
		try {
			res=client.query(queryName, null, 10000);
		} catch (SEPAProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPASecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPAPropertiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPABindingsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (res!=null && res.isQueryResponse()) {
			
			result=(QueryResponse) res;
		}
		
		
		return result;
	}
	
	public void updateAirProperties () {
		
		/*
		 *  o3: "Ozone",
			no2: "Nitrogen Dioxide",
			so2: "Sulphur Dioxide",
			co: "Carbon Monoxyde",
			t: "Temperature",
			w: "Wind",
			r: "Rain (precipitation)",
			h: "Relative Humidity",
			d: "Dew",
			p: "Atmostpheric Pressure"*/
		Bindings bindingAQI= new Bindings();
		bindingAQI.addBinding("property",  new RDFTermURI("http://airQuality/context/aqi"));
		bindingAQI.addBinding("label", new RDFTermLiteral("aqi"));
		
		Bindings bindingO3= new Bindings();
		bindingO3.addBinding("property",  new RDFTermURI("http://airQuality/context/o3"));
		bindingO3.addBinding("label", new RDFTermLiteral("Ozono"));
		
		Bindings bindingNO2= new Bindings();
		bindingNO2.addBinding("property",  new RDFTermURI("http://airQuality/context/no2"));
		bindingNO2.addBinding("label", new RDFTermLiteral("Diossido di azoto"));
		
		Bindings bindingSO2= new Bindings();
		bindingSO2.addBinding("property",  new RDFTermURI("http://airQuality/context/so2"));
		bindingSO2.addBinding("label", new RDFTermLiteral("Diossido di zolfo"));
		
		Bindings bindingCO= new Bindings();
		bindingCO.addBinding("property",  new RDFTermURI("http://airQuality/context/co"));
		bindingCO.addBinding("label", new RDFTermLiteral("Monossido di carbonio"));
		
		Bindings bindingT= new Bindings();
		bindingT.addBinding("property",  new RDFTermURI("http://airQuality/context/t"));
		bindingT.addBinding("label", new RDFTermLiteral("Temperatura"));
		
		Bindings bindingW= new Bindings();
		bindingW.addBinding("property",  new RDFTermURI("http://airQuality/context/w"));
		bindingW.addBinding("label", new RDFTermLiteral("Vento"));
		
		Bindings bindingR= new Bindings();
		bindingR.addBinding("property",  new RDFTermURI("http://airQuality/context/r"));
		bindingR.addBinding("label", new RDFTermLiteral("Pioggia"));
		
		Bindings bindingH= new Bindings();
		bindingH.addBinding("property",  new RDFTermURI("http://airQuality/context/h"));
		bindingH.addBinding("label", new RDFTermLiteral("Umidità relativa"));
		
		Bindings bindingD= new Bindings();
		bindingD.addBinding("property",  new RDFTermURI("http://airQuality/context/d"));
		bindingD.addBinding("label", new RDFTermLiteral("Rugiada"));
		
		Bindings bindingP= new Bindings();
		bindingP.addBinding("property",  new RDFTermURI("http://airQuality/context/p"));
		bindingP.addBinding("label", new RDFTermLiteral("Pressione atmosferica"));
		
	
			
			Bindings bindingDistanceError= new Bindings();
		bindingDistanceError.addBinding("property",  new RDFTermURI("http://airQuality/context/distanceError"));
		bindingDistanceError.addBinding("label", new RDFTermLiteral("Distanza della stazione"));
		
		try {
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingAQI, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingO3, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingNO2, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingSO2, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingCO, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingT, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingW, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingR, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingH, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingD, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingP, 5000);
			this.client.update("ADD_OBSERVABLE_PROPERTY", bindingDistanceError, 5000);
		} catch (SEPAProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPASecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPAPropertiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPABindingsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void deleteData () {
		
				//cancella il grafo http://airQuality/observation
			try {
				client.update("DELETE", null, 5000);
			} catch (SEPAProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SEPASecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SEPAPropertiesException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SEPABindingsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		
		
		
		
	}
	
	
	public void updateData (String provincia, String timestamp, HashMap <String, Double> data, double distanceError) {

				
		
		for (String property: data.keySet()) {
			if (supportedProperty(property)) {
		
	
				
		//aggiungi i nuovi dati		
		Bindings bindingData= new Bindings();
		bindingData.addBinding("graph", new RDFTermURI("http://airQuality/observation"));
		bindingData.addBinding("place", new RDFTermURI(provincia));
		bindingData.addBinding("timestamp", new RDFTermLiteral(timestamp));
		
		bindingData.addBinding("property", new RDFTermURI("http://airQuality/context/"+property));
		bindingData.addBinding("unit", new RDFTermURI(chooseUnit(property)));
		bindingData.addBinding("value", new RDFTermLiteral(String.valueOf(data.get(property))));
		//-------------------
		
		//aggiungi i nuovi dati a quelli storici
		Bindings bindingDataHistorical= new Bindings();
		bindingDataHistorical.addBinding("graph", new RDFTermURI("http://airQuality/observation/history"));
		bindingDataHistorical.addBinding("place", new RDFTermURI(provincia));
		bindingDataHistorical.addBinding("timestamp", new RDFTermLiteral(timestamp));
		
		bindingDataHistorical.addBinding("property", new RDFTermURI("http://airQuality/context/"+property));
		bindingDataHistorical.addBinding("unit", new RDFTermURI(chooseUnit(property)));
		bindingDataHistorical.addBinding("value", new RDFTermLiteral(String.valueOf(data.get(property))));
		
			
		try {
			client.update("ADD_OBSERVATION", bindingData, 5000);
			client.update("ADD_OBSERVATION", bindingDataHistorical, 5000);
		} catch (SEPAProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPASecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPAPropertiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPABindingsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		}
		
		//aggiungi distanceError
		Bindings bindingDistanceError= new Bindings();
		bindingDistanceError.addBinding("graph", new RDFTermURI("http://airQuality/observation"));
		bindingDistanceError.addBinding("place", new RDFTermURI(provincia));
		bindingDistanceError.addBinding("timestamp", new RDFTermLiteral(timestamp));
		
		bindingDistanceError.addBinding("property", new RDFTermURI("http://airQuality/context/distanceError"));
		bindingDistanceError.addBinding("unit", new RDFTermURI("unit:KiloM"));
		bindingDistanceError.addBinding("value", new RDFTermLiteral(String.valueOf(distanceError)));
		
		Bindings bindingDistanceErrorHistorical= new Bindings();
		bindingDistanceErrorHistorical.addBinding("graph", new RDFTermURI("http://airQuality/observation/history"));
		bindingDistanceErrorHistorical.addBinding("place", new RDFTermURI(provincia));
		bindingDistanceErrorHistorical.addBinding("timestamp", new RDFTermLiteral(timestamp));
		
		bindingDistanceErrorHistorical.addBinding("property", new RDFTermURI("http://airQuality/context/distanceError"));
		bindingDistanceErrorHistorical.addBinding("unit", new RDFTermURI("unit:KiloM"));
		bindingDistanceErrorHistorical.addBinding("value", new RDFTermLiteral(String.valueOf(distanceError)));
		
		
		try {
			client.update("ADD_OBSERVATION", bindingDistanceError, 5000);
			client.update("ADD_OBSERVATION", bindingDistanceErrorHistorical, 5000);
		} catch (SEPAProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPASecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPAPropertiesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SEPABindingsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private boolean supportedProperty(String property) {
		// TODO Auto-generated method stub
		for (String temp: supportedPropertyArray) {			
			if (temp.compareTo(property)==0) return true;
		}
		return false;
	}
	
	private String chooseUnit(String property) {
		//da completare
		
		// TODO Auto-generated method stub
		/*
		 *  o3: "Ozone",
			no2: "Nitrogen Dioxide",
			so2: "Sulphur Dioxide",
			co: "Carbon Monoxyde",
			t: "Temperature",
			w: "Wind",
			r: "Rain (precipitation)",
			h: "Relative Humidity",
			d: "Dew",
			p: "Atmostpheric Pressure"*/
		if (property.compareTo("aqi")==0) {
			return "unit:Number";
			
		}
		
		if (property.compareTo("o3")==0) {
			return "unit:Number";
			
		}
		if (property.compareTo("no2")==0) {
			return "unit:Number";
			
		}
		if (property.compareTo("so2")==0) {
			return "unit:Number";
			
		}
		if (property.compareTo("co")==0) {
			return "unit:Number";
			
		}
		if (property.compareTo("t")==0) {
			
			
		}
		if (property.compareTo("w")==0) {
			
			
		}
		if (property.compareTo("r")==0) {
			
			
		}
		if (property.compareTo("h")==0) {
			

		}
		if (property.compareTo("d")==0) {
			
			
		}
		if (property.compareTo("p")==0) {
			
			
		}
		
		
		
		
		
		return null;
	}
	

	

}
