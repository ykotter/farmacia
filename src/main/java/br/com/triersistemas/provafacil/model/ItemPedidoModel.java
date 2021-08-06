package br.com.triersistemas.provafacil.model;

import java.math.BigDecimal;

public class ItemPedidoModel {
	
	private static long count = 0;
	
	private Long id;
	private ProdutoModel produto;
	private Integer qtd;
	private BigDecimal valorTotal;
	
	public ItemPedidoModel(ProdutoModel p, Integer qtd) {
		this.id = ++count;
		this.produto = p;
		this.qtd = qtd;
		this.valorTotal = p.getValor().multiply(BigDecimal.valueOf(qtd));
	}

	public static long getCount() {
		return count;
	}

	public Long getId() {
		return id;
	}

	public ProdutoModel getProduto() {
		return produto;
	}

	public Integer getQtd() {
		return qtd;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}
}
