package com.jumbo.store.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Stores {

    private List<Store> stores;
    private Attributes attributes;
}

@Data
class Attributes{
    private Object longitude;
    private Object latitude;
}
