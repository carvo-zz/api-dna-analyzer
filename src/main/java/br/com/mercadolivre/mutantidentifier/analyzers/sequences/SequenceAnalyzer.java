package br.com.mercadolivre.mutantidentifier.analyzers.sequences;

import br.com.mercadolivre.mutantidentifier.analyzers.MutantSequenceCountable;

public abstract class SequenceAnalyzer implements MutantSequenceCountable {

    private final int mutantFactor;

    private char lastGene[];
    private int countSequence[];
    private int countMutantSequence;

    public SequenceAnalyzer(final int mutantFactor, final int holdersDim) {
        this.mutantFactor = mutantFactor;
        this.lastGene = new char[holdersDim];
        this.countSequence = new int[holdersDim];
        this.countMutantSequence = 0;
    }

    protected abstract int resolveIndex(final int lineIdx, final int columnIdx);

    public void computeGene(final int lineIdx, final int columnIdx, final char gene) {
        final int idx = resolveIndex(lineIdx, columnIdx);

        if (isValidGene(gene) && gene == lastGene[idx]) {
            countSequence[idx] += 1;
        }
        else {
            countSequence[idx] = 1;
        }

        if (countSequence[idx] == mutantFactor) {
            countMutantSequence += 1;
            lastGene[idx] = '\u0000';
        }
        else {
            lastGene[idx] = gene;
        }
    }

    private boolean isValidGene(char current) {
        return (current == 'A' || current == 'T' || current == 'C' || current == 'G');
    }

    @Override
    public int getCountMutantSequence() {
        return countMutantSequence;
    }
}
