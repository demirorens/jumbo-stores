package com.jumbo.store.service.impl;

import com.jumbo.store.model.Store;
import com.jumbo.store.service.JumboStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Store service implementation. It queries the mongodb for store service requirements.
 */
@RequiredArgsConstructor
@Service
public class JumboStoreServiceImpl implements JumboStoreService {

    /**
     * Mongo template is used for query mongodb
     */
    private final MongoTemplate mongoTemplate;

    /**
     * Method that finds nearest stores to the given location
     * @param lon  - longitude parameter for given location
     * @param lat  - latitude parameter for given location
     * @param limit - parameter for limiting the number of close
     *              store result which start form closest to less close
     * @return  - method returns the list of Store objects.
     */
    @Override
    public List<Store> findNearestStoresToAGivenLocation(double lon, double lat, int limit) {
        Query query =
                new BasicQuery(
                        "{location:" +
                                "{ $near: " +
                                "{ $geometry: " +
                                "{ type: 'Point'," +
                                "coordinates: [" + lon + "," + lat + " ] " +
                                "}" +
                                "}" +
                                "}" +
                                "}").limit(limit);

        List<Store> stores = mongoTemplate.find(query, Store.class);
        return stores;
    }

    /**
     * Mothod that inserts collection of Store objects
     * @param stores
     * @return
     */
    @Override
    public Collection<Store> insertAll(List<Store> stores) {
        return mongoTemplate.insertAll(stores);
    }

    /**
     * Method that returns the document count on Store collection.
     * @return
     */
    @Override
    public long getDocumentCount() {
        return mongoTemplate.estimatedCount(Store.class);
    }


}
