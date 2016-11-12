import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Operator{
	public Operator(){
		
	}
	
	public void SobelEdgeDetection(Image image, Mask mask){
		//TODO
		//apply mask operation on each matrix
		//recombine color matrices into a new image
		//save image
	}
	
	public void savePNG(String filename, Image image){
		try{
			File outputfile = new File(filename);
			ImageIO.write(image.bufferedImage, "png", outputfile);
		}
		catch(IOException e){
			System.out.println("Error writing image.");
			System.exit(-1);
		}
	}
}
