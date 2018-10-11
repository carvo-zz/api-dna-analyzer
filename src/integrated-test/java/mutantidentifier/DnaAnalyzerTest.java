package mutantidentifier;

import br.com.mercadolivre.mutantidentifier.analysis.DnaService;
import org.junit.Test;

import static org.junit.Assert.*;

public class DnaAnalyzerTest {

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

        final boolean b = new DnaService().isMutant(dna);

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

        final boolean b = new DnaService().isMutant(dna);

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

        final boolean b = new DnaService().isMutant(dna);

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

        final boolean b = new DnaService().isMutant(dna);

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

        final boolean b = new DnaService().isMutant(dna);

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

        final boolean b = new DnaService().isMutant(dna);

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

        final boolean b = new DnaService().isMutant(dna);

        assertTrue(b);
    }

}
