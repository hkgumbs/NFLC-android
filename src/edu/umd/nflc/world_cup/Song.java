package edu.umd.nflc.world_cup;

import org.w3c.dom.Element;

public class Song {

	public String name;
	public String audio;
	public String text;
	
	public Song(String name, String audio, String text){
		this.name = name;
		this.audio = audio;
		this.text = text;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAudio() {
		return audio;
	}
	
	public String getText() {
		return text;
	}
	
	public static Song parseSongElement(Element songElement) {
		return new Song(songElement.getAttribute("name"), 
				songElement.getAttribute("audio"), 
				songElement.getAttribute("text"));
	}
	
}
