package br.com.triersistemas.provafacil.model;

import java.math.BigDecimal;

public class ProdutoModel {
	
private static long count = 0;
	
	private Long id;
	private String nome;
	private BigDecimal valor;
	private EnumProdutoModel tipo;
	
	public ProdutoModel(String nome, BigDecimal valor, EnumProdutoModel tipo) {
		this.id = ++count;
		this.nome = nome;
		this.valor = valor;
		this.tipo = tipo;
	}
	
	public void alterar(String nome, EnumProdutoModel tipo, BigDecimal valor) {
		this.nome = nome;
		this.valor = valor;
		this.tipo = tipo;
	}

	public static long getCount() {
		return count;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public EnumProdutoModel getTipo() {
		return tipo;
	}

	public BigDecimal getValor() {
		return valor;
	}
}
