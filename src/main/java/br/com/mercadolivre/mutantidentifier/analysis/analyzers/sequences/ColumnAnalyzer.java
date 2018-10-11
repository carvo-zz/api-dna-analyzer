package br.com.mercadolivre.mutantidentifier.analysis.analyzers.sequences;

public class ColumnAnalyzer extends SequenceAnalyzer {

    public ColumnAnalyzer(int mutantFactor, int matrixDim) {
        super(mutantFactor, matrixDim);
    }

    @Override
    protected int resolveIndex(int lineIdx, int columnIdx) {
        return columnIdx;
    }

}
