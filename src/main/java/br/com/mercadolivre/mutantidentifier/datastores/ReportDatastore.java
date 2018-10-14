package br.com.mercadolivre.mutantidentifier.datastores;

import br.com.mercadolivre.mutantidentifier.model.Stats;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDatastore {

    private static final Logger LOG = LoggerFactory.getLogger(ReportDatastore.class);

    private static final String MY_ID_NAME = "myId";
    private static final String MY_ID_VALUE = "uniqueDoc";
    private static final String COUNT_MUTANT_NAME = "countMutant";
    private static final String COUNT_HUMAN_NAME = "countHuman";

    @Autowired
    private Datastore datastore;

    public Stats getStats() {
        final QueryResults<Entity> results = selectStats();

        Stats result = null;
        if (results.hasNext()) {
            final Entity next = results.next();
            result = new Stats(next.getLong(COUNT_MUTANT_NAME), next.getLong(COUNT_HUMAN_NAME));
        }

        return result;
    }

    public void summarizeDna(boolean isMutant) {
        final QueryResults<Entity> results = selectStats();

        if (results.hasNext()) {
            LOG.info("hasNext Summary..");
            final Entity next = results.next();

            long countMutant = next.getLong(COUNT_MUTANT_NAME) + (isMutant ? 1 : 0);
            long countHuman = next.getLong(COUNT_HUMAN_NAME) + (!isMutant ? 1 : 0);

            final Entity summary = Entity.newBuilder(next.getKey())
                    .set(COUNT_MUTANT_NAME, countMutant)
                    .set(COUNT_HUMAN_NAME, countHuman)
                    .set("lastUpdate", Timestamp.now())
                    .set(MY_ID_NAME, MY_ID_VALUE)
                    .build();

            datastore.put(summary);
        }
    }

    private QueryResults<Entity> selectStats() {
        final Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("AnalyzedDnaSummary")
                .setFilter(StructuredQuery.PropertyFilter.eq(MY_ID_NAME, MY_ID_VALUE))
                .build();
        return datastore.run(query);
    }
}
