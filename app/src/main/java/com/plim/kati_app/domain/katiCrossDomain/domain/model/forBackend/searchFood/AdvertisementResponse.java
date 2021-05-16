package com.plim.kati_app.domain.katiCrossDomain.domain.model.forBackend.searchFood;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdvertisementResponse {
    private final Long id;
    private final FoodResponse food;
}