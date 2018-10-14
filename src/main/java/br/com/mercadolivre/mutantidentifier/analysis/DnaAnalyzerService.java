package br.com.mercadolivre.mutantidentifier.analysis;

import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.BackslashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.ColumnAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.LineAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.SlashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.factories.AnalyzerFactory;
import br.com.mercadolivre.mutantidentifier.analysis.helpers.HashHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
public class DnaAnalyzerService {

    private static final Logger LOG = LoggerFactory.getLogger(DnaAnalyzerService.class);

    private static final int MUTANT_FACTOR = 4;
    private static final int LINES_FOR_HASH = 1000;

    @Autowired
    private AnalyzerFactory analyzerFactory;

    @Autowired
    private DnaDatastore dnaDatastore;

    public DnaAnalyzerService() {
    }

    public boolean isMutant(final String[] dna) {
        final long startedAt = System.currentTimeMillis();
        long totalInteractions = 0; // for trace

        final int dim = dna.length;

        final LineAnalyzer lineAnalyzer = analyzerFactory.createLineAnalyzer(MUTANT_FACTOR, dim);
        final ColumnAnalyzer colAnalyzer = analyzerFactory.createColumnAnalyzer(MUTANT_FACTOR, dim);
        final SlashDirectionAnalyzer slashAnalyzer = analyzerFactory.createSlashDirectionAnalyzer(MUTANT_FACTOR, dim);
        final BackslashDirectionAnalyzer backslashAnalyzer = analyzerFactory.createBackslashDirectionAnalyzer(MUTANT_FACTOR, dim);

        final HashHolder hashHolder = new HashHolder(dim);

        boolean isMutant = Boolean.FALSE;
        int lineIdx = 0;
        while (!isMutant && lineIdx < dim) {
            final String line = dna[lineIdx];
            hashHolder.computeLine(line);

            int columnIdx = 0;
            while (!isMutant && columnIdx < dim) {
                totalInteractions++;
                final char gene = line.charAt(columnIdx);

                lineAnalyzer.computeGene(lineIdx, columnIdx, gene);
                isMutant = foundMutant(lineAnalyzer, colAnalyzer, slashAnalyzer, backslashAnalyzer);

                if (!isMutant) {
                    colAnalyzer.computeGene(lineIdx, columnIdx, gene);
                    isMutant = foundMutant(lineAnalyzer, colAnalyzer, slashAnalyzer, backslashAnalyzer);
                }

                if (!isMutant) {
                    slashAnalyzer.computeGene(lineIdx, columnIdx, gene);
                    isMutant = foundMutant(lineAnalyzer, colAnalyzer, slashAnalyzer, backslashAnalyzer);
                }

                if (!isMutant) {
                    backslashAnalyzer.computeGene(lineIdx, columnIdx, gene);
                    isMutant = foundMutant(lineAnalyzer, colAnalyzer, slashAnalyzer, backslashAnalyzer);
                }

                columnIdx++;
            }

            lineIdx++;
        }

        //TODO async
        if (lineIdx != dim - 1) {
            IntStream.range(lineIdx, dim).forEach(i -> hashHolder.computeLine(dna[i]));
        }

        dnaDatastore.computeDna(isMutant, hashHolder.getHash());

        LOG.info("Mutant squarematrix found: " +
                        "\n\t {} in lines, \n\t {} in columns, \n\t {} in slash directs, \n\t {} in backslash directs",
                lineAnalyzer.getCountMutantSequence(), colAnalyzer.getCountMutantSequence(),
                slashAnalyzer.getCountMutantSequence(), backslashAnalyzer.getCountMutantSequence());

        LOG.trace("Total interections: " + totalInteractions + " in " + (System.currentTimeMillis() - startedAt) + "ms");

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
