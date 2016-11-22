package application;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import javafx.scene.image.Image;

public class Photo implements Serializable{

	Image image;
	String name;
	LocalDateTime date;
	String uri;
	private HashMap<String, ArrayList<String>> phototags;
	String[] tags;
	
	public Photo(String uri) throws IOException
	{
		
		phototags=new HashMap<String,ArrayList<String>>();
		image = new Image("file:///" + uri,1280,720,true,false);
		File f = new File(uri);
		
		//GET CAPTION WHEN FIRST IMPORTING THE IMAGE
		// WHEN SERIALIZING USE THAT INSTEAD OF THE FILE NAME
		this.name = f.getName();
		

		System.out.println(f.isFile());
		System.out.println(f.getAbsolutePath());		
	}
	public Photo(File fileName,LocalDateTime dtemp){
		this.name=fileName.getName();
		date =dtemp;
	}
	
	
	public LocalDateTime getTime(){
		return date;
	}
	
	
	public String getUri()
	{
		return uri;
	}
		
	public Image getImage(){
		return image;
	}
	public  HashMap<String, ArrayList<String>> getPhotoTags(){
		return phototags;
	}
	
	
	public String getName(){
		return name;
	}
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public void printTags() {
		for (String key : phototags.keySet()) {
			for (int i = 0; i < phototags.get(key).size(); i++) {
				System.out.println(key + " - " + phototags.get(key).get(i));
			}
		}
		}
	
	
	
	
	
	
	
	
	public String getTagDisplay() {
		String display = "";
		for (String key : phototags.keySet()) {
			String key_display = key + ": ";
			for (int i = 0; i < phototags.get(key).size(); i++) {
				String val = phototags.get(key).get(i);
				key_display += val + ", ";
			}
			key_display = key_display.substring(0, key_display.length() - 2);
			display += key_display + "; ";
		}
		if (display.length() > 0) {
			display = display.substring(0, display.length() - 2);
		}
		return display;
	}
	
	
}
