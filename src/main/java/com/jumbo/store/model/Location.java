package com.jumbo.store.model;

import lombok.Data;

@Data
public class Location {
    private String type;
    private double[] coordinates;
}