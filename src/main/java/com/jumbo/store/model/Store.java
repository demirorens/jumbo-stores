package com.jumbo.store.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jumbo.store.json.deserializer.StoreDeserializer;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonDeserialize(using = StoreDeserializer.class)
@Data
@ToString
@Document("store")
public class Store {
    @Id
    private String id;
    private String city;
    private String postalCode;
    private String street;
    private String street2;
    private String street3;
    private String addressName;
    private String uuid;
    private String latitude;
    private String longitude;
    private String complexNumber;
    private boolean showWarningMessage;
    private String todayOpen;
    private String locationType;
    private boolean collectionPoint;
    private String sapStoreID;
    private String todayClose;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE, name = "location-2dsphere")
    private Location location;
}



