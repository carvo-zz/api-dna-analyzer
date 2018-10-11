package br.com.mercadolivre.mutantidentifier.analyzers;

import br.com.mercadolivre.mutantidentifier.analyzers.sequences.ColumnAnalyzer;
import br.com.mercadolivre.mutantidentifier.analyzers.sequences.BackslashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analyzers.sequences.SlashDirectionAnalyzer;
import org.springframework.stereotype.Component;

@Component
public class AnalyzerFactory {

    public LineAnalyzer createLineAnalyzer(final int mutantFactor) {
        return new LineAnalyzer(mutantFactor);
    }

    public SlashDirectionAnalyzer createSlashDirectionAnalyzer(final int mutantFactor, final int matrixDim) {
        return new SlashDirectionAnalyzer(mutantFactor, matrixDim);
    }

    public BackslashDirectionAnalyzer createBackslashDirectionAnalyzer(final int mutantFactor, final int matrixDim) {
        return new BackslashDirectionAnalyzer(mutantFactor, matrixDim);
    }

    public ColumnAnalyzer createColumnAnalyzer(int mutantFactor, int matrixDim) {
        return new ColumnAnalyzer(mutantFactor, matrixDim);
    }

}
