
public class Calculadora {

	public double somar(double a, double b){
		return a+b;
	}
	public double subtrair(double a, double b){
		return a-b;
	}
	public double multiplicar(double a, double b){
		return a*b;
	}
	public double dividir(double a, double b){
		return a*b;
	}
	public double percentual_10(double a, double b){
		double resultado=0;
		if (b>0){
			if (b<10){
				resultado = a * (b/100);
			}else{
				resultado = a * 0.1;
			}
		}else{
			resultado = a;
		}
		return resultado;
	}	
}
