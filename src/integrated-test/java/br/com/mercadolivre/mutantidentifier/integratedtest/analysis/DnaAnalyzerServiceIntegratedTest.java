package br.com.mercadolivre.mutantidentifier.integratedtest.analysis;

import br.com.mercadolivre.mutantidentifier.analysis.DnaAnalyzerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DnaAnalyzerServiceIntegratedTest {

    @Autowired
    private DnaAnalyzerService dnaService;

    @Test
    public void isMutantOnlyInVertical() {
        final String[] dna = {
                "CACATAAA",
                "CTCGGGAT",
                "CACATAAC",
                "CAAATGAG",
                "ATTTGAAA",
                "AAGTTCAT",
                "TTGTGTTC",
                "AGAAGGCG"
        };

        final boolean b = dnaService.isMutant(dna);

        assertTrue(b);
    }

    @Test
    public void isMutantOnlyInHorizontal() {
        final String[] dna = {
                "AAAATCGA",
                "CAGTGCTA",
                "TTATGTCT",
                "AGAAGGAT",
                "CTCCTAAT",
                "AAGGTTCC",
                "AAGGCTCC",
                "GGAATTTT"
        };

        final boolean b = dnaService.isMutant(dna);

        assertTrue(b);
    }

    @Test
    public void isMutantOnlyInHorizontalSameLine() {
        final String[] dna = {
                "ACAATCGA",
                "CAGTGCTA",
                "TTATGTCT",
                "AGAAGGAT",
                "CTCCTAAT",
                "AAGGTTCC",
                "AAGGCTCC",
                "TTTTTTTT"
        };

        final boolean b = dnaService.isMutant(dna);

        assertTrue(b);
    }

    /**
     * Horizontal: AAAAAAAA first line
     * Oblique: TTTT last four lines
     */
    @Test
    public void isMutantInHorizontalPlusOblique() {
        final String[] dna = {
                "AAAATCTA",
                "CCGTGCTA",
                "TTCTGTCT",
                "AGAAGGAT",
                "CTCCTAAT",
                "AAGGTTCC",
                "AAGGCTTC",
                "GGAATGTT"
        };

        final boolean b = dnaService.isMutant(dna);

        assertTrue(b);
    }

    /**
     * Vertical: CCCC first column last lines
     * Oblique: TTTT last four lines
     */
    @Test
    public void isMutantInVerticalPlusOblique() {
        final String[] dna = {
                "ACGTATAA",
                "CCGTGCTA",
                "TTATGTCT",
                "AGAAGGAT",
                "CTCCTAAT",
                "CAGGTTCC",
                "CAGGCTTC",
                "CGAATCTT"
        };

        final boolean b = dnaService.isMutant(dna);

        assertTrue(b);
    }

    @Test
    public void isMutantOnlySouthwestOblique() {
        final String[] dna = {
                "ACGTATAA",
                "CCGTTCTA",
                "TTATGTCT",
                "AGTAGGAT",
                "CTCCTAAT",
                "CAGGTTTC",
                "TAGGCTTC",
                "CGAATCTC"
        };

        final boolean b = dnaService.isMutant(dna);

        assertTrue(b);
    }

    @Test
    public void isMutantOnlySoutheastOblique() {
        final String[] dna = {
                "ACGTATAA",
                "CCGTCCTA",
                "TTATGTCT",
                "AGTAGGAT",
                "CTCATAAT",
                "CAAGTTTC",
                "TAGGCTTC",
                "AGAATCTT"
        };

        final boolean b = dnaService.isMutant(dna);

        assertTrue(b);
    }

}