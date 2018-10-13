package br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix;

public class LineAnalyzer extends SequenceAnalyzer {

    public LineAnalyzer(int mutantFactor, int holdersDim) {
        super(mutantFactor, holdersDim);
    }

    @Override
    protected int resolveIndex(int lineIdx, int columnIdx) {
        return lineIdx;
    }

}
