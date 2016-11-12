import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image{
	protected BufferedImage bufferedImage;
	private int height;
	private int width;
	protected byte[] pixelData;
	private boolean hasAlpha;
	private int numPixelComponents;
	
	protected int[][] pixels;
	
	//constructor
	public Image(String filename){
		try{
			bufferedImage = ImageIO.read(new File(filename));
			height = bufferedImage.getHeight();
			width = bufferedImage.getWidth();
			pixelData = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
			pixels = new int[height][width];
			
			//numPixelComponents = 3 if no alpha is present, 4 if alpha is present
			numPixelComponents = 3;
			if(hasAlpha = (bufferedImage.getAlphaRaster() != null)){
				numPixelComponents = 4;
			}
			
			populatePixelMatrix();
		}
		catch(IOException e){
			System.out.println("Unable to open input image.");
			System.exit(-1);
		}
	}
	
	private void populatePixelMatrix(){
		if (hasAlpha){
			for(int pixel = 0, row = 0, col = 0; pixel < pixelData.length; pixel += numPixelComponents){
				int pixelRGB = 0;
				//blue component
				pixelRGB += ((int) pixelData[pixel + 1] & 0xff);
				//green component
				pixelRGB += (((int) pixelData[pixel + 2] & 0xff) << 8);
				//red component
				pixelRGB += (((int) pixelData[pixel + 3] & 0xff) << 16);
				//alpha component
				pixelRGB += (((int) pixelData[pixel] & 0xff) << 24);
				pixels[row][col] = pixelRGB;
				col++;
				if(col == width){
					col = 0;
					row++;
				}
			}
		}
		else{
			for(int pixel = 0, row = 0, col = 0; pixel < pixelData.length; pixel += numPixelComponents){
				int pixelRGB = 0;
				//blue component
				pixelRGB += ((int) pixelData[pixel] & 0xff);
				//green component
				pixelRGB += (((int) pixelData[pixel + 1] & 0xff) << 8);
				//red component
				pixelRGB += (((int) pixelData[pixel + 2] & 0xff) << 16);
				//alpha component (255)
				pixelRGB += -16777216;
				pixels[row][col] = pixelRGB;
				col++;
				if(col == width){
					col = 0;
					row++;
				}
			}
		}
	}
}
