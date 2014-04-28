package edu.umd.nflc.world_cup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.res.Resources;

public class LookupURL {
	private static Map<String, ArrayList<Song>> countryList;
	private static List<String> countries;
	
//	private static final Map<String, ArrayList<Song>> countryList;
//	static {
//		Map<String, ArrayList<Song>> temp = loadSongData();
//		countryList = Collections.unmodifiableMap(temp);
//	}
//	
//	private static Map<String, ArrayList<Song>> loadSongData() {
//		
//		InputStream is = LookupURL.class.getResourceAsStream(Resources.getSystem().getResourceName(R.xml.countries));
//		Map<String, ArrayList<Song>> result = new HashMap<String, ArrayList<Song>>();
//		try {
//			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//			Document doc = db.parse(is);
//			Element root = doc.getDocumentElement();
//			NodeList countries = root.getElementsByTagName("country");
//			for (int i = 0; i < countries.getLength(); i++) {
//				Node node = countries.item(i);
//				if (node.getNodeType() == Node.ELEMENT_NODE) {
//					Element country = (Element) node;
//					NodeList songs = country.getChildNodes();
//					for (int j = 0; j < songs.getLength(); j++) {
//						Node songNode = songs.item(j);
//						if (songNode.getNodeType() == Node.ELEMENT_NODE) {
//							Element song = (Element) songNode;
//							if (!result.containsKey(country.getAttribute("name"))) {
//								result.put(country.getAttribute("name"), new ArrayList<Song>());
//							}
//							result.get(country.getAttribute("name")).add(Song.parseSongElement(song));
//						}
//					}
//				}
//			}
//			return result;
//		} catch (ParserConfigurationException | SAXException | IOException e) {
//			System.err.println(e);
//			return result;
//		}
//	}
	
