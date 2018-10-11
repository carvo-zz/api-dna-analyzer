package br.com.mercadolivre.mutantidentifier.analyzers;

import br.com.mercadolivre.mutantidentifier.analyzers.sequences.ColumnSequenceAnalyzer;
import br.com.mercadolivre.mutantidentifier.analyzers.sequences.SoutheastSequenceAnalyzer;
import br.com.mercadolivre.mutantidentifier.analyzers.sequences.SouthwestSequenceAnalyzer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DnaAnalyzer.class })
public class DnaAnalyzerTest {

    @Test
    public void isMutant() throws Exception {
        final String dna[] = new String[0];

        final ColumnSequenceAnalyzer colAnalyzer = mock(ColumnSequenceAnalyzer.class);
        final SoutheastSequenceAnalyzer seAnalyzer = mock(SoutheastSequenceAnalyzer.class);
        final SouthwestSequenceAnalyzer swAnalyzer = mock(SouthwestSequenceAnalyzer.class);
        final LineAnalyzer lineAnalyzer = mock(LineAnalyzer.class);

        whenNew(ColumnSequenceAnalyzer.class).withAnyArguments().thenReturn(colAnalyzer);
        whenNew(SoutheastSequenceAnalyzer.class).withAnyArguments().thenReturn(seAnalyzer);
        whenNew(SouthwestSequenceAnalyzer.class).withAnyArguments().thenReturn(swAnalyzer);
        whenNew(LineAnalyzer.class).withAnyArguments().thenReturn(lineAnalyzer);

        final boolean isMutant = new DnaAnalyzer().isMutant(dna);

        assertTrue(isMutant);
    }

}
