package br.com.triersistemas.provafacil.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.triersistemas.provafacil.armazenamento.SalvaDados;
import br.com.triersistemas.provafacil.model.ClienteModel;
import br.com.triersistemas.provafacil.model.FarmaceuticoModel;
import br.com.triersistemas.provafacil.model.PessoaModel;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	@GetMapping("/cadastrar-farmaceutico")
	public FarmaceuticoModel CadastrarFamaceutico(@RequestParam(value = "nome") String nome, 
			                                      @RequestParam(value = "documento") String documento,
			                                      @RequestParam(value = "pis") String pis)
	{
		FarmaceuticoModel f = new FarmaceuticoModel(nome, documento, pis);
		SalvaDados.pessoas.add(f);
		return f;
	}

	@GetMapping("/cadastrar-cliente")
	public ClienteModel Cadastrarcliente(@RequestParam(value = "nome") String nome, 
			                             @RequestParam(value = "documento") String documento,
			                             @RequestParam(value = "sintoma") String sintoma)
	{
		ClienteModel c = new ClienteModel(nome, documento, sintoma);
		SalvaDados.pessoas.add(c);
		return c;
	}

	@GetMapping("/excluir")
	public PessoaModel ExcluirPessoa(@RequestParam(value = "id") Long id) {
		PessoaModel pessoa = null;
		for (PessoaModel p : SalvaDados.pessoas) {
			if (id.equals(p.getId())) {
				pessoa = p;
				break;
			}
		}

		if (Objects.nonNull(pessoa)) {
			SalvaDados.pessoas.remove(pessoa);
			return pessoa;
		}
		return null;
	}

	@GetMapping("/listar")
	public List<PessoaModel> ListarPessoa() {
		return SalvaDados.pessoas;
	}

	@GetMapping("/validar-documento")
	public String ValidarCpf(@RequestParam (value = "id") Long id) {
		String mensagem = "Documento inválido";
		for (PessoaModel p : SalvaDados.pessoas) {
			if (id.equals(p.getId())) {
				if (p.isDocumentoValido()) {
					return p.getDocumentoFormatado();
				} else {
					return mensagem;
				}

			}

		}
		return null;
	}
}
