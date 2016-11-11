package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Locale;

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

public class Photo {

	Image image;
	String name;
	String date;
	String uri;
	String[] tags;
	
	public Photo(String uri) throws IOException
	{
		image = new Image(uri,300,200,true,false);
		File f = new File(uri);
		this.name = f.getName();
	}
	
	
	
	public Image getImage(){
		return image;
	}
	public String getName(){
		return name;
	}
}
