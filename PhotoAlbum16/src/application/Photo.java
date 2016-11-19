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
		image = new Image("file:///" + uri,300,200,true,false);
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
	
	
	public void getMetadata()
	{
		
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
	
	
	
	
	
}
