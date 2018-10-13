package br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix;

/**
 * Sequence analysis of obliques in <strong>slash (&#8601; or &#8599;)</strong> direction
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
