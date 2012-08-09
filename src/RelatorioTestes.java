import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class RelatorioTestes {

	public static void main(String args[]){
		Result result = JUnitCore.runClasses(CalculadoraSuiteTU.class);
		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}
		System.out.println("Total de testes executados => " + result.getRunCount());
		System.out.println("Total de erros => " + result.getFailureCount());
	}
}
