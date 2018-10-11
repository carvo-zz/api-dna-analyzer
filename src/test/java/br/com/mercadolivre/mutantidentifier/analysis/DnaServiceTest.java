package br.com.mercadolivre.mutantidentifier.analysis;

import br.com.mercadolivre.mutantidentifier.analysis.analyzers.LineAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.sequences.BackslashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.sequences.ColumnAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.sequences.SlashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.factories.AnalyzerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DnaServiceTest {

    private static final int MUTANT_FACTOR = 4;

    @InjectMocks
    DnaService dnaService;

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
        verify(lineAnalyzer, times(matrixDim)).computeLine(anyString());
        verify(colAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
        verify(slashAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
        verify(backslashAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
    }

    @Test
    public void isMutantInLinePlusColumn() {
        final String[] dna = {"AA", "BB" };
        final int matrixDim = dna.length;

        initRequiredMocks(matrixDim);
        when(lineAnalyzer.getCountMutantSequence()).thenAnswer(new Answer() {
            private int count = 1;

            public Object answer(InvocationOnMock invocation) {
                if (count++ >= 4 * matrixDim)
                    return 1;

                return 0;
            }
        });
        when(colAnalyzer.getCountMutantSequence()).thenAnswer(new Answer() {
            private int count = 0;

            public Object answer(InvocationOnMock invocation) {
                if (count++ >= 4 * matrixDim)
                    return 1;

                return 0;
            }
        });

        final boolean isMutant = dnaService.isMutant(dna);

        assertTrue(isMutant);
        verify(lineAnalyzer, times(2)).computeLine(anyString());
        verify(colAnalyzer, times(3)).computeGene(anyInt(), anyInt(), anyChar());
        verify(slashAnalyzer, times(2)).computeGene(anyInt(), anyInt(), anyChar());
        verify(backslashAnalyzer, times(2)).computeGene(anyInt(), anyInt(), anyChar());
    }

    @Test
    public void isMutantInFirstLine() {
        final String[] dna = {"AA", "BB" };
        final int matrixDim = dna.length;

        this.initRequiredMocks(matrixDim);
        when(lineAnalyzer.getCountMutantSequence()).thenReturn(2);

        final boolean isMutant = dnaService.isMutant(dna);

        assertTrue(isMutant);
        verify(lineAnalyzer, times(1)).computeLine(anyString());
        verify(colAnalyzer, never()).computeGene(anyInt(), anyInt(), anyChar());
        verify(slashAnalyzer, never()).computeGene(anyInt(), anyInt(), anyChar());
        verify(backslashAnalyzer, never()).computeGene(anyInt(), anyInt(), anyChar());
    }

    private void initRequiredMocks(int matrixDim) {
        when(factory.createLineAnalyzer(MUTANT_FACTOR)).thenReturn(lineAnalyzer);
        when(factory.createColumnAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(colAnalyzer);
        when(factory.createSlashDirectionAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(slashAnalyzer);
        when(factory.createBackslashDirectionAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(backslashAnalyzer);
    }

}
