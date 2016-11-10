import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

//MASK FORMAT
/*
 * numRows
 * numCols
 * 1 1 1
 * 1 1 1
 * 1 1 1
 * 
 * numCols number of values per row, whitespace delimited
 * numRows number of values per column, whitespace delimited
 * 
 * valid values are all integers; (negative, positive, and zero)
 */

public class Mask{
	protected int[][] mask;
	protected int numRows;
	protected int numCols;
	
	//constructor
	public Mask(String filename){
		try{
			BufferedReader fileReader = new BufferedReader(new FileReader(new File(filename)));
			mask = readMask(fileReader);
		}
		catch(FileNotFoundException e){
			System.out.println("File not found.");
			System.exit(-1);
			e.printStackTrace();
		}
	}
	
	private int[][] readMask(BufferedReader fileReader){
		int[][] returnMask;
		
		try{
			String currentLine = fileReader.readLine();
			numRows = Integer.parseInt(currentLine);
			
			currentLine = fileReader.readLine();
			numCols = Integer.parseInt(currentLine);
			
			returnMask = new int[numRows][numCols];
			
			String[] maskValues;
			for(int m = 0; m < numRows; m++){
				currentLine = fileReader.readLine();
				maskValues = currentLine.split("\\s+");
				for(int n = 0; n < numCols; n++){
					returnMask[m][n] = Integer.parseInt(maskValues[n]);
				}
			}
			return returnMask;
		}
		catch(Exception e){
			System.out.println("Invalid file format.");
			System.exit(-1);
		}
		return null;
	}
	
	public int getNumRows(){
		return numRows;
	}
	
	public int getNumCols(){
		return numCols;
	}
}