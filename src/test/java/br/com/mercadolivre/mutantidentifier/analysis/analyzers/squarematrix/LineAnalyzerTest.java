package br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineAnalyzerTest {

    @Test
    public void shouldFindFourSequences() {
        final String[] dna = {
                "CACATAAA",
                "CTCGGGAT",
                "TACATAAC",
                "CAAATGAG",
                "ATTTGAAA",
                "GGGGTCAT",
                "TTTTTTTT",
                "AAAAGGCG"
        };

        SequenceAnalyzer analyzer = new LineAnalyzer(4, dna.length);
        CommonExecutorForTest.commonExecution(dna, analyzer);

        assertEquals(4, analyzer.getCountMutantSequence());
    }

    @Test
    public void shouldZeroSequences() {
        final String[] dna = {
                "CACATAAA",
                "CTCGGGAT",
                "TACATAAC",
                "CATATGAG",
                "ATTTGAAA",
                "GGGTTCAT",
                "TTTGTTTA",
                "AGAAGGCG"
        };

        SequenceAnalyzer analyzer = new LineAnalyzer(4, dna.length);
        CommonExecutorForTest.commonExecution(dna, analyzer);

        assertEquals(0, analyzer.getCountMutantSequence());
    }
}
