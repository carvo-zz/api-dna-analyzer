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
        final QueryResults<?> results = selectAnalyzedDna(hash);

        if (!results.hasNext()) {
            persistDna(isMutant, hash);
            summarizeDna(isMutant);
        }
    }

    private QueryResults<?> selectAnalyzedDna(String hash) {
        final Query<?> select = Query.newEntityQueryBuilder()
                .setKind("AnalyzedDna")
                .setFilter(StructuredQuery.PropertyFilter.eq("hash", hash))
                .build();

        return datastore.run(select);
    }

    private void persistDna(boolean isMutant, String hash) {
        final Key key = datastore.allocateId(analyzedDnaFactory.newKey());
        final Entity analysis = Entity.newBuilder(key)
                .set("createdAt", Timestamp.now())
                .set("isMutant", isMutant)
                .set("hash", hash)
                .build();

        datastore.put(analysis);
    }

    private void summarizeDna(boolean isMutant) {
        final String myIdValue = "uniqueDoc";
        final Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("AnalyzedDnaSummary")
                .setFilter(StructuredQuery.PropertyFilter.eq("myId", myIdValue))
                .build();

        final QueryResults<Entity> results = datastore.run(query);
        if (results.hasNext()) {
            LOG.info("hasNext Summary..");
            final Entity next = results.next();

            long countMutant = next.getLong("countMutant") + (isMutant ? 1 : 0);
            long countNotMutant = next.getLong("countNotMutant") + (!isMutant ? 1 : 0);

            final Entity summary = Entity.newBuilder(next.getKey())
                    .set("countMutant", countMutant)
                    .set("countNotMutant", countNotMutant)
                    .set("lastUpdate", Timestamp.now())
                    .set("myId", myIdValue)
                    .build();

            datastore.put(summary);
        }
    }
}
