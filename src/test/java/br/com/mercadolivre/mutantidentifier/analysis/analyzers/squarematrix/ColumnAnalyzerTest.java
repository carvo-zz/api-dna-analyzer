package br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColumnAnalyzerTest {

    @Test
    public void shouldFindTwoSequences() {
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

        SequenceAnalyzer analyzer = new ColumnAnalyzer(4, dna.length);
        CommonExecutorForTest.commonExecution(dna, analyzer);

        assertEquals(2, analyzer.getCountMutantSequence());
    }

    @Test
    public void shouldFindZeroSequences() {
        final String[] dna = {
                "CACATAAA",
                "TTCGGGAT",
                "CACATATC",
                "CAAATGAG",
                "ATTTGAAA",
                "AAGTTCAT",
                "TTGTGTTC",
                "AGAAGGCG"
        };

        SequenceAnalyzer analyzer = new ColumnAnalyzer(4, dna.length);
        CommonExecutorForTest.commonExecution(dna, analyzer);

        assertEquals(0, analyzer.getCountMutantSequence());
    }


}
