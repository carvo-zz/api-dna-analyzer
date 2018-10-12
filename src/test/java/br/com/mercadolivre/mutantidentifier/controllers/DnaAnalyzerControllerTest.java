package br.com.mercadolivre.mutantidentifier.controllers;

import br.com.mercadolivre.mutantidentifier.UrlMapping;
import br.com.mercadolivre.mutantidentifier.analysis.DnaService;
import br.com.mercadolivre.mutantidentifier.analysis.validators.DnaStructureValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DnaAnalyzerController.class)
public class DnaAnalyzerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DnaService service;

    @MockBean
    private DnaStructureValidator validator;

    @Test
    public void shouldBeMutant() throws Exception {
        final String[] dna = { "A", "A" };

        when(validator.validate(dna)).thenReturn(Optional.empty());
        when(service.isMutant(dna)).thenReturn(Boolean.TRUE);

        final RequestBuilder call = createRequest(new DnaAnalysisRequest(dna));

        mockMvc.perform(call)
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().string(""));

        verify(validator, times(1)).validate(dna);
        verify(service, times(1)).isMutant(dna);
    }

    @Test
    public void shouldNotBeMutant() throws Exception {
        final String[] dna = { "A", "B" };

        when(validator.validate(dna)).thenReturn(Optional.empty());
        when(service.isMutant(dna)).thenReturn(Boolean.FALSE);

        final RequestBuilder call = createRequest(new DnaAnalysisRequest(dna));

        mockMvc.perform(call)
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
                .andExpect(content().string(""));

        verify(validator, times(1)).validate(dna);
        verify(service, times(1)).isMutant(dna);
    }


    @Test
    public void shouldValidateNullMatrix() throws Exception {
        final String[] dna = null;

        when(validator.validate(dna)).thenReturn(Optional.of(DnaStructureValidator.MSG_NULL_OR_EMPTY));

        final RequestBuilder call = createRequest(new DnaAnalysisRequest(dna));

        mockMvc.perform(call)
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().string(DnaStructureValidator.MSG_NULL_OR_EMPTY));

        verify(validator, times(1)).validate(dna);
        verify(service, never()).isMutant(dna);
    }

    private RequestBuilder createRequest(DnaAnalysisRequest dnaAnalysisRequest) throws JsonProcessingException {
        final String json = new ObjectMapper().writeValueAsString(dnaAnalysisRequest);

        return post(UrlMapping.POST_DNA)
                .contentType("application/json")
                .content(json);
    }
}
