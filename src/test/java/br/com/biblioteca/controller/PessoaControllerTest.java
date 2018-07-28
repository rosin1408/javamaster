package br.com.biblioteca.controller;



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

import br.com.biblioteca.MainServer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainServer.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0", "management.port=0"})
public class PessoaControllerTest {
	
//	@Autowired
//	private PessoaController controller;
//	
//	private MockMvc mockMvc;
//	
//	 @Before
//     public void setup() {
//         this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//     }
//	 
//	 @Test
//	 public void primeiroTeste() throws Exception {
//		 RequestBuilder get = MockMvcRequestBuilders.get("/pessoa");
//		 this.mockMvc.perform(get).;
//	 }
}
