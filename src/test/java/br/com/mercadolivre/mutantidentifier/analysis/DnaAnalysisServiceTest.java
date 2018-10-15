package br.com.mercadolivre.mutantidentifier.analysis;

import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.BackslashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.ColumnAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.LineAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.analyzers.squarematrix.SlashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analysis.factories.AnalyzerFactory;
import br.com.mercadolivre.mutantidentifier.analysis.factories.HashHolderFactory;
import br.com.mercadolivre.mutantidentifier.analysis.helpers.HashHolder;
import br.com.mercadolivre.mutantidentifier.reports.ReportService;
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
public class DnaAnalysisServiceTest {

    @InjectMocks
    private DnaAnalysisService dnaService;

    @Mock
    private ReportService reportService;

    @Mock
    private AnalyzerFactory factory;

    @Mock
    private LineAnalyzer lineAnalyzer;

    @Mock
    private ColumnAnalyzer colAnalyzer;

    @Mock
    private SlashDirectionAnalyzer slashAnalyzer;

    @Mock
    private BackslashDirectionAnalyzer backslashAnalyzer;

    @Mock
    private HashHolderFactory hashHolderFactory;

    @Mock
    private HashHolder hashHolder;

    @Test
    public void notIsMutant() {
        final String hash = "abc";
        final String[] dna = { "AABB", "CCBB", "BBTT", "ABCD" };
        final int matrixDim = dna.length;

        initRequiredMocks(matrixDim);
        when(hashHolder.getHash()).thenReturn(hash);

        final boolean isMutant = dnaService.isMutant(dna);

        assertFalse(isMutant);
        verify(lineAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
        verify(colAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
        verify(slashAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());
        verify(backslashAnalyzer, times(matrixDim * matrixDim)).computeGene(anyInt(), anyInt(), anyChar());

        verify(reportService, times(1)).computeDna(Boolean.FALSE, hash);
    }

    @Test
    public void isMutantInLinePlusColumn() {
        final String hash = "abc";
        final String[] dna = { "AAAA", "ATTG", "AGCT", "ATTG" };
        final int matrixDim = dna.length;

        initRequiredMocks(matrixDim);
        when(hashHolder.getHash()).thenReturn(hash);
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

        verify(reportService, times(1)).computeDna(Boolean.TRUE, hash);
    }

    private void initRequiredMocks(int matrixDim) {
        when(factory.createLineAnalyzer(DnaAnalysisService.MUTANT_FACTOR, matrixDim)).thenReturn(lineAnalyzer);
        when(factory.createColumnAnalyzer(DnaAnalysisService.MUTANT_FACTOR, matrixDim)).thenReturn(colAnalyzer);
        when(factory.createSlashDirectionAnalyzer(DnaAnalysisService.MUTANT_FACTOR, matrixDim)).thenReturn(slashAnalyzer);
        when(factory.createBackslashDirectionAnalyzer(DnaAnalysisService.MUTANT_FACTOR, matrixDim)).thenReturn(backslashAnalyzer);

        when(hashHolderFactory.createHashHolder(matrixDim, DnaAnalysisService.BUFFER_SIZE)).thenReturn(hashHolder);
    }

}
