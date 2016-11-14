public class entryPoint {
	public static void main(String[] args){
		Operator myOperator = new Operator();
		Image inputImage = new Image(args[0]);
		Mask mask = new Mask(args[1]);
		Image tester = myOperator.convolve(inputImage, mask);
		//mask.printMask();
		//tester.printPixelData("pixelData.txt");
		//Image newImage = myOperator.convolve(inputImage, mask);
		myOperator.savePNG(args[2], tester);
	}
}
