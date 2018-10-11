package br.com.mercadolivre.mutantidentifier.analysis.analyzers.sequences;

import org.junit.Test;

import static org.junit.Assert.*;

public class SlashDirectionAnalyzerTest {

    @Test
    public void shouldFindOneSequence() {
        final String[] dna = {
                "ACGTATAA",
                "CCGTCCTA",
                "TTATGTCT",
                "AGTAGGAT",
                "CTCATAAT",
                "CAAGTTTC",
                "TAGGCCTC",
                "AGAATCTT"
        };

        final SlashDirectionAnalyzer analyzer = new SlashDirectionAnalyzer(4, dna.length);
        CommonExecutorForTest.commonExecution(dna, analyzer);

        assertEquals(1, analyzer.getCountMutantSequence());
    }

    @Test
    public void shouldFindTwoSequencesInSameOblique() {
        final String[] dna = {
                "ACGTATAA",
                "CCGTCCAA",
                "TTATGACT",
                "AGTAAGAT",
                "CTCATAAT",
                "CAAGTTTC",
                "TAGGCCTC",
                "AGAATCTT"
        };

        final SlashDirectionAnalyzer analyzer = new SlashDirectionAnalyzer(4, dna.length);
        CommonExecutorForTest.commonExecution(dna, analyzer);

        assertEquals(2, analyzer.getCountMutantSequence());
    }

}
