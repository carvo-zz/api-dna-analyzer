package br.com.mercadolivre.mutantidentifier.controllers;

import br.com.mercadolivre.mutantidentifier.UrlMapping;
import br.com.mercadolivre.mutantidentifier.model.Stats;
import br.com.mercadolivre.mutantidentifier.reports.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ReportController controller;

    @Mock
    private ReportService service;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldRetrieveStats() throws Exception {
        final Stats stats = new Stats(1, 1);
        when(service.getStats()).thenReturn(stats);

        mockMvc.perform(get(UrlMapping.STATS))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(stats)));

        verify(service, times(1)).getStats();
        verify(service, never()).computeDna(anyBoolean(), anyString());
    }
}
