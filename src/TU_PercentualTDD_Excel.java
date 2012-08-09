import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import util.ExcelMock;

@RunWith(Parameterized.class)
public class TU_PercentualTDD_Excel {
	
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
        ExcelMock emm;
        emm = new ExcelMock("c:\\temp\\calculadora.xls");
        return emm.ObtemDados("percentual", true);        		
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
	public TU_PercentualTDD_Excel(String paramMensagem, 
			String paramValorA, 
			String paramValorB, 
			String paramResultado, 
			String paramVariacao){
		this.mensagem = paramMensagem;
		this.valorA = Double.valueOf(paramValorA);
		this.valorB	= Double.valueOf(paramValorB);
		this.resultado = Double.valueOf(paramResultado);
		this.variacao = Double.valueOf(paramVariacao);
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
				calc.percentual_10(this.valorA, this.valorB), 
				this.variacao);
	}
	
	@AfterClass
	public static void tearDown() throws Exception{
		calc=null;
		System.out.println("Apagada a instancia da calculadora");
	}
}
