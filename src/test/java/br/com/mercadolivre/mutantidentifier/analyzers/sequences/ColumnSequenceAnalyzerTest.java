package br.com.mercadolivre.mutantidentifier.analyzers.sequences;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColumnSequenceAnalyzerTest {

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

        SequenceAnalyzer analyzer = new ColumnSequenceAnalyzer(4, dna.length);
        CommonExecutorForTest.commonExecution(dna, analyzer);

        assertEquals(2, analyzer.getCountMutantSequence());
    }


}
