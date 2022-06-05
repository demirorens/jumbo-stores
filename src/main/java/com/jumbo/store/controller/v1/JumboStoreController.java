package com.jumbo.store.controller.v1;

import com.jumbo.store.model.Store;
import com.jumbo.store.payload.StoreResponse;
import com.jumbo.store.service.JumboStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/store")
@Tag(name = "Store Api methods")
public class JumboStoreController {

    private final JumboStoreService jumboStoreService;
    private final ModelMapper modelMapper;

    @GetMapping("/near")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "The nearest Jumbo Stores to a given location",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<StoreResponse>> getNearestStoresToAGivenLocation(
            @RequestParam
            @Min(value = -180, message = "The min value for longitude is -180")
            @Max(value = 180, message = "The max value for longitude is 180")
                    double longitude,
            @RequestParam
            @Min(value = -90, message = "The min value for latitude is -90")
            @Max(value = 90, message = "The min value for latitude is -90")
                    double latitude,
            @RequestParam(defaultValue = "5")
                    int limit
    ) {
        List<Store> stores = jumboStoreService.findNearestStoresToAGivenLocation(longitude, latitude, limit);
        List<StoreResponse> storeResponses = Arrays.asList(modelMapper.map(stores, StoreResponse[].class));
        return new ResponseEntity<>(storeResponses, HttpStatus.OK);
    }


}
