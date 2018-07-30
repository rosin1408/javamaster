package br.com.biblioteca.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.biblioteca.exception.ValidationException;
import br.com.biblioteca.model.Pessoa;
import br.com.biblioteca.model.Projeto;
import br.com.biblioteca.model.Status;
import br.com.biblioteca.repository.PessoaRepository;
import br.com.biblioteca.repository.ProjetoRepository;

@Service
public class ProjetoService {
	
	@Autowired
	private ProjetoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public List<Projeto> listarTodos() {
		return this.repository.findAll();
	}

	public Projeto buscarPorId(Long id) {
		return this.repository.findOne(id);
	}

	public Projeto salvar(Projeto projeto) {
		return this.repository.save(projeto);
	}
	
	public void excluir(Long id) throws ValidationException {
		Projeto projeto = repository.findOne(id);
		List<Status> statusNaoPodeExcluir = Arrays.asList(Status.INICIADO, Status.EM_ANDAMENTO, Status.ENCERRADO);
		
		if (statusNaoPodeExcluir.contains(projeto.getStatus())) {
			throw new ValidationException("Não é permitida a exclusão de um projeto Iniciado");
		}
		this.repository.delete(id);
	}
	
	public Projeto adicionarMembro(Long idProjeto, Long idPessoa) throws ValidationException {
		Projeto projeto = this.repository.findOne(idProjeto);
		Pessoa membro = this.pessoaRepository.findOne(idPessoa);
		
		if (projeto == null) {
			throw new ValidationException("Projeto não encontrado");
		}
		if (membro == null) {
			throw new ValidationException("Pessoa não encontrada");
		}
		if (! membro.isFuncionario()) {
			throw new ValidationException("Somente funcionários podem ser membros de projeto");
		}
		projeto.getMembros().add(membro);
		
		return repository.save(projeto);
	}
}
