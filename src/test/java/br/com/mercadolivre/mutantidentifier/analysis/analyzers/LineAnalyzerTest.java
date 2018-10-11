package br.com.mercadolivre.mutantidentifier.analysis.analyzers;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineAnalyzerTest {

    @Test
    public void shouldFindNoSequence() {
        final LineAnalyzer lineAnalyzer = new LineAnalyzer(4);
        lineAnalyzer.computeLine("ATCGATCGGGTTTAAACTATGCTAGTCAGTTGCATGCA");

        assertEquals(0, lineAnalyzer.getCountMutantSequence());
    }

    @Test
    public void shouldFindFourSequences() {
        final LineAnalyzer lineAnalyzer = new LineAnalyzer(4);
        lineAnalyzer.computeLine("AAAAATGCCCCATTTTTTTT");

        assertEquals(4, lineAnalyzer.getCountMutantSequence());
    }

    @Test
    public void shouldFindTwoSequencesIn150Millions() {
        final int size = 150_000_000;
        final StringBuilder sbLine = new StringBuilder(size);
        for (long i = 1; i <= size; i++) {
            final String gene = (isLast4Chars(i, size) || isNext5CharsAfterOneMillion(i)) ? "A" : "X";
            sbLine.append(gene);
        }

        long time = System.currentTimeMillis();

        final LineAnalyzer lineAnalyzer = new LineAnalyzer(4);
        lineAnalyzer.computeLine(sbLine.toString());

        System.out.println("computeLine: " + (System.currentTimeMillis() - time));

        assertEquals(2, lineAnalyzer.getCountMutantSequence());
    }

    private boolean isLast4Chars(long i, long size) {
        return i > size - 4;
    }

    private boolean isNext5CharsAfterOneMillion(long i) {
        return i > 1_000_000 && i < 1_000_006;
    }
}
