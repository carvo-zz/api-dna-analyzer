package br.com.mercadolivre.mutantidentifier.analysis;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class DnaDatastore {

    private static final Logger LOG = LoggerFactory.getLogger(DnaDatastore.class);

    @Autowired
    Datastore datastore;

    private KeyFactory analyzedDnaFactory;

    @PostConstruct
    public void initializeKeyFactories() {
        analyzedDnaFactory = datastore.newKeyFactory().setKind("AnalyzedDna");
    }

//    @Async
    public void computeDna(final boolean isMutant, final String hash) {
        final QueryResults<?> a = selectAnalyzedDna(hash);
        if (a.hasNext()) {
            persistDna(isMutant, hash);
            summarizeDna(isMutant);
        }
    }

    private QueryResults<?> selectAnalyzedDna(String hash) {
        LOG.info("#selectAnalyzedDna()");
        final Query<?> select = Query.newEntityQueryBuilder()
                .setKind("AnalyzedDna")
                .setFilter(StructuredQuery.PropertyFilter.eq("hash", hash))
                .build();

        return datastore.run(select);
    }

    private void persistDna(boolean isMutant, String hash) {
        LOG.info("persistDna");
        final Key key = datastore.allocateId(analyzedDnaFactory.newKey());
        final Entity analysis = Entity.newBuilder(key)
                .set("createdAt", Timestamp.now())
                .set("isMutant", isMutant)
                .set("hash", hash)
                .build();

        datastore.put(analysis);
    }

    private void summarizeDna(boolean isMutant) {
        LOG.info("#summarizeDna()");
        final Query<ProjectionEntity> query = Query.newProjectionEntityQueryBuilder()
                .setKind("AnalyzedDnaSummary")
                .setProjection("total", "countMutant", "countNotMutant")
                .build();

        final QueryResults<ProjectionEntity> results = datastore.run(query);
        if (results.hasNext()) {
            final ProjectionEntity next = results.next();

            long total = next.getLong("total") + 1;
            long countMutant = next.getLong("countMutant") + (isMutant ? 1 : 0);
            long countNotMutant = next.getLong("countNotMutant") + (!isMutant ? 1 : 0);

            final Entity summary = Entity.newBuilder(next.getKey())
                    .set("total", total)
                    .set("countMutant", countMutant)
                    .set("countNotMutant", countNotMutant)
                    .set("lastUpdate", Timestamp.now())
                    .build();

            datastore.put(summary);
        }
    }
}
