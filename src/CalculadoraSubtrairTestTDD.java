import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CalculadoraSubtrairTestTDD {
	
	static Calculadora calc;
	String mensagem; 
	double valorA; 
	double valorB; 
	double resultado; 
	double variacao;
	
	/**
	 * Parametros do teste
	 * 
	 * @return Matriz com os valores a serem usados no TDD
	 */
	@Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
        		 {"Subtracao de valores inteiros", 2.0, 2.0, 4.0, 0},
        		 {"Subtracao de valores negativos", -2.0, -2.0, -4.0, 0},
        		 {"Subtracao igual a zero", 2.0, -2.0, 0.0, 0},
        		 {"Subtracao igual a valor fracionado", 0.0, 2.1, 2.1, 0},       		 
        			});
        }
	/**
	 * Classe Construtora do teste TDD
	 * 
	 * @param paramMensagem Mensagem associada ao teste
	 * @param paramValorA Valor usado como parametro A
	 * @param paramValorB Valor usado como parametro V
	 * @param paramResultado Valor usado na comparação de resultados
	 * @param paramVariacao Limite de variação usado para garantir um resultado válido
	 */
	public CalculadoraSubtrairTestTDD(String paramMensagem, 
			double paramValorA, 
			double paramValorB, 
			double paramResultado, 
			double paramVariacao){
		this.mensagem = paramMensagem;
		this.valorA = paramValorA;
		this.valorB	= paramValorB;
		this.resultado = paramResultado;
		this.variacao = paramVariacao;
	}
		
	@BeforeClass
	public static void  setUp() throws Exception{
		calc = new Calculadora();
		System.out.println("Criada uma nova instancia de calculadora");
	}
	@Test
	public void testSoma_001() {
		assertEquals(this.mensagem, 
				this.resultado, 
				calc.subtrair(this.valorA, this.valorB), 
				this.variacao);
	}
	
	@AfterClass
	public static void tearDown() throws Exception{
		calc=null;
		System.out.println("Apagada a instancia da calculadora");
	}
}
