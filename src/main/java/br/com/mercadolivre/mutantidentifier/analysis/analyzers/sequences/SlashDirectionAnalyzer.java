package br.com.mercadolivre.mutantidentifier.analysis.analyzers.sequences;

/**
 * Sequence analysis of obliques in SOUTH WEST direction (&#8601;)
 *
 * @author carvo
 */
public class SlashDirectionAnalyzer extends SequenceAnalyzer {

    public SlashDirectionAnalyzer(int mutantFactor, int matrixDim) {
        super(mutantFactor, matrixDim * 2);
    }

    @Override
    protected int resolveIndex(int lineIdx, int columnIdx) {
        return lineIdx + columnIdx;
    }

}
