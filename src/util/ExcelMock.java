package util;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public class ExcelMock {

	Collection<Object[]> dadosPlanilha = null;
	String nomeFolha = "";
	String nomeArquivo = "";
	String nomeCampoIdentificadorRegistro = "";
	String numeroConta = "";
	int numeroLinha =-1;
	
	public ExcelMock() {
		super(); 
	}

	public ExcelMock(String nomeArquivo, String nomeFolha, 
			boolean semCabecalho) {
		super();
		this.nomeFolha = nomeFolha;
		this.nomeArquivo = nomeArquivo;
		this.ObtemDados(semCabecalho); 
	}
	
	public ExcelMock(String nomeArquivo) {
		super();
		this.nomeArquivo = nomeArquivo; 
	}
	
	public String getNomeCampoIdentificadorRegistro() {
		return nomeCampoIdentificadorRegistro;
	}

	public void setNomeCampoIdentificadorRegistro(String nomeCampoIdentificadorRegistro) {
		this.nomeCampoIdentificadorRegistro = nomeCampoIdentificadorRegistro;
	}

	public Collection<Object[]> getDados() {
		return dadosPlanilha;
	}
	
	public Collection<Object[]> ObtemDados(String nomeFolha, boolean semCabecalho){
		this.nomeFolha = nomeFolha;
		return this.ObtemDados(semCabecalho);
	}
	
	public Collection<Object[]> ObtemDados(boolean semCabecalho){
		try {
			InputStream spreadsheet = new FileInputStream(nomeArquivo);
			dadosPlanilha =  
					new ObtemDadosExcel(spreadsheet, nomeFolha, semCabecalho).getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dadosPlanilha;	
	}
	
	public String obterValor(String valorChavePesquisa, String nomeCampoPesquisado){
		String temp="";
		//Identifica o registro correspondente a conta
		if (!numeroConta.equals(valorChavePesquisa)){
			numeroLinha = this.obterNumeroLinha(dadosPlanilha, 
					nomeCampoIdentificadorRegistro, valorChavePesquisa);
			numeroConta = valorChavePesquisa;
		}
		temp = obterValor(dadosPlanilha, numeroLinha, 
				nomeCampoPesquisado).toString();
		return temp;
	}		
	
	public int obterNumeroColuna(Collection<Object[]> colecaoRegistros, 
			String nomeCampo){	
		
		Object[] camposCabecalho = obterRegistro(colecaoRegistros, 0);
		int i, j;
		j=-1;
		i=0;
		for (Object campo : camposCabecalho){
			if(campo.equals(nomeCampo)){
				j=i; break;
			}
			i++;
		}
		return j;
	}
	
	public int obterNumeroLinha(Collection<Object[]> lista, 
			String nomeColuna, String valor){
		int posicaoColuna = this.obterNumeroColuna(lista, nomeColuna);
		return obterNumeroLinha(lista, posicaoColuna, valor);
	}
	
	public int obterNumeroLinha(Collection<Object[]> lista, 
			int posicaoColuna, String valor){
		int temp = -1;
		if (posicaoColuna >= 0){
			int j=0;
			for(Object[] linha : lista){
				if(linha[posicaoColuna].equals(valor)){
					temp = j;
					break;
				}j++;
			}
		}
		return temp;		
	}
	
	public Object[] obterRegistro(Collection<Object[]> colecaoRegistros, int indice){
		@SuppressWarnings("rawtypes")
		List tabela = (List) colecaoRegistros;
		Object[] objRegistro = (Object[]) tabela.get(indice);
		return objRegistro;
	}
	
	public Object obterValor(Object[] registro, int posicao){
		return registro[posicao];
	}
	
	public String obterValor(int numeroLinha, String nomeCampo){
		int numeroColuna = this.obterNumeroColuna(dadosPlanilha, nomeCampo);
		return this.obterValor(dadosPlanilha, numeroLinha, numeroColuna);
	}
	
	public String obterValor(Collection<Object[]> colecaoRegistros, 
			int numeroLinha, String nomeCampo){
		int numeroColuna = this.obterNumeroColuna(colecaoRegistros, nomeCampo);
		return this.obterValor(colecaoRegistros, numeroLinha, numeroColuna);
	}
	
	public String obterValor(Collection<Object[]> colecaoRegistros, 
			int numeroLinha, int numeroColuna){
		Object[] registro = this.obterRegistro(colecaoRegistros, numeroLinha);
		return registro[numeroColuna].toString();
	}
	
	public int getNumeroOcorrencias(){
		int total = 0;
		for (@SuppressWarnings("unused") Object[] i : dadosPlanilha) total ++;
		return total;
	}
	
	public static int getNumeroOcorrencias(Collection<Object[]> planilha){
		int total = 0;
		for (@SuppressWarnings("unused") Object[] i : planilha) total ++;
		return total;
	}
	
	
}
