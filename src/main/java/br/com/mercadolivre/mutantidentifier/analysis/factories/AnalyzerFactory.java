package br.com.mercadolivre.mutantidentifier.analysis.factories;

import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.BackslashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.ColumnAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.LineAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.SlashDirectionAnalyzer;
import org.springframework.stereotype.Component;

@Component
public class AnalyzerFactory {

    public LineAnalyzer createLineAnalyzer(final int mutantFactor, final int matrixDim) {
        return new LineAnalyzer(mutantFactor, matrixDim);
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
