package br.com.triersistemas.provafacil.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.triersistemas.provafacil.armazenamento.SalvaDados;
import br.com.triersistemas.provafacil.model.EnumPedidoModel;
import br.com.triersistemas.provafacil.model.ItemPedidoModel;
import br.com.triersistemas.provafacil.model.PedidoModel;
import br.com.triersistemas.provafacil.model.ProdutoModel;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
		
	@GetMapping("/cadastrar")
	public PedidoModel CadastrarPedido() {
		PedidoModel p = new PedidoModel();
		SalvaDados.pedido.add(p);
		return p;
	}

	@GetMapping("/adicionar")
	public PedidoModel AdicionarProduto(@RequestParam(value = "id-pedido") Long idPe,
										@RequestParam(value = "id-produto") Long idPr,
										@RequestParam(value = "qtd") Integer qtd)
	{	
		ProdutoModel produto = null;
		for (ProdutoModel p : SalvaDados.produtos) {
			if (idPr.equals(p.getId())) {
				produto = p;
			}
		}

		for (PedidoModel pedi : SalvaDados.pedido) {
			if (idPe.equals(pedi.getId())) {
				pedi.adicionaProduto(produto, qtd);
				return pedi;
			}
		}
		return null;
	}


	
	@GetMapping("/retirar") public PedidoModel
	RemoverItemProduto(@RequestParam Long id) { 
		for (PedidoModel p : SalvaDados.pedido) {
			for (ItemPedidoModel i : p.getItens()) {
				if (id.equals(i.getId())) {
					p.removerItem(i);
					return p;
				}
			}
		}
		return null; 
	}
	 

		@GetMapping("/listar")
		public List<PedidoModel> ListarPedidos() {
			return SalvaDados.pedido;
		}

		@GetMapping("/pagar")
		public PedidoModel ConfirmarPagamento(@RequestParam(value = "id") Long id) { 
			PedidoModel p = null;
			for (PedidoModel ped : SalvaDados.pedido) {
				if (id.equals(ped.getId())) {
					p = ped;
					p.pagar();
					return p;
				}
			}
			return null;
		}

		@GetMapping("/excluir")
		public PedidoModel ExcluirProduto(@RequestParam(value = "id") Long id) {
			for (PedidoModel p : SalvaDados.pedido) {
				if (id.equals(p.getId())) {
					if (EnumPedidoModel.AGUARDANDO_PAGAMENTO.equals(p.getStatus())) {
						SalvaDados.pedido.remove(p);
						return p;
					}
				}
			}
			return null;
		}
	}