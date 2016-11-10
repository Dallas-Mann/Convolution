import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image{
	protected BufferedImage image;
	
	//constructor
	public Image(String filename){
		try{
			image = ImageIO.read(new File(filename));
		}
		catch(IOException e){
			System.out.println("Unable to open input image.");
			System.exit(-1);
		}
	}
}
