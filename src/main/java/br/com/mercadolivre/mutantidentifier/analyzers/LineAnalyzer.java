package br.com.mercadolivre.mutantidentifier.analyzers;

public class LineAnalyzer implements MutantSequenceCountable {

    private final int mutantFactor;
    private int countMutantSequence;

    public LineAnalyzer(final int mutantFactor) {
        this.mutantFactor = mutantFactor;
        this.countMutantSequence = 0;
    }

    public void processLine(final String line) {
        char lastChar = '\u0000';
        int countSequence = 0;

        for (int i = 0; i < line.length(); i++) {
            final char c = line.charAt(i);
            if (isValidGene(c) && c == lastChar) {
                countSequence++;
            }
            else {
                countSequence = 1;
            }
            lastChar = c;

            if (countSequence == mutantFactor) {
                this.countMutantSequence += 1;
                lastChar = '\u0000';
                countSequence = 0;
            }
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
