import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Operator{
	public Operator(){
		
	}
	
	public Image SobelEdgeDetection(Image image, Mask HorizontalMask, Mask VerticalMask){
		//apply mask operation on each matrix
		Image horizontalEdgeImage = convolve(image, HorizontalMask);
		Image verticalEdgeImage = convolve(image, VerticalMask);
		//recombine color matrices into a new image
		Image finalImage = combineImages(horizontalEdgeImage, verticalEdgeImage);
		return finalImage;
	}
	
	public Image convolve(Image image, Mask mask){
		//new image dimensions will be narrower on left/right and top/bottom depending on mask size
		int newImageHeight = image.getHeight() - mask.getNumRows() + 1;
		int newImageWidth = image.getWidth() - mask.getNumCols() + 1;
		byte[] convolvedImage = new byte[4 * newImageHeight * newImageWidth];
		
		for(int rowIndex = 0; rowIndex < newImageHeight; rowIndex++){
			for(int colIndex = 0; colIndex < newImageWidth; colIndex++){
				int newAlphaVal = 0;
				int newBlueVal = 0;
				int newGreenVal = 0;
				int newRedVal = 0;
				int baseIndex = 4*(colIndex+(newImageWidth*rowIndex));
				
				for(int mRowIndex = 0, maxMRowIndex = mask.numRows; mRowIndex < maxMRowIndex; mRowIndex++){
					for(int mColIndex = 0, maxMColIndex = mask.numCols; mColIndex < maxMColIndex; mColIndex++){
						int multiplier = mask.getMaskVal(mColIndex, mRowIndex);
						int x = colIndex + mColIndex;
						int y = rowIndex + mRowIndex;
						int currentAlphaVal = image.getAlphaVal(x, y);
						int currentBlueVal = image.getBlueVal(x, y);
						int currentGreenVal = image.getGreenVal(x, y);
						int currentRedVal = image.getRedVal(x, y);
						
						newAlphaVal += (multiplier * currentAlphaVal);
						newBlueVal += (multiplier * currentBlueVal);
						newGreenVal += (multiplier * currentGreenVal);
						newRedVal += (multiplier * currentRedVal);
					}
				}
				
				//TODO
				//will need to rescale this value
				convolvedImage[baseIndex] = (byte) 255;
				convolvedImage[baseIndex + 1] = (byte) Math.abs(newBlueVal);
				convolvedImage[baseIndex + 2] = (byte) Math.abs(newGreenVal);
				convolvedImage[baseIndex + 3] = (byte) Math.abs(newRedVal);
			}
		}
		return new Image(newImageWidth, newImageHeight, convolvedImage);
	}
	
	private Image combineImages(Image image1, Image image2){
		//TODO combine the two pixel arrays
		
		return null;
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
