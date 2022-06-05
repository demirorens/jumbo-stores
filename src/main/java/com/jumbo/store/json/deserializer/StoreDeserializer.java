package com.jumbo.store.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jumbo.store.model.Location;
import com.jumbo.store.model.Store;

import java.io.IOException;

public class StoreDeserializer extends StdDeserializer<Store> {

    public StoreDeserializer() {
        this(null);
    }

    public StoreDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Store deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Store store = new Store();
        if (node.get("city") != null)
            store.setCity(node.get("city").asText());
        if (node.get("postalCode") != null)
            store.setPostalCode(node.get("postalCode").asText());
        if (node.get("street") != null)
            store.setStreet(node.get("street").asText());
        if (node.get("street2") != null)
            store.setStreet2(node.get("street2").asText());
        if (node.get("street3") != null)
            store.setStreet3(node.get("street3").asText());
        if (node.get("addressName") != null)
            store.setAddressName(node.get("addressName").asText());
        if (node.get("uuid") != null)
            store.setUuid(node.get("uuid").asText());
        if (node.get("latitude") != null)
            store.setLatitude(node.get("latitude").asText());
        if (node.get("longitude") != null)
            store.setLongitude(node.get("longitude").asText());
        if (node.get("complexNumber") != null)
            store.setComplexNumber(node.get("complexNumber").asText());
        if (node.get("todayOpen") != null)
            store.setTodayOpen(node.get("todayOpen").asText());
        if (node.get("locationType") != null)
            store.setLocationType(node.get("locationType").asText());
        if (node.get("sapStoreID") != null)
            store.setSapStoreID(node.get("sapStoreID").asText());
        if (node.get("todayClose") != null)
            store.setTodayClose(node.get("todayClose").asText());
        if (node.get("showWarningMessage") != null)
            store.setShowWarningMessage(node.get("showWarningMessage").asBoolean());
        if (node.get("collectionPoint") != null)
            store.setCollectionPoint(node.get("collectionPoint").asBoolean());
        Location location = new Location();
        location.setType("Point");
        double[] coordinates = {Double.parseDouble(store.getLongitude()), Double.parseDouble(store.getLatitude())};
        location.setCoordinates(coordinates);
        store.setLocation(location);
        return store;
    }
}
