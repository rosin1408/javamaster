package br.com.biblioteca.repository;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.biblioteca.MainServer;
import br.com.biblioteca.model.Pessoa;
import br.com.biblioteca.repository.PessoaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainServer.class)
public class PessoaRepositoryTest {
	
	@Autowired
	private PessoaRepository repository;
	
	@Before
	public void limparBancoDeDados() {
		repository.deleteAll();
	}
	
	@Test
	public void deveRetornarPessoaCadastrada() {
		Pessoa pessoa = criarPessoa();
		repository.save(pessoa);
		
		List<Pessoa> busca = repository.findByNomeContainingIgnoreCase(pessoa.getNome());
		
		assertEquals(1, busca.size());
		assertEquals(pessoa.getNome(), busca.get(0).getNome());
	}
	
	@Test
	public void deveRetornarPessoaCadastradaInformandoParteDoNome() {
		Pessoa pessoa = criarPessoa();
		repository.save(pessoa);
		
		List<Pessoa> busca = repository.findByNomeContainingIgnoreCase("Roberto");
		
		assertEquals(1, busca.size());
		assertEquals(pessoa.getNome(), busca.get(0).getNome());
	}
	
	@Test
	public void deveRetornarListaVaziaQuandoNaoCadastrado() {
		Pessoa pessoa = criarPessoa();
		repository.save(pessoa);
		
		List<Pessoa> busca = repository.findByNomeContainingIgnoreCase("outro nome");
		
		assertEquals(0, busca.size());
	}
	
	private Pessoa criarPessoa() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("roberto rosin");
		pessoa.setDataNascimento(new Date());
		pessoa.setCpf("12345678901");
		pessoa.setFuncionario(true);
		
		return pessoa;
	}
}
