package br.com.mercadolivre.mutantidentifier.analyzers;

import br.com.mercadolivre.mutantidentifier.analyzers.sequences.BackslashDirectionAnalyzer;
import br.com.mercadolivre.mutantidentifier.analyzers.sequences.ColumnAnalyzer;
import br.com.mercadolivre.mutantidentifier.analyzers.sequences.SlashDirectionAnalyzer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void isMutantInLinePlusColumn() {
        final String[] dna = {"AA", "BB" };
        final int matrixDim = dna.length;

        when(factory.createLineAnalyzer(MUTANT_FACTOR)).thenReturn(lineAnalyzer);
        when(factory.createColumnAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(colAnalyzer);
        when(factory.createSlashDirectionAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(slashAnalyzer);
        when(factory.createBackslashDirectionAnalyzer(MUTANT_FACTOR, matrixDim)).thenReturn(backslashAnalyzer);

//        when(lineAnalyzer.getCountMutantSequence())
//                .thenReturn(0).thenReturn(0).thenReturn(0).thenReturn(0)//firs line interection
//                .thenReturn(1) ;
//
//        when(colAnalyzer.getCountMutantSequence())
//                .thenReturn(0).thenReturn(0).thenReturn(0).thenReturn(0)//firs line interection
//                .thenReturn(1);

        final boolean isMutant = dnaService.isMutant(dna);

        assertTrue(isMutant);
    }

}
