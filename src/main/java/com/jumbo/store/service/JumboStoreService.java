package com.jumbo.store.service;

import com.jumbo.store.model.Store;

import java.util.Collection;
import java.util.List;

public interface JumboStoreService {

    List<Store> findNearestStoresToAGivenLocation(double lon, double lat, int limit);

    Collection<Store> insertAll(List<Store> stores);

    long getDocumentCount();
}
