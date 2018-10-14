package br.com.mercadolivre.mutantidentifier.datastores;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class DnaAnalysisDatastore {

    private static final Logger LOG = LoggerFactory.getLogger(DnaAnalysisDatastore.class);

    @Autowired
    private Datastore datastore;

    private KeyFactory analyzedDnaFactory;

    @PostConstruct
    public void initializeKeyFactories() {
        analyzedDnaFactory = datastore.newKeyFactory().setKind("AnalyzedDna");
    }

    public boolean contains(String hash) {
        final Query<?> select = Query.newEntityQueryBuilder()
                .setKind("AnalyzedDna")
                .setFilter(StructuredQuery.PropertyFilter.eq("hash", hash))
                .build();

        final QueryResults<?> results = datastore.run(select);
        return results.hasNext();
    }

    public void persistDna(boolean isMutant, String hash) {
        final Key key = datastore.allocateId(analyzedDnaFactory.newKey());
        final Entity analysis = Entity.newBuilder(key)
                .set("createdAt", Timestamp.now())
                .set("isMutant", isMutant)
                .set("hash", hash)
                .build();

        datastore.put(analysis);
    }

}
