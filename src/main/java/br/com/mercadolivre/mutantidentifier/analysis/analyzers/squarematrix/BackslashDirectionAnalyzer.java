package br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix;

/**
 * Sequence analysis of obliques in <strong>backslash (&#8600; or &#8598;)</strong> direction
 *
 * @author carvo
 */
public class BackslashDirectionAnalyzer extends SequenceAnalyzer {

    private final int matrixDim;

    public BackslashDirectionAnalyzer(int mutantFactor, int matrixDim) {
        super(mutantFactor, matrixDim * 2);
        this.matrixDim = matrixDim;
    }

    @Override
    protected int resolveIndex(int lineIdx, int columnIdx) {
        int idx = lineIdx - columnIdx;
        if (idx < 0) {
            idx = (idx * -1) + matrixDim;
        }

        return idx;
    }

}
