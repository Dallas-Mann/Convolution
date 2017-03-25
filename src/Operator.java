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
		byte[] convolvedImage = new byte[3 * newImageHeight * newImageWidth];
		
		for(int rowIndex = 0; rowIndex < newImageHeight; rowIndex++){
			for(int colIndex = 0; colIndex < newImageWidth; colIndex++){
				int newBlueVal = 0;
				int newGreenVal = 0;
				int newRedVal = 0;
				for(int mRowIndex = 0, maxMRowIndex = mask.numRows; mRowIndex < maxMRowIndex; mRowIndex++){
					for(int mColIndex = 0, maxMColIndex = mask.numCols; mColIndex < maxMColIndex; mColIndex++){
						int multiplier = mask.getMaskVal(mColIndex, mRowIndex);
						int x = colIndex + mColIndex;
						int y = rowIndex + mRowIndex;
						int currentBlueVal = image.getBlueVal(x, y);
						int currentGreenVal = image.getGreenVal(x, y);
						int currentRedVal = image.getRedVal(x, y);
						
						newBlueVal += (multiplier * currentBlueVal);
						newGreenVal += (multiplier * currentGreenVal);
						newRedVal += (multiplier * currentRedVal);
					}
				}
				//TODO
				//will need to rescale this value
				int baseIndex = 3*(colIndex+(newImageWidth*rowIndex));
				
				convolvedImage[baseIndex + 0] = (byte) Math.abs(newBlueVal);
				convolvedImage[baseIndex + 1] = (byte) Math.abs(newGreenVal);
				convolvedImage[baseIndex + 2] = (byte) Math.abs(newRedVal);
			}
		}
		Image scaledImage = new Image(newImageWidth, newImageHeight, convolvedImage);
		//scaledImage.rescale();
		return scaledImage;
	}
	
	private Image combineImages(Image image1, Image image2){
		byte[] pixelData1 = image1.getPixelData();
		byte[] pixelData2 = image2.getPixelData();
		if(pixelData1.length != pixelData2.length){
			return null;
		}
		else{
			int length = pixelData1.length;
			int width = image1.getWidth();
			int height = image1.getHeight();
			byte[] combinedPixels = new byte[length];
			for(int i = 0; i < length; i++){
				combinedPixels[i] = (byte) Math.sqrt(pixelData1[i]*pixelData1[i] + pixelData2[i]*pixelData2[i]);
			}
			return new Image(width, height, combinedPixels);
		}
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
