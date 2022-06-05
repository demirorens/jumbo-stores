package com.jumbo.store.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private Integer status;
    private String message;
    private Instant timestamp;
    private List<String> exMessages;
}
