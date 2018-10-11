package br.com.mercadolivre.mutantidentifier.analyzers.sequences;

/**
 * Sequence analysis of obliques in SOUTH EAST direction (&#8600;)
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
