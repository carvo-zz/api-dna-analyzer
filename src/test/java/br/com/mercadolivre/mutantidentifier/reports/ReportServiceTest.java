package br.com.mercadolivre.mutantidentifier.reports;

import br.com.mercadolivre.mutantidentifier.datastores.DnaAnalysisDatastore;
import br.com.mercadolivre.mutantidentifier.datastores.ReportDatastore;
import br.com.mercadolivre.mutantidentifier.model.Stats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @InjectMocks
    private ReportService service;

    @Mock
    private DnaAnalysisDatastore analysisDs;

    @Mock
    private ReportDatastore reportDs;

    @Test
    public void shouldReturnStats() {
        final Stats stats = new Stats(1, 1);
        when(reportDs.getStats()).thenReturn(stats);

        final Stats returnedStats = service.getStats();

        assertSame(stats, returnedStats);
        verify(reportDs, times(1)).getStats();
        verify(reportDs, never()).summarizeDna(anyBoolean());
    }
    
    @Test
    public void shouldSummarize() {
        final String hash = "";
        final boolean isMutant = true;

        when(analysisDs.contains(hash)).thenReturn(Boolean.FALSE);

        final boolean b = service.computeDna(isMutant, hash);

        assertTrue(b);
        verify(analysisDs, times(1)).contains(hash);
        verify(analysisDs, times(1)).persistDna(isMutant, hash);
        verify(reportDs, times(1)).summarizeDna(isMutant);
    }

    @Test
    public void shouldNotSummarize() {
        final String hash = "";
        final boolean isMutant = true;

        when(analysisDs.contains(hash)).thenReturn(Boolean.TRUE);

        final boolean b = service.computeDna(isMutant, hash);

        assertTrue(b);
        verify(analysisDs, times(1)).contains(hash);
        verify(analysisDs, never()).persistDna(isMutant, hash);
        verify(reportDs, never()).summarizeDna(isMutant);
    }

}
