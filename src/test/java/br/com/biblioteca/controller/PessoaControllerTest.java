package br.com.biblioteca.controller;



import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.biblioteca.MainServer;
import br.com.biblioteca.builder.PessoaBuilder;
import br.com.biblioteca.model.Pessoa;
import br.com.biblioteca.repository.PessoaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainServer.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0", "management.port=0"})
public class PessoaControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private PessoaRepository repository;
	
	private MockMvc mvc;
	
	 @Before
     public void setup() {
		 this.repository.deleteAll();
		 this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
     }
	 
	 @Test
	 public void deveRetornarPessoaDoBanco() throws Exception {
		 Pessoa inserido = this.repository.save(new PessoaBuilder().comId(null).build());
		 
		 RequestBuilder get = MockMvcRequestBuilders.get("/pessoa/" + inserido.getId());
		 String view = this.mvc.perform(get).andReturn().getModelAndView().getViewName();
		 Pessoa pessoaRequest = (Pessoa) this.mvc.perform(get).andReturn().getModelAndView().getModel().get("pessoa");
		 
		 assertEquals("/pessoa/form", view);
		 assertEquals(inserido.getId(), pessoaRequest.getId());
		 assertEquals(inserido.getNome(), pessoaRequest.getNome());
		 assertEquals(inserido.getCpf(), pessoaRequest.getCpf());
	 }
	 
	 @SuppressWarnings("unchecked")
	 @Test
	 public void deveRetornarTodasPessoasDoBancoDeDados() throws Exception {
		 List<Pessoa> pessoasInserir = Arrays.asList(
				 new PessoaBuilder().comId(null).build(),
				 new PessoaBuilder().comId(null).build(),
				 new PessoaBuilder().comId(null).build());
		 
		 repository.save(pessoasInserir);
		 
		 RequestBuilder get = MockMvcRequestBuilders.get("/pessoa");
		 
		 String view = this.mvc.perform(get).andReturn().getModelAndView().getViewName();
		 
		 List<Pessoa> pessoasRequest = (List<Pessoa>) this.mvc.perform(get).andReturn().getModelAndView().getModel().get("pessoas");
		 
		 assertEquals("/pessoa/index", view);
		 assertEquals(3, pessoasRequest.size());
	 }
	 
	 @Test
	 public void deveRetornarFormularioDeIclusaoAlteracao() throws Exception {
		 RequestBuilder get = MockMvcRequestBuilders.get("/pessoa/novo");
		 
		 String view = this.mvc.perform(get).andReturn().getModelAndView().getViewName();
		 
		 
		 assertEquals("/pessoa/form", view);
	 }
	 
	 @Test
	 public void deveInserirUmaNovaPessoaNoBancoDeDados() throws Exception {
		 RequestBuilder post = MockMvcRequestBuilders.post("/pessoa")
				 .param("nome", "Projeto Post")
				 .param("dataNascimento", "01/08/2018")
				 .param("cpf", "12345678901")
				 .param("funcionario", "false");
		 
		 ModelAndView model = this.mvc.perform(post).andReturn().getModelAndView();
		 
		 String view = model.getViewName();
		 String mensagem = (String) model.getModel().get("mensagem");
		 
		 assertEquals("/pessoa/form", view);
		 assertEquals("Registro salvo com sucesso!", mensagem);
	 }
}
