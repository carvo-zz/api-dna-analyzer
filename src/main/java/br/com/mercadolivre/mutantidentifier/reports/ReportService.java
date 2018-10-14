package br.com.mercadolivre.mutantidentifier.reports;

import br.com.mercadolivre.mutantidentifier.datastores.DnaAnalysisDatastore;
import br.com.mercadolivre.mutantidentifier.datastores.ReportDatastore;
import br.com.mercadolivre.mutantidentifier.model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private DnaAnalysisDatastore analysisDatastore;

    @Autowired
    private ReportDatastore reportDatastore;

    public Stats getStats() {
        return reportDatastore.getStats();
    }

    //    @Async
    public boolean computeDna(final boolean isMutant, final String hash) {
        if (!analysisDatastore.contains(hash)) {
            analysisDatastore.persistDna(isMutant, hash);
            reportDatastore.summarizeDna(isMutant);
        }

        return Boolean.TRUE;
    }
}
