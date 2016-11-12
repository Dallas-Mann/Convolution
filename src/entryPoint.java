public class entryPoint {
	public static void main(String[] args){
		if(args.length != 2){
			//TODO replace with correct format
			System.out.println("Invalid Arguments: <format>");
		}
		Operator myOperator = new Operator();
		Image inputImage = new Image(args[0]);
		myOperator.savePNG(args[1], inputImage);
	}
}
