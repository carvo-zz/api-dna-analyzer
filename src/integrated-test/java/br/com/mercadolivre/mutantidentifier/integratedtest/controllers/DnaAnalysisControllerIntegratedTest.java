package br.com.mercadolivre.mutantidentifier.integratedtest.controllers;

import br.com.mercadolivre.mutantidentifier.App;
import br.com.mercadolivre.mutantidentifier.UrlMapping;
import br.com.mercadolivre.mutantidentifier.analysis.validators.DnaStructureValidator;
import br.com.mercadolivre.mutantidentifier.controllers.DnaAnalysisRequest;
import br.com.mercadolivre.mutantidentifier.integratedtest.UriResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DnaAnalysisControllerIntegratedTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;
    private final HttpHeaders headers;

    public DnaAnalysisControllerIntegratedTest() {
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @Test
    public void shoulBeMutant() throws JsonProcessingException {
        final String[] dna = {
                "ATGCGA",
                "CAGTCC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        final ResponseEntity<String> response = callApi(new DnaAnalysisRequest(dna));

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    public void shouldNotBeMutant() throws JsonProcessingException {
        final String[] dna = {
                "ATGCGA",
                "CAGTCC",
                "TTATGT",
                "AGAAGG",
                "CCTCTA",
                "TCACTG"
        };

        final ResponseEntity<String> response = callApi(new DnaAnalysisRequest(dna));

        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCodeValue());
    }

    @Test
    public void shouldValidateNotSquareMatrix() throws JsonProcessingException {
        final String[] dna = {
                "ATGCGA",
                "CAGTCC",
                "TTATGT",
                "AGAAGG",
                "CCTCTA",
                "TCACA"
        };

        final ResponseEntity<String> response = callApi(new DnaAnalysisRequest(dna));

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(DnaStructureValidator.MSG_NOT_SQUARE, response.getBody());
    }

    @Test
    public void shouldValidateEmptyMatrix() throws JsonProcessingException {
        final String[] dna = {};
        final ResponseEntity<String> response = callApi(new DnaAnalysisRequest(dna));

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(DnaStructureValidator.MSG_NULL_OR_EMPTY, response.getBody());
    }

    @Test
    public void shouldValidateNullMatrix() throws JsonProcessingException {
        final String[] dna = null;
        final ResponseEntity<String> response = callApi(new DnaAnalysisRequest(dna));

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(DnaStructureValidator.MSG_NULL_OR_EMPTY, response.getBody());
    }

    private ResponseEntity<String> callApi(DnaAnalysisRequest request) throws JsonProcessingException {
        final String body = new ObjectMapper().writeValueAsString(request);
        final HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                UriResolver.localhost(port, UrlMapping.POST_DNA),
                HttpMethod.POST,
                entity,
                String.class);
    }
}
