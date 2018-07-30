package br.com.biblioteca.controller;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.biblioteca.MainServer;
import br.com.biblioteca.builder.PessoaBuilder;
import br.com.biblioteca.builder.ProjetoBuilder;
import br.com.biblioteca.model.Pessoa;
import br.com.biblioteca.model.Projeto;
import br.com.biblioteca.model.Status;
import br.com.biblioteca.repository.PessoaRepository;
import br.com.biblioteca.repository.ProjetoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainServer.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0", "management.port=0"})
public class ProjetoControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private ProjetoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	private MockMvc mvc;
	
	 @Before
     public void setup() {
		 this.repository.deleteAll();
		 pessoaRepository.deleteAll();
		 this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
     }
	 
	 @Test
	 public void deveRetornarFormularioDeIclusaoAlteracao() throws Exception {
		 RequestBuilder get = MockMvcRequestBuilders.get("/projeto/novo");
		 
		 String view = this.mvc.perform(get).andReturn().getModelAndView().getViewName();
		 
		 assertEquals("/projeto/form", view);
	 }
	 
	 @SuppressWarnings("unchecked")
	 @Test
	 public void deveRetornarTodosProjetosDoBancoDeDados() throws Exception {
		 Pessoa gerente = pessoaRepository.save(new PessoaBuilder().comId(null).build());
		 
		 List<Projeto> projetosInserir = Arrays.asList(
				 new ProjetoBuilder().comId(null).comGerente(gerente).build(),
				 new ProjetoBuilder().comId(null).comGerente(gerente).build(),
				 new ProjetoBuilder().comId(null).comGerente(gerente).build());
		 
		 repository.save(projetosInserir);
		 
		 RequestBuilder get = MockMvcRequestBuilders.get("/projeto");
		 
		 String view = this.mvc.perform(get).andReturn().getModelAndView().getViewName();
		 
		 List<Projeto> projetosRequest = (List<Projeto>) this.mvc.perform(get).andReturn().getModelAndView().getModel().get("projetos");
		 
		 assertEquals("/projeto/index", view);
		 assertEquals(3, projetosRequest.size());
	 }
	 
	 @Test
	 public void deveRetornarProjetoDoBancoDeDados() throws Exception {
		 Pessoa gerente = pessoaRepository.save(new PessoaBuilder().comId(null).build());
		 Projeto inserido = this.repository.save(new ProjetoBuilder().comId(null).comGerente(gerente).build());
		 
		 RequestBuilder get = MockMvcRequestBuilders.get("/projeto/" + inserido.getId());
		 String view = this.mvc.perform(get).andReturn().getModelAndView().getViewName();
		 Projeto projetoRequest = (Projeto) this.mvc.perform(get).andReturn().getModelAndView().getModel().get("projeto");
		 
		 assertEquals("/projeto/form", view);
		 assertEquals(inserido.getId(), projetoRequest.getId());
		 assertEquals(inserido.getNome(), projetoRequest.getNome());
		 assertEquals(inserido.getStatus(), projetoRequest.getStatus());
	 }
	 
	 @Test
	 public void deveInserirUmNovoProjetoNoBancoDeDados() throws Exception {
		 Pessoa gerente = pessoaRepository.save(new PessoaBuilder().comId(null).build());
		 
		 RequestBuilder post = MockMvcRequestBuilders.post("/projeto")
				 .param("nome", "Projeto Post")
				 .param("dataInicio", "01/08/2018")
				 .param("dataPrevisaoFim", "01/08/2018")
				 .param("dataFim", "01/08/2018")
				 .param("descricao", "Descrição do projeto do Post")
				 .param("status", "INICIADO")
				 .param("orcamento", "250000.00")
				 .param("risco", "ALTO")
				 .param("gerente.id", gerente.getId().toString());
		 
		 ModelAndView model = this.mvc.perform(post).andReturn().getModelAndView();
		 
		 String view = model.getViewName();
		 String mensagem = (String) model.getModel().get("mensagem");
		 
		 assertEquals("/projeto/form", view);
		 assertEquals("Registro salvo com sucesso!", mensagem);
	 }
	 
	 @SuppressWarnings("unchecked")
	 @Test
	 public void deveValidarOsCamposObrigatorios() throws Exception {
		 RequestBuilder post = MockMvcRequestBuilders.post("/projeto")
				 .param("dataInicio", "01/08/2018")
				 .param("dataPrevisaoFim", "01/08/2018")
				 .param("dataFim", "01/08/2018")
				 .param("descricao", "Descrição do projeto do Post")
				 .param("status", "INICIADO")
				 .param("orcamento", "250000.00")
				 .param("risco", "ALTO");
		 
		 ModelAndView model = this.mvc.perform(post).andReturn().getModelAndView();
		 
		 String view = model.getViewName();
		 List<FieldError> erros = (List<FieldError>) model.getModel().get("erros");
		 List<String> errosStr = erros.stream().map((erro) -> erro.getDefaultMessage()).collect(Collectors.toList());
		 
		 assertEquals("/projeto/form", view);
		 assertThat(errosStr, hasItems("projeto.nome.obrigatorio"));
		 assertThat(errosStr, hasItems("projeto.gerente.obrigatorio"));
		 assertThat(erros, hasSize(2));
	 }
	 
	 @Test
	 public void deveExcluirUmProjeto() throws Exception {
		 Pessoa gerente = pessoaRepository.save(new PessoaBuilder().comId(null).build());
		 Projeto inserido = this.repository.save(new ProjetoBuilder().comId(null).comStatus(Status.EM_ANALISE).comGerente(gerente).build());
		 
		 String url = String.format("/projeto/%d/excluir", inserido.getId());
		 RequestBuilder get = MockMvcRequestBuilders.get(url);
		 
		 ModelAndView model = this.mvc.perform(get).andReturn().getModelAndView();
		 String view = model.getViewName();
		 String mensagem = (String) model.getModel().get("mensagem");
		 
		 assertEquals("/projeto/index", view);
		 assertEquals("Projeto excluido com sucesso!", mensagem);
	 }
	 
	 @Test
	 public void deveRetornarErroQuandoNaoPodeExcluirProjeto() throws Exception {
		 Pessoa gerente = pessoaRepository.save(new PessoaBuilder().comId(null).build());
		 Projeto inserido = this.repository.save(new ProjetoBuilder().comId(null).comStatus(Status.INICIADO).comGerente(gerente).build());
		 
		 String url = String.format("/projeto/%d/excluir", inserido.getId());
		 RequestBuilder get = MockMvcRequestBuilders.get(url);
		 
		 ModelAndView model = this.mvc.perform(get).andReturn().getModelAndView();
		 String view = model.getViewName();
		 String erro = (String) model.getModel().get("erro");
		 
		 assertEquals("/projeto/form", view);
		 assertEquals("Não é permitida a exclusão de um projeto Iniciado", erro);
	 }
	 
	 @Test
	 public void devePermitirAdicionarMembrosAoProjeto() throws Exception {
		 Pessoa gerente = pessoaRepository.save(new PessoaBuilder().comId(null).comFuncionario(true).build());
		 Projeto projeto = repository.save(new ProjetoBuilder().comId(null).comGerente(gerente).comMembros(new ArrayList<>()).build());
		 Pessoa membro = pessoaRepository.save(new PessoaBuilder().comId(null).comFuncionario(true).build());
		 
		 String url = String.format("/projeto/%d/adicionarmembro/%d", projeto.getId(), membro.getId());
		 
		 RequestBuilder put = MockMvcRequestBuilders.put(url);
		 
		 String response = mvc.perform(put).andReturn().getResponse().getContentAsString();
		 
		 String expected = String.format("{\"message\": \"%s\"}", "Membro adicionado com sucesso");
		 assertEquals(expected, response);
	 }
	 
	 @Test
	 public void naoDevePermitirAdicionarMembrosAoProjetoQueNaoFuncionario() throws Exception {
		 Pessoa gerente = pessoaRepository.save(new PessoaBuilder().comId(null).comFuncionario(true).build());
		 Projeto projeto = repository.save(new ProjetoBuilder().comId(null).comGerente(gerente).comMembros(new ArrayList<>()).build());
		 Pessoa membro = pessoaRepository.save(new PessoaBuilder().comId(null).comFuncionario(false).build());
		 
		 String url = String.format("/projeto/%d/adicionarmembro/%d", projeto.getId(), membro.getId());
		 
		 RequestBuilder put = MockMvcRequestBuilders.put(url);
		 
		 String response = mvc.perform(put).andReturn().getResponse().getContentAsString();
		 
		 String expected = String.format("{\"erro\": \"%s\"}", "Somente funcionários podem ser membros de projeto");
		 assertEquals(expected, response);
	 }
	 
	 @Test
	 public void naoDevePermitirAdicionarMembrosAoProjetoQueNaoExisteNoBancoDeDados() throws Exception {
		 Pessoa gerente = pessoaRepository.save(new PessoaBuilder().comId(null).comFuncionario(true).build());
		 Projeto projeto = repository.save(new ProjetoBuilder().comId(null).comGerente(gerente).comMembros(new ArrayList<>()).build());
		 
		 String url = String.format("/projeto/%d/adicionarmembro/%d", projeto.getId(), 1L);
		 
		 RequestBuilder put = MockMvcRequestBuilders.put(url);
		 
		 String response = mvc.perform(put).andReturn().getResponse().getContentAsString();
		 
		 String expected = String.format("{\"erro\": \"%s\"}", "Pessoa não encontrada");
		 assertEquals(expected, response);
	 }
}
