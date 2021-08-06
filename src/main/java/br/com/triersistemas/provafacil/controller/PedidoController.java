package br.com.triersistemas.provafacil.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.triersistemas.provafacil.armazenamento.SalvaDados;
import br.com.triersistemas.provafacil.model.PedidoModel;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
		
	@GetMapping("/cadastrar")
	public PedidoModel CadastrarPedido() {
		return new PedidoModel();
	}

	@GetMapping("/adicionar")
	public PedidoModel AdicionarProduto(@RequestParam(value = "id-pedido") Long idPe,
										@RequestParam(value = "id-produto") Long idPr,
										@RequestParam(value = "qtd") Integer qtd)
	{	
		PedidoModel pedido = null;
		for (PedidoModel p : SalvaDados.pedido) {
			if (idPe.equals(p.getId())) {
				pedido = p;
				break;
			}
		}

		if (Objects.nonNull(pedido)) {
			for (PedidoModel produto : SalvaDados.pedido) {
				if (idPr.equals(produto.getId())) {
					if (Objects.nonNull(pedido)) {
						SalvaDados.pedido.add(produto);
						return pedido;
					}
				}
			}
		}
		return null;
	}


		@GetMapping("/retirar")
		public PedidoModel RemoverItemProduto(@RequestParam(value = "id") Long id) {
			PedidoModel pedido = null;
			for (PedidoModel p : SalvaDados.pedido) {
				if (id.equals(p.getId())) {
					pedido = p;
					break;
				}
			}

			if (Objects.nonNull(pedido)) {
				SalvaDados.pedido.remove(pedido);
				return pedido;
			}
			return null;
		}

		@GetMapping("/listar")
		public PedidoModel ListarPedidos() {
			return (PedidoModel) SalvaDados.pedido;
		}

		@GetMapping("/pagar")
		public PedidoModel ConfirmarPagamento() {
			PedidoModel p = (PedidoModel) SalvaDados.pedido;
			p.pagar();
			SalvaDados.pedido = (List<PedidoModel>) new PedidoModel();
			return p;
		}

		@GetMapping("/excluir")
		public PedidoModel ExcluirProduto(@RequestParam(value = "id") Long id) {
			PedidoModel pedido = null;
			for (PedidoModel p : SalvaDados.pedido) {
				if (id.equals(p.getId())) {
					pedido = p;
					break;
				}
			}
			if (Objects.nonNull(pedido)) {
				SalvaDados.produtos.remove(pedido);
				return pedido;
			}
			return null;
		}
	}