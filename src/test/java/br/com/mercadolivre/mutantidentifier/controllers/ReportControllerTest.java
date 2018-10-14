package br.com.mercadolivre.mutantidentifier.controllers;

import br.com.mercadolivre.mutantidentifier.UrlMapping;
import br.com.mercadolivre.mutantidentifier.model.Stats;
import br.com.mercadolivre.mutantidentifier.reports.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService service;

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
