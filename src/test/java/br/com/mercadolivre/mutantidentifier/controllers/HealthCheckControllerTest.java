package br.com.mercadolivre.mutantidentifier.controllers;

import br.com.mercadolivre.mutantidentifier.UrlMapping;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class HealthCheckControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private HealthCheckController controller;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldRetrieveStats() throws Exception {
        mockMvc.perform(get(UrlMapping.ROOT))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

}
