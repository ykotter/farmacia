package br.com.triersistemas.provafacil.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoModel {

private static long count = 0;
	
	private Long id;
	private String nome;
	private EnumPedidoModel status;
	private LocalDateTime dataPagamento;
	private LocalDateTime dataPedido;
	private List<ItemPedidoModel>itens;
	
	public PedidoModel() {
		this.id = ++count;
		this.status = EnumPedidoModel.AGUARDANDO_PAGAMENTO;
		this.dataPedido = LocalDateTime.now();
		this.itens = new ArrayList<>();
	}
	
	public void adicionaProduto(ProdutoModel p, Integer qtd) {
		if (EnumPedidoModel.PAGO.equals(this.status)) {
			throw new RuntimeException("Pedido finalizado");
		}
		this.itens.add(new ItemPedidoModel(p, qtd));
	}
	
	public void excluirPedido() {
		if (EnumPedidoModel.PAGO.equals(this.status)) {
			throw new RuntimeException("Pedido pago");
		}
	}
	
	public void pagar() {
		this.status = EnumPedidoModel.PAGO;
		this.dataPagamento = LocalDateTime.now();
	}
	
	public ItemPedidoModel removerItem(ItemPedidoModel item) {
		this.itens.remove(item);
		return item;
	}
	
	public BigDecimal getValorTotal() {
		BigDecimal soma = BigDecimal.ZERO;
		for (ItemPedidoModel i : itens) {
			soma = soma.add(i.getValorTotal());
		}
		return soma;
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

	public EnumPedidoModel getStatus() {
		return status;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public List<ItemPedidoModel> getItens() {
		return itens;
	}
}
