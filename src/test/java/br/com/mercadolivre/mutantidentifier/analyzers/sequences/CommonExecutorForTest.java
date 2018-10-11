package br.com.mercadolivre.mutantidentifier.analyzers.sequences;

public final class CommonExecutorForTest {

    private CommonExecutorForTest() {}

    public static void commonExecution(final String[] dna, final SequenceAnalyzer analyzer) {
        for (int horizIdx = 0; horizIdx < dna.length; horizIdx++) {
            final String line = dna[horizIdx];
            for (int vertIdx = 0; vertIdx < dna.length; vertIdx++) {
                analyzer.processGene(horizIdx, vertIdx, line.charAt(vertIdx));
            }
        }
    }

}
