package it.unibo.arces.wot.sepa.apps.ambient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AmbientREST {

	private final String urlAmbientBase= "http://api.waqi.info/feed/geo:";
	private final String token="ca9b7949cd73a6a84998a623ee15a937eca7c650";
	
	private double latitudineAQP,longitudineAQP;
	private String latitudineInput, longitudineInput;
	private String time;
	private HashMap <String, Double> airData;
	
	
	
	public double getLatitudineAQP() {
		return latitudineAQP;
	}



	public void setLatitudineAQP(double latitudineAQP) {
		this.latitudineAQP = latitudineAQP;
	}



	public double getLongitudineAQP() {
		return longitudineAQP;
	}



	public void setLongitudineAQP(double longitudineAQP) {
		this.longitudineAQP = longitudineAQP;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public HashMap<String, Double> getAirData() {
		return airData;
	}



	public void setAirData(HashMap<String, Double> airData) {
		this.airData = airData;
	}



	public AmbientREST(String latitudine, String longitudine) {
		super();
		this.latitudineInput=latitudine;
		this.longitudineInput=longitudine;
		URL url;
		String content="";
		try {
			url = new URL(urlAmbientBase+latitudine+";"+longitudine+"/?token="+token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
		
			int status = con.getResponseCode();
			if (status==200) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
					String inputLine;
					content="";
					while ((inputLine = in.readLine()) != null) {
					    content+=inputLine;
					}
					in.close();			
			}
			
			con.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Gson gson=new Gson();
		JsonObject jsonObject = new Gson().fromJson(content, JsonObject.class);
		JsonElement data=jsonObject.get("data");
		//latitudine e longitudine AQP
		JsonElement geo=data.getAsJsonObject().get("city").getAsJsonObject().get("geo");
		this.latitudineAQP=geo.getAsJsonArray().get(0).getAsDouble();
		this.longitudineAQP=geo.getAsJsonArray().get(1).getAsDouble();
		
		//estraggo il timestamp in cui sono stati acquisiti i dati (orario assoluto + fusorario) da cambiare
		this.time=data.getAsJsonObject().get("time").getAsJsonObject().get("s").getAsString();
		int i = time.indexOf(' ');
		String temp=time.substring(0,i).trim();
		String temp1=time.substring(i).trim();
		time= temp+"T"+temp1;

		String tz=data.getAsJsonObject().get("time").getAsJsonObject().get("tz").getAsString();
		this.time=this.time+ tz;
		
		
		//estraggo tutti i dati dell'aria presenti
		getAirData(data);	
		
		
		
		
		
	}



	private void getAirData(JsonElement data) {
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
		this.airData=new HashMap<String, Double>();
		
		//indice aqi di riassunto
		double aqi=-1;
		if (data.getAsJsonObject().get("aqi")!=null) {
			aqi=data.getAsJsonObject().get("aqi").getAsDouble();
			airData.put ("aqi", aqi);			
		}
		
		
		JsonElement iaqi=data.getAsJsonObject().get("iaqi");
		double co=-1,h=-1,no2=-1,o3=-1,p=-1,pm10=-1,pm25=-1,so2=-1,t=-1,w=-1;
		//co (Carbon Monoxyde)
	if (iaqi.getAsJsonObject().get("co")!=null) {
	 co=iaqi.getAsJsonObject().get("co").getAsJsonObject().get("v").getAsDouble();
	airData.put("co", co);
	}
		//h (relative Humidity
	if (iaqi.getAsJsonObject().get("h")!=null) {
	h=iaqi.getAsJsonObject().get("h").getAsJsonObject().get("v").getAsDouble();
	airData.put("h", h);
	}
		//no2 (Nitrogen Dioxide)
	if (iaqi.getAsJsonObject().get("no2")!=null) {
	no2=iaqi.getAsJsonObject().get("no2").getAsJsonObject().get("v").getAsDouble();
	airData.put("no2", no2);
	}
		//o3 (Ozone)
	if (iaqi.getAsJsonObject().get("o3")!=null) {
	 o3=iaqi.getAsJsonObject().get("o3").getAsJsonObject().get("v").getAsDouble();
	 airData.put("o3", o3);
	}
		//p (Atmospheric Pressure)
	if (iaqi.getAsJsonObject().get("p")!=null) {
	 p=iaqi.getAsJsonObject().get("p").getAsJsonObject().get("v").getAsDouble();
	 airData.put("p", p);
	}
		//pm10 (Particulate Matter)
	if (iaqi.getAsJsonObject().get("pm10")!=null) {
	 pm10=iaqi.getAsJsonObject().get("pm10").getAsJsonObject().get("v").getAsDouble();
	 airData.put("pm10", pm10);
	}
		//pm25
	if (iaqi.getAsJsonObject().get("pm25")!=null) {
	 pm25=iaqi.getAsJsonObject().get("pm25").getAsJsonObject().get("v").getAsDouble();
	 airData.put("pm25", pm25);
	}
		//so2 (Sulphur Dioxide)
	if (iaqi.getAsJsonObject().get("so2")!=null) {
	 so2=iaqi.getAsJsonObject().get("so2").getAsJsonObject().get("v").getAsDouble();
	 airData.put("so2", so2);
	}
		//t (Temperature)
	if (iaqi.getAsJsonObject().get("t")!=null) {
	 t=iaqi.getAsJsonObject().get("t").getAsJsonObject().get("v").getAsDouble();
	 airData.put("t", t);
	}
		//w (Wind)
	if (iaqi.getAsJsonObject().get("w")!=null) {
	 w=iaqi.getAsJsonObject().get("w").getAsJsonObject().get("v").getAsDouble();
	 airData.put("w", w);
	}
	}
	
	
	public double getDistanceError() {
		double result=-1;
		
		/*
		 * La formula utilizzata per determinare la distanza più breve tra due punti
		 * terrestri approssima il geoide a una sfera di raggio R =
		 * 6372,795477598 Km, quindi il calcolo della distanza
		 * potrebbe avere un errore dello 0.3%, in particolare nelle estremitá polari, e
		 * per distanze lunghe che attraversano diversi paralleli. Dati due punti A e B
		 * sulla sfera espressi con la latitudine (lat) e longitudine (lon) si avrà:
		 * 
		 * distanza (A,B) = R * arccos( sin(latA) * sin(latB) + cos(latA) * cos(latB) *
		 * cos(lonA-lonB))
		 */
						double latA=Double.valueOf(latitudineInput);
						double lonA=Double.valueOf(longitudineInput);
						double latB=latitudineAQP;
						double lonB=longitudineAQP;
						double Raggio =6372.795477598;
						double sin_latA=Math.sin(Math.toRadians(latA));
						double sin_latB=Math.sin(Math.toRadians(latB));
						double cos_latA=Math.cos(Math.toRadians(latA));
						double cos_latB=Math.cos(Math.toRadians(latB));
						double cos_lonAlonB=Math.cos(Math.toRadians(lonA)-Math.toRadians(lonB));
						result=Raggio* Math.acos( sin_latA * sin_latB + cos_latA * cos_latB * cos_lonAlonB);
		
		
		
		return result;
	}
	
	
	
	
}
