package br.com.mercadolivre.mutantidentifier.analysis.factories;

import br.com.mercadolivre.mutantidentifier.analysis.analyzers.LineAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.sequences.BackslashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.sequences.ColumnAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.sequences.SlashDirectionAnalyzer;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnalyzerFactoryTest {

    private static final int MUTANT_FACTOR = 4;

    @Test
    public void shouldCreateLineAnalyzer() {
        final LineAnalyzer lineAnalyzer = new AnalyzerFactory()
                .createLineAnalyzer(MUTANT_FACTOR);
        assertNotNull(lineAnalyzer);
    }

    @Test
    public void shouldCreateColumnAnalyzer() {
        final ColumnAnalyzer analyzer = new AnalyzerFactory()
                .createColumnAnalyzer(MUTANT_FACTOR, 4);
        assertNotNull(analyzer);
    }

    @Test
    public void shouldSlashDirectionAnalyzer() {
        final SlashDirectionAnalyzer analyzer = new AnalyzerFactory()
                .createSlashDirectionAnalyzer(MUTANT_FACTOR, 4);
        assertNotNull(analyzer);
    }

    @Test
    public void shouldCreateBackslashDirectionAnalyzer() {
        final BackslashDirectionAnalyzer analyzer = new AnalyzerFactory()
                .createBackslashDirectionAnalyzer(MUTANT_FACTOR, 4);
        assertNotNull(analyzer);
    }


}
