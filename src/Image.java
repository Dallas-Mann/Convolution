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
	private boolean hasAlpha;
	
	//constructors
	public Image(String filename){
		try{
			bufferedImage = ImageIO.read(new File(filename));
			if(bufferedImage.getType() != BufferedImage.TYPE_4BYTE_ABGR){
				bufferedImage = convertTo4ByteABGR(bufferedImage);
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
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
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
	
	// converts type of image into a 4ByteABGR format to store pixel values correctly
	private BufferedImage convertTo4ByteABGR(BufferedImage image)
	{
	    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
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
	
	public byte getAlphaVal(int x, int y){
		int index = 4*(x+(width*y));
		return pixelData[index];
	}
	
	public byte getBlueVal(int x, int y){
		int index = 4*(x+(width*y));
		return pixelData[index + 1];
	}
	
	public byte getGreenVal(int x, int y){
		int index = 4*(x+(width*y));
		return pixelData[index + 2];
	}
	
	public byte getRedVal(int x, int y){
		int index = 4*(x+(width*y));
		return pixelData[index + 3];
	}
	
	public boolean hasAlpha(){
		return hasAlpha;
	}
	
	public void printPixelData(String filename){
		try{
			PrintWriter out = new PrintWriter(new File(filename));
			out.println(pixelData.length);
			int index = 0;
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
					index = (4*(j+(i*width)));
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
