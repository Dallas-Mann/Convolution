public class entryPoint {
	public static void main(String[] args){
		Operator myOperator = new Operator();
		Image inputImage = new Image(args[0]);
		Mask maskHorizontal = new Mask(args[1]);
		Mask maskVertical = new Mask(args[2]);
		Image output = myOperator.SobelEdgeDetection(inputImage, maskHorizontal, maskVertical);
		myOperator.savePNG(args[3], output);
		
//		Operator myOperator = new Operator();
//		Image inputImage = new Image(args[0]);
//		Mask maskHorizontal = new Mask(args[1]);
//		Image output = myOperator.convolve(inputImage, maskHorizontal);
//		myOperator.savePNG(args[2], output);

	}
}
