package br.com.mercadolivre.mutantidentifier.analyzers;

import br.com.mercadolivre.mutantidentifier.analyzers.sequences.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnaAnalyzer {

    private static final Logger LOG = LoggerFactory.getLogger(DnaAnalyzer.class);
    private static final int MUTANT_FACTOR = 4;

    public DnaAnalyzer() { }

    public boolean isMutant(final String[] dna) {
        final long startedAt = System.currentTimeMillis();
        long countInterections = 0;

        final int dim = dna.length;

        final SequenceAnalyzer colAnalyzer = new ColumnSequenceAnalyzer(MUTANT_FACTOR, dim);
        final SequenceAnalyzer seAnalyzer = new SoutheastSequenceAnalyzer(MUTANT_FACTOR, dim);
        final SequenceAnalyzer swAnalyzer = new SouthwestSequenceAnalyzer(MUTANT_FACTOR, dim);
        final LineAnalyzer lineAnalyzer = new LineAnalyzer(MUTANT_FACTOR);

        boolean isMutant = Boolean.FALSE;
        int horizIdx = 0;
        while (!isMutant && horizIdx < dim) {
            final String line = dna[horizIdx];

            lineAnalyzer.processLine(line);
            if (foundMutant(lineAnalyzer, colAnalyzer, seAnalyzer, swAnalyzer)) {
                isMutant = Boolean.TRUE;
                break;
            }

            int vertIdx = 0;
            while (!isMutant && vertIdx < dim) {
                countInterections++;
                final char current = line.charAt(vertIdx);

                colAnalyzer.processGene(horizIdx, vertIdx, current);
                isMutant = foundMutant(lineAnalyzer, colAnalyzer, seAnalyzer, swAnalyzer);

                if (!isMutant) {
                    swAnalyzer.processGene(horizIdx, vertIdx, current);
                    isMutant = foundMutant(lineAnalyzer, colAnalyzer, seAnalyzer, swAnalyzer);
                }

                if (!isMutant) {
                    seAnalyzer.processGene(horizIdx, vertIdx, current);
                    isMutant = foundMutant(lineAnalyzer, colAnalyzer, seAnalyzer, swAnalyzer);
                }

                vertIdx++;
            }

            horizIdx++;
        }

        LOG.info("Total interections: " + countInterections + " in " + (System.currentTimeMillis() - startedAt) + "ms");

        return isMutant;
    }

    private boolean foundMutant(MutantSequenceCountable lineAnalyzer, MutantSequenceCountable colAnalyzer,
                                MutantSequenceCountable seAnalyzer, MutantSequenceCountable swAnalyzer) {
        return lineAnalyzer.getCountMutantSequence() +
                colAnalyzer.getCountMutantSequence() +
                seAnalyzer.getCountMutantSequence() +
                swAnalyzer.getCountMutantSequence() > 1;
    }

}
