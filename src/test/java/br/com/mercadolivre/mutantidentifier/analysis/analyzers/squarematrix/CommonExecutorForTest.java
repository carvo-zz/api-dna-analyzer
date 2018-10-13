package br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix;

public final class CommonExecutorForTest {

    private CommonExecutorForTest() {}

    public static void commonExecution(final String[] dna, final SequenceAnalyzer analyzer) {
        for (int horizIdx = 0; horizIdx < dna.length; horizIdx++) {
            final String line = dna[horizIdx];
            for (int vertIdx = 0; vertIdx < dna.length; vertIdx++) {
                analyzer.computeGene(horizIdx, vertIdx, line.charAt(vertIdx));
            }
        }
    }

}
