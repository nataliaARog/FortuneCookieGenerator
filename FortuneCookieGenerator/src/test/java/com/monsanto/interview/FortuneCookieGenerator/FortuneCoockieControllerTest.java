package com.monsanto.interview.FortuneCookieGenerator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  FortuneCookieGeneratorApplication.class)
public class FortuneCoockieControllerTest {
	
	@InjectMocks
	private FortuneCookieController controller;	
	@Spy
	private FortuneCookieBuilder builder;
	@Mock
	private QuoteRepository repository;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void generateFortuneCookieTest() throws Exception {
		Mockito.when(repository.getRandomQuote()).thenReturn("A friend asks only for your time not your money.");		
		MockHttpServletRequestBuilder get = get("/generateFortuneCookie").param("client","Sandra")
				                                                         .param("company","MegaMarket");
		mockMvc.perform(get)
		       .andDo(MockMvcResultHandlers.print())
		       .andExpect(status().isOk())
		       .andExpect(content().json("{'message':'Hi Sandra! Thanks for buying at MegaMarket :) And remember: A friend asks only for your time not your money.'}"));
	}
}
