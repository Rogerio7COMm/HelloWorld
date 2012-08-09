import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CalculadoraTest {
	static Calculadora calc;
	@Before
	public void  setUp() throws Exception{
		calc = new Calculadora();
		System.out.println("Criada uma nova instancia de calculadora");
	}
	@Test
	public void testSoma_001() {
		calc = new Calculadora();
		double resultado = calc.somar(2.0, 2.0);
		assertEquals("Soma de valores inteiros", 4, resultado, 0);
	}
	@Test
	public void testSoma_002() {
		calc = new Calculadora();
		double resultado = calc.somar(-2.0, -2.0);
		assertEquals("Soma de valores negativos", -4, resultado, 0);
	}
	@Test
	public void testSoma_003() {
		calc = new Calculadora();
		double resultado = calc.somar(2.0, -2.0);
		assertEquals("Resultado igual a zero", 0, resultado, 0);
	}
	@Test
	public void testSoma_004() {
		calc = new Calculadora();
		double resultado = calc.somar(2.0, -2.1);
		assertEquals("Resultado de soma fracionada", 0.1, resultado, 0);
	}	
	@After
	public void tearDown() throws Exception{
		calc=null;
		System.out.println("Apagada a instancia da calculadora");
	}
}
