package br.com.triersistemas.provafacil.model;

import java.util.Objects;

public class ClienteModel extends PessoaModel {
	
	private String sintoma;
	
	public ClienteModel (String nome,  String documento, String sintoma) { 
		super(nome, documento);
		this.sintoma = sintoma;
	}
	
	
	@Override
	public String getDocumentoFormatado() {
		if (!this.isDocumentoValido()) {
			return this.getDocumento();
		}
		return this.getDocumento().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}

	public String getSintoma() {
		return sintoma;
	}
	

	@Override
	public Boolean isDocumentoValido() {
		if (Objects.isNull(super.getDocumento())
				|| super.getDocumento().length() != 11) {
			return false;
		}
		Integer primeiroVerificador = 0;
		Integer segundoVerificador = 0;
		Integer soma = 0;
		Integer somaSegundo = 0;
		Integer j = 1;
		char[] arrayDoc = super.getDocumento().toCharArray();
		String[] arrayString = new String[arrayDoc.length];
		Integer[] digitosInt = new Integer[arrayDoc.length];

		for (int i = 0; i < arrayDoc.length-2; i++) {
			arrayString[i] = String.valueOf(arrayDoc[i]);
			digitosInt[i] = Integer.parseInt(arrayString[i]);
			soma += digitosInt[i] * j;
			j++;
		}
		primeiroVerificador = soma % 11;
		if (primeiroVerificador == 10) {
			primeiroVerificador = 0;
		}
		j = 0;
		for (int i = 0; i < arrayDoc.length-1; i++) {
			arrayString[i] = String.valueOf(arrayDoc[i]);
			digitosInt[i] = Integer.parseInt(arrayString[i]);
			somaSegundo += digitosInt[i] * j;
			j++;
		}
		segundoVerificador = somaSegundo % 11;
		if (segundoVerificador == 10) {
			segundoVerificador = 0;
		}

		for (int i = 0; i < digitosInt.length; i++) {
			arrayString[i] = String.valueOf(arrayDoc[i]);
		}
		if (primeiroVerificador.equals(Integer.parseInt(arrayString[9])) && segundoVerificador.equals(Integer.parseInt(arrayString[10]))) {
			return true;
		}
		return false;
	}
}
