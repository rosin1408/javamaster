package br.com.biblioteca.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.biblioteca.MainServer;
import br.com.biblioteca.builder.PessoaBuilder;
import br.com.biblioteca.builder.ProjetoBuilder;
import br.com.biblioteca.exception.ValidationException;
import br.com.biblioteca.model.Pessoa;
import br.com.biblioteca.model.Projeto;
import br.com.biblioteca.model.Status;
import br.com.biblioteca.repository.PessoaRepository;
import br.com.biblioteca.repository.ProjetoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainServer.class)
public class ProjetoServiceTest {
	
	@InjectMocks
	private ProjetoService service;
	
	@Mock
	private ProjetoRepository repository;
	
	@Mock
	private PessoaRepository pessoaRepository;
	
	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void deveListarTodosProjetos() {
		when(repository.findAll()).thenReturn(Arrays.asList(criarProjeto(), criarProjeto(), criarProjeto()));
		
		List<Projeto> projetos = service.listarTodos();
		
		assertEquals(3, projetos.size());
		verify(repository).findAll();
	}
	
	@Test
	public void deveBuscarProjetoPorId() {
		Projeto projeto = criarProjeto();
		when(repository.findOne(projeto.getId())).thenReturn(projeto);
		
		Projeto busca = service.buscarPorId(projeto.getId());
		
		assertEquals(projeto.getNome(), busca.getNome());
		verify(repository).findOne(projeto.getId());
	}
	
	@Test
	public void deveIncluirUmNovoProjeto() {
		Projeto projeto = criarProjeto();
		when(repository.save(projeto)).thenReturn(projeto);
		
		Projeto salvo = service.salvar(projeto);
		
		assertEquals(projeto.getNome(), salvo.getNome());
		verify(repository).save(projeto);
	}
	
	@Test
	public void deveExcluirUmProjeto() throws ValidationException {
		Projeto projeto = criarProjeto();
		projeto.setStatus(Status.EM_ANALISE);
		when(repository.findOne(projeto.getId())).thenReturn(projeto);
		
		service.excluir(1L);
		
		verify(repository).delete(1L);
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveExcluirUmProjetoIniciado() throws ValidationException {
		Projeto projeto = criarProjeto();
		projeto.setStatus(Status.INICIADO);
		when(repository.findOne(projeto.getId())).thenReturn(projeto);
		service.excluir(projeto.getId());
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveExcluirUmProjetoEmAndamento() throws ValidationException {
		Projeto projeto = criarProjeto();
		projeto.setStatus(Status.EM_ANDAMENTO);
		when(repository.findOne(projeto.getId())).thenReturn(projeto);
		service.excluir(projeto.getId());
	}
	
	@Test(expected = ValidationException.class)
	public void naoDeveExcluirUmProjetoEncerrado() throws ValidationException {
		Projeto projeto = criarProjeto();
		projeto.setStatus(Status.ENCERRADO);
		when(repository.findOne(projeto.getId())).thenReturn(projeto);
		service.excluir(projeto.getId());
	}
	
	@Test
	public void deveIncluirMembrosNoProjeto() throws ValidationException {
		Projeto projeto = new ProjetoBuilder().comMembros(new ArrayList<>()).build();
		Pessoa membro = new PessoaBuilder().comFuncionario(true).build();
		
		when(repository.findOne(projeto.getId())).thenReturn(projeto);
		when(pessoaRepository.findOne(membro.getId())).thenReturn(membro);
		
		Projeto salvo = service.adicionarMembro(projeto.getId(), membro.getId());
		
		assertEquals(1, salvo.getMembros().size());
		verify(repository).save(projeto);
	}
	
	@Test(expected = ValidationException.class)
	public void deveIncluirApenasFuncionariosComoMembrosDoProjeto() throws ValidationException {
		Projeto projeto = new ProjetoBuilder().comMembros(new ArrayList<>()).build();
		Pessoa membro = new PessoaBuilder().comFuncionario(false).build();
		
		when(repository.findOne(projeto.getId())).thenReturn(projeto);
		when(pessoaRepository.findOne(membro.getId())).thenReturn(membro);
		
		service.adicionarMembro(projeto.getId(), membro.getId());
	}
	
	@Test(expected = ValidationException.class)
	public void deveValidarSePessoaExiste() throws ValidationException {
		Projeto projeto = new ProjetoBuilder().comMembros(new ArrayList<>()).build();
		
		when(repository.findOne(projeto.getId())).thenReturn(projeto);
		when(pessoaRepository.findOne(1L)).thenReturn(null);
		
		service.adicionarMembro(projeto.getId(), 1L);
	}
	
	@Test(expected = ValidationException.class)
	public void deveValidarSeProjetoExiste() throws ValidationException {
		Pessoa membro = new PessoaBuilder().comFuncionario(true).build();
		
		when(repository.findOne(1L)).thenReturn(null);
		when(pessoaRepository.findOne(membro.getId())).thenReturn(membro);
		
		service.adicionarMembro(1L, membro.getId());
	}
	
	private Projeto criarProjeto() {
		Pessoa gerente = new PessoaBuilder().comNome("Nome do Gerente").build();
		
		return new ProjetoBuilder().comGerente(gerente).build();
	}
}
