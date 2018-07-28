package br.com.biblioteca.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
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
import br.com.biblioteca.model.Pessoa;
import br.com.biblioteca.repository.PessoaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainServer.class)
public class PessoaServiceTest {
	
	@InjectMocks
	private PessoaService service;
	
	@Mock
	private PessoaRepository repository;
	
	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void deveChamarMetodoNoRepositorio() {
		service.procurarPorNome("nome");
		verify(repository).findByNomeContainingIgnoreCase("nome");
	}
	
	@Test
	public void deveRetornarListaComPessoasDoBancoQuePossuamNomeIgualParametro() {
		Pessoa pessoa = criarPessoa();
		when(repository.findByNomeContainingIgnoreCase(pessoa.getNome())).thenReturn(Arrays.asList(pessoa));
		
		List<Pessoa> pessoas = service.procurarPorNome(pessoa.getNome());
		
		assertEquals(1, pessoas.size());
		assertEquals(pessoa.getNome(), pessoas.get(0).getNome());
	}
	
	@Test
	public void deveChamarMetodoListAll() {
		service.listarTodas();
		verify(repository).findAll();
	}
	
	@Test
	public void deveRetornarListaComTodasPessoasDoBanco() {
		when(repository.findAll()).thenReturn(Arrays.asList(criarPessoa(), criarPessoa(), criarPessoa()));
		
		List<Pessoa> pessoas = service.listarTodas();
		
		assertEquals(3, pessoas.size());
	}
	
	@Test
	public void deveChamarMetodoFindOne() {
		service.buscarPorId(1L);
		verify(repository).findOne(1L);
	}
	
	@Test
	public void deveRetornarPessoaAoBuscarPorId() {
		Pessoa pessoa = criarPessoa();
		when(repository.findOne(1L)).thenReturn(pessoa);
		
		Pessoa busca = service.buscarPorId(1L);
		
		assertEquals(1L, busca.getId());
	}
	
	private Pessoa criarPessoa() {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(1L);
		pessoa.setNome("nome");
		pessoa.setCpf("12345678901");
		pessoa.setDataNascimento(new Date());
		pessoa.setFuncionario(true);
		
		return pessoa;
	}
}
