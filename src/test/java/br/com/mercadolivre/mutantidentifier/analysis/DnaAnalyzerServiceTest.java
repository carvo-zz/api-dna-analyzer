package br.com.mercadolivre.mutantidentifier.analysis;

import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.BackslashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.ColumnAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.LineAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.SlashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.factories.AnalyzerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.jmx.export.annotation.ManagedOperation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DnaAnalyzerServiceTest {

    private static final int MUTANT_FACTOR = 4;

    @InjectMocks
    DnaAnalyzerService dnaService;

    @Mock
    DnaDatastore datastore;

    @Mock
    AnalyzerFactory factory;

    @Mock
    LineAnalyzer lineAnalyzer;

    @Mock
    ColumnAnalyzer colAnalyzer;

    @Mock
    SlashDirectionAnalyzer slashAnalyzer;

    @Mock
    BackslashDirectionAnalyzer backslashAnalyzer;

    @Test
    public void notIsMutant() {
        final String[] dna = { "AABB", "CCBB", "BBTT", "ABCD" };
        final int matrixDim = dna.length;

        initRequiredMocks(matrixDim);

        final boolean isMutant = dnaService.isMutant(dna);

        assertFalse(isMutant);
        verify(lineAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
        verify(colAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
        verify(slashAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
        verify(backslashAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
    }

    @Test
    public void isMutantInLinePlusColumn() {
        final String[] dna = { "AAAA", "ATTG", "AGCT", "ATTG" };
        final int matrixDim = dna.length;

        initRequiredMocks(matrixDim);
        when(lineAnalyzer.getCountMutantSequence()).thenAnswer(new Answer() {
            private int count = 0;
            private final int numAnalyzers = 4;
            private final int charIdx = 4;

            public Object answer(InvocationOnMock invocation) {
                if (count++ >= charIdx * numAnalyzers)
                    return 1;

                return 0;
            }
        });
        when(colAnalyzer.getCountMutantSequence()).thenAnswer(new Answer() {
            private int count = 0;
            private final int numAnalyzers = 4;
            private final int charIdx = 13;

            public Object answer(InvocationOnMock invocation) {
                if (count++ > charIdx * numAnalyzers)
                    return 1;

                return 0;
            }
        });

        final boolean isMutant = dnaService.isMutant(dna);

        assertTrue(isMutant);
        verify(lineAnalyzer, times(14)).computeGene(anyInt(), anyInt(), anyChar());
        verify(colAnalyzer, times(14)).computeGene(anyInt(), anyInt(), anyChar());
        verify(slashAnalyzer, times(13)).computeGene(anyInt(), anyInt(), anyChar());
        verify(backslashAnalyzer, times(13)).computeGene(anyInt(), anyInt(), anyChar());
    }

    private void initRequiredMocks(int matrixDim) {
        when(factory.createLineAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(lineAnalyzer);
        when(factory.createColumnAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(colAnalyzer);
        when(factory.createSlashDirectionAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(slashAnalyzer);
        when(factory.createBackslashDirectionAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(backslashAnalyzer);
    }

}
