package br.com.mercadolivre.mutantidentifier.analyzers.sequences;

public class ColumnSequenceAnalyzer extends SequenceAnalyzer {

    public ColumnSequenceAnalyzer(int mutantFactor, int matrixDim) {
        super(mutantFactor, matrixDim);
    }

    @Override
    protected int resolveIndex(int lineIdx, int columnIdx) {
        return columnIdx;
    }

}
