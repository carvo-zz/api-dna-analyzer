package br.com.mercadolivre.mutantidentifier.analysis.validators;

import org.junit.Test;

import javax.xml.validation.Validator;
import java.util.Optional;

import static org.junit.Assert.*;

public class DnaStructureValidatorTest {

    @Test
    public void shoulBeValidMatrix() {
        final String[] dna = {
                "ATGCGA",
                "CAGTCC",
                "TTATGT",
                "AGAAGG",
                "CCTCTA",
                "TCACAA"
        };

        final Optional<String> error = new DnaStructureValidator().validate(dna);

        assertFalse(error.isPresent());
    }

    @Test
    public void shouldValidateNonSquareMatrix() {
        final String[] dna = {
                "ATGCGA",
                "CAGTCC",
                "TTATGT",
                "AGAAGG",
                "CCTCTA",
                "TCACA"
        };

        final Optional<String> error = new DnaStructureValidator().validate(dna);

        assertTrue(error.isPresent());
        assertEquals(DnaStructureValidator.MSG_NOT_SQUARE, error.get());
    }

    @Test
    public void shouldValidateNullMatrix() {
        final String[] dna = null;

        final Optional<String> error = new DnaStructureValidator().validate(dna);

        assertTrue(error.isPresent());
        assertEquals(DnaStructureValidator.MSG_NULL_OR_EMPTY, error.get());
    }

    @Test
    public void isSquareMatrix() {
        final String[] dna = {
                "ATGCGA",
                "CAGTCC",
                "TTATGT",
                "AGAAGG",
                "CCTCTA",
                "TCACAA"
        };

        final Optional<String> error = new DnaStructureValidator().isSquareMatrix(dna);

        assertFalse(error.isPresent());
    }

    @Test
    public void notIsSquareMatrix() {
        final String[] dna = {
                "ATGCGA",
                "CAGTCC",
                "TTATGT",
                "AGAAGG",
                "CCTCTA",
                "TCACA"
        };

        final Optional<String> error = new DnaStructureValidator().isSquareMatrix(dna);

        assertTrue(error.isPresent());
        assertEquals(DnaStructureValidator.MSG_NOT_SQUARE, error.get());
    }

    @Test
    public void isNotNullAndNotEmpty() {
        final String[] dna = {
                "1234",
                "1234",
                "1234",
                "1234"
        };

        final Optional<String> error = new DnaStructureValidator().isNotNull(dna);

        assertFalse(error.isPresent());
    }

    @Test
    public void isEmptyArray() {
        final String[] dna = { };

        final Optional<String> error = new DnaStructureValidator().isNotNull(dna);

        assertTrue(error.isPresent());
        assertEquals(DnaStructureValidator.MSG_NULL_OR_EMPTY, error.get());
    }

    @Test
    public void isNullArray() {
        final String[] dna = null;

        final Optional<String> error = new DnaStructureValidator().isNotNull(dna);

        assertTrue(error.isPresent());
        assertEquals(DnaStructureValidator.MSG_NULL_OR_EMPTY, error.get());
    }


}
