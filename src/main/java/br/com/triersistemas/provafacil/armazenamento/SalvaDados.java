package br.com.triersistemas.provafacil.armazenamento;

import java.util.ArrayList;
import java.util.List;

import br.com.triersistemas.provafacil.model.ItemPedidoModel;
import br.com.triersistemas.provafacil.model.PedidoModel;
import br.com.triersistemas.provafacil.model.PessoaModel;
import br.com.triersistemas.provafacil.model.ProdutoModel;

public class SalvaDados {
	
private SalvaDados() { }
	
	public static List<ProdutoModel> produtos = new ArrayList<>();
	public static List<ItemPedidoModel> itens = new ArrayList<>();
	public static List<PessoaModel> pessoas = new ArrayList<>();
	public static List<PedidoModel> pedido = new ArrayList<>();
}
