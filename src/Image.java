import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class Image{
	protected BufferedImage bufferedImage;
	private int height;
	private int width;
	protected byte[] pixelData;
	
	//constructors
	public Image(String filename){
		try{
			bufferedImage = ImageIO.read(new File(filename));
			if(bufferedImage.getType() != BufferedImage.TYPE_3BYTE_BGR){
				bufferedImage = convertTo3ByteBGR(bufferedImage);
			}
			height = bufferedImage.getHeight();
			width = bufferedImage.getWidth();
			pixelData = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		}
		catch(IOException e){
			System.err.println("Unable to open input image.");
			System.exit(-1);
		}
	}
	
	public Image(int width, int height, byte[] pixelData){
		try
		{
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			bufferedImage.setData(Raster.createRaster(bufferedImage.getSampleModel(), new DataBufferByte(pixelData, pixelData.length), null));
			this.height = height;
			this.width = width;
			this.pixelData = pixelData;
		}
		catch(Exception e){
			System.err.println("Error creating bufferedImage from data.");
			System.exit(-1);
		}
	}
	
	// converts type of image into a 3ByteBGR format to store pixel values correctly
	private BufferedImage convertTo3ByteBGR(BufferedImage image)
	{
	    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}
	
	//getters
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getBlueVal(int x, int y){
		int index = 3*(x+(width*y));
		return pixelData[index + 0] & 0xff;
	}
	
	public int getGreenVal(int x, int y){
		int index = 3*(x+(width*y));
		return pixelData[index + 1] & 0xff;
	}
	
	public int getRedVal(int x, int y){
		int index = 3*(x+(width*y));
		return pixelData[index + 2] & 0xff;
	}
	
	public byte[] getPixelData(){
		return pixelData;
	}
	
	public void rescale(){
		int minBlue = 255;
		int minGreen = 255;
		int minRed = 255;
		int maxBlue = 0;
		int maxGreen = 0;
		int maxRed = 0;
		
		for(int row = 0; row < height; row++){
			for(int col = 0; col < width; col++){
				int curBlue = getBlueVal(row, col);
				int curGreen = getGreenVal(row, col);
				int curRed = getRedVal(row, col);
				
				if(curBlue < minBlue){
					minBlue = curBlue;
				}
				if(curBlue > maxBlue){
					maxBlue = curBlue;
				}
				
				if(curGreen < minGreen){
					minGreen = curGreen;
				}
				if(curGreen > maxGreen){
					maxGreen = curGreen;
				}
				
				if(curRed < minRed){
					minRed = curRed;
				}
				if(curRed > maxRed){
					maxRed = curRed;
				}
				
				for(int y = 0; y < height; y++){
					for(int x = 0; x < width; x++){
						int index = 3*(x+(width*y));
						pixelData[index + 0] = (byte) (255*((getBlueVal(x, y) - minBlue)/(maxBlue - minBlue)));
						pixelData[index + 1] = (byte) (255*((getGreenVal(x, y) - minGreen)/(maxGreen - minGreen)));
						pixelData[index + 2] = (byte) (255*((getRedVal(x, y) - minRed)/(maxRed - minRed)));
					}
				}
				bufferedImage.setData(Raster.createRaster(bufferedImage.getSampleModel(), new DataBufferByte(pixelData, pixelData.length), null));
			}
		}
	}
	
	public void printPixelData(String filename){
		try{
			PrintWriter out = new PrintWriter(new File(filename));
			out.println(pixelData.length);
			int index = 0;
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
					index = (3*(j+(i*width)));
					out.format(index + ": [%02x ", pixelData[index]);
					out.format("%02x ", pixelData[index+1]);
					out.format("%02x ", pixelData[index+2]);
					out.format("%02x] ", pixelData[index+3]);
				}
				out.println();
			}
			out.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Error printing pixel data to file.");
		}
	}
}
