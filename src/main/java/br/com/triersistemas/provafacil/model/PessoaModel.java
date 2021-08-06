package br.com.triersistemas.provafacil.model;

import lombok.Getter;

@Getter
public abstract class PessoaModel {

	private static long count = 0;
	
	private Long id;
	private String nome;
    private String documento;

    public PessoaModel(String nome, String documento) {
    	this.id = ++count;
    	this.nome = nome;
        this.documento = documento;
    }
    
    public abstract Boolean isDocumentoValido();

    public abstract String getDocumentoFormatado();

	public static long getCount() {
		return count;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDocumento() {
		return documento;
	}
    
    
}