	public LookupURL(){
		countries = Arrays.asList(new String[]{
				"Algeria", "Argentina", "Australia", "Belgium", "Bosnia", "Brazil", "Cameroon", "Chile", "Colombia", "CostaRica", "Croatia", "Ecuador", "England", "France", "Germany", "Ghana", "Greece", 
				"Honduras", "Iran", "Italy", "CoteDlvoire", "Japan", "KoreaRepublic", "Mexico", "Netherlands", "Nigeria", "Portugal", "Russia", "Spain", "Switzerland", "Uruguay", "Usa"
		});
		
		countryList = new HashMap<String, ArrayList<Song>>();
		
		ArrayList<Song> algeriaSongs = new ArrayList<Song>();
		ArrayList<Song> argentinaSongs = new ArrayList<Song>();
		ArrayList<Song> australiaSongs = new ArrayList<Song>();
		ArrayList<Song> belgiumSongs = new ArrayList<Song>();
		ArrayList<Song> bosniaAndHerzegovinaSongs = new ArrayList<Song>();
		ArrayList<Song> brazilSongs = new ArrayList<Song>();
		ArrayList<Song> cameroonSongs = new ArrayList<Song>();
		ArrayList<Song> chileSongs = new ArrayList<Song>();
		ArrayList<Song> colombiaSongs = new ArrayList<Song>();
		ArrayList<Song> costaRicaSongs = new ArrayList<Song>();
		ArrayList<Song> coteDlvoireSongs = new ArrayList<Song>();
		ArrayList<Song> croatiaSongs = new ArrayList<Song>();
		ArrayList<Song> ecuadorSongs = new ArrayList<Song>();
		ArrayList<Song> englandSongs = new ArrayList<Song>();
		ArrayList<Song> franceSongs = new ArrayList<Song>();
		ArrayList<Song> germanySongs = new ArrayList<Song>();
		ArrayList<Song> ghanaSongs = new ArrayList<Song>();
		ArrayList<Song> greeceSongs = new ArrayList<Song>();
		ArrayList<Song> hondurasSongs = new ArrayList<Song>();
		ArrayList<Song> iranSongs = new ArrayList<Song>();
		ArrayList<Song> italySongs = new ArrayList<Song>();
		ArrayList<Song> japanSongs = new ArrayList<Song>();
		ArrayList<Song> koreaRepublicSongs = new ArrayList<Song>();
		ArrayList<Song> mexicoSongs = new ArrayList<Song>();
		ArrayList<Song> netherlandsSongs = new ArrayList<Song>();
		ArrayList<Song> nigeriaSongs = new ArrayList<Song>();
		ArrayList<Song> portugalSongs = new ArrayList<Song>();
		ArrayList<Song> russiaSongs = new ArrayList<Song>();
		ArrayList<Song> spainSongs = new ArrayList<Song>();
		ArrayList<Song> switzerlandSongs = new ArrayList<Song>();
		ArrayList<Song> uruguaySongs = new ArrayList<Song>();
		ArrayList<Song> usaSongs = new ArrayList<Song>();

		
		algeriaSongs.add(new Song("Hi Algeria1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		algeriaSongs.add(new Song("Hi Algeria2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		algeriaSongs.add(new Song("Hi Algeria3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		algeriaSongs.add(new Song("Hi Algeria4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		algeriaSongs.add(new Song("Hi Algeria5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		argentinaSongs.add(new Song("Hi Argentina1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		argentinaSongs.add(new Song("Hi Argentina2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		argentinaSongs.add(new Song("Hi Argentina3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		argentinaSongs.add(new Song("Hi Argentina4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		argentinaSongs.add(new Song("Hi Argentina5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		australiaSongs.add(new Song("Hi Australia1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		australiaSongs.add(new Song("Hi Australia2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		australiaSongs.add(new Song("Hi Australia3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		australiaSongs.add(new Song("Hi Australia4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		australiaSongs.add(new Song("Hi Australia5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		belgiumSongs.add(new Song("Hi Belgium1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		belgiumSongs.add(new Song("Hi Belgium2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		belgiumSongs.add(new Song("Hi Belgium3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		belgiumSongs.add(new Song("Hi Belgium4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		belgiumSongs.add(new Song("Hi Belgium5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		bosniaAndHerzegovinaSongs.add(new Song("Hi bosniaAndHerzegovina1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		bosniaAndHerzegovinaSongs.add(new Song("Hi bosniaAndHerzegovina2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		bosniaAndHerzegovinaSongs.add(new Song("Hi bosniaAndHerzegovina3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		bosniaAndHerzegovinaSongs.add(new Song("Hi bosniaAndHerzegovina4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		bosniaAndHerzegovinaSongs.add(new Song("Hi bosniaAndHerzegovina5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		brazilSongs.add(new Song("Hi brazil1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		brazilSongs.add(new Song("Hi brazil2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		brazilSongs.add(new Song("Hi brazil3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		brazilSongs.add(new Song("Hi brazil4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		brazilSongs.add(new Song("Hi brazil5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		cameroonSongs.add(new Song("Hi cameroon1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		cameroonSongs.add(new Song("Hi cameroon2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		cameroonSongs.add(new Song("Hi cameroon3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		cameroonSongs.add(new Song("Hi cameroon4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		cameroonSongs.add(new Song("Hi cameroon5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		
		chileSongs.add(new Song("Hi chile1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		chileSongs.add(new Song("Hi chile2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		chileSongs.add(new Song("Hi chile3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		chileSongs.add(new Song("Hi chile4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		chileSongs.add(new Song("Hi chile5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		colombiaSongs.add(new Song("Hi colombia1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		colombiaSongs.add(new Song("Hi colombia2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		colombiaSongs.add(new Song("Hi colombia3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		colombiaSongs.add(new Song("Hi colombia4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		colombiaSongs.add(new Song("Hi colombia5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		
		costaRicaSongs.add(new Song("Hi costaRica1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		costaRicaSongs.add(new Song("Hi costaRica2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		costaRicaSongs.add(new Song("Hi costaRica3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		costaRicaSongs.add(new Song("Hi costaRica4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		costaRicaSongs.add(new Song("Hi costaRica5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		coteDlvoireSongs.add(new Song("Hi cote d'lvoire1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		coteDlvoireSongs.add(new Song("Hi cote d'lvoire2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		coteDlvoireSongs.add(new Song("Hi cote d'lvoire3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		coteDlvoireSongs.add(new Song("Hi cote d'lvoire4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		coteDlvoireSongs.add(new Song("Hi cote d'lvoire5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		croatiaSongs.add(new Song("Hi croatia1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		croatiaSongs.add(new Song("Hi croatia2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		croatiaSongs.add(new Song("Hi croatia3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		croatiaSongs.add(new Song("Hi croatia4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		croatiaSongs.add(new Song("Hi croatia5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		ecuadorSongs.add(new Song("Hi ecuador1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		ecuadorSongs.add(new Song("Hi ecuador2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		ecuadorSongs.add(new Song("Hi ecuador3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		ecuadorSongs.add(new Song("Hi ecuador4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		ecuadorSongs.add(new Song("Hi ecuador5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		
		englandSongs.add(new Song("Hi england1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		englandSongs.add(new Song("Hi england2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		englandSongs.add(new Song("Hi england3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		englandSongs.add(new Song("Hi england4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		englandSongs.add(new Song("Hi england5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));

		franceSongs.add(new Song("Hi france1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		franceSongs.add(new Song("Hi france2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		franceSongs.add(new Song("Hi france3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		franceSongs.add(new Song("Hi france4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		franceSongs.add(new Song("Hi france5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		germanySongs.add(new Song("Hi germany1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		germanySongs.add(new Song("Hi germany2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		germanySongs.add(new Song("Hi germany3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		germanySongs.add(new Song("Hi germany4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		germanySongs.add(new Song("Hi germany5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		ghanaSongs.add(new Song("Hi ghana1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		ghanaSongs.add(new Song("Hi ghana2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		ghanaSongs.add(new Song("Hi ghana3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		ghanaSongs.add(new Song("Hi ghana4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		ghanaSongs.add(new Song("Hi ghana5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		greeceSongs.add(new Song("Hi greece1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		greeceSongs.add(new Song("Hi greece2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		greeceSongs.add(new Song("Hi greece3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		greeceSongs.add(new Song("Hi greece4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		greeceSongs.add(new Song("Hi greece5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		hondurasSongs.add(new Song("Hi honduras1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		hondurasSongs.add(new Song("Hi honduras2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		hondurasSongs.add(new Song("Hi honduras3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		hondurasSongs.add(new Song("Hi honduras4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		hondurasSongs.add(new Song("Hi honduras5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		iranSongs.add(new Song("Hi iran1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		iranSongs.add(new Song("Hi iran2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		iranSongs.add(new Song("Hi iran3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		iranSongs.add(new Song("Hi iran4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		iranSongs.add(new Song("Hi iran5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		italySongs.add(new Song("Hi italy1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		italySongs.add(new Song("Hi italy2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		italySongs.add(new Song("Hi italy3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		italySongs.add(new Song("Hi italy4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		italySongs.add(new Song("Hi italy5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		japanSongs.add(new Song("Hi japan1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		japanSongs.add(new Song("Hi japan2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		japanSongs.add(new Song("Hi japan3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		japanSongs.add(new Song("Hi japan4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		japanSongs.add(new Song("Hi japan5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		koreaRepublicSongs.add(new Song("Hi koreaRepublic1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		koreaRepublicSongs.add(new Song("Hi koreaRepublic2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		koreaRepublicSongs.add(new Song("Hi koreaRepublic3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		koreaRepublicSongs.add(new Song("Hi koreaRepublic4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		koreaRepublicSongs.add(new Song("Hi koreaRepublic5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		mexicoSongs.add(new Song("Hi mexico1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		mexicoSongs.add(new Song("Hi mexico2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		mexicoSongs.add(new Song("Hi mexico3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		mexicoSongs.add(new Song("Hi mexico4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		mexicoSongs.add(new Song("Hi mexico5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		netherlandsSongs.add(new Song("Hi netherlands1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		netherlandsSongs.add(new Song("Hi netherlands2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		netherlandsSongs.add(new Song("Hi netherlands3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		netherlandsSongs.add(new Song("Hi netherlands4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		netherlandsSongs.add(new Song("Hi netherlands5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		nigeriaSongs.add(new Song("Hi nigeria1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		nigeriaSongs.add(new Song("Hi nigeria2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		nigeriaSongs.add(new Song("Hi nigeria3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		nigeriaSongs.add(new Song("Hi nigeria4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		nigeriaSongs.add(new Song("Hi nigeria5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		portugalSongs.add(new Song("Hi portugal1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		portugalSongs.add(new Song("Hi portugal2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		portugalSongs.add(new Song("Hi portugal3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		portugalSongs.add(new Song("Hi portugal4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		portugalSongs.add(new Song("Hi portugal5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		russiaSongs.add(new Song("Hi russia1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		russiaSongs.add(new Song("Hi russia2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		russiaSongs.add(new Song("Hi russia3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		russiaSongs.add(new Song("Hi russia4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		russiaSongs.add(new Song("Hi russia5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		spainSongs.add(new Song("Hi spain1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		spainSongs.add(new Song("Hi spain2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		spainSongs.add(new Song("Hi spain3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		spainSongs.add(new Song("Hi spain4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		spainSongs.add(new Song("Hi spain5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		switzerlandSongs.add(new Song("Hi switzerland1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		switzerlandSongs.add(new Song("Hi switzerland2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		switzerlandSongs.add(new Song("Hi switzerland3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		switzerlandSongs.add(new Song("Hi switzerland4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		switzerlandSongs.add(new Song("Hi switzerland5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		uruguaySongs.add(new Song("Hi uruguay1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		uruguaySongs.add(new Song("Hi uruguay2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		uruguaySongs.add(new Song("Hi uruguay3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		uruguaySongs.add(new Song("Hi uruguay4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		uruguaySongs.add(new Song("Hi uruguay5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		usaSongs.add(new Song("Hi usa1", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		usaSongs.add(new Song("Hi usa2", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		usaSongs.add(new Song("Hi usa3", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		usaSongs.add(new Song("Hi usa4", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		usaSongs.add(new Song("Hi usa5", "http://storage.googleapis.com/testbucket1111/samples/sample2.wav", "http://storage.googleapis.com/testbucket1111/samples/sample1.txt"));
		
		
		countryList.put("Algeria", algeriaSongs);
		countryList.put("Argentina", argentinaSongs);
		countryList.put("Australia", australiaSongs);
		countryList.put("Belgium", belgiumSongs);
		countryList.put("Bosnia", bosniaAndHerzegovinaSongs);
		countryList.put("Brazil", brazilSongs);
		countryList.put("Cameroon", cameroonSongs);
		countryList.put("Chile", chileSongs);
		countryList.put("Colombia", colombiaSongs);
		countryList.put("CostaRica", costaRicaSongs);
		countryList.put("Croatia", croatiaSongs);
		countryList.put("Ecuador", ecuadorSongs);
		countryList.put("England", englandSongs);
		countryList.put("France", franceSongs);
		countryList.put("Germany", germanySongs);
		countryList.put("Ghana", ghanaSongs);
		countryList.put("Greece", greeceSongs);
		countryList.put("Honduras", hondurasSongs);
		countryList.put("Iran", iranSongs);
		countryList.put("Italy", italySongs);
		countryList.put("CoteDlvoire", coteDlvoireSongs);
		countryList.put("Japan", japanSongs);
		countryList.put("KoreaRepublic", koreaRepublicSongs);
		countryList.put("Mexico", mexicoSongs);
		countryList.put("Netherlands", netherlandsSongs);
		countryList.put("Nigeria", nigeriaSongs);
		countryList.put("Portugal", portugalSongs);
		countryList.put("Russia", russiaSongs);
		countryList.put("Spain", spainSongs);
		countryList.put("Switzerland", switzerlandSongs);
		countryList.put("Uruguay", uruguaySongs);
		countryList.put("Usa", usaSongs);
	}

	public String getAudio(int country, int index){
		String c = countries.get(country);
		return countryList.get(c).get(index).getAudio();
	}
	
	public String getText(int country, int index){
		String c = countries.get(country);
		return countryList.get(c).get(index).getText();
	}
	
	public ArrayList<Song> getSongList(int country){
		String c = countries.get(country);
		return countryList.get(c);
	}
	
	public ArrayList<String> getAllAudioURLs(int country){
		ArrayList<String> urls = new ArrayList<String>();
		String c = countries.get(country);
		for (int i = 0; i < countryList.get(c).size(); i++) {
			urls.add(getAudio(country, i));
		}
		return urls;
	}
	
}
