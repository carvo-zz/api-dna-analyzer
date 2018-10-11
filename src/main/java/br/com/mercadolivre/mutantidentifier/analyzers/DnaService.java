package br.com.mercadolivre.mutantidentifier.analyzers;

import br.com.mercadolivre.mutantidentifier.analyzers.sequences.ColumnAnalyzer;
import br.com.mercadolivre.mutantidentifier.analyzers.sequences.BackslashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analyzers.sequences.SlashDirectionAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DnaService {

    private static final Logger LOG = LoggerFactory.getLogger(DnaService.class);
    private static final int MUTANT_FACTOR = 4;

    @Autowired
    private AnalyzerFactory analyzerFactory;

    public DnaService() { }

    public boolean isMutant(final String[] dna) {
        final long startedAt = System.currentTimeMillis();
        long countInterections = 0;

        final int dim = dna.length;

        final LineAnalyzer lineAnalyzer = analyzerFactory.createLineAnalyzer(MUTANT_FACTOR);
        final ColumnAnalyzer colAnalyzer = analyzerFactory.createColumnAnalyzer(MUTANT_FACTOR, dim);
        final SlashDirectionAnalyzer slashAnalyzer = analyzerFactory.createSlashDirectionAnalyzer(MUTANT_FACTOR, dim);
        final BackslashDirectionAnalyzer backslashAnalyzer = analyzerFactory.createBackslashDirectionAnalyzer(MUTANT_FACTOR, dim);

        boolean isMutant = Boolean.FALSE;
        int horizIdx = 0;
        while (!isMutant && horizIdx < dim) {
            final String line = dna[horizIdx];

            lineAnalyzer.processLine(line);
            if (foundMutant(lineAnalyzer, colAnalyzer, slashAnalyzer, backslashAnalyzer)) {
                isMutant = Boolean.TRUE;
                break;
            }

            int vertIdx = 0;
            while (!isMutant && vertIdx < dim) {
                countInterections++;
                final char current = line.charAt(vertIdx);

                colAnalyzer.computeGene(horizIdx, vertIdx, current);
                isMutant = foundMutant(lineAnalyzer, colAnalyzer, slashAnalyzer, backslashAnalyzer);

                if (!isMutant) {
                    slashAnalyzer.computeGene(horizIdx, vertIdx, current);
                    isMutant = foundMutant(lineAnalyzer, colAnalyzer, slashAnalyzer, backslashAnalyzer);
                }

                if (!isMutant) {
                    backslashAnalyzer.computeGene(horizIdx, vertIdx, current);
                    isMutant = foundMutant(lineAnalyzer, colAnalyzer, slashAnalyzer, backslashAnalyzer);
                }

                vertIdx++;
            }

            horizIdx++;
        }

        LOG.info("Mutant sequences found: " +
                        "\n\t {} lines, \n\t {} columns, \n\t {} slash dir, \n\t {} backslash dir",
                lineAnalyzer.getCountMutantSequence(), colAnalyzer.getCountMutantSequence(),
                slashAnalyzer.getCountMutantSequence(), backslashAnalyzer.getCountMutantSequence());

        LOG.trace("Total interections: " + countInterections + " in " + (System.currentTimeMillis() - startedAt) + "ms");

        return isMutant;
    }

    private boolean foundMutant(LineAnalyzer lineAnalyzer, ColumnAnalyzer colAnalyzer,
                                SlashDirectionAnalyzer slashAnalyzer, BackslashDirectionAnalyzer backslashAnalyzer) {
        return lineAnalyzer.getCountMutantSequence() +
                colAnalyzer.getCountMutantSequence() +
                slashAnalyzer.getCountMutantSequence() +
                backslashAnalyzer.getCountMutantSequence() > 1;
    }

}
