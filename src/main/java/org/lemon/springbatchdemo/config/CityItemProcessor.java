package org.lemon.springbatchdemo.config;

import org.lemon.springbatchdemo.model.City;
import org.lemon.springbatchdemo.model.Location;
import org.springframework.batch.item.ItemProcessor;

import java.util.Collections;

public class CityItemProcessor  implements ItemProcessor<City, Location> {
    @Override
    public Location process(City city) {
        return Location.builder()
                .name(city.getName())
                .extendedData(Collections.singletonMap("mastercard.geographicId", city.getGeoId()))
                .build();
    }
}
