
public class ClasseHelloWorld {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Oie " + args[0]);
		int paramA = Integer.valueOf(args[1]);
		int paramB = Integer.valueOf(args[2]);
		Calculadora calc = new Calculadora();
		System.out.println(args[0] + ", " + paramA + " + " + paramB +
				" é igual a " + calc.somar(paramA, paramB));
	}
}
