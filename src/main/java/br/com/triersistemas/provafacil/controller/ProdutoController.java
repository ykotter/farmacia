package br.com.triersistemas.provafacil.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.triersistemas.provafacil.armazenamento.SalvaDados;
import br.com.triersistemas.provafacil.model.EnumProdutoModel;
import br.com.triersistemas.provafacil.model.ProdutoModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
	
	@GetMapping("/cadastrar")
	public ProdutoModel CadastrarProduto(@RequestParam(value = "nome") String nome, 
								         @RequestParam(value = "tipo") EnumProdutoModel tipo,
								         @RequestParam(value = "valor") BigDecimal valor)
	{
		
		ProdutoModel p = new ProdutoModel(nome, valor, tipo);
		SalvaDados.produtos.add(p);
		return p;
	}
	
	@GetMapping("/alterar")
	public ProdutoModel AlterarProduto(@RequestParam(value = "id") Long id,
			                           @RequestParam(value = "nome") String nome, 
								       @RequestParam(value = "tipo") EnumProdutoModel tipo,
								       @RequestParam(value = "valor") BigDecimal valor)
	{
		for (ProdutoModel p : SalvaDados.produtos) {
			if (id.equals(p.getId())) {
				p.alterar(nome, tipo, valor);
				return p;
			}
		}
		return null;
	}
	
	@GetMapping("/excluir")
	public ProdutoModel ExcluirProduto(@RequestParam(value = "id") Long id) {
		ProdutoModel produto = null;
		for (ProdutoModel p : SalvaDados.produtos) {
			if (id.equals(p.getId())) {
			produto = p;
			break;
			}
		}
	
		if (Objects.nonNull(produto)) {
			SalvaDados.produtos.remove(produto);
			return produto;
		}
	return null;
	}
	
	@GetMapping("/listar")
	public List<ProdutoModel> ListarProduto() {
		return SalvaDados.produtos;
	}
}
